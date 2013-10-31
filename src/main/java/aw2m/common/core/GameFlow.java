package aw2m.common.core;

import aw2m.common.ai.Search;
import aw2m.common.ai.eval.BranchEvalFunctions;
import aw2m.common.ai.eval.NodeEvalFunctions;
import aw2m.common.ai.model.Branch;
import aw2m.common.serialize.Deserialize;
import aw2m.common.serialize.MakePostRequest;
import aw2m.common.serialize.Serialize;
import aw2m.common.stats.Statistic;
import aw2m.remote.creator.maploader.MapCatalog;
import aw2m.remote.creator.maploader.MapLoader;
import java.util.Date;
import java.util.LinkedList;

/**
 * The main method of this class will host the mainGameInstance flow, with
 * players taking turns and actions within them.
 *
 * @author Alejandro Téllez G. <java.util.fck@hotmail.com>
 * @date 23/07/2013 - 08:21:44 PM
 */
public class GameFlow {

    static GameInstance mainGameInstance;

    /**
     *
     *
     * @param args
     */
    public static void main(String args[]) {
        //Game Started
        System.out.println("Gameflow starated");

        //First, an instance of GameInstance must be created
        //Specify which map to play
        System.out.println("Creating GameInstance");
        mainGameInstance = new GameInstance(MapCatalog.SPANN_ISLAND);
        //Players are initialized
        //A map must be loaded unto that mainGameInstance
        System.out.println("Loading map");

        MapLoader.loadMapFromCatalog(mainGameInstance, mainGameInstance.mapChosen);
        //game.map = MapLoader.map;

        //DEBUG
        System.out.println("Number of players on map: " + MapCatalog.getNoOfPlayers(mainGameInstance.mapChosen));


        //BEGGINING OF TESTS
        /*
         * Data to be serialized:
         * + Size X and Y of the map OR the predefined map chosen
         * + Players, units, terrain
         */

        //Testing Serialize
        Serialize s = new Serialize();

        //Testing serialize players
        System.out.println("Serialized players");
        System.out.println(s.serializePlayers(mainGameInstance));

        //Testing serialize terrain
        System.out.println("Serialized terrain");
        System.out.println(
                s.serializeTerrain(mainGameInstance));

        //Testing serialize properties
        System.out.println("Serialized properties");
        System.out.println(s.serializeProperties(mainGameInstance));

        //Testing serialize units

        //One unit: Rockets
        Unit u1 = new Unit();
        u1.location = mainGameInstance.map[9][9];
        u1.unitType = Unit.BATTLESHIP;
        u1.player = mainGameInstance.players[1];
        u1.currentHP = 10;
        u1.currentFuel = Unit.getTotalFuel(u1.unitType);
        u1.currentAmmo = 4;
        mainGameInstance.players[1].units.add(u1);

        //Another unit: An Infantry
        Unit u2 = new Unit();
        u2.location = mainGameInstance.map[8][4];
        u2.unitType = Unit.INFANTRY;
        u2.player = mainGameInstance.players[2];
        u2.currentHP = 10;
        u2.currentFuel = Unit.getTotalFuel(u2.unitType);
        u2.currentAmmo = Unit.getTotalAmmo(u2.unitType);
        mainGameInstance.players[2].units.add(u2);

        //Making player1 GRIT, so Indirects shall deal more damage
        /*
         u1.player.currentCO.id = CO.GRIT;

         u1.player.hasNoPower = false;
         u1.player.hasPowerOn = false;
         u1.player.hasSuperPowerOn = true;
         * */
        System.out.println("Combat Tests");
        //Logic.combat(u1, u2);
        System.out.println("R-coeff for combat: " + NodeEvalFunctions.R_balanceCoefficient(u1, u2, Logic.combatSimulation(u1, u2)));

        System.out.println("Serialized units");
        System.out.println(s.serializeUnits(mainGameInstance));

        //Testing deserialize


        Deserialize d = new Deserialize();

        //Testing deserealize players
        System.out.println("Deseralized players:");
        Player[] players;
        players = d.deserializePlayers(s.serializePlayers(mainGameInstance));

        //Testing deserealize terrain
        System.out.println("Deseralized terrain:");
        GridCell[][] map = d.deserializeTerrain(
                s.serializeTerrain(mainGameInstance),
                (byte) mainGameInstance.map.length,
                (byte) mainGameInstance.map[0].length);

        //Testing deserealize properties
        System.out.println("Deseralized properties:");

        d.deserializeProperties(s.serializeProperties(mainGameInstance),
                                d.deserializeTerrain(
                s.serializeTerrain(mainGameInstance),
                MapCatalog.getXsize(mainGameInstance.mapChosen),
                MapCatalog.getYsize(mainGameInstance.mapChosen)),
                                d.deserializePlayers(
                s.serializePlayers(mainGameInstance)));

        d.deserializeProperties(s.serializeProperties(mainGameInstance), map, players);
        //Testing deserealize units
        System.out.println("Deserealized units:");

        d.deserializeUnits(s.serializeUnits(mainGameInstance),
                           d.deserializeTerrain(
                s.serializeTerrain(mainGameInstance),
                MapCatalog.getXsize(mainGameInstance.mapChosen),
                MapCatalog.getYsize(mainGameInstance.mapChosen)),
                           d.deserializePlayers(
                s.serializePlayers(mainGameInstance)));

        d.deserializeUnits(s.serializeUnits(mainGameInstance), map, players);

        //To proof the point: first, create a new instance of a game, with reconstructed info.

        //Creating another instance of a game
        GameInstance rebuiltGame = new GameInstance(players, map);

        //Then, serialize the info contained in the rebuilt game.

        //Testing serialize players
        System.out.println("Serialized REBUILT players");
        System.out.println(s.serializePlayers(rebuiltGame));

        //Testing serialize terrain
        System.out.println("Serialized REBUILT terrain");
        System.out.println(s.serializeTerrain(rebuiltGame));

        //Testing serialize properties
        System.out.println("Serialized REBUILT properties");
        System.out.println(s.serializeProperties(rebuiltGame));

        //Testing serialize units
        System.out.println("Serialized REBUILT units");
        System.out.println(s.serializeUnits(rebuiltGame));

        //A reconstructed game should also know who's turn it is to move.

        System.out.println("TEST: Getting sizeX and sizeY only with GridCell[][]");
        System.out.println("Size of map.length " + mainGameInstance.map.length);
        System.out.println("Size of map[0].length " + mainGameInstance.map[0].length);

        System.out.println("Size X of SPANN ISLAND is: " + MapCatalog.getXsize(mainGameInstance.mapChosen));
        System.out.println("Size Y of SPANN ISLAND is: " + MapCatalog.getYsize(mainGameInstance.mapChosen));

        //Testing booleans to String
        boolean bulean = false;
        System.out.println("Boolean value to String: " + bulean);


        //First, deconstruct and reconstruct the game, calling the app on the cloud


        //DEBUG
        /* Comment the next line to switch between 
         * not predefined and predefined modes of the map
         * To test two different types of reconstruction on the server side.
         */
        //mainGameInstance.mapChosen = MapCatalog.UNDEFINED;


        //LAST CHANGE!:
        //Create a new object for executing the HTTP POST request
        MakePostRequest request = new MakePostRequest(mainGameInstance);

        //Calculate time before executing request and after receiving a response.
        Date timeBeforeRequest = new Date();
        System.out.println("Time on client before request:" + timeBeforeRequest);

        String result = request.executePostRequest();

        //Calculate time before executing request and after receiving a response.
        Date timeAfterRequest = new Date();
        System.out.println("Time on client after request:" + timeAfterRequest);

        System.out.println("THIS SHOULD PRINT THE RESPONSE BODY");
        System.out.println(result);





        //END OF TESTS
        //Then, design the API for the UI

        //A program is a flow...       
        System.out.println("Is player " + mainGameInstance.currentPlayer.id + "'s turn to move");


        //DEBUG
        //Combat Tests, Mk II
        //Battle between 4 units in one turn

        //Units for Player 1:
        //One unit: BCopter
        Unit BCptrP1 = new Unit();
        BCptrP1.location = mainGameInstance.map[1][1];
        BCptrP1.unitType = Unit.B_COPTER;
        BCptrP1.player = mainGameInstance.players[1];
        BCptrP1.currentHP = 10;
        BCptrP1.currentFuel = Unit.getTotalFuel(BCptrP1.unitType);
        BCptrP1.currentAmmo = Unit.getTotalAmmo(BCptrP1.unitType);
        mainGameInstance.players[1].units.add(BCptrP1);
        BCptrP1.location.unit = BCptrP1;

        //Another unit: A tank
        Unit TankP1 = new Unit();
        TankP1.location = mainGameInstance.map[1][2];
        TankP1.unitType = Unit.TANK;
        TankP1.player = mainGameInstance.players[1];
        TankP1.currentHP = 10;
        TankP1.currentFuel = Unit.getTotalFuel(TankP1.unitType);
        TankP1.currentAmmo = Unit.getTotalAmmo(TankP1.unitType);
        mainGameInstance.players[1].units.add(TankP1);
        TankP1.location.unit = TankP1;

        //Team 1
        mainGameInstance.players[1].team = 1;

        //CO is Andy
        TankP1.player.currentCO.id = CO.ANDY;


        //Units for Player 2:
        //One unit: A mech
        Unit MechP2 = new Unit();
        MechP2.location = mainGameInstance.map[1][3];
        MechP2.unitType = Unit.MECH;
        MechP2.player = mainGameInstance.players[2];
        MechP2.currentHP = 10;
        MechP2.currentFuel = Unit.getTotalFuel(MechP2.unitType);
        MechP2.currentAmmo = Unit.getTotalAmmo(MechP2.unitType);
        mainGameInstance.players[2].units.add(MechP2);
        MechP2.location.unit = MechP2;

        //Another unit: An A-Air
        Unit AAirP2 = new Unit();
        AAirP2.location = mainGameInstance.map[1][4];
        AAirP2.unitType = Unit.ANTI_AIR;
        AAirP2.player = mainGameInstance.players[2];
        AAirP2.currentHP = 10;
        AAirP2.currentFuel = Unit.getTotalFuel(AAirP2.unitType);
        AAirP2.currentAmmo = Unit.getTotalAmmo(AAirP2.unitType);
        mainGameInstance.players[2].units.add(AAirP2);
        AAirP2.location.unit = AAirP2;

        mainGameInstance.players[2].team = 2;

        //Testing 1st of 4 branches of search tree
        //Manually


        //Combat

        //1st, evaluate node
        //GameFlow.batteryTest(TankP1, MechP2);

        //Evaluate 2nd generated node
        //GameFlow.batteryTest(BCptrP1, AAirP2);

        //Search for optimal branch
        LinkedList<Unit> attackingUnits = new LinkedList<Unit>();
        attackingUnits.add(TankP1);
        attackingUnits.add(BCptrP1);

        Search search = new Search(mainGameInstance.map);

        Branch branch = search.createBranchFromOptimalNodes(attackingUnits);
        branch.evalValue = BranchEvalFunctions.evalBranch(branch);
        System.out.println("Total branch eval value: " + branch.evalValue);
        System.out.println("\n Total branches on Branch list: " + search.branches.size());
        System.out.println("\nOptimal branch:" + search.findOptimalBranchFromBranches(search.branches).toString());

        //Count properties
        byte properties = 0;
        byte x = 0, y = 0;
        //Iterate throgh array without considering dimensions
        for (GridCell gridRow[] : mainGameInstance.map) {
            for (GridCell gridCell : gridRow) {
                if (gridCell.isProperty) {
                    properties++;
                }
                x++;
            }
            y++;
        }
        System.out.println("\nProperties:" + properties + "\n");


        //Start estimations
        //Statistic.estimate(ownUnits, enemyUnits, sizeX, sizeY, properties);
        Statistic.estimate(50, 0, x, y, 48);
        Statistic.estimate(1, 1, x, y, 48);
        Statistic.estimate(2, 2, x, y, 48);
        Statistic.estimate(40, 1, x, y, 48);
        Statistic.estimate(1, 40, x, y, 48);
        Statistic.estimate(50, 50, x, y, 48);
        Statistic.estimate(100, 100, x, y, 48);
        Statistic.estimate(500, 500, x, y, 48);
        
        System.out.println("\n\nNumber of clones generated: " + search.clonesGenerated);
        System.out.println("\n\nNumber of nodes generated (all): " + search.clonesGenerated);
        System.out.println("\n\nNumber of nodes on optimal Branch: " + search.optimalBranch.branch.size());

    }

    /**
     * A battery of tests for a specified combat.
     *
     * @param attacker
     * @param defender
     */
    static void batteryTest(Unit attacker, Unit defender) {

        if (Unit.getEngagingWeapon(attacker, defender) != Unit.CANT_ENGAGE) {
            //1st, evaluate node           
            System.out.println("M-coeff for combat: " + NodeEvalFunctions.M_balanceCoefficient(attacker, defender, Logic.combatSimulation(attacker, defender)) + "\n");

            Logic.combat(attacker, defender, false);
            //DEBUG: 1st Combat Outcome
            System.out.println("\nCOMBAT OUTCOME:");
            System.out.println(Unit.toString(attacker));
            System.out.println(Unit.toString(defender));
            System.out.println("");
        }
        else {
            System.out.println("\n" + Unit.toString(attacker) + " can't engage " + Unit.toString(defender));
        }

    }
}
