package aw2m.common.factory;

import aw2m.common.core.GridCell;
import aw2m.common.core.Player;
import aw2m.common.core.Unit;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.LinkedList;

/**
 * This class creates and deploys units on the map. Its utility purpose is to
 * create and deploy various types and quantities of units for testing purposes.
 *
 * @author Alejandro Téllez G. <java.util.fck@hotmail.com>
 * @date 13/06/2013 - 03:28:08 AM
 */
public class UnitFactory {

    /**
     * Returns a new unit, belonging to the player passed as parameter, and
     * located on the specified location.
     *
     * @param unitType The unit type to be built
     * @param player   The player to which belongs this unit
     * @param location The location on the map on where this unit will be
     *                 deployed
     * @return
     */
    public static Unit getNewUnit(byte unitType, Player player, GridCell location) {
        Unit unit;
        if (Unit.getTotalAmmo(unitType) == -1) {
            unit = new Unit(unitType, (byte) 10, player, Unit.getTotalFuel(unitType), true, location);
        }
        else {
            unit = new Unit(unitType, (byte) 10, player, Unit.getTotalFuel(unitType), Unit.getTotalAmmo(unitType), true, location);
        }
        player.units.add(unit);
        return unit;
    }

    public static LinkedList<Unit> getLootOfSpecificUnitType(Player p, byte unitType, LinkedHashSet<GridCell> locations, byte numberOfUnits) {
        //Number of Units can't be larger or equal to the number of locations

        return null;
    }

    public static LinkedList<Unit> getLootOfSpecificUnitTypeOnRandomLocations(Player p, byte unitType, GridCell[][] map, byte numberOfUnits) {
        return null;
    }

    public static LinkedList<Unit> getLootOfSpecificUnitTypeOnRandomLocation(Player p, byte unitType, LinkedHashSet<GridCell> locations, byte numberOfUnits) {
        return null;
    }

    public static LinkedList<Unit> getLootOfRandomUnitType(Player p, byte unitType, LinkedHashSet<GridCell> locations, byte numberOfUnits) {
        return null;
    }

    public static LinkedList<Unit> getLootOfRandomUnitTypeOnRandomLocation(Player p, byte unitType, GridCell[][] map, byte numberOfUnits) {
        return null;
    }

    public static LinkedList<Unit> getLootOfRandomUnitTypeOnRandomLocation(Player p, byte unitType, LinkedHashSet<GridCell> locations, int numberOfUnits) {
        //NumberOfUnits can't be larger or equal than size of Locations
        if (numberOfUnits >= locations.size()) {
            numberOfUnits = locations.size() - 1;
        }
        LinkedList<GridCell> randomLocations = new LinkedList<GridCell>(locations);

        //Shuffle the list
        Collections.shuffle(randomLocations);

        //Created units counter
        int createdUnits = 0;

        //List of units
        LinkedList<Unit> units = new LinkedList<Unit>();

        for (GridCell randomLocation : randomLocations) {
            if (randomLocation.unit == null) {
            }
        }

        return null;
    }

    /**
     * This method creates random enemy units that can be engaged with Unit u,
     * assigning them to the Player object p. Note that the player and unit
     * passed must be from different teams. The number of engageable units is
     * passed as parameter. If ZERO is passed as parameter, the number of units
     * will be equal to the Unit u deathZone size (on GridCell objects). Each
     * enemy unit created will be assigned as location one of the Unit u
     * deathzone's GridCells.
     *
     * @param p
     * @param u
     * @param numberOfUnits
     * @return
     */
    public static LinkedList<Unit> getLootOfRandomEngageableUnitsOnRandomLocations(
            Player p, Unit u, int numberOfUnits, LinkedHashSet<GridCell> locations) {
        //NumberOfUnits can't be larger or equal than size of Locations
        if (numberOfUnits >= locations.size()) {
            numberOfUnits = locations.size() - 1;
        }
        LinkedList<GridCell> randomLocations = new LinkedList<GridCell>(locations);

        //Shuffle the list

        //Created units counter
        int createdUnits = 0;

        int loopCounter = 0, loopLimit = 2 * numberOfUnits;

        //List of units
        LinkedList<Unit> units = new LinkedList<Unit>();

        do {
            Collections.shuffle(randomLocations);
            for (GridCell randomLocation : randomLocations) {
                loopCounter++;
                if (createdUnits < numberOfUnits) {
                    if (randomLocation.unit == null) {
                        Unit newUnit = UnitFactory.getRandomEngageableUnit(u, p, randomLocation);
                        if (randomLocation.terrain.getCanTransverse(newUnit.unitType)) {
                            randomLocation.unit = newUnit;
                            units.add(newUnit);
                            createdUnits++;
                        }
                    }
                }
            }
        }
        while (createdUnits < numberOfUnits || loopCounter < loopLimit);
         System.out.println("Loot of " + createdUnits + " engageable units created");

        return units;
    }

    /**
     * This method returns a unit that can be engaged (and is enemy of ) the
     * unit object sent as parameter. The unit object created will be assigned
     * to the Player object 'owner' sent as parameter.
     *
     * @param enemyUnit Enemy unit that must be able to engage the newly created
     *                  unit.
     * @param owner     The owner of the newly created unit.
     * @return The newly created unit, engageable by enemyUnit
     */
    public static Unit getEngageableUnitOnRandomLocation(Unit enemyUnit, Player owner) {
        return null;
    }

    /**
     * This method returns a unit that can be engaged (and is enemy of ) the
     * unit object sent as parameter. The unit object created will be assigned
     * to the Player object 'owner' sent as parameter.
     *
     * The location of the created unit will be set to the GridCell object sent
     * as parameter.
     *
     * @param enemyUnit Enemy unit that must be able to engage the newly created
     *                  unit.
     * @param owner     The owner of the newly created unit.
     * @param location  The location of the newly created unit.
     * @return The newly created unit, engageable by enemyUnit
     */
    public static Unit getRandomEngageableUnit(Unit enemyUnit, Player owner, GridCell location) {
        //Get a random engageable unit
        //means, get a random number within a set of values

        //Get all posibilities on an array or LIST
        LinkedList<Byte> engageableUnitTypes = new LinkedList<Byte>();
        for (byte c = Unit.INFANTRY; c <= Unit.CARRIER; c++) {
            if (Unit.getEngagingWeapon(enemyUnit.unitType, c) != Unit.CANT_ENGAGE) {
                engageableUnitTypes.add(c);
            }
        }
        //Use array size +1 as max size on randomGenerator
        byte engageableUnitType = UnitFactory.selectRandomValueFromSetOfValues(engageableUnitTypes.size());
        return UnitFactory.getNewUnit(engageableUnitType, owner, location);
    }

    public static Unit getRandomUnit(Player p, GridCell location) {
        return UnitFactory.getNewUnit(UnitFactory.generateRandomUnitType(), p, location);
    }

    /**
     * This method, as well as all others, must validate that an unit is
     * deployed on valid terrain.
     *
     * @param p
     * @param map
     */
    public static Unit getRandomUnitOnRandomLocation(Player p, GridCell[][] map) {
        //Add all the map to a linkedlist
        LinkedHashSet<GridCell> locations = new LinkedHashSet<GridCell>();
        //Iterate throgh array without considering dimensions
        for (GridCell gridRow[] : map) {
            locations.addAll(Arrays.asList(gridRow));
        }
        return UnitFactory.getRandomUnitOnRandomLocation(p, locations);
    }

    /**
     * Creates and deploys a random Unit object, on one of the GridCell objects
     * contained in the locations LinkedHashSet sent as parameter. The unit is
     * only deployed if no other units already exists on the randomly selected
     * location. If a unit already exists, this method does not deploy the unit,
     * and the newly created Unit object is destroyed, returning null.
     *
     * Randomness is only specific in unitType, but not in HP.
     *
     * @param p
     * @param locations
     */
    public static Unit getRandomUnitOnRandomLocation(Player p, LinkedHashSet<GridCell> locations) {

        LinkedList<GridCell> randomlyAccessibleLocations = new LinkedList<GridCell>(locations);
        GridCell location = null;

        //Unit with null location
        Unit unit = UnitFactory.getRandomUnit(p, location);

        GridCell validRandomLocationForUnit = UnitFactory.getValidRandomLocationForUnit(unit, randomlyAccessibleLocations);

        //At this point a valid random location or null is stored on the gridcell reference        
        //The gridCell reference might store a unit (a different Unit object from "unit").

        //If the location is not null
        if (validRandomLocationForUnit != null) {
            //A valid location for unit
            //If the unit stored on the reference is null
            if (validRandomLocationForUnit.unit == null) {
                //Assign it the randomly generated unit
                //Double reference
                unit.location = validRandomLocationForUnit;
                validRandomLocationForUnit.unit = unit;
                return unit;
            }
            else {
                //Another unit exists, therefore, we can't overwrite it
                return null;
            }
        }
        else {
            //No suitable locations were found
            return null;
        }
    }

    /**
     * Generates a random number between 0 and 24: All the primary unit types.
     *
     * @return
     */
    public static byte generateRandomUnitType() {
        return selectRandomValueFromSetOfValues((byte) 24);
    }

    public static byte selectRandomValueFromSetOfValues(int maxValue) {
        double random;

        do {
            //Use cross-multiplication
            random = Math.round(Math.random() * (maxValue + 1));
        }
        while (random < 0 || random > maxValue);

        return (byte) Math.round(Math.random() * maxValue);
    }

    /**
     * Returns a randomly selected valid location for the Unit sent as parameter
     *
     * @param u
     * @param location
     * @return
     */
    public static GridCell getValidRandomLocationForUnit(Unit u, LinkedList<GridCell> locations) {
        GridCell location = null;
        int counter = 0;
        //Find a valid terrain location within the set
        do {
            //First, randomly access a location within the LinkedList
            byte index = selectRandomValueFromSetOfValues(locations.size());
            location = locations.get(index);

            //Loop counter
            counter++;
        }
        //while the unit can't transverse the terrain and loop counter limit has not been reached
        while (location.terrain.getCanTransverse(u.unitType) == false && counter < locations.size());

        //If the terrain can transverse it, return it, else return null;
        if (location.terrain.getCanTransverse(u.unitType)) {
            return location;
        }
        else {
            return null;
        }
    }
}
