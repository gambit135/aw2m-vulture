package aw2m.common.serialize;

import aw2m.common.core.GameInstance;
import aw2m.common.core.GridCell;
import aw2m.common.core.Player;
import aw2m.common.core.Unit;

/**
 * This class provides methods for converting the entire state of the game into
 * a set of strings.
 *
 * @author Alejandro Téllez G. <java.util.fck@hotmail.com>
 * @date 1/08/2013 - 12:18:44 AM
 */
public class Serialize {

    /**
     * This method serializes all players found on the GameInstance player[]
     * array, in order from 1 to n.
     *
     * The order in which each player attribute is serialized is:
     *
     * "isHuman.TeamSymbol.CO_ID.funds.hasPowerOn"
     *
     * hasPowerOn is represented as a number, where 0 is noPower, 1 is powerOn,
     * 2 is superOn and 3 is tagOn
     *
     * @param game The GameInstance object that contains a reference to the data
     *             to be serialized
     * @return a String containing the serialized data of all players on
     *         GameInstance, as CSVs.
     */
    public String serializePlayers(GameInstance game) {
        String players = "";
        for (Player p : game.players) {
            String s = "";
            if (p.isHuman) {
                s += "1";
            }
            else {
                s += "0";
            }
            s += ".";
            s += String.valueOf(p.team);
            /*
             if (p.teamSymbol == null) {
             System.out.println("Player about to serialize has no team");
             s += "n";
             }
             else {
             s += p.teamSymbol;
             }*/
            s += ".";
            s += p.currentCO.id;
            s += ".";
            s += p.funds;
            s += ".";
            if (p.hasNoPower) {
                s += "0";
            }
            if (p.hasPowerOn) {
                s += "1";
            }
            if (p.hasSuperPowerOn) {
                s += "2";
            }
            s += ",";
            players += s;
        }
        return players;
    }

    /**
     * Terrain is serialized as a flow containing all terrain identifiers from
     * the GridCell bidimensional array (the game map), as running through them
     * first on X and then on Y order.
     *
     * @param game The GameInstance object that contains a reference to the data
     *             to be serialized
     * @return
     */
    public String serializeTerrain(GameInstance game) {
        String terrain = "";
        for (byte j = 0; j < game.map[0].length; j++) {
            for (byte i = 0; i < (byte) game.map.length; i++) {
                terrain += game.map[i][j].terrain.terrainType;
                terrain += ",";
            }
        }
        return terrain;
    }

    /**
     * This method serializes property data from each player on a single String.
     * Property data is serialized using the characters "#", "," and "." as in:
     *
     * playerID#Prop1XCoordinate.Prop1YCoordinate.Prop1CaptPoints,Prop2XCoordinate...
     * etc.
     *
     * @param game The GameInstance object that contains a reference to the data
     *             to be serialized
     * @return a String containing the properties
     */
    public String serializeProperties(GameInstance game) {
        String properties = "";
        for (Player player : game.players) {
            properties += player.id;
            properties += "#";
            for (GridCell property : player.properties) {
                properties += property.x;
                properties += ".";
                properties += property.y;
                properties += ".";
                properties += property.propertyInstance.capture;
                properties += ",";
            }
            properties += ";";
        }
        return properties;
    }

    /**
     * This method gathers the information of all units on a given GameInstace
     * object and returns a String object with the serialized data of the units
     * on the map. This serializes the unit information using the characters
     * "#", "," and "." as in:
     *
     * playerID#unit1XCoordinate .Unit1YCoordinate .Unit1TypeID .Unit1CurrentHP
     * .Unit1CurrentFuel .Unit1CurrentAmmo ,Unit2XCoordinate... etc.
     *
     * @param game The game instance on which the units exist.
     * @return a String object containing the serialized information of the
     *         units on the game map.
     */
    public String serializeUnits(GameInstance game) {
        String units = "";
        for (Player p : game.players) {
            units += p.id;
            units += "#";
            for (Unit u : p.units) {
                units += u.location.x;
                units += ".";
                units += u.location.y;
                units += ".";
                units += u.unitType;
                units += ".";
                units += u.currentHP;
                units += ".";
                units += u.currentFuel;
                units += ".";
                if (Unit.getTotalAmmo(u.unitType) != -1) {
                    units += u.currentAmmo;
                }
                units += ",";
            }
            units += ";";
        }
        return units;
    }
}
