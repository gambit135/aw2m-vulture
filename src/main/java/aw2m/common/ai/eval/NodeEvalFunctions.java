package aw2m.common.ai.eval;

import aw2m.common.core.Unit;

/**
 * Non any of these functions modifies the Unit objects sent to them as
 * parameteres.
 *
 * @author Alejandro Téllez G. <java.util.fck@hotmail.com>
 * @date 10/10/2013 - 03:20:39 AM
 */
public class NodeEvalFunctions {

    /*
     * NOTE: CONSIDER EVAL FUNCTION FOR BRANCHES MK II!!!!
     */
    public static short D_balanceCoefficient(Unit attacker, Unit defender, short[] combatOutcome) {
        //(ownHP - enemyHP) * (ownCost - enemyCost)
        //DEBUG
        System.out.println("Previous OWN HP: " + attacker.currentHP);
        System.out.println("OWN HP: " + combatOutcome[1]);
        System.out.println("Previous ENEMY HP: " + defender.currentHP);
        System.out.println("ENEMY HP: " + combatOutcome[0]);
        System.out.println("OWN COST: " + Unit.getUnitCostFactor(attacker.unitType));
        System.out.println("ENEMY COST: " + Unit.getUnitCostFactor(defender.unitType));
        
        return (short) ((combatOutcome[1] - combatOutcome[0])
                * (Unit.getUnitCostFactor(attacker.unitType) - Unit.getUnitCostFactor(defender.unitType)));
        
    }
    
    public static short C_balanceCoefficient(Unit attacker, Unit defender, short[] combatOutcome) {
        //(ownHP - enemyHP) * (enemyCost)
        //DEBUG
        System.out.println("OWN HP: " + combatOutcome[1]);
        System.out.println("ENEMY HP: " + combatOutcome[0]);
        System.out.println("OWN COST: " + Unit.getUnitCostFactor(attacker.unitType));
        System.out.println("ENEMY COST: " + Unit.getUnitCostFactor(defender.unitType));
        
        return (short) (combatOutcome[1] - combatOutcome[0] * (Unit.getUnitCost(defender.unitType) / 1000));
    }
    
    public static short R_balanceCoefficient(Unit attacker, Unit defender, short[] combatOutcome) {
        //Real damage on enemy * enemy cost
        //DEBUG
        System.out.println("OWN HP: " + combatOutcome[1]);
        System.out.println("ENEMY HP: " + combatOutcome[0]);
        System.out.println("OWN COST: " + Unit.getUnitCost(attacker.unitType) / 1000);
        System.out.println("ENEMY COST: " + Unit.getUnitCost(defender.unitType) / 1000);
        
        return (short) (combatOutcome[2] * (Unit.getUnitCost(defender.unitType) / 1000));
    }
    
    public static short M_balanceCoefficient(Unit attacker, Unit defender, short[] combatOutcome) {
        //(Total damage dealt to defender * enemy cost factor) - (Total damage received per own cost factor)
        //+ (Remaining own HP * own cost) - (Remaining enemy HP * enemy cost)
        //**
        // = (Own Cost) * (Remaining own HP - total damage received)
        // + (Enemy Cost) * (Total damage dealt to defender - remaining enemy HP)

        
        byte damageOnAttacker = (byte) (attacker.currentHP - combatOutcome[1]);
        byte damageOnDefender = (byte) (defender.currentHP - combatOutcome[0]);

        //DEBUG
//        System.out.println("OWN HP: " + combatOutcome[1]);
//        System.out.println("ENEMY HP: " + combatOutcome[0]);
//        System.out.println("OWN COST: " + Unit.getUnitCostFactor(attacker.unitType));
//        System.out.println("ENEMY COST: " + Unit.getUnitCostFactor(defender.unitType));
        
        return (short) (damageOnDefender * Unit.getUnitCostFactor(defender.unitType) * (((Unit.getUnitCostFactor(attacker.unitType)) * (combatOutcome[1] - damageOnAttacker))
                + ((Unit.getUnitCostFactor(defender.unitType)) * (damageOnDefender - combatOutcome[0]))));
    }
}
