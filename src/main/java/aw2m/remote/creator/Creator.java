package aw2m.remote.creator;

import aw2m.common.factory.UnitFactory;
import aw2m.common.core.GridCell;
import aw2m.common.core.Map;
import aw2m.common.core.Player;
import aw2m.common.core.Terrain;

/**
 * The main class for the Creator module. Its function is to create or generate
 * maps for testing/playing.
 *
 * @author Alejandro Téllez G. <java.util.fck@hotmail.com>
 * @date 13/06/2013 - 01:52:39 AM
 */
public class Creator {

    /**
     * A reference to itself, as it is a Singleton object.
     */
    private Creator creator;
    /**
     * An UnitFactory object delivers built-on-demand units, ready to be
     * deployed on a GridCell of the Mapboard. Consider static methods on the
     * UnitFactory class.
     */
    UnitFactory unitFactory;
    /**
     * The map that this Creator object will initialize and modify to deliver an
     * initial state of a game, ready to be played.
     */
    Map map;

    //The first task of the creator is to generate a mapboard, of given x and y coordinates.
    //And to initialize TWO players, ready to play.
    private Creator() {
    }

    public void createAbstractMap(byte x, byte y) {
        this.createAbstractMapboard(x, y);
        map.setPlayer1(Player.getGenericPlayer((byte) 1));
        map.setPlayer2(Player.getGenericPlayer((byte) 2));
    }

    /**
     * Fills the entire mapboard contained on the current Map of the Creator
     * singleton object with the specified terrain passed as argument. The ID of
     * the terrain is defined on the Terrain class. The Terrain objects
     * associated to each GridCell object follow the Singleton pattern and,
     * therefore, two distinct GridCell objects may share a reference to the
     * same Terrain objet. See the Terrain class documentation.
     *
     * @param terrainID The ID of the Terrain to assign to each of the GridCell
     *                  obects of the entire mapboard, as defined on the Terrain
     *                  class.
     */
    public void fillMap(byte terrainID) {
        //Run along the array without considering it's dimensions
        for (GridCell gridRow[] : map.getMapboard()) {
            for (GridCell gridCell : gridRow) {
                gridCell.terrain = Terrain.getTerrainById(terrainID);
            }
        }
    }

    public void createAbstractMapboard(byte x, byte y) {
        this.map = new Map();
        map.createMapbard(x, y);
    }

    public Creator getCreator() {
        if (creator != null) {
            return creator;
        }
        return new Creator();
    }
}
