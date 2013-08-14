package aw2m.common.core;

import java.util.Set;

/**
 * @author Alejandro Téllez G <java.util.fck@hotmail.com>
 */
public class Unit {

    static {
        System.out.println("Unit class loaded. Lots of bytes here.");
    }
    //Classifiers
    /**
     * unitSuperClass: Land, Air, Sea
     */
    public static final byte LAND_CLASS = 0;
    public static final byte AIR_CLASS = 1;
    public static final byte SEA_CLASS = 2;
    /**
     * unitClass: Infntry, Veh, Cptr, Plane, Ship, Sub
     */
    public static final byte INFTRY_CLASS = 0;
    public static final byte VEH_CLASS = 1;
    public static final byte CPTR_CLASS = 2;
    public static final byte PLANE_CLASS = 3;
    public static final byte SHIP_CLASS = 4;
    public static final byte SUB_CLASS = 5;
    //When attacking buildings, also, the class of oozium, as it is a land unit
    public static final byte BUILDING_CLASS = 6;
    /**
     * movementType: Inftry, Mech, Tires, Treads, Air, Sea, Coast
     */
    public static final byte INFANTRY_MOV = 0;
    public static final byte MECH_MOV = 1;
    public static final byte TIRES_MOV = 2;
    public static final byte TREADS_MOV = 3;
    public static final byte AIR_MOV = 4;
    public static final byte SEA_MOV = 5;
    public static final byte COAST_MOV = 6;
    /**
     * The number of different units available on the game. Used for shifting
     * between AW, AW2 and AWDS modes. is analogous to the max number in
     * unitType
     *
     */
    static byte totalUnits;
    /**
     * Types of units, as represented by unitType
     */
    public static final byte INFANTRY = 0;
    public static final byte MECH = 1;
    public static final byte RECON = 2;
    public static final byte TANK = 3;
    public static final byte MD_TANK = 4;
    public static final byte NEOTANK = 5;
    public static final byte MEGATANK = 6;
    public static final byte APC = 7;
    public static final byte ARTILLERY = 8;
    public static final byte ROCKETS = 9;
    public static final byte ANTI_AIR = 10;
    public static final byte MISSILES = 11;
    public static final byte PIPERUNNER = 12;
    public static final byte FIGHTER = 13;
    public static final byte BOMBER = 14;
    public static final byte B_COPTER = 15;
    public static final byte T_COPTER = 16;
    public static final byte STEALTH = 17;
    public static final byte BLACK_BOMB = 18;
    public static final byte BATTLESHIP = 19;
    public static final byte CRUISER = 20;
    public static final byte LANDER = 21;
    public static final byte SUB = 22;
    public static final byte BLACK_BOAT = 23;
    public static final byte CARRIER = 24;
    public static final byte BUILDING = 25;
    public static final byte OOZIUM = 26;
    //Data relative to each individual unit
    /**
     * unitType: One of the 18 units available (in AW2)
     */
    public byte unitType;
    /**
     * Current currentHP?
     */
    public byte currentHP;
    /**
     * Player who controls this unit
     */
    public Player player;
    /**
     * Current fuel the unit carries. When a unit is created or replenished,
     * it's current fuel shall be equal to it's total fuel
     */
    public byte currentFuel;
    /**
     * Current ammo the unit carries on it's primary weapon (secondary weapons
     * don't use ammo) When a unit is created or replenished, it's current ammo
     * shall be equal to it's total ammo
     */
    public byte currentAmmo;
    /**
     * If the unit has yet moved on the current turn
     */
    boolean hasMoved;
    /**
     * Location of the unit along the grid
     */
    public GridCell location;
    //Constructors

    /**
     * Used for reconstruction via deserializing.
     *
     */
    public Unit() {
    }

    /**
     * Builds a unit with no primary Weapon e.g., Infantry, Recon, APC, Lander,
     * T Copter
     *
     * @param unitType
     * @param currentHP
     * @param player
     * @param currentFuel
     * @param hasMoved
     * @param x
     * @param y
     */
    public Unit(byte unitType, byte HP, Player player, byte currentFuel, boolean hasMoved, GridCell location) {
        this.unitType = unitType;
        this.currentHP = HP;
        this.player = player;
        this.currentFuel = currentFuel;
        this.hasMoved = hasMoved;
        this.location = location;
    }

    /**
     * Builds a unit with Primary Weapon e.g., Mech, Tank, Battleship, etc
     *
     * @param unitType
     * @param currentHP
     * @param player
     * @param currentFuel
     * @param currentAmmo
     * @param hasMoved
     * @param location
     */
    public Unit(byte unitType, byte HP, Player player, byte currentFuel, byte currentAmmo, boolean hasMoved, GridCell location) {
        this(unitType, HP, player, currentFuel, hasMoved, location);
        this.currentAmmo = currentAmmo;
    }

    public void onSelect() {
        //You can't select a unit to issue orders if it has moved already
        if (this.hasMoved) {
            //pop out the game menu
        }
        else { //Issuing orders to this unit
            //The unit is ready to move
            //Calculate movement radius
            //When the destination has reached
            //if (isDirect) then validate wether an enemy is near to attack
            //if there's an enemy near
            //Search on four adyacent GridCells for enemy units
            //that this unit can Attack (determine with
            //getDamageByChart not returning -1)
            //Create a hashSet with locations on enemy units
            //if hashSet is empty, then do not return an option
            //else - offer FIRE
            //if FIRE is selected
            // Iterate by user-interaction on hashSet of enemy units,
            //showing on each time the damage done
            //if (isIndirect)
            //SI EL ORIGEN ES IGUAL AL DESTINO (es decir, nunca se movió)
            //(and if there is enough ammo) (else-return CAN'T_ATTACK)
            //then calculate deathZone
            //Iterate on deathZone, searching for enemy units that this
            //unit can attack (determine with getDamageByChart
            //not returning -1)
            //Create a hashSet with locations on enemy units
            // Iterate by user-interaction on hashSet of enemy units,
            //showing on each time the damage done
            //SI SE MOVIO, NO OFRECER NADA, SOLO WAIT
            //offer wait by default on popout menu, along with the other options
        }

    }

    /*
     * Static methods, used to calculate generic unit information
     */
    /**
     * Returns the unit's class, depending on the parameter Inftry, Veh, Plane,
     * Cptr, Ship, Sub (pending for buildings)
     *
     * @param unitType the type of unit
     * @return the unitClass
     */
    public static byte getUnitClass(byte unitType) {
        //Inftry class
        if (unitType == Unit.INFANTRY || unitType == Unit.MECH) {
            return Unit.INFTRY_CLASS;
        }
        //Vehicle class
        if (unitType >= Unit.RECON && unitType <= Unit.PIPERUNNER) {
            return Unit.VEH_CLASS;
        }
        switch (unitType) {
            //Plane Class
            case Unit.FIGHTER:
            case Unit.BOMBER:
            case Unit.STEALTH:
            case Unit.BLACK_BOMB:
                return Unit.PLANE_CLASS;
            //Copter Class
            case Unit.T_COPTER:
            case Unit.B_COPTER:
                return Unit.CPTR_CLASS;
            //Ship Class
            case Unit.BATTLESHIP:
            case Unit.CRUISER:
            case Unit.LANDER:
            case Unit.BLACK_BOAT:
            case Unit.CARRIER:
                return Unit.SHIP_CLASS;
            //Sub Class
            case Unit.SUB:
                return Unit.SUB_CLASS;
            case Unit.OOZIUM:
            case Unit.BUILDING:
                return Unit.BUILDING_CLASS;
            default:    //Oozium, or building
                return -1;
        }
    }

    /**
     * Returns the unit superClass, as in land, air, or sea (for repair
     * purposes, maybe also for the Carrier loading).
     *
     * @param unitType the type of unit
     * @return the unit superClass
     */
    public static byte getUnitSuperClass(byte unitType) {
        if (unitType >= Unit.INFANTRY && unitType <= Unit.PIPERUNNER) {
            return Unit.LAND_CLASS;
        }
        if (unitType >= Unit.FIGHTER && unitType <= Unit.BLACK_BOMB) {
            return Unit.AIR_CLASS;
        }
        if (unitType >= Unit.BATTLESHIP && unitType <= Unit.CARRIER) {
            return Unit.SEA_CLASS;
        }
        //Also applicable (but not yet) for buildings
        if (unitType == Unit.OOZIUM || unitType == Unit.BUILDING) {
            return Unit.LAND_CLASS;
        }
        return -1;
    }

    /**
     * Returns true if an attacking unit can engage in combat with a defender
     * unit, regardless of which weapon is used by the attacker; also ignoring
     * if the attacker can fight back. An example can be an A-Air enganging a
     * Fighter. The fighter cannot engage ground units.
     *
     * @param attacker
     * @param defender
     * @return
     */
    public static boolean canEngage(Unit attacker, Unit defender) {
        return false;
    }

    /**
     * Return true if a unit can attack another with it's primary weapon, based
     * on the unit class restrictions imposed by game's logic. E.g., a T Copter
     * can attach Cptr clas, with Hydra missiles.
     *
     * @param atacker
     * @param defender The unit which class will be determiend, in order to know
     *                 if it can be attacked by the attacker's primary weapon.
     * @return
     */
    public static boolean canAttackUnitClassWithPrimaryWeapon(Unit attacker, Unit defender) {
        //Switch
        switch (attacker.unitType) {
            //Infantry only uses secondary weapon
            case Unit.INFANTRY:
                return false;
            case Unit.MECH:
                switch (Unit.getUnitClass(defender.unitType)) {
                    case Unit.VEH_CLASS:
                        return true;
                }
                return false;
            //Recon only uses secondary weapon
            case Unit.RECON:
                return false;
            case Unit.TANK:
            case Unit.MD_TANK:
            case Unit.NEOTANK:
            case Unit.MEGATANK:
                switch (Unit.getUnitClass(defender.unitType)) {
                    case Unit.VEH_CLASS:
                    case Unit.SUB_CLASS:
                    case Unit.SHIP_CLASS:
                        return true;
                }
                return false;
            case Unit.APC:
                //APC has no guns
                return false;
            case Unit.ARTILLERY:
                switch (Unit.getUnitClass(defender.unitType)) {
                    case Unit.INFTRY_CLASS:
                    case Unit.VEH_CLASS:
                    case Unit.SHIP_CLASS:
                    case Unit.SUB_CLASS:
                        return true;
                }
                return false;
            case Unit.ROCKETS:
                switch (Unit.getUnitClass(defender.unitType)) {
                    case Unit.INFTRY_CLASS:
                    case Unit.VEH_CLASS:
                    case Unit.SHIP_CLASS:
                    case Unit.SUB_CLASS:
                        return true;
                }
                return false;
            case Unit.ANTI_AIR:
                switch (Unit.getUnitClass(defender.unitType)) {
                    case Unit.INFTRY_CLASS:
                    case Unit.VEH_CLASS:
                    case Unit.CPTR_CLASS:
                    case Unit.PLANE_CLASS:
                        return true;
                }
                return false;
            case Unit.MISSILES:
                switch (Unit.getUnitClass(defender.unitType)) {
                    case Unit.CPTR_CLASS:
                    case Unit.PLANE_CLASS:
                        return true;
                }
                return false;
            case Unit.PIPERUNNER:
                return true;
            case Unit.FIGHTER:
                switch (Unit.getUnitClass(defender.unitType)) {
                    case Unit.CPTR_CLASS:
                    case Unit.PLANE_CLASS:
                        return true;
                }
                return false;
            case Unit.BOMBER:
                switch (Unit.getUnitClass(defender.unitType)) {
                    case Unit.INFTRY_CLASS:
                    case Unit.VEH_CLASS:
                    case Unit.SHIP_CLASS:
                    case Unit.SUB_CLASS:
                        return true;
                }
                return false;
            case Unit.B_COPTER:
                switch (Unit.getUnitClass(defender.unitType)) {
                    case Unit.VEH_CLASS:
                    case Unit.SHIP_CLASS:
                    case Unit.SUB_CLASS:
                        return true;
                }
                return false;
            case Unit.T_COPTER:
                return false;
            case Unit.STEALTH:

                break;
            case Unit.BLACK_BOMB:

                break;
            case Unit.BATTLESHIP:

                break;
            case Unit.CRUISER:

                break;
            case Unit.LANDER:

                break;
            case Unit.SUB:

                break;
            case Unit.BLACK_BOAT:

                break;
            case Unit.CARRIER:

                break;
            case Unit.OOZIUM:

                break;
        }
        return false;
    }

    /**
     * Returns true if the specified unitType is a transport (used for bonus
     * movements, but not to know if someone can carry [Cruisers and Carriers
     * are not transports])
     *
     * @param unitType the unit type
     * @return true if the unitType referes to a transport; false otherwise
     */
    public static boolean isTransport(byte unitType) {
        switch (unitType) {
            case Unit.APC:
            case Unit.T_COPTER:
            case Unit.LANDER:
            case Unit.BLACK_BOAT:
                return true;
        }
        return false;
    }

    public static boolean isDirect(byte unitType) {
        switch (unitType) {
            case Unit.INFANTRY:
            case Unit.MECH:
            case Unit.RECON:
            case Unit.TANK:
            case Unit.MD_TANK:
            case Unit.NEOTANK:
            case Unit.MEGATANK:
            case Unit.ANTI_AIR:
            case Unit.FIGHTER:
            case Unit.BOMBER:
            case Unit.B_COPTER:
            case Unit.STEALTH:
            case Unit.CRUISER:
            case Unit.SUB:
                return true;
        }
        return false;
    }

    public static boolean isIndirect(byte unitType) {
        switch (unitType) {
            case Unit.ARTILLERY:
            case Unit.ROCKETS:
            case Unit.MISSILES:
            case Unit.PIPERUNNER:
            case Unit.CARRIER:
                return true;
        }
        return false;
    }

    public static boolean isInftry(byte unitType) {
        if (unitType == Unit.INFANTRY || unitType == Unit.MECH) {
            return true;
        }
        return false;
    }

    public static boolean isVehicle(byte unitType) {
        //Vehicle class
        if (unitType >= Unit.RECON && unitType <= Unit.PIPERUNNER) {
            return true;
        }
        return false;
    }

    public static boolean isLand(byte unitType) {
        if (unitType >= Unit.INFANTRY && unitType <= Unit.PIPERUNNER) {
            return true;
        }
        return false;
    }

    public static boolean isPlane(byte unitType) {
        switch (unitType) {
            case Unit.FIGHTER:
            case Unit.BOMBER:
            case Unit.STEALTH:
            case Unit.BLACK_BOMB:
                return true;
        }
        return false;
    }

    public static boolean isAir(byte unitType) {
        if (unitType >= Unit.FIGHTER && unitType <= Unit.BLACK_BOMB) {
            return true;
        }
        return false;
    }

    public static boolean isSea(byte unitType) {
        if (unitType >= Unit.BATTLESHIP && unitType <= Unit.CARRIER) {
            return true;


        }
        return false;


    }

    public static byte getMovementType(byte unitType) {
        switch (unitType) {
            case Unit.INFANTRY:
                return Unit.INFANTRY_MOV;
            case Unit.MECH:
            case Unit.OOZIUM:
                return Unit.MECH_MOV;
            case Unit.RECON:
            case Unit.ROCKETS:
            case Unit.MISSILES:
                return Unit.TIRES_MOV;
            case Unit.TANK:
            case Unit.MD_TANK:
            case Unit.NEOTANK:
            case Unit.MEGATANK:
            case Unit.APC:
            case Unit.ARTILLERY:
            case Unit.ANTI_AIR:
                return Unit.TREADS_MOV;
            case Unit.FIGHTER:
            case Unit.BOMBER:
            case Unit.B_COPTER:
            case Unit.T_COPTER:
            case Unit.BLACK_BOMB:
                return Unit.AIR_MOV;
            case Unit.BATTLESHIP:
            case Unit.CRUISER:
            case Unit.LANDER:
            case Unit.SUB:
                return Unit.SEA_MOV;
        }
        return -1;
    }

    /**
     * Used to get the movement of a certain unit, after CO, but no after
     * weather (so far) (Weather penalty movement should be calculated on some
     * other method, as getMovement is used to display info from deploy
     * properties before creating an unit [Thus, only the CO modifiers are taken
     * onto account]). Range of vision in FOW is deployed as it is from deploy
     * properties, CO bonuses included; since FOW is constant in a map (i.e.,
     * there is always FOW).
     *
     * @param unit
     * @return
     */
    public static byte getMovement(Unit unit) {
        byte movement = -1;
        // <editor-fold defaultstate="collapsed" desc="Generic movements for all units">
        switch (unit.unitType) {
            case Unit.INFANTRY:
                movement = 3;
                break;
            case Unit.MECH:
                movement = 2;
                break;
            case Unit.RECON:
                movement = 8;
                break;
            case Unit.TANK:
                movement = 6;
                break;
            case Unit.MD_TANK:
                movement = 5;
                break;
            case Unit.NEOTANK:
                movement = 6;
                break;
            case Unit.MEGATANK:
                movement = 4;
                break;
            case Unit.APC:
                movement = 6;
                break;
            case Unit.ARTILLERY:
                movement = 5;
                break;
            case Unit.ROCKETS:
                movement = 5;
                break;
            case Unit.ANTI_AIR:
                movement = 6;
                break;
            case Unit.MISSILES:
                movement = 4;
                break;
            case Unit.PIPERUNNER:
                movement = 9;
                break;
            case Unit.FIGHTER:
                movement = 9;
                break;
            case Unit.BOMBER:
                movement = 7;
                break;
            case Unit.B_COPTER:
                movement = 6;
                break;
            case Unit.T_COPTER:
                movement = 6;
                break;
            case Unit.STEALTH:
                movement = 6;
                break;
            case Unit.BLACK_BOMB:
                movement = 9;
                break;
            case Unit.BATTLESHIP:
                movement = 5;
                break;
            case Unit.CRUISER:
                movement = 6;
                break;
            case Unit.LANDER:
                movement = 6;
                break;
            case Unit.SUB:
                movement = 5;
                break;
            case Unit.BLACK_BOAT:
                movement = 7;
                break;
            case Unit.CARRIER:
                movement = 5;
                break;
            case Unit.OOZIUM:
                movement = 1;
                break;
        }// </editor-fold>
        //Movement modifiers, CO dependant
//        <editor-fold defaultstate="collapsed" desc="CO movement modifiers">

        switch (unit.player.currentCO.id) {
            // <editor-fold defaultstate="collapsed" desc="Andy movement modifiers">
            case CO.ANDY:
                if (unit.player.hasSuperPowerOn) {
                    movement++;
                }
                break;// </editor-fold>
            // <editor-fold defaultstate="collapsed" desc="Max movement modifiers">
            case CO.MAX:
                //Bonus for Max Force in his AW and AW2 versions
                if (Unit.isDirect(unit.unitType)) {
                    if (unit.player.hasPowerOn && unit.player.currentCO.awdsVersion == false) {
                        movement++;
                    } //Bonus for Max Blast in his AW2 version
                    if (unit.player.hasSuperPowerOn && unit.player.currentCO.aw2Version) {
                        movement += 2;
                    }
                }
                break;// </editor-fold>
            // <editor-fold defaultstate="collapsed" desc="Sami movement modifiers">
            case CO.SAMI:
                //If it's infntry
                if (Unit.isInftry(unit.unitType)) {
                    //Double time for infntry
                    if (unit.player.hasPowerOn) {
                        movement++;
                    } //Victory March for inftry
                    if (unit.player.hasSuperPowerOn) {
                        movement += 2;
                    }
                }
                //Transport movement bonus for Sami in AW and AW2
                if (unit.player.currentCO.awVersion == true
                        || unit.player.currentCO.aw2Version == true) {
                    //If is transport
                    if (Unit.isTransport(unit.unitType)) {
                        movement++;
                    }
                }
                break;// </editor-fold>
            // <editor-fold defaultstate="collapsed" desc="Jake movement modifiers">
            case CO.JAKE:
                //If it's vehicle and has superPower: Vehicle bonus for Block Rock
                if (Unit.isVehicle(unit.unitType) && unit.player.hasSuperPowerOn) {
                    movement += 2;
                }
                break;// </editor-fold>
            // <editor-fold defaultstate="collapsed" desc="Drake movement modifiers">
            case CO.DRAKE:
                //Bonus movement for naval units on day-to-day, except on his AWDS version
                if (unit.player.currentCO.awdsVersion == false && Unit.isSea(unit.unitType)) {
                    movement++;
                }
                break;// </editor-fold>
            // <editor-fold defaultstate="collapsed" desc="Jess movement modifiers">
            case CO.JESS:
                if (Unit.isVehicle(unit.unitType)) {
                    if (unit.player.hasPowerOn) {
                        movement++;
                    }
                    if (unit.player.hasSuperPowerOn) {
                        movement += 2;
                    }
                }
                break;// </editor-fold>
            // <editor-fold defaultstate="collapsed" desc="Sensei movement modifiers">
            case CO.SENSEI:
                if (Unit.isTransport(unit.unitType)) {
                    movement++;
                }
                break;// </editor-fold>
            // <editor-fold defaultstate="collapsed" desc="Adder movement modifiers">
            case CO.ADDER:
                if (unit.player.hasPowerOn) {
                    movement++;
                }
                if (unit.player.hasSuperPowerOn) {
                    movement += 2;
                }
                break;// </editor-fold>
            // <editor-fold defaultstate="collapsed" desc="Koal movement modifiers">
            case CO.KOAL:
                if (unit.player.hasPowerOn) {
                    movement++;
                }
                if (unit.player.hasSuperPowerOn) {
                    movement += 2;
                }
                break;// </editor-fold>

        }
        //</editor-fold>
        return movement;
    }

    public static byte getDamageByChart(Unit attacker, Unit defender) {
        if (GlobalSettings.awChart) {
            return Unit.getDamageByChartAW(attacker, defender);
        }
        if (GlobalSettings.aw2Chart) {
            return Unit.getDamageByChartAW2(attacker, defender);
        }
        if (GlobalSettings.awdsChart) {
            return Unit.getDamageByChartAWDS(attacker, defender);
        }
        return -1;
    }

    public static Set<GridCell> getIndirectDeathZone(Unit unit) {

        return null;
    }

    public static byte getMinIndirectRange(Unit unit) {
        byte minRange = -1;
        switch (unit.unitType) {
            case Unit.ARTILLERY:
            case Unit.BATTLESHIP:
            case Unit.PIPERUNNER:
                return 2;
            case Unit.ROCKETS:
            case Unit.MISSILES:
            case Unit.CARRIER:
                return 3;

        }
        return minRange;
    }

    public static byte getMaxIndirectRange(Unit unit) {
        byte maxRange = -1;
        //standard maxRange
        switch (unit.unitType) {
            case Unit.ARTILLERY:
                maxRange = 2;
            case Unit.BATTLESHIP:
                maxRange = 3;
            case Unit.ROCKETS:
            case Unit.MISSILES:
            case Unit.PIPERUNNER:
                maxRange = 5;
            case Unit.CARRIER:
                maxRange = 8;
        }
        //maxRange modifiers, CO dependant
        switch (unit.player.currentCO.id) {
            case CO.MAX:
                return --maxRange;
            case CO.GRIT:
                if (unit.player.hasNoPower) {
                    return (byte) (maxRange + 1);
                }
                if (unit.player.hasPowerOn) {
                    return (byte) (maxRange + 2);
                }
                if (unit.player.hasSuperPowerOn) {
                    return (byte) (maxRange + 3);
                }
                break;
            case CO.JAKE:
                if (unit.player.hasNoPower == false) {
                    return (byte) (maxRange + 1);
                }
                break;
        }
        return maxRange;
    }

    /**
     *
     * @param attacker
     * @param Defender
     * @return
     */
    public static boolean usesPrimaryWeapon(Unit attacker, Unit Defender) {

        return false;
    }

    public static byte getDamageByChartAW(Unit attacker, Unit defender) {
        return -1;
    }

    public static byte getDamageByChartAW2(Unit attacker, Unit defender) {
        switch (attacker.unitType) {
            // <editor-fold defaultstate="collapsed" desc="Infantry attacking - Damage Chart">
            case Unit.INFANTRY:
                switch (defender.unitType) {
                    case Unit.INFANTRY:
                        return 55;
                    case Unit.MECH:
                        return 45;
                    case Unit.RECON:
                        return 12;
                    case Unit.TANK:
                        return 5;
                    case Unit.MD_TANK:
                        return 1;
                    case Unit.NEOTANK:
                        return 1;
                    case Unit.MEGATANK:
                        return 1;
                    case Unit.APC:
                        return 14;
                    case Unit.ARTILLERY:
                        return 15;
                    case Unit.ROCKETS:
                        return 25;
                    case Unit.ANTI_AIR:
                        return 5;
                    case Unit.MISSILES:
                        return 26;
                    case Unit.PIPERUNNER:
                        return 5;
                    case Unit.FIGHTER:
                        return -1;
                    case Unit.BOMBER:
                        return -1;
                    case Unit.B_COPTER:
                        return 7;
                    case Unit.T_COPTER:
                        return 30;
                    case Unit.STEALTH:
                        return 31;
                    case Unit.BLACK_BOMB:
                        return -1;
                    case Unit.BATTLESHIP:
                        return -1;
                    case Unit.CRUISER:
                        return -1;
                    case Unit.LANDER:
                        return -1;
                    case Unit.SUB:
                        return -1;
                    case Unit.BLACK_BOAT:
                        return -1;
                    case Unit.CARRIER:
                        return -1;
                    case Unit.BUILDING:
                        return 1;
                    case Unit.OOZIUM:
                        return 20;
                }
                break;// </editor-fold>
            // <editor-fold defaultstate="collapsed" desc="Mech attacking - Damage Chart">
            case Unit.MECH:

                if (attacker.currentAmmo > 0) {
                    // <editor-fold defaultstate="collapsed" desc="with primary weapon">
                    switch (defender.unitType) {
                        case Unit.INFANTRY:
                            return 65;
                        case Unit.MECH:
                            return 55;
                        case Unit.RECON:
                            return 85;
                        case Unit.TANK:
                            return 55;
                        case Unit.MD_TANK:
                            return 15;
                        case Unit.NEOTANK:
                            return 15;
                        case Unit.MEGATANK:
                            return 5;
                        case Unit.APC:
                            return 75;
                        case Unit.ARTILLERY:
                            return 70;
                        case Unit.ROCKETS:
                            return 85;
                        case Unit.ANTI_AIR:
                            return 65;
                        case Unit.MISSILES:
                            return 85;
                        case Unit.PIPERUNNER:
                            return 55;
                        case Unit.FIGHTER:
                            return -1;
                        case Unit.BOMBER:
                            return -1;
                        case Unit.B_COPTER:
                            return 9;
                        case Unit.T_COPTER:
                            return 35;
                        case Unit.STEALTH:
                            return -1;
                        case Unit.BLACK_BOMB:
                            return -1;
                        case Unit.BATTLESHIP:
                            return -1;
                        case Unit.CRUISER:
                            return -1;
                        case Unit.LANDER:
                            return -1;
                        case Unit.SUB:
                            return -1;
                        case Unit.BLACK_BOAT:
                            return -1;
                        case Unit.CARRIER:
                            return -1;
                        case Unit.BUILDING:
                            return 15;
                        case Unit.OOZIUM:
                            return 30;
                        // </editor-fold>
                    }
                }
                else {
                    // <editor-fold defaultstate="collapsed" desc="with secondary weapon">
                    switch (defender.unitType) {
                        case Unit.INFANTRY:
                            return 65;
                        case Unit.MECH:
                            return 55;
                        case Unit.RECON:
                            return 18;
                        case Unit.TANK:
                            return 6;
                        case Unit.MD_TANK:
                            return 1;
                        case Unit.NEOTANK:
                            return 1;
                        case Unit.MEGATANK:
                            return 1;
                        case Unit.APC:
                            return 20;
                        case Unit.ARTILLERY:
                            return 32;
                        case Unit.ROCKETS:
                            return 35;
                        case Unit.ANTI_AIR:
                            return 6;
                        case Unit.MISSILES:
                            return 35;
                        case Unit.PIPERUNNER:
                            return 6;
                        case Unit.FIGHTER:
                            return -1;
                        case Unit.BOMBER:
                            return -1;
                        case Unit.B_COPTER:
                            return 9;
                        case Unit.T_COPTER:
                            return 35;
                        case Unit.STEALTH:
                            return -1;
                        case Unit.BLACK_BOMB:
                            return -1;
                        case Unit.BATTLESHIP:
                            return -1;
                        case Unit.CRUISER:
                            return -1;
                        case Unit.LANDER:
                            return -1;
                        case Unit.SUB:
                            return -1;
                        case Unit.BLACK_BOAT:
                            return -1;
                        case Unit.CARRIER:
                            return -1;
                        case Unit.BUILDING:
                            return 1;
                        case Unit.OOZIUM:
                            return 20;
                    }// </editor-fold>
                }
                break;// </editor-fold>
            // <editor-fold defaultstate="collapsed" desc="Recon attacking - Damage Chart">
            case Unit.RECON:
                switch (defender.unitType) {
                    case Unit.INFANTRY:
                        return 70;
                    case Unit.MECH:
                        return 65;
                    case Unit.RECON:
                        return 35;
                    case Unit.TANK:
                        return 6;
                    case Unit.MD_TANK:
                        return 1;
                    case Unit.NEOTANK:
                        return 1;
                    case Unit.MEGATANK:
                        return 1;
                    case Unit.APC:
                        return 45;
                    case Unit.ARTILLERY:
                        return 45;
                    case Unit.ROCKETS:
                        return 55;
                    case Unit.ANTI_AIR:
                        return 4;
                    case Unit.MISSILES:
                        return 28;
                    case Unit.PIPERUNNER:
                        return 6;
                    case Unit.FIGHTER:
                        return -1;
                    case Unit.BOMBER:
                        return -1;
                    case Unit.B_COPTER:
                        return 10;
                    case Unit.T_COPTER:
                        return 35;
                    case Unit.STEALTH:
                        return -1;
                    case Unit.BLACK_BOMB:
                        return -1;
                    case Unit.BATTLESHIP:
                        return -1;
                    case Unit.CRUISER:
                        return -1;
                    case Unit.LANDER:
                        return -1;
                    case Unit.SUB:
                        return -1;
                    case Unit.BLACK_BOAT:
                        return -1;
                    case Unit.CARRIER:
                        return -1;
                    case Unit.OOZIUM:
                        return 20;
                    case Unit.BUILDING:
                        return 1;
                }
                break;// </editor-fold>
            // <editor-fold defaultstate="collapsed" desc="Tank attacking - Damage Chart">
            case Unit.TANK:
                if (attacker.currentAmmo > 0) {
                    // <editor-fold defaultstate="collapsed" desc="with primary weapon">
                    switch (defender.unitType) {
                        case Unit.INFANTRY:
                            return 75;
                        case Unit.MECH:
                            return 70;
                        case Unit.RECON:
                            return 85;
                        case Unit.TANK:
                            return 55;
                        case Unit.MD_TANK:
                            return 15;
                        case Unit.NEOTANK:
                            return 15;
                        case Unit.MEGATANK:
                            return 10;
                        case Unit.APC:
                            return 75;
                        case Unit.ARTILLERY:
                            return 70;
                        case Unit.ROCKETS:
                            return 85;
                        case Unit.ANTI_AIR:
                            return 65;
                        case Unit.MISSILES:
                            return 85;
                        case Unit.PIPERUNNER:
                            return 55;
                        case Unit.FIGHTER:
                            return -1;
                        case Unit.BOMBER:
                            return -1;
                        case Unit.B_COPTER:
                            return 9;
                        case Unit.T_COPTER:
                            return 35;
                        case Unit.STEALTH:
                            return -1;
                        case Unit.BLACK_BOMB:
                            return -1;
                        case Unit.BATTLESHIP:
                            return 1;
                        case Unit.CRUISER:
                            return 5;
                        case Unit.LANDER:
                            return 10;
                        case Unit.SUB:
                            return 1;
                        case Unit.BLACK_BOAT:
                            return 10;
                        case Unit.CARRIER:
                            return 1;
                        case Unit.OOZIUM:
                            return 15;
                        case Unit.BUILDING:
                            return 15;
                    }
                    // </editor-fold>
                }
                else {
                    // <editor-fold defaultstate="collapsed" desc="with secondary weapon">
                    switch (defender.unitType) {
                        case Unit.INFANTRY:
                            return 75;
                        case Unit.MECH:
                            return 70;
                        case Unit.RECON:
                            return 40;
                        case Unit.TANK:
                            return 6;
                        case Unit.MD_TANK:
                            return 1;
                        case Unit.NEOTANK:
                            return 1;
                        case Unit.MEGATANK:
                            return 1;
                        case Unit.APC:
                            return 45;
                        case Unit.ARTILLERY:
                            return 45;
                        case Unit.ROCKETS:
                            return 55;
                        case Unit.ANTI_AIR:
                            return 5;
                        case Unit.MISSILES:
                            return 30;
                        case Unit.PIPERUNNER:
                            return 6;
                        case Unit.FIGHTER:
                            return -1;
                        case Unit.BOMBER:
                            return -1;
                        case Unit.B_COPTER:
                            return 10;
                        case Unit.T_COPTER:
                            return 40;
                        case Unit.STEALTH:
                            return -1;
                        case Unit.BLACK_BOMB:
                            return -1;
                        case Unit.BATTLESHIP:
                            return -1;
                        case Unit.CRUISER:
                            return -1;
                        case Unit.LANDER:
                            return -1;
                        case Unit.SUB:
                            return -1;
                        case Unit.BLACK_BOAT:
                            return -1;
                        case Unit.CARRIER:
                            return -1;
                        case Unit.OOZIUM:
                            return 20;
                        case Unit.BUILDING:
                            return 1;
                    }
                    // </editor-fold>
                }
                break;// </editor-fold>
            case Unit.MD_TANK:
                if (attacker.currentAmmo > 0) {
                    switch (defender.unitType) {
                        case Unit.INFANTRY:
                            return 105;
                        case Unit.MECH:
                            return 95;
                        case Unit.RECON:
                            return 105;
                        case Unit.TANK:
                            return 85;
                        case Unit.MD_TANK:
                            return 55;
                        case Unit.NEOTANK:
                            return 45;
                        case Unit.MEGATANK:
                            return 25;
                        case Unit.APC:
                            return 105;
                        case Unit.ARTILLERY:
                            return 105;
                        case Unit.ROCKETS:
                            return 105;
                        case Unit.ANTI_AIR:
                            return 105;
                        case Unit.MISSILES:
                            return 105;
                        case Unit.PIPERUNNER:
                            return 85;
                        case Unit.FIGHTER:
                            return -1;
                        case Unit.BOMBER:
                            return -1;
                        case Unit.B_COPTER:
                            return 12;
                        case Unit.T_COPTER:
                            return 45;
                        case Unit.STEALTH:
                            return -1;
                        case Unit.BLACK_BOMB:
                            return -1;
                        case Unit.BATTLESHIP:
                            return 10;
                        case Unit.CRUISER:
                            return 45;
                        case Unit.LANDER:
                            return 35;
                        case Unit.SUB:
                            return 10;
                        case Unit.BLACK_BOAT:
                            return 35;
                        case Unit.CARRIER:
                            return 10;
                        case Unit.OOZIUM:
                            return 30;
                        case Unit.BUILDING:
                            return 55;
                    }
                }
                else {
                    switch (defender.unitType) {
                        case Unit.INFANTRY:
                            return 105;
                        case Unit.MECH:
                            return 95;
                        case Unit.RECON:
                            return 45;
                        case Unit.TANK:
                            return 8;
                        case Unit.MD_TANK:
                            return 1;
                        case Unit.NEOTANK:
                            return 1;
                        case Unit.MEGATANK:
                            return 1;
                        case Unit.APC:
                            return 45;
                        case Unit.ARTILLERY:
                            return 45;
                        case Unit.ROCKETS:
                            return 55;
                        case Unit.ANTI_AIR:
                            return 7;
                        case Unit.MISSILES:
                            return 35;
                        case Unit.PIPERUNNER:
                            return 8;
                        case Unit.FIGHTER:
                            return -1;
                        case Unit.BOMBER:
                            return -1;
                        case Unit.B_COPTER:
                            return 12;
                        case Unit.T_COPTER:
                            return 45;
                        case Unit.STEALTH:
                            return -1;
                        case Unit.BLACK_BOMB:
                            return -1;
                        case Unit.BATTLESHIP:
                            return -1;
                        case Unit.CRUISER:
                            return -1;
                        case Unit.LANDER:
                            return -1;
                        case Unit.SUB:
                            return -1;
                        case Unit.BLACK_BOAT:
                            return -1;
                        case Unit.CARRIER:
                            return -1;
                        case Unit.OOZIUM:
                            return 20;
                        case Unit.BUILDING:
                            return 1;
                    }
                }
                break;
            case Unit.NEOTANK:
                break;
            case Unit.MEGATANK:
                break;
            case Unit.APC:
                break;
            case Unit.ARTILLERY:
                if (attacker.currentAmmo > 0) {
                    switch (defender.unitType) {
                        case Unit.INFANTRY:
                            return 90;
                        case Unit.MECH:
                            return 85;
                        case Unit.RECON:
                            return 80;
                        case Unit.TANK:
                            return 70;
                        case Unit.MD_TANK:
                            return 45;
                        case Unit.NEOTANK:
                            return 40;
                        case Unit.MEGATANK:
                            return 15;
                        case Unit.APC:
                            return 70;
                        case Unit.ARTILLERY:
                            return 75;
                        case Unit.ROCKETS:
                            return 80;
                        case Unit.ANTI_AIR:
                            return 75;
                        case Unit.MISSILES:
                            return 80;
                        case Unit.PIPERUNNER:
                            return 70;
                        case Unit.FIGHTER:
                            return -1;
                        case Unit.BOMBER:
                            return -1;
                        case Unit.B_COPTER:
                            return -1;
                        case Unit.T_COPTER:
                            return -1;
                        case Unit.STEALTH:
                            return -1;
                        case Unit.BLACK_BOMB:
                            return -1;
                        case Unit.BATTLESHIP:
                            return 40;
                        case Unit.CRUISER:
                            return 65;
                        case Unit.LANDER:
                            return 55;
                        case Unit.SUB:
                            return 60;
                        case Unit.BLACK_BOAT:
                            return 55;
                        case Unit.CARRIER:
                            return 45;
                        case Unit.OOZIUM:
                            return 5;
                        case Unit.BUILDING:
                            return 45;
                    }
                }
                break;
            case Unit.ROCKETS:
                break;
            case Unit.ANTI_AIR:
                break;
            case Unit.MISSILES:
                break;
            case Unit.PIPERUNNER:
                break;
            case Unit.FIGHTER:
                break;
            case Unit.BOMBER:
                break;
            case Unit.B_COPTER:
                break;
            case Unit.T_COPTER:
                break;
            case Unit.STEALTH:
                break;
            case Unit.BLACK_BOMB:
                break;
            case Unit.BATTLESHIP:
                break;
            case Unit.CRUISER:
                break;
            case Unit.LANDER:
                break;
            case Unit.SUB:
                break;
            case Unit.BLACK_BOAT:
                break;
            case Unit.CARRIER:
                break;
        }
        return -1;
    }

    public static byte getDamageByChartAWDS(Unit attacker, Unit defender) {
        return -1;
    }

    public static byte getTotalFuel(byte unitType) {
        switch (unitType) {
            case Unit.INFANTRY:
                return 99;
            case Unit.MECH:
                return 70;
            case Unit.RECON:
                return 80;
            case Unit.TANK:
                return 70;
            case Unit.MD_TANK:
                return 50;
            case Unit.NEOTANK:
                return 99;
            case Unit.MEGATANK:
                return 50;
            case Unit.APC:
                return 70;
            case Unit.ARTILLERY:
                return 50;
            case Unit.ROCKETS:
                return 50;
            case Unit.ANTI_AIR:
                return 60;
            case Unit.MISSILES:
                return 50;
            case Unit.PIPERUNNER:
                return 99;
            case Unit.FIGHTER:
                return 99;
            case Unit.BOMBER:
                return 99;
            case Unit.B_COPTER:
                return 99;
            case Unit.T_COPTER:
                return 99;
            case Unit.STEALTH:
                return 60;
            case Unit.BLACK_BOMB:
                return 45;
            case Unit.BATTLESHIP:
                return 99;
            case Unit.CRUISER:
                return 99;
            case Unit.LANDER:
                return 99;
            case Unit.SUB:
                return 60;
            case Unit.BLACK_BOAT:
                return 60;
            case Unit.CARRIER:
                return 99;
            case Unit.OOZIUM:
                //Oozium fuel is infinite
                return 99;
            case Unit.BUILDING:
                return 0;
        }
        return -1;
    }

    public static byte getTotalAmmo(byte unitType) {
        switch (unitType) {
            case Unit.MECH:
                return 3;
            case Unit.TANK:
                return 9;
            case Unit.MD_TANK:
                return 8;
            case Unit.NEOTANK:
                return 9;
            case Unit.MEGATANK:
                return 3;
            case Unit.ARTILLERY:
                return 9;
            case Unit.ROCKETS:
                return 6;
            case Unit.ANTI_AIR:
                return 9;
            case Unit.MISSILES:
                return 6;
            case Unit.PIPERUNNER:
                return 9;
            case Unit.FIGHTER:
                return 9;
            case Unit.BOMBER:
                return 9;
            case Unit.B_COPTER:
                return 6;
            case Unit.STEALTH:
                return 6;
            case Unit.BATTLESHIP:
                return 9;
            case Unit.CRUISER:
                return 9;
            case Unit.SUB:
                return 6;
            case Unit.BLACK_BOAT:
                return 60;
            case Unit.CARRIER:
                return 9;
        }
        return -1;
    }
}
