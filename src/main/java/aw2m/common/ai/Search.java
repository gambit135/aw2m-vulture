package aw2m.common.ai;

import aw2m.common.ai.eval.BranchEvalFunctions;
import aw2m.common.ai.eval.NodeEvalFunctions;
import aw2m.common.ai.model.Branch;
import aw2m.common.ai.model.Node;
import aw2m.common.core.GridCell;
import aw2m.common.core.Logic;
import aw2m.common.core.Unit;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.ListIterator;

/**
 *
 * @author Alejandro Téllez G. <java.util.fck@hotmail.com>
 * @date 14/10/2013 - 03:55:21 AM
 */
public class Search {

    public GridCell[][] map;
    /**
     * Keep a reference to the optimal value found so far, and during search
     */
    public Branch optimalBranch;
    /**
     * Stores all the branches, in aid for searching for the optimal branch.
     */
    public LinkedList<Branch> branches;
    public int clonesGenerated;
    public int nodesGenerated;

    /**
     * Creates Branch objets and stores them on this object's LinkedList<Branch>
     * branches. A Branch object is composed by a list of combat Node objects,
     * taking place in order as stored on such list. This method creates as many
     * branches as attacking units, contained in the list sent as parameter. *
     *
     * @param playerUnits The units that the AI player can move
     */
    public Branch createBranchFromOptimalNodes(LinkedList<Unit> playerUnits) {
        //DEBUG
        System.out.println("on create branch from optimal nodes");

        //Initialize "attackingList" with clones of playerUnits
        LinkedList<Unit> clonedAttackingUnits = new LinkedList<Unit>();
        for (Unit original : playerUnits) {
            clonedAttackingUnits.add(Unit.cloneUnit(original));
            this.clonesGenerated++;
            //DEBUG
            System.out.println("Clone created from: " + original.toString());
        }
        //List is ready to go. Start

        //List simulating the branch formed by optimal nodes of each attacking unit
        LinkedList<Node> branch = new LinkedList<Node>();
        int branchCurrentEvalValue = 0;

        //For each clone on the list
        //An optimal branch is created for each iteration of this FOR statement
        //And a new set of Enemy clones is requiered to operate
        for (Unit clonedAttacker : clonedAttackingUnits) {

            //Clone all enemy units that may get engaged in combat
            //Store them in a list. This will be the set of clones the whole branch will work with
            //When getting an enemy from a GridCell on a Deathzone set,
            //  get the equivalent (same but cloned) unit from the clone list
            //  Overwrite equals or hashCode from Unit class accordingly.

            //To create such enemy clone list: we must iterate through the entire map,
            //searching for enemies; cloning them, and adding them unto a List.

            //Since enemies can also be located via the coordinates on the map
            //and its own location object, objects can be stored on a Map 
            //Receiving as key a pair of coordinates: their coordinates on the game map
            //To avoid creating extra objects, the GridCell can be sent as key

            HashMap<GridCell, Unit> clonedEnemyMap = new HashMap<GridCell, Unit>();

            //Populate the Map with all the enemies
            //Run along the array without considering it's dimensions
            for (GridCell gridRow[] : map) {
                for (GridCell gridCell : gridRow) {
                    if (gridCell.unit != null) {

                        //If the team on the cell is different from the clone's
                        if (gridCell.unit.player.team != clonedAttacker.player.team) {
                            //Add it to the clone map
                            Unit clonedEnemy = Unit.cloneUnit(gridCell.unit);
                            this.clonesGenerated++;
                            clonedEnemyMap.put(gridCell, clonedEnemy);

                            //DEBUG
                            System.out.println("Cloned enemy: " + clonedEnemy.toString());
                        }
                    }
                }
            }

            //Keep a reference to the optimal value found so far, and during search
            Node optimalNode = null;

            //First, get deathZone
            LinkedHashSet<GridCell> deathZone = Logic.getDeathZone(map, clonedAttacker);
            //For each enemy on deathZone
            for (GridCell cell : deathZone) {
                if (cell.unit != null) {

                    //Get the equilvalent unit from the map of clones for this branch
                    //Using the gridCell as key
                    Unit clonedDefender = clonedEnemyMap.get(cell);
                    if (clonedDefender != null) {
                        //If the team on the cell is different from the clone's
                        if (clonedDefender.player.team != clonedAttacker.player.team) {
                            //if the clone unit can engage the enemy
                            if (Unit.getEngagingWeapon(clonedAttacker, clonedDefender) != Unit.CANT_ENGAGE) {
                                //Eval node to see if it becomes part of the branch
                                //Use M-Coefficient
                                short[] combatOutcome = Logic.combatSimulation(clonedAttacker, clonedDefender);
                                short nodeEvalValue = NodeEvalFunctions.M_balanceCoefficient(clonedAttacker, clonedDefender, combatOutcome);
                                this.nodesGenerated++;
                                
                                if (optimalNode == null) {
                                    //No other optimal candidate exists
                                    //Assign current value to optimal node
                                    optimalNode = new Node(clonedAttacker, clonedDefender, nodeEvalValue);
                                }
                                else {
                                    //Compare optimal vs new
                                    if (optimalNode.value < nodeEvalValue) {
                                        //Optimal is no longer THE optimal value
                                        //So store newfound as optimal
                                        optimalNode.attacker = clonedAttacker;
                                        optimalNode.defender = clonedDefender;
                                        optimalNode.value = nodeEvalValue;
                                    }
                                }
                            }
                        }
                    }
                }
            }
            //At this point in the process
            //Optimal may still be null
            //as a result of no available enemies to attack
            if (optimalNode != null) {
                branch.add(optimalNode);

                //After the optimal node is found
                //Units MUST engage in REAL COMBAT 
                //To modify clones by reference
                //Specifically, an enemy: To update its "weakened" new status
                //And so, being able to progress the branch evolution as a natural process.

                //Engage in REAL combat
                Logic.combat(optimalNode.attacker, optimalNode.defender, true);
                //Units modified by reference

                //Also, consider when an enemy unit is destroyed, 
                //it must be removed from the clonedEnemy Map
                //It can be achieved by mapping its GridCell key to null

                //If the defender was destroyed
                if (optimalNode.defender.currentHP <= 0) {
                    //Remove from HashMap. Map location to null
                    clonedEnemyMap.put(optimalNode.defender.location, null);
                }
            }
        }

        //At the end of this FOR statement,
        //the branch formed by all optimal nodes is complete
        //So, return it if its not empty
        if (!branch.isEmpty()) {
            Branch b = new Branch(branch);
            //Eval branch
            b.evalValue = BranchEvalFunctions.evalBranch(b);

            //Add to branch list
            this.branches.add(b);

            //DEBUG
            //Print branch
            System.out.println("Branch: ");
            System.out.println(b.toString());

            return b;
        }
        return null;
        //ALL OF THIS GOES IN OTHER METHOD
        //If the branch is not empty
        //  Eval the branch
        //  To eval a branch: 
        //      You must send to a branch evaluation function:
        //          1. The original list of units
        //          2. The new, modified by reference and "combated" list of clone units
        //      AND:
        //          Store the amount of damage dealt * own unit cost
        //          And the difference of that damage against enemy vs previous enemy HP
        //      OR
        //          Simply add the eval values of each of its nodes

        //If optimalBranch is null
        //  Assign to current branch
        //else
        //  Compare current against optimal, and choose an optimal branch

    }

    public Branch findOptimalBranchFromBranches(LinkedList<Branch> branches) {
        //First, create a branch from each attacking unit

        for (Branch b : branches) {
            if (this.optimalBranch == null) {
                this.optimalBranch = b;
            }
            else {
                if (b.evalValue > optimalBranch.evalValue) {
                    this.optimalBranch = b;
                }
            }
        }
        return optimalBranch;
    }

    public Search(GridCell[][] map) {
        this.map = map;
        this.optimalBranch = null;
        this.branches = new LinkedList<Branch>();

        this.clonesGenerated = 0;
        this.nodesGenerated = 0;

        //DEBUG
        System.out.println("Search CONSTRUCTOR");
    }

    public void createAllPossibleBranches(LinkedList<Unit> attackingUnits) {
        //For each attacking unit, create a branch:
        //And add it to this object's LinkedList<Branch> branches;

        //This for is supposed to give all permutations possible for all starting 
        //or ROOT units of a Branch
        //Ergo, we must iterate through the attacking unit list, 
        //to get all posible combinations of starting ROOT units.

        //One solution could be storing them in a doubly linked list
        //Set u as the starting element of the list.
        //All elements after u come next to him in the list
        //All elements before u come after that
        if (attackingUnits.size() > 1) {
            for (Unit u : attackingUnits) {
                //Create "permutation" of units
                LinkedList<Unit> units = new LinkedList<Unit>();
                units.add(u);

                //Get position of u
                int index = attackingUnits.indexOf(u);

                //Validate if index+1 is valid
                if (index + 1 < attackingUnits.size()) {
                    //Get an iterator
                    ListIterator<Unit> iterator = attackingUnits.listIterator(index + 1);
                    //Add all elements after u
                    while (iterator.hasNext()) {
                        units.add(iterator.next());
                    }
                    //Add elements before u
                    iterator = attackingUnits.listIterator();
                    int index2 = 0;
                    while (iterator.hasNext()) {
                        Unit temp = iterator.next();
                        if (index2 < index) {
                            units.add(temp);
                            index2++;
                        }
                        else {
                            break;
                        }
                    }
                    //The "permutation" has been generated.
                    //Use it to generate its branch from optimal nodes
                    //Call to createBranchFromOptimalNodes
                    this.createBranchFromOptimalNodes(units);
                }
            }
        }
        else {
            //Only one element exists on the attackingUnits list
            //The only permutation is that element
            //Get branch from this only permutation

            //Also, this branch is the optimal branch
            this.createAllPossibleBranches(attackingUnits);
        }

    }
}
