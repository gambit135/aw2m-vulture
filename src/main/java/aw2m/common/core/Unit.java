package aw2m.common.core;

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
    /**
     * Secondary units.
     */
    public static final byte BUILDING = 25;
    public static final byte OOZIUM = 26;
    /**
     * Primary Weapon.
     */
    public static final byte PRIMARY_WEAPON = 2;
    public static final byte SECONDARY_WEAPON = 1;
    public static final byte CANT_ENGAGE = 0;

    private static byte getDamageByChartAW(Unit attacker, Unit defender) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
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

    /**
     * This method returns a clone of an original Unit object, sent as
     * parameter. The method accounts for all the attributes of the original to
     * be passed unto the clone, primitive datatypes as well as references to
     * other objects. The resulting clone does not in any way replace the
     * original unit: e.g., if the original is referenced by a GridCell object,
     * the reference shall remain unchanged and won't, at any point, change its
     * reference to the new clone object.
     *
     * This method should provide handy when creating nodes on the search tree,
     * to simulate combat outcomes.
     *
     * @param original The unit to be cloned
     * @return A new Unit object: a clone of the original unit.
     */
    public static Unit cloneUnit(Unit original) {
        Unit clone = new Unit(original.unitType,
                              original.currentHP,
                              original.player,
                              original.currentFuel,
                              original.currentAmmo,
                              original.hasMoved,
                              original.location);
        return clone;
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

    public static short getUnitCost(byte unitType) {
        switch (unitType) {
            case Unit.INFANTRY:
                return 1000;
            case Unit.MECH:
                return 3000;
            case Unit.RECON:
                return 4000;
            case Unit.TANK:
                return 7000;
            case Unit.MD_TANK:
                return 16000;
            case Unit.NEOTANK:
                return 22000;
            case Unit.MEGATANK:
                return 28000;
            case Unit.APC:
                return 5000;
            case Unit.ARTILLERY:
                return 6000;
            case Unit.ROCKETS:
                return 15000;
            case Unit.ANTI_AIR:
                return 8000;
            case Unit.MISSILES:
                return 12000;
            case Unit.PIPERUNNER:
                return 20000;
            case Unit.FIGHTER:
                return 20000;
            case Unit.BOMBER:
                return 22000;
            case Unit.B_COPTER:
                return 9000;
            case Unit.T_COPTER:
                return 5000;
            case Unit.STEALTH:
                return 24000;
            case Unit.BLACK_BOMB:
                return 25000;
            case Unit.BATTLESHIP:
                return 28000;
            case Unit.CRUISER:
                return 18000;
            case Unit.LANDER:
                return 12000;
            case Unit.SUB:
                return 20000;
            case Unit.BLACK_BOAT:
                return 7500;
            case Unit.CARRIER:
                return 30000;
        }
        return -1;
    }

    public static byte getUnitCostFactor(byte unitType) {
        return (byte) (Math.round(Unit.getUnitCost(unitType) / 1000));
    }

    public static String getUnitName(byte unitType) {
        switch (unitType) {
            case Unit.INFANTRY:
                return "Infantry";
            case Unit.MECH:
                return "Mech";
            case Unit.RECON:
                return "Recon";
            case Unit.TANK:
                return "Tank";
            case Unit.MD_TANK:
                return "Md. Tank";
            case Unit.NEOTANK:
                return "Neotank";
            case Unit.MEGATANK:
                return "Megatank";
            case Unit.APC:
                return "APC";
            case Unit.ARTILLERY:
                return "Artillery";
            case Unit.ROCKETS:
                return "Rockets";
            case Unit.ANTI_AIR:
                return "A-Air";
            case Unit.MISSILES:
                return "Missiles";
            case Unit.PIPERUNNER:
                return "Piperunner";
            case Unit.FIGHTER:
                return "Fighter";
            case Unit.BOMBER:
                return "Bomber";
            case Unit.B_COPTER:
                return "B Cptr";
            case Unit.T_COPTER:
                return "T-Cptr";
            case Unit.STEALTH:
                return "Stealth";
            case Unit.BLACK_BOMB:
                return "Black Bomb";
            case Unit.BATTLESHIP:
                return "Battleship";
            case Unit.CRUISER:
                return "Cruiser";
            case Unit.LANDER:
                return "Lander";
            case Unit.SUB:
                return "Sub";
            case Unit.BLACK_BOAT:
                return "Black Boat";
            case Unit.CARRIER:
                return "Carrier";
        }
        return "";
    }

    /**
     * Returns true if an attacking unit can engage in combat with a defender
     * unit, regardless of which weapon is used by the attacker; also ignoring
     * if the defender can fight back. An example can be an A-Air enganging a
     * Fighter. The fighter cannot engage ground units.
     *
     * @param attacker
     * @param defender
     * @return
     */
    /*public static boolean canEngage(Unit attacker, Unit defender) {
     return false;
     }*/
    /**
     * Returns the weapon that the attacker can use against the defener, with
     * priority to its primary weapon; NOTE THAT the value returned by this
     * method takes unto account the circumstance of the game, more
     * specifically: if the attacker has or has not ammo. This is, if the
     * attacker can use its primary weapon, it returs the constant value
     * PRIMARY_WEAPON. If it can't, but can use its secondary weapon, this
     * method returns the SECONDARY_WEAPON value. If the attacker can't engange
     * the defender with either weapons, a CANT_ENGAGE value is returned.
     *
     *
     * @param attacker
     * @param defender
     * @return
     */
    public static byte getEngagingWeapon(Unit attacker, Unit defender) {
        //If the attacker has primary weapon
        if (Unit.getTotalAmmo(attacker.unitType) > 0) {
            //If the attacker has ammo
            if (attacker.currentAmmo > 0) {
                //test if it can attack with primary
                if (Unit.canEngageUnitClassUsingPrimaryWeapon(attacker.unitType, defender.unitType)) {
                    return Unit.PRIMARY_WEAPON;
                }
            }
            //At this point, it can't use primary, so try asking for secondary
            if (Unit.canEngageUnitClassUsingSecondaryWeapon(attacker.unitType, defender.unitType)) {
                return Unit.SECONDARY_WEAPON;
            }
        }
        //If it can't use either, it can't engage.
        return Unit.CANT_ENGAGE;
    }

    /**
     * Returns if an attacking unit can, hypothetically, engage an enemy
     * defending unit.
     *
     * @param attacker
     * @param defender
     * @return
     */
    public static byte getEngagingWeapon(byte attacker, byte defender) {
        //If the attacker has primary weapon
        //test if it can attack with primary
        if (Unit.canEngageUnitClassUsingPrimaryWeapon(attacker, defender)) {
            return Unit.PRIMARY_WEAPON;
        }
        //At this point, it can't use primary, so try asking for secondary
        if (Unit.canEngageUnitClassUsingSecondaryWeapon(attacker, defender)) {
            return Unit.SECONDARY_WEAPON;
        }
        //If it can't use either, it can't engage.
        return Unit.CANT_ENGAGE;
    }

    /**
     * Return true if a unit can attack another with its primary weapon, based
     * on the unit class restrictions imposed by game's logic, but not by
     * circumstances such as ammo. E.g., a T Copter can attach Cptr clas, with
     * Hydra missiles.
     *
     * @param atacker  The attacking unit.
     * @param defender The unit which class will be determiend, in order to know
     *                 if it can be attacked by the attacker's primary weapon.
     * @return True if the attacking unit can engage the defender unit using its
     *         primary weapon. False otherwise.
     */
    public static boolean canEngageUnitClassUsingPrimaryWeapon(byte attacker, byte defender) {
        //Switch to determine attacking unit class
        switch (attacker) {
            case Unit.INFANTRY:
                //Infantry only use secondary weapon
                return false;
            case Unit.MECH:
                switch (Unit.getUnitClass(defender)) {
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
                switch (Unit.getUnitClass(defender)) {
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
                switch (Unit.getUnitClass(defender)) {
                    case Unit.INFTRY_CLASS:
                    case Unit.VEH_CLASS:
                    case Unit.SHIP_CLASS:
                    case Unit.SUB_CLASS:
                        return true;
                }
                return false;
            case Unit.ROCKETS:
                switch (Unit.getUnitClass(defender)) {
                    case Unit.INFTRY_CLASS:
                    case Unit.VEH_CLASS:
                    case Unit.SHIP_CLASS:
                    case Unit.SUB_CLASS:
                        return true;
                }
                return false;
            case Unit.ANTI_AIR:
                switch (Unit.getUnitClass(defender)) {
                    case Unit.INFTRY_CLASS:
                    case Unit.VEH_CLASS:
                    case Unit.CPTR_CLASS:
                    case Unit.PLANE_CLASS:
                        return true;
                }
                return false;
            case Unit.MISSILES:
                //Can only hit air units
                switch (Unit.getUnitClass(defender)) {
                    case Unit.CPTR_CLASS:
                    case Unit.PLANE_CLASS:
                        return true;
                }
                return false;
            case Unit.PIPERUNNER:
                //Piperunners can hit anything
                return true;
            case Unit.FIGHTER:
                //Fighters can only engage other air units
                switch (Unit.getUnitClass(defender)) {
                    case Unit.CPTR_CLASS:
                    case Unit.PLANE_CLASS:
                        return true;
                }
                return false;
            case Unit.BOMBER:
                switch (Unit.getUnitClass(defender)) {
                    case Unit.INFTRY_CLASS:
                    case Unit.VEH_CLASS:
                    case Unit.SHIP_CLASS:
                    case Unit.SUB_CLASS:
                        return true;
                }
                return false;
            case Unit.B_COPTER:
                switch (Unit.getUnitClass(defender)) {
                    case Unit.VEH_CLASS:
                    case Unit.SHIP_CLASS:
                    case Unit.SUB_CLASS:
                        return true;
                }
                return false;
            case Unit.T_COPTER:
                //Transport copters carry no guns
                return false;
            case Unit.STEALTH:
                //Omni missiles can hit anything
                return true;
            case Unit.BLACK_BOMB:
                //Left out
                return false;
            case Unit.BATTLESHIP:
                //Battleships can bomb on anything but air units
                switch (Unit.getUnitSuperClass(defender)) {
                    case Unit.AIR_CLASS:
                        return false;
                }
                return true;
            case Unit.CRUISER:
                //Cruisers can engage only subs with their torpedos
                switch (Unit.getUnitClass(defender)) {
                    case Unit.SUB_CLASS:
                        return true;
                }
                return false;
            case Unit.LANDER:
                //Lander has no guns
                return false;
            case Unit.SUB:
                //Subs can only attack sea units
                switch (Unit.getUnitSuperClass(defender)) {
                    case Unit.SEA_CLASS:
                        return true;
                }
                return false;
            case Unit.BLACK_BOAT:
                //Black boat has no guns
                return false;
            case Unit.CARRIER:
                //Carriers can take down air units from the distance
                switch (Unit.getUnitSuperClass(defender)) {
                    case Unit.AIR_CLASS:
                        return true;
                }
                return false;
            case Unit.OOZIUM:
                //OOZIUM is left out
                return false;
        }
        return false;
    }

    /**
     * Return true if a unit can attack another with its primary weapon, based
     * on the unit class restrictions imposed by game's logic, but not by
     * circumstances such as ammo. E.g., a Tank unit can attack an Infantry unit
     * with its machine gun.
     *
     * @param atacker  The attacking unit.
     * @param defender The unit which class will be determiend, in order to know
     *                 if it can be attacked by the attacker's secondary weapon.
     * @return True if the attacking unit can engage the defender unit using its
     *         secondary weapon. False otherwise.
     */
    public static boolean canEngageUnitClassUsingSecondaryWeapon(byte attacker, byte defender) {
        //Switch to determine attacking unit class
        switch (attacker) {
            //Infantry only can use its machine gun against other inftry, vehicles and copters.
            case Unit.INFANTRY:
            //Same as infantry for mechs
            case Unit.MECH:
            //Same as mechs for recons
            case Unit.RECON:
            //And the same for all the tanks
            case Unit.TANK:
            case Unit.MD_TANK:
            case Unit.NEOTANK:
            case Unit.MEGATANK:
                switch (Unit.getUnitClass(defender)) {
                    case Unit.INFTRY_CLASS:
                    case Unit.VEH_CLASS:
                    case Unit.CPTR_CLASS:
                        return true;
                }
                return false;
            //The rest of land units has no secondary gun
            case Unit.APC:
            case Unit.ARTILLERY:
            case Unit.ROCKETS:
            case Unit.ANTI_AIR:
            case Unit.MISSILES:
            case Unit.PIPERUNNER:
                return false;
            //Nor fighters and bombers
            case Unit.FIGHTER:
            case Unit.BOMBER:
                return false;
            //Same as inftry for copters
            case Unit.B_COPTER:
                switch (Unit.getUnitClass(defender)) {
                    case Unit.INFTRY_CLASS:
                    case Unit.VEH_CLASS:
                    case Unit.CPTR_CLASS:
                        return true;
                }
                return false;
            case Unit.T_COPTER:
                //Transport copters carry no guns
                return false;
            case Unit.STEALTH:
                //No secondaries for stealth
                return false;
            case Unit.BLACK_BOMB:
                //Left out
                return false;
            case Unit.BATTLESHIP:
                return false;
            case Unit.CRUISER:
                //Cruisers can engage air units with its machine guns
                switch (Unit.getUnitSuperClass(defender)) {
                    case Unit.AIR_CLASS:
                        return true;
                }
                return false;
            case Unit.LANDER:
                //Lander has no guns
                return false;
            case Unit.SUB:
                //Subs has no secondary weapon
                return false;
            case Unit.BLACK_BOAT:
                //Black boat has no guns
                return false;
            case Unit.CARRIER:
                return false;
            case Unit.OOZIUM:
                //OOZIUM is left out
                return false;
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
            case Unit.BATTLESHIP:
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

    public static short getDamageByChart(Unit attacker, Unit defender) {
        //if (GlobalSettings.awChart) {
        return Unit.getDamageByChartAW2(attacker, defender);
        /*}
         if (GlobalSettings.aw2Chart) {
         return Unit.getDamageByChartAW2(attacker, defender);
         }
         if (GlobalSettings.awdsChart) {
         return Unit.getDamageByChartAWDS(attacker, defender);
         }
         */
        //return -1;
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
        //maxRange modifiers, CO dependent
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
     * This method determines wether an attacking unit can use its primary or
     * secondary weapon against a defending unit; returns true if the attacker
     * can use its primary weapon, or false otherwise.
     *
     * Determining wether or not a primary weapon can be used depends on several
     * factors:
     *
     * 1st: If the attacker can use the primary weapon against the defender by
     * definition.
     *
     * 2nd: The ammunition on the attacker's primary weapon.
     *
     * @param attacker
     * @param Defender
     * @return
     */
    public static boolean canUsePrimaryWeapon(Unit attacker, Unit defender) {
        if (attacker.currentAmmo <= 0) {
            return false;
        }
        switch (attacker.unitType) {
            case Unit.INFANTRY:
                //Infantry units don't have primary weapon
                return false;
            case Unit.MECH:
                switch (Unit.getUnitClass(defender.unitType)) {
                    case Unit.VEH_CLASS:
                        return true;
                    case Unit.BUILDING_CLASS:
                        return true;
                    default:
                        return false;
                }
            case Unit.RECON:
                //Recons don't have primary weapon
                return false;
            case Unit.TANK:
            case Unit.MD_TANK:
            case Unit.NEOTANK:
            case Unit.MEGATANK:
                switch (Unit.getUnitClass(defender.unitType)) {
                    case Unit.INFTRY_CLASS:
                        return false;
                    case Unit.VEH_CLASS:
                        return true;
                    case Unit.CPTR_CLASS:
                        return false;
                    case Unit.PLANE_CLASS:
                        return false;
                    case Unit.SHIP_CLASS:
                        return true;
                    case Unit.SUB_CLASS:
                        return true;
                    case Unit.BUILDING_CLASS:
                        return true;
                }
                break;
            case Unit.APC:
                //Apcs can't attack
                return false;
            case Unit.ARTILLERY:
            case Unit.ROCKETS:
                switch (Unit.getUnitClass(defender.unitType)) {
                    case Unit.INFTRY_CLASS:
                        return true;
                    case Unit.VEH_CLASS:
                        return true;
                    case Unit.CPTR_CLASS:
                        return false;
                    case Unit.PLANE_CLASS:
                        return false;
                    case Unit.SHIP_CLASS:
                        return true;
                    case Unit.SUB_CLASS:
                        return true;
                    case Unit.BUILDING_CLASS:
                        return true;
                }
                break;
            case Unit.ANTI_AIR:
                switch (Unit.getUnitClass(defender.unitType)) {
                    case Unit.INFTRY_CLASS:
                        return true;
                    case Unit.VEH_CLASS:
                        return true;
                    case Unit.CPTR_CLASS:
                        return true;
                    case Unit.PLANE_CLASS:
                        return true;
                    case Unit.SHIP_CLASS:
                        return false;
                    case Unit.SUB_CLASS:
                        return false;
                    case Unit.BUILDING_CLASS:
                        return true;
                }
                break;
            case Unit.MISSILES:
                switch (Unit.getUnitSuperClass(defender.unitType)) {
                    case Unit.AIR_CLASS:
                        return true;
                    default:
                        return false;
                }
            case Unit.PIPERUNNER:
                //Piperunners can hit anything with its pipe cannon.
                return true;
            case Unit.FIGHTER:
                //fighters can only attack air units
                switch (Unit.getUnitSuperClass(defender.unitType)) {
                    case Unit.AIR_CLASS:
                        return true;
                    default:
                        return false;
                }
            case Unit.BOMBER:
                //bombers can bomb down on anything but air units
                switch (Unit.getUnitSuperClass(defender.unitType)) {
                    case Unit.AIR_CLASS:
                        return false;
                    default:
                        return true;
                }
            case Unit.B_COPTER:
                //B copters can hit other copters, vehicles, ships and subs.
                switch (Unit.getUnitClass(defender.unitType)) {
                    case Unit.INFTRY_CLASS:
                        return false;
                    case Unit.VEH_CLASS:
                        return true;
                    case Unit.CPTR_CLASS:
                        return true;
                    case Unit.PLANE_CLASS:
                        return false;
                    case Unit.SHIP_CLASS:
                        return true;
                    case Unit.SUB_CLASS:
                        return true;
                    case Unit.BUILDING_CLASS:
                        return true;
                }
                break;
            case Unit.T_COPTER:
                //transport copters can't attack
                return false;
            case Unit.STEALTH:
                //Stealths can bomb anything with its omnimissile
                return true;
            case Unit.BLACK_BOMB:
                //Black bombs are kamikaze units that damage enemy units around them
                return false;
            case Unit.BATTLESHIP:
                //Battleship units can rain havoc amongst land and sea units
                switch (Unit.getUnitClass(defender.unitType)) {
                    case Unit.INFTRY_CLASS:
                        return true;
                    case Unit.VEH_CLASS:
                        return true;
                    case Unit.CPTR_CLASS:
                        return false;
                    case Unit.PLANE_CLASS:
                        return false;
                    case Unit.SHIP_CLASS:
                        return true;
                    case Unit.SUB_CLASS:
                        return true;
                    case Unit.BUILDING_CLASS:
                        return true;
                }
                break;
            case Unit.CRUISER:
                //Cruiser can only use missiles against subs
                switch (Unit.getUnitClass(defender.unitType)) {
                    case Unit.SUB_CLASS:
                        return true;
                    default:
                        return false;
                }
            case Unit.LANDER:
                //Landers can't attack
                return false;
            case Unit.SUB:
                //Subs only can attack ships and other subs
                switch (Unit.getUnitSuperClass(defender.unitType)) {
                    case Unit.SEA_CLASS:
                        return true;
                    default:
                        return false;
                }
            case Unit.BLACK_BOAT:
                return false;
            case Unit.CARRIER:
                //Carriers are the missiles of the seas
                switch (Unit.getUnitSuperClass(defender.unitType)) {
                    case Unit.AIR_CLASS:
                        return true;
                    default:
                        return false;
                }
            case Unit.BUILDING:
                return false;
            case Unit.OOZIUM:
                //Oozium doesnt have any weapons
                return false;
        }
        return false;
    }

    public static short getDamageByChartAW2(Unit attacker, Unit defender) {
        switch (attacker.unitType) {
            //Infantry can only attack with secondary, so no need for switch
                /*
             switch (Unit.getEngagingWeapon(attacker, defender)){
             case Unit.PRIMARY_WEAPON:
             break;
             case Unit.SECONDARY_WEAPON:
             break;                        
             }*/
            // <editor-fold defaultstate="collapsed" desc="Infantry attacking - Damage Chart">
            case Unit.INFANTRY:
                // <editor-fold defaultstate="collapsed" desc="with secondary weapon">
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
                    case Unit.B_COPTER:
                        return 7;
                    case Unit.T_COPTER:
                        return 30;
                    case Unit.STEALTH:
                        return 31;
                    case Unit.BUILDING:
                        return 1;
                    case Unit.OOZIUM:
                        return 20;
                }
                //</editor-fold>
                break;// </editor-fold>
            // <editor-fold defaultstate="collapsed" desc="Mech attacking - Damage Chart">
            case Unit.MECH:
                switch (Unit.getEngagingWeapon(attacker, defender)) {
                    case Unit.PRIMARY_WEAPON:
                        // <editor-fold defaultstate="collapsed" desc="with primary weapon">
                        switch (defender.unitType) {
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
                            case Unit.BUILDING:
                                return 15;
                            case Unit.OOZIUM:
                                return 30;
                        }// </editor-fold>
                        break;
                    case Unit.SECONDARY_WEAPON:
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
                            case Unit.B_COPTER:
                                return 9;
                            case Unit.T_COPTER:
                                return 35;
                            case Unit.BUILDING:
                                return 1;
                            case Unit.OOZIUM:
                                return 20;
                        }// </editor-fold>
                        break;
                }
                break;// </editor-fold>
            // <editor-fold defaultstate="collapsed" desc="Recon attacking - Damage Chart">
            case Unit.RECON:
                //<editor-fold defaultstate="collapsed" desc="with secondary weapon">
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
                    case Unit.B_COPTER:
                        return 10;
                    case Unit.T_COPTER:
                        return 35;
                    case Unit.OOZIUM:
                        return 20;
                    case Unit.BUILDING:
                        return 1;
                }
                //</editor-fold>
                break;
            // </editor-fold>
            // <editor-fold defaultstate="collapsed" desc="Tank attacking - Damage Chart">
            case Unit.TANK:
                switch (Unit.getEngagingWeapon(attacker, defender)) {
                    case Unit.PRIMARY_WEAPON:
                        // <editor-fold defaultstate="collapsed" desc="with primary weapon">
                        switch (defender.unitType) {
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
                                return 20;
                            case Unit.BUILDING:
                                return 15;
                        }
                        // </editor-fold>
                        break;
                    case Unit.SECONDARY_WEAPON:
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
                            case Unit.B_COPTER:
                                return 10;
                            case Unit.T_COPTER:
                                return 40;
                            case Unit.OOZIUM:
                                return 20;
                            case Unit.BUILDING:
                                return 1;
                        }
                        // </editor-fold>
                        break;
                }
                break;// </editor-fold>
            // <editor-fold defaultstate="collapsed" desc="MD Tank attacking - Damage Chart">
            case Unit.MD_TANK:
                switch (Unit.getEngagingWeapon(attacker, defender)) {
                    case Unit.PRIMARY_WEAPON:
                        // <editor-fold defaultstate="collapsed" desc="with primary weapon">
                        switch (defender.unitType) {
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
                        //</editor-fold>
                        break;
                    case Unit.SECONDARY_WEAPON:
                        // <editor-fold defaultstate="collapsed" desc="with secondary weapon">
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
                            case Unit.B_COPTER:
                                return 12;
                            case Unit.T_COPTER:
                                return 45;
                            case Unit.OOZIUM:
                                return 20;
                            case Unit.BUILDING:
                                return 1;
                        }
                        // </editor-fold>
                        break;
                }
                break;
            //</editor-fold>
            // <editor-fold defaultstate="collapsed" desc="NeoTank attacking - Damage Chart">
            case Unit.NEOTANK:
                switch (Unit.getEngagingWeapon(attacker, defender)) {
                    case Unit.PRIMARY_WEAPON:
                        // <editor-fold defaultstate="collapsed" desc="with primary weapon">
                        switch (defender.unitType) {
                            case Unit.RECON:
                                return 125;
                            case Unit.TANK:
                                return 105;
                            case Unit.MD_TANK:
                                return 75;
                            case Unit.NEOTANK:
                                return 55;
                            case Unit.MEGATANK:
                                return 35;
                            case Unit.APC:
                                return 125;
                            case Unit.ARTILLERY:
                                return 115;
                            case Unit.ROCKETS:
                                return 125;
                            case Unit.ANTI_AIR:
                                return 115;
                            case Unit.MISSILES:
                                return 125;
                            case Unit.PIPERUNNER:
                                return 105;
                            case Unit.BATTLESHIP:
                                return 15;
                            case Unit.CRUISER:
                                return 50;
                            case Unit.LANDER:
                                return 40;
                            case Unit.SUB:
                                return 15;
                            case Unit.BLACK_BOAT:
                                return 40;
                            case Unit.CARRIER:
                                return 15;
                            case Unit.OOZIUM:
                                return 35;
                            case Unit.BUILDING:
                                return 75;
                        }
                        //</editor-fold>
                        break;
                    case Unit.SECONDARY_WEAPON:
                        // <editor-fold defaultstate="collapsed" desc="with secondary weapon">
                        switch (defender.unitType) {
                            case Unit.INFANTRY:
                                return 125;
                            case Unit.MECH:
                                return 115;
                            case Unit.RECON:
                                return 65;
                            case Unit.TANK:
                                return 10;
                            case Unit.MD_TANK:
                                return 1;
                            case Unit.NEOTANK:
                                return 1;
                            case Unit.MEGATANK:
                                return 1;
                            case Unit.APC:
                                return 65;
                            case Unit.ARTILLERY:
                                return 65;
                            case Unit.ROCKETS:
                                return 75;
                            case Unit.ANTI_AIR:
                                return 17;
                            case Unit.MISSILES:
                                return 55;
                            case Unit.PIPERUNNER:
                                return 10;
                            case Unit.B_COPTER:
                                return 22;
                            case Unit.T_COPTER:
                                return 55;
                            case Unit.OOZIUM:
                                return 20;
                            case Unit.BUILDING:
                                return 1;
                        }
                        // </editor-fold>
                        break;
                }
                break;
            // </editor-fold>
            // <editor-fold defaultstate="collapsed" desc="MegaTank attacking - Damage Chart">
            case Unit.MEGATANK:
                switch (Unit.getEngagingWeapon(attacker, defender)) {
                    case Unit.PRIMARY_WEAPON:
                        // <editor-fold defaultstate="collapsed" desc="with primary weapon">
                        switch (defender.unitType) {
                            case Unit.RECON:
                                return 195;
                            case Unit.TANK:
                                return 180;
                            case Unit.MD_TANK:
                                return 125;
                            case Unit.NEOTANK:
                                return 115;
                            case Unit.MEGATANK:
                                return 65;
                            case Unit.APC:
                                return 195;
                            case Unit.ARTILLERY:
                                return 195;
                            case Unit.ROCKETS:
                                return 125;
                            case Unit.ANTI_AIR:
                                return 115;
                            case Unit.MISSILES:
                                return 125;
                            case Unit.PIPERUNNER:
                                return 180;
                            case Unit.BATTLESHIP:
                                return 45;
                            case Unit.CRUISER:
                                return 65;
                            case Unit.LANDER:
                                return 75;
                            case Unit.SUB:
                                return 45;
                            case Unit.BLACK_BOAT:
                                return 105;
                            case Unit.CARRIER:
                                return 45;
                            case Unit.OOZIUM:
                                return 45;
                            case Unit.BUILDING:
                                return 125;
                        }
                        //</editor-fold>
                        break;
                    case Unit.SECONDARY_WEAPON:
                        // <editor-fold defaultstate="collapsed" desc="with secondary weapon">
                        switch (defender.unitType) {
                            case Unit.INFANTRY:
                                return 135;
                            case Unit.MECH:
                                return 125;
                            case Unit.RECON:
                                return 65;
                            case Unit.TANK:
                                return 10;
                            case Unit.MD_TANK:
                                return 1;
                            case Unit.NEOTANK:
                                return 1;
                            case Unit.MEGATANK:
                                return 1;
                            case Unit.APC:
                                return 65;
                            case Unit.ARTILLERY:
                                return 65;
                            case Unit.ROCKETS:
                                return 75;
                            case Unit.ANTI_AIR:
                                return 17;
                            case Unit.MISSILES:
                                return 55;
                            case Unit.PIPERUNNER:
                                return 10;
                            case Unit.B_COPTER:
                                return 22;
                            case Unit.T_COPTER:
                                return 55;
                            case Unit.OOZIUM:
                                return 20;
                            case Unit.BUILDING:
                                return 1;
                        }
                        // </editor-fold>
                        break;
                }
                break;
            // </editor-fold>
            //APCS can't attack
            case Unit.APC:
                break;
            // <editor-fold defaultstate="collapsed" desc="Artillery attacking - Damage Chart">
            case Unit.ARTILLERY:
                switch (Unit.getEngagingWeapon(attacker, defender)) {
                    case Unit.PRIMARY_WEAPON:
                        // <editor-fold defaultstate="collapsed" desc="with primary weapon">
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
                        //</editor-fold>
                        break;
                }
                break;
            // </editor-fold>
            // <editor-fold defaultstate="collapsed" desc="Rockets attacking - Damage Chart">
            case Unit.ROCKETS:
                switch (Unit.getEngagingWeapon(attacker, defender)) {
                    case Unit.PRIMARY_WEAPON:
                        // <editor-fold defaultstate="collapsed" desc="with primary weapon">
                        switch (defender.unitType) {
                            case Unit.INFANTRY:
                                return 95;
                            case Unit.MECH:
                                return 90;
                            case Unit.RECON:
                                return 90;
                            case Unit.TANK:
                                return 80;
                            case Unit.MD_TANK:
                                return 55;
                            case Unit.NEOTANK:
                                return 50;
                            case Unit.MEGATANK:
                                return 25;
                            case Unit.APC:
                                return 80;
                            case Unit.ARTILLERY:
                                return 80;
                            case Unit.ROCKETS:
                                return 85;
                            case Unit.ANTI_AIR:
                                return 85;
                            case Unit.MISSILES:
                                return 90;
                            case Unit.PIPERUNNER:
                                return 80;
                            case Unit.BATTLESHIP:
                                return 55;
                            case Unit.CRUISER:
                                return 85;
                            case Unit.LANDER:
                                return 60;
                            case Unit.SUB:
                                return 85;
                            case Unit.BLACK_BOAT:
                                return 60;
                            case Unit.CARRIER:
                                return 60;
                            case Unit.OOZIUM:
                                return 15;
                            case Unit.BUILDING:
                                return 55;
                        }
                        //</editor-fold>
                        break;
                }
                break;
            // </editor-fold>
            // <editor-fold defaultstate="collapsed" desc="A-Air attacking - Damage Chart">
            case Unit.ANTI_AIR:
                switch (Unit.getEngagingWeapon(attacker, defender)) {
                    case Unit.PRIMARY_WEAPON:
                        // <editor-fold defaultstate="collapsed" desc="with primary weapon">
                        switch (defender.unitType) {
                            case Unit.INFANTRY:
                                return 105;
                            case Unit.MECH:
                                return 105;
                            case Unit.RECON:
                                return 60;
                            case Unit.TANK:
                                return 25;
                            case Unit.MD_TANK:
                                return 10;
                            case Unit.NEOTANK:
                                return 5;
                            case Unit.MEGATANK:
                                return 1;
                            case Unit.APC:
                                return 50;
                            case Unit.ARTILLERY:
                                return 50;
                            case Unit.ROCKETS:
                                return 55;
                            case Unit.ANTI_AIR:
                                return 45;
                            case Unit.MISSILES:
                                return 55;
                            case Unit.PIPERUNNER:
                                return 25;
                            case Unit.FIGHTER:
                                return 65;
                            case Unit.BOMBER:
                                return 75;
                            case Unit.B_COPTER:
                                return 120;
                            case Unit.T_COPTER:
                                return 120;
                            case Unit.STEALTH:
                                return 75;
                            case Unit.BLACK_BOMB:
                                return 120;
                            case Unit.OOZIUM:
                                return 10;
                            case Unit.BUILDING:
                                return 30;
                        }
                        //</editor-fold>
                        break;
                }
                break;
            // </editor-fold>
            // <editor-fold defaultstate="collapsed" desc="Missiles attacking - Damage Chart">
            case Unit.MISSILES:
                switch (Unit.getEngagingWeapon(attacker, defender)) {
                    case Unit.PRIMARY_WEAPON:
                        // <editor-fold defaultstate="collapsed" desc="with primary weapon">
                        switch (defender.unitType) {
                            case B_COPTER:
                                return 120;
                            case T_COPTER:
                                return 120;
                            case FIGHTER:
                                return 120;
                            case BOMBER:
                                return 100;
                        }
                        // </editor-fold>
                        break;
                }
                break;
            // </editor-fold>
            // <editor-fold defaultstate="collapsed" desc="Piperunner attacking - Damage Chart">
            case Unit.PIPERUNNER:
                switch (Unit.getEngagingWeapon(attacker, defender)) {
                    case Unit.PRIMARY_WEAPON:
                        // <editor-fold defaultstate="collapsed" desc="with primary weapon">
                        switch (defender.unitType) {
                            case Unit.INFANTRY:
                                return 95;
                            case Unit.MECH:
                                return 90;
                            case Unit.RECON:
                                return 90;
                            case Unit.TANK:
                                return 80;
                            case Unit.MD_TANK:
                                return 55;
                            case Unit.NEOTANK:
                                return 50;
                            case Unit.MEGATANK:
                                return 25;
                            case Unit.APC:
                                return 80;
                            case Unit.ARTILLERY:
                                return 80;
                            case Unit.ROCKETS:
                                return 85;
                            case Unit.ANTI_AIR:
                                return 85;
                            case Unit.MISSILES:
                                return 90;
                            case Unit.PIPERUNNER:
                                return 80;
                            case Unit.FIGHTER:
                                return 65;
                            case Unit.BOMBER:
                                return 75;
                            case Unit.B_COPTER:
                                return 105;
                            case Unit.T_COPTER:
                                return 105;
                            case Unit.STEALTH:
                                return 75;
                            case Unit.BLACK_BOMB:
                                return 120;
                            case Unit.BATTLESHIP:
                                return 55;
                            case Unit.CRUISER:
                                return 60;
                            case Unit.LANDER:
                                return 60;
                            case Unit.SUB:
                                return 85;
                            case Unit.BLACK_BOAT:
                                return 60;
                            case Unit.CARRIER:
                                return 60;
                            case Unit.OOZIUM:
                                return 15;
                            case Unit.BUILDING:
                                return 55;
                        }
                        // </editor-fold>
                        break;
                }
                break;
            // </editor-fold>
            // <editor-fold defaultstate="collapsed" desc="Fighter attacking - Damage Chart">
            case Unit.FIGHTER:
                switch (Unit.getEngagingWeapon(attacker, defender)) {
                    case Unit.PRIMARY_WEAPON:
                        // <editor-fold defaultstate="collapsed" desc="with primary weapon">
                        switch (defender.unitType) {
                            case Unit.FIGHTER:
                                return 55;
                            case Unit.BOMBER:
                                return 100;
                            case Unit.B_COPTER:
                                return 100;
                            case Unit.T_COPTER:
                                return 100;
                            case Unit.STEALTH:
                                return 85;
                            case Unit.BLACK_BOMB:
                                return 120;
                        }
                        // </editor-fold>
                        break;
                }
                break;
            // </editor-fold>
            // <editor-fold defaultstate="collapsed" desc="Bomber attacking - Damage Chart">
            case Unit.BOMBER:
                switch (Unit.getEngagingWeapon(attacker, defender)) {
                    case Unit.PRIMARY_WEAPON:
                        // <editor-fold defaultstate="collapsed" desc="with primary weapon">
                        switch (defender.unitType) {
                            case Unit.INFANTRY:
                                return 110;
                            case Unit.MECH:
                                return 110;
                            case Unit.RECON:
                                return 105;
                            case Unit.TANK:
                                return 105;
                            case Unit.MD_TANK:
                                return 95;
                            case Unit.NEOTANK:
                                return 90;
                            case Unit.MEGATANK:
                                return 35;
                            case Unit.APC:
                                return 105;
                            case Unit.ARTILLERY:
                                return 105;
                            case Unit.ROCKETS:
                                return 105;
                            case Unit.ANTI_AIR:
                                return 95;
                            case Unit.MISSILES:
                                return 105;
                            case Unit.PIPERUNNER:
                                return 95;
                            case Unit.BATTLESHIP:
                                return 75;
                            case Unit.CRUISER:
                                return 85;
                            case Unit.LANDER:
                                return 95;
                            case Unit.SUB:
                                return 95;
                            case Unit.BLACK_BOAT:
                                return 95;
                            case Unit.CARRIER:
                                return 75;
                            case Unit.OOZIUM:
                                return 35;
                            case Unit.BUILDING:
                                return 95;
                        }
                        // </editor-fold>
                        break;
                }
                break;
            // </editor-fold>
            // <editor-fold defaultstate="collapsed" desc="B copter attacking - Damage Chart">
            case Unit.B_COPTER:
                switch (Unit.getEngagingWeapon(attacker, defender)) {
                    case Unit.PRIMARY_WEAPON:
                        // <editor-fold defaultstate="collapsed" desc="with primary weapon">
                        switch (defender.unitType) {
                            case Unit.RECON:
                                return 55;
                            case Unit.TANK:
                                return 55;
                            case Unit.MD_TANK:
                                return 25;
                            case Unit.NEOTANK:
                                return 20;
                            case Unit.MEGATANK:
                                return 10;
                            case Unit.APC:
                                return 60;
                            case Unit.ARTILLERY:
                                return 65;
                            case Unit.ROCKETS:
                                return 65;
                            case Unit.ANTI_AIR:
                                return 25;
                            case Unit.MISSILES:
                                return 65;
                            case Unit.PIPERUNNER:
                                return 55;
                            case Unit.BATTLESHIP:
                                return 25;
                            case Unit.CRUISER:
                                return 55;
                            case Unit.LANDER:
                                return 25;
                            case Unit.SUB:
                                return 25;
                            case Unit.BLACK_BOAT:
                                return 25;
                            case Unit.CARRIER:
                                return 25;
                            case Unit.BUILDING:
                                return 15;
                            case Unit.OOZIUM:
                                return 30;
                        }// </editor-fold>
                        break;
                    case Unit.SECONDARY_WEAPON:
                        // <editor-fold defaultstate="collapsed" desc="with secondary weapon">
                        switch (defender.unitType) {
                            case Unit.INFANTRY:
                                return 75;
                            case Unit.MECH:
                                return 75;
                            case Unit.RECON:
                                return 30;
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
                                return 25;
                            case Unit.ROCKETS:
                                return 35;
                            case Unit.ANTI_AIR:
                                return 6;
                            case Unit.MISSILES:
                                return 35;
                            case Unit.PIPERUNNER:
                                return 6;
                            case Unit.B_COPTER:
                                return 65;
                            case Unit.T_COPTER:
                                return 95;
                            case Unit.BUILDING:
                                return 1;
                            case Unit.OOZIUM:
                                return 20;
                        }// </editor-fold>
                        break;
                }
                break;
            // </editor-fold>
            //T Copters can't attack
            case Unit.T_COPTER:
                break;
            // <editor-fold defaultstate="collapsed" desc="Stealth attacking - Damage Chart">
            case Unit.STEALTH:
                switch (Unit.getEngagingWeapon(attacker, defender)) {
                    case Unit.PRIMARY_WEAPON:
                        // <editor-fold defaultstate="collapsed" desc="with primary weapon">
                        switch (defender.unitType) {
                            case Unit.INFANTRY:
                                return 90;
                            case Unit.MECH:
                                return 90;
                            case Unit.RECON:
                                return 85;
                            case Unit.TANK:
                                return 75;
                            case Unit.MD_TANK:
                                return 70;
                            case Unit.NEOTANK:
                                return 60;
                            case Unit.MEGATANK:
                                return 15;
                            case Unit.APC:
                                return 85;
                            case Unit.ARTILLERY:
                                return 75;
                            case Unit.ROCKETS:
                                return 85;
                            case Unit.ANTI_AIR:
                                return 50;
                            case Unit.MISSILES:
                                return 85;
                            case Unit.PIPERUNNER:
                                return 80;
                            case Unit.FIGHTER:
                                return 45;
                            case Unit.BOMBER:
                                return 70;
                            case Unit.B_COPTER:
                                return 85;
                            case Unit.T_COPTER:
                                return 95;
                            case Unit.STEALTH:
                                return 55;
                            case Unit.BLACK_BOMB:
                                return 120;
                            case Unit.BATTLESHIP:
                                return 45;
                            case Unit.CRUISER:
                                return 35;
                            case Unit.LANDER:
                                return 65;
                            case Unit.SUB:
                                return 55;
                            case Unit.BLACK_BOAT:
                                return 65;
                            case Unit.CARRIER:
                                return 45;
                            case Unit.BUILDING:
                                return 70;
                            case Unit.OOZIUM:
                                return 30;
                        }// </editor-fold>
                        break;
                }
                break;
            // </editor-fold>
            //Black Bombs can't attack
            case Unit.BLACK_BOMB:
                break;
            // <editor-fold defaultstate="collapsed" desc="B Ship attacking - Damage Chart">    
            case Unit.BATTLESHIP:
                switch (Unit.getEngagingWeapon(attacker, defender)) {
                    case Unit.PRIMARY_WEAPON:
                        // <editor-fold defaultstate="collapsed" desc="with primary weapon">
                        switch (defender.unitType) {
                            case Unit.INFANTRY:
                                return 95;
                            case Unit.MECH:
                                return 90;
                            case Unit.RECON:
                                return 90;
                            case Unit.TANK:
                                return 90;
                            case Unit.MD_TANK:
                                return 80;
                            case Unit.NEOTANK:
                                return 55;
                            case Unit.MEGATANK:
                                return 25;
                            case Unit.APC:
                                return 80;
                            case Unit.ARTILLERY:
                                return 80;
                            case Unit.ROCKETS:
                                return 85;
                            case Unit.ANTI_AIR:
                                return 85;
                            case Unit.MISSILES:
                                return 90;
                            case Unit.PIPERUNNER:
                                return 80;
                            case Unit.BATTLESHIP:
                                return 50;
                            case Unit.CRUISER:
                                return 95;
                            case Unit.LANDER:
                                return 95;
                            case Unit.SUB:
                                return 95;
                            case Unit.BLACK_BOAT:
                                return 95;
                            case Unit.CARRIER:
                                return 60;
                            case Unit.BUILDING:
                                return 55;
                            case Unit.OOZIUM:
                                return 20;
                        }// </editor-fold>
                        break;
                }
                break;
            // </editor-fold>
            // <editor-fold defaultstate="collapsed" desc="Cruiser attacking - Damage Chart">
            case Unit.CRUISER:
                switch (Unit.getEngagingWeapon(attacker, defender)) {
                    case Unit.PRIMARY_WEAPON:
                        // <editor-fold defaultstate="collapsed" desc="with primary weapon">
                        switch (defender.unitType) {
                            case Unit.SUB:
                                return 90;
                        }// </editor-fold>
                        break;
                    case Unit.SECONDARY_WEAPON:
                        // <editor-fold defaultstate="collapsed" desc="with secondary weapon">
                        switch (defender.unitType) {
                            case Unit.B_COPTER:
                                return 115;
                            case Unit.T_COPTER:
                                return 115;
                            case Unit.FIGHTER:
                                return 55;
                            case Unit.BOMBER:
                                return 65;
                            case Unit.STEALTH:
                                return 100;
                            case Unit.BLACK_BOMB:
                                return 120;
                        }// </editor-fold>
                        break;
                }
                break;
            // </editor-fold>
            //Landers can't attack
            case Unit.LANDER:
                break;
            // <editor-fold defaultstate="collapsed" desc="Sub attacking - Damage Chart">
            case Unit.SUB:
                switch (Unit.getEngagingWeapon(attacker, defender)) {
                    case Unit.PRIMARY_WEAPON:
                        // <editor-fold defaultstate="collapsed" desc="with primary weapon">
                        switch (defender.unitType) {
                            case Unit.B_COPTER:
                                return 115;
                            case Unit.T_COPTER:
                                return 115;
                            case Unit.FIGHTER:
                                return 100;
                            case Unit.BOMBER:
                                return 100;
                            case Unit.STEALTH:
                                return 100;
                            case Unit.BLACK_BOMB:
                                return 120;
                        }// </editor-fold>
                        break;
                }
                break;
            // </editor-fold>
            //Black boat can't attack
            case Unit.BLACK_BOAT:
                break;
            // <editor-fold defaultstate="collapsed" desc="Carrier attacking - Damage Chart">   
            case Unit.CARRIER:
                switch (Unit.getEngagingWeapon(attacker, defender)) {
                    case Unit.PRIMARY_WEAPON:
                        // <editor-fold defaultstate="collapsed" desc="with primary weapon">
                        switch (defender.unitType) {
                            case Unit.LANDER:
                                return 95;
                            case Unit.BATTLESHIP:
                                return 55;
                            case Unit.CRUISER:
                                return 25;
                            case Unit.SUB:
                                return 55;
                            case Unit.CARRIER:
                                return 75;
                            case Unit.BLACK_BOAT:
                                return 95;
                        }// </editor-fold>
                        break;
                }
                break;
            //</editor-fold>
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

    @Override
    public String toString() {
        return Unit.toString(this);
    }

    public static String toString(Unit u) {
        return "Unit: " + Unit.getUnitName(u.unitType)
                + ", HP: " + u.currentHP
                + ", Fuel: " + u.currentFuel
                + ", P: " + u.player.id
                + ", CurrentAmmo: " + u.currentAmmo
                + " on " + u.location.terrain.name
                + " @ (" + u.location.x + ", " + u.location.y + ")";
    }

    @Override
    public boolean equals(Object o) {
        return false;
    }
}
