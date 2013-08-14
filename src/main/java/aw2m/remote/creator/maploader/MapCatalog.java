package aw2m.remote.creator.maploader;

/**
 *
 * @author Alejandro Téllez G. <java.util.fck@hotmail.com>
 * @date 23/07/2013 - 09:41:35 PM
 */
public class MapCatalog {

    public static final byte UNDEFINED = -1;
    public static final byte SPANN_ISLAND = 0;
    public static final byte POINT_STORMY = 1;

    public static byte getXsize(byte mapID) {
        switch (mapID) {
            case SPANN_ISLAND:
                return 15;
            case POINT_STORMY:
                return 25;
            default:
                return 0;
        }
    }

    public static byte getYsize(byte mapID) {
        switch (mapID) {
            case SPANN_ISLAND:
                return 10;
            case POINT_STORMY:
                return 22;
            default:
                return 0;
        }
    }

    public static byte getNoOfPlayers(byte mapID) {
        switch (mapID) {
            case SPANN_ISLAND:
                return 2;
            case POINT_STORMY:
                return 2;
            default:
                return 0;
        }
    }

    public static String getURL(byte mapID) {
        switch (mapID) {
            case SPANN_ISLAND:
                return "https://dl.dropboxusercontent.com/u/20278793/aw2m/maps/SpannIsland/SpannIsland_console.csv";
            case POINT_STORMY:
                return "";
            default:
                return "";
        }
    }
}
