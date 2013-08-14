package aw2m.common.serialize;

import aw2m.common.core.CO;
import aw2m.common.core.GridCell;
import aw2m.common.core.Player;
import aw2m.common.core.Property;
import aw2m.common.core.PropertyInstance;
import aw2m.common.core.Terrain;
import aw2m.common.core.Unit;
import java.util.LinkedList;
import java.util.StringTokenizer;

/**
 * This class provives methods for rebuilding objects from Strings, serialized
 * as values like CSVs.
 *
 * @author Alejandro Téllez G. <java.util.fck@hotmail.com>
 * @date 1/08/2013 - 03:26:24 PM
 */
public class Deserialize {

    /**
     * Receives a raw string representing the players and their data on a given
     * turn in a GameInstance object, decodes and returns an array of Player
     * objects, which is to be bound to a GameInstance object.
     *
     * @param raw The String to be deserialized, containing Player data.
     * @return an array of Player objects
     */
    public Player[] deserializePlayers(String raw) {
        LinkedList<Player> list = new LinkedList<Player>();
        StringTokenizer st = new StringTokenizer(raw, ",");
        byte counter = 0;
        while (st.hasMoreTokens()) {
            String rawPlayer = st.nextToken();

            //DEBUG
            System.out.println("Player deserialized CSV " + rawPlayer);

            StringTokenizer st2 = new StringTokenizer(rawPlayer, ".");

            Player playerObject = new Player(counter);
            //DEBUG
            System.out.println("Player created with id " + counter);

            byte c = 0;
            while (st2.hasMoreTokens()) {
                String rawDSV = st2.nextToken();
                switch (c) {
                    case 0:
                        if (rawDSV.equals("0")) {
                            playerObject.isHuman = false;
                        }
                        if (rawDSV.equals("1")) {
                            playerObject.isHuman = true;
                        }
                        break;
                    case 1:
                        if (!rawDSV.equals("n")) {
                            playerObject.teamSymbol = rawDSV;
                        }
                        else {
                            playerObject.teamSymbol = null;
                        }
                        break;
                    case 2:
                        playerObject.currentCO = new CO(Byte.parseByte(rawDSV));
                        break;
                    case 3:
                        playerObject.funds = Integer.parseInt(rawDSV);
                        break;
                    case 4:
                        playerObject.hasNoPower = false;
                        switch (Byte.parseByte(rawDSV)) {
                            case 1:
                                playerObject.hasNoPower = false;
                                playerObject.hasPowerOn = true;
                                playerObject.hasSuperPowerOn = false;
                                break;
                            case 2:
                                playerObject.hasNoPower = false;
                                playerObject.hasPowerOn = false;
                                playerObject.hasSuperPowerOn = true;
                                break;
                        }
                }
                System.out.println("Player data, deserealized DSV " + rawDSV);
                c++;
            }
            list.add(playerObject);
            counter++;
        }
        Player[] array = new Player[list.size()];
        for (byte a = 0; a < list.size(); a++) {
            array[a] = list.get(a);
        }
        return array;
    }

    /**
     * Decomposes a String of CSV representing Terrain IDs and returns a
     * GridCell bidimensional array, also known as the map, with a Terrain
     * object bounded to each GridCell. Also the property information is
     * bounded, but not the instance information of each property, such as
     * player controlling the property and capture points.
     *
     * @param raw   The string to be deserialized, containing terrain IDs.
     * @param xSize the x size of the map of the game.
     * @param ySize the y size of the map of the game.
     * @return
     */
    public GridCell[][] deserializeTerrain(String raw, byte xSize, byte ySize) {
        GridCell map[][] = new GridCell[xSize][ySize];
        StringTokenizer st = new StringTokenizer(raw, ",");
        for (byte yIndex = 0; yIndex < ySize; yIndex++) {
            for (byte xIndex = 0; xIndex < xSize; xIndex++) {
                String currentTerrain = st.nextToken();
                byte terrainID = Byte.parseByte(currentTerrain);
                map[xIndex][yIndex] = new GridCell();
                map[xIndex][yIndex].terrain = Terrain.getTerrainById(terrainID);
                map[xIndex][yIndex].x = xIndex;
                map[xIndex][yIndex].y = yIndex;

                System.out.println("Deserialized terrain: " + map[xIndex][yIndex].terrain.name);
                if (terrainID >= 0 && terrainID <= 4) {
                    map[xIndex][yIndex].isProperty = true;
                    map[xIndex][yIndex].property = Property.getAbstractProperty(terrainID);
                }
            }
        }
        return map;
    }

    /**
     * This method deserializes property info for each player, contained on a
     * String object, and modifies the game map and players info by reference.
     *
     * @param raw     The String object containing all the serialized data
     * @param map     The map object to be updated by reference.
     * @param players The array of players to be updated by reference.
     */
    public void deserializeProperties(String raw, GridCell[][] map, Player[] players) {
        //First, use ; separator: separates each player-properties part.
        StringTokenizer st1 = new StringTokenizer(raw, ";");
        while (st1.hasMoreTokens()) {
            //Allocate a new String object, for each player-property line
            String playerPropertyLine = st1.nextToken();
            //Separate then player from properties
            StringTokenizer st2 = new StringTokenizer(playerPropertyLine, "#");
            //First iteration is player, all consecutives are properties
            byte c = 0;
            String playerID = "";
            while (st2.hasMoreTokens()) {
                switch (c) {
                    case 0: //Player ID
                        playerID = st2.nextToken();
                        break;
                    default: //All properties as CSVs
                        String properties = st2.nextToken();
                        //Then, separate each property.
                        StringTokenizer st3 = new StringTokenizer(properties, ",");
                        while (st3.hasMoreTokens()) {
                            String property = st3.nextToken();
                            byte attributeCounter = 0;
                            byte x = 0, y = 0;
                            //Separate each property data
                            StringTokenizer st4 = new StringTokenizer(property, ".");
                            while (st4.hasMoreTokens()) {
                                switch (attributeCounter) {
                                    case 0: //x coordinate
                                        x = Byte.parseByte(st4.nextToken());
                                        break;
                                    case 1: //y coordinate
                                        y = Byte.parseByte(st4.nextToken());
                                        break;
                                    case 2: //Capt points
                                        if (map[x][y].isProperty) {
                                            map[x][y].propertyInstance =
                                                    new PropertyInstance(
                                                    players[Byte.parseByte(playerID)],
                                                    Byte.parseByte(st4.nextToken()));
                                            System.out.println("The reconstructed property is type "
                                                    + map[x][y].property.propertyType
                                                    + " with "
                                                    + map[x][y].propertyInstance.capture
                                                    + " capt points @("
                                                    + map[x][y].x
                                                    + ","
                                                    + map[x][y].y
                                                    + ") of Player " + map[x][y].propertyInstance.player.id);
                                        }
                                        else {
                                            System.err.println("THIS IS NOT A PROPERTY! SOMETHING WENT WRONG");
                                            System.exit(-1);
                                        }
                                }
                                attributeCounter++;
                            }
                        }
                        break;
                }
                c++;
            }

        }
    }

    /**
     * This method deserializes unit info for each player, contained on a String
     * object, and modifies the game map and players info by reference.
     *
     * @param raw     The String object containing all the serialized data
     * @param map     The map object to be updated by reference.
     * @param players The array of players to be updated by reference.
     */
    public void deserializeUnits(String raw, GridCell[][] map, Player[] players) {
        System.out.println("On deserialize units");
        //First, use ; separator: separates each player-units part.
        StringTokenizer st1 = new StringTokenizer(raw, ";");
        while (st1.hasMoreTokens()) {
            //Allocate a new String object, for each player-unit line
            String playerUnitsLine = st1.nextToken();
            System.out.println("PlayerUnitsLine:" + playerUnitsLine);
            //Separate then player from units
            StringTokenizer st2 = new StringTokenizer(playerUnitsLine, "#");
            //First iteration is player, all consecutives are units
            byte c = 0;
            String playerID = "";
            while (st2.hasMoreTokens()) {
                switch (c) {
                    case 0: //Player ID
                        playerID = st2.nextToken();
                        System.out.println("Deserializing units of Player " + playerID);
                        break;
                    default: //All units as CSVs
                        String units = st2.nextToken();
                        //Then, separate each unit
                        StringTokenizer st3 = new StringTokenizer(units, ",");
                        while (st3.hasMoreTokens()) {
                            Unit unitObject = new Unit();
                            players[Byte.parseByte(playerID)].units.add(unitObject);
                            String unit = st3.nextToken();
                            byte attributeCounter = 0;
                            byte x = 0, y = 0;
                            //Separate each unit  data
                            StringTokenizer st4 = new StringTokenizer(unit, ".");
                            while (st4.hasMoreTokens()) {
                                switch (attributeCounter) {
                                    case 0: //X coordinate
                                        x = Byte.parseByte(st4.nextToken());
                                        break;
                                    case 1: //Y Coordinate
                                        y = Byte.parseByte(st4.nextToken());
                                        unitObject.location = map[x][y];
                                        map[x][y].unit = unitObject;
                                        break;
                                    case 2: //Unit type ID
                                        unitObject.unitType = Byte.parseByte(st4.nextToken());
                                        break;
                                    case 3: //Current HP
                                        unitObject.currentHP = Byte.parseByte(st4.nextToken());
                                        break;
                                    case 4: //Current Fuel
                                        unitObject.currentFuel = Byte.parseByte(st4.nextToken());
                                        break;
                                    case 5: //Current Ammo
                                        unitObject.currentAmmo = Byte.parseByte(st4.nextToken());
                                        break;
                                }
                                attributeCounter++;
                            }
                        }
                        break;
                }
                c++;
            }
        }
        for (Player p : players) {
            System.out.println("Units for Player " + p.id);
            for (Unit u : p.units) {
                System.out.println("UnitType: " + u.unitType
                        + ", HP: " + u.currentHP
                        + ", Fuel: " + u.currentFuel
                        + ", CurrentAmmo: " + u.currentAmmo
                        + " @ (" + u.location.x + ", " + u.location.y + ")");
            }
        }
    }
}
