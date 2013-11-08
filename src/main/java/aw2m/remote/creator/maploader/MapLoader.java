package aw2m.remote.creator.maploader;

import aw2m.common.core.GameInstance;
import aw2m.common.core.GridCell;
import aw2m.common.core.Player;
import aw2m.common.core.Property;
import aw2m.common.core.PropertyInstance;
import aw2m.common.core.Terrain;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

/**
 *
 *
 * @author Alejandro Téllez G. <java.util.fck@hotmail.com>
 * @date 18/06/2013 - 08:20:26 PM
 */
public class MapLoader {

    static BufferedReader br;
    public static byte Xindex;
    public static byte Yindex;
    public static byte sizeX;
    public static byte sizeY;
    public static GameInstance game;

    //public static GridCell[][] map;
    static {
    }

    /**
     * Loads a map from the catalog and unto the map attribute of the
     * GameInstance object sent as parameter. Note that the GameInstance
     * players[] attribute should already be initialized with the information of
     * actual players, otherwise it will throw a NullPointerException.
     *
     * @param game  The GameInstance object to be modified by reference,
     *              containing players[] attribute already initialized, and which
     *              map[][] attribute wil be overriden.
     * @param mapID The id of the map to be loaded from catalog.
     */
    public static void loadMapFromCatalog(GameInstance game, byte mapID) {
        MapLoader.loadMap(
                game,
                MapCatalog.getXsize(mapID),
                MapCatalog.getYsize(mapID),
                MapCatalog.getURL(mapID), true);

    }

    /**
     * Takes a GameInstance object already initialized with player info and
     * loads the information of a map file, locally or via an URL as a matrix of
     * GridCell objects unto its map attribute. Note that this method requires
     * that the players[] attribute of the GameInstance object sent as parameter
     * be already initialized with the information of actual players.
     *
     * @param xSize    the x size of the map to be loaded
     * @param ySize    the y size of the map to be loaded
     * @param address  A String object containing the address, local or URL,
     *                 from where the map will be loaded.
     * @param isRemote Indicates wether the file will be loaded locally or
     *                 remotely.
     */
    public static void loadMap(GameInstance game, byte xSize, byte ySize, String address, boolean isRemote) {
        MapLoader.game = game;
        MapLoader.sizeX = xSize;
        sizeY = ySize;
        game.map = new GridCell[MapLoader.sizeX][MapLoader.sizeY];
        //Initialize map[][] with empty gridcells
        for (byte j = 0; j < MapLoader.sizeY; j++) {
            for (byte i = 0; i < MapLoader.sizeX; i++) {
                game.map[i][j] = new GridCell();
                game.map[i][j].x = i;
                game.map[i][j].y = j;
            }
        }
        if (isRemote) {
            MapLoader.loadRemoteFile(address);
        }
        else {
            MapLoader.loadLocalFile(address);
        }
        //Account properties for each player
        accountPropertiesForEachPlayer(game.map, game.players);


    }

    /**
     * Accounts properties on a map for each player, adding each player's
     * property to their individual "properties" list.
     *
     * @param map
     * @param players
     */
    public static void accountPropertiesForEachPlayer(GridCell[][] map, Player[] players) {
        for (GridCell[] row : map) {
            for (GridCell g : row) {
                if (g.isProperty) {
                	//DEBUG
                    //System.out.println("Accounting property @(" + g.x + ", " + g.y + ")");
                    players[g.propertyInstance.player.id].properties.add(g);
                }
            }
        }

    }

    public static boolean loadLocalFile(String fileName) {

        File f = new File(fileName);;
        try {
            br = new BufferedReader(new FileReader(fileName));
        }
        catch (FileNotFoundException exc) {
            //    System.out.println("File not Found!");
            return false;
        }
        //Read each line of the CSV file

        while (true) {
            try {
                String line = br.readLine();
                //DEBUG
                //      System.out.println("Reading new line:" + line);
                //If the current line is not empty
                if (line != null) {
                    //DEBUG
                    //        System.out.println("Going to processCSVLine("
                    //              + line + "," + Yindex);
                    MapLoader.processCSVLine(line, Yindex);

                    MapLoader.Yindex++;
                }
                else {
                    break;
                }

            }
            catch (IOException ioe) {
                System.err.println("Can't read the CSV line!");
                return false;
            }
        }
        // Cierra el lector de entrada abierto.
        try {
            br.close();
        }
        catch (IOException exc2) {
            System.err.println("IO Error at closing file!");
            return false;
        }
        return false;
    }

    public static void loadRemoteFile(String urlAddress) {
        try {
            System.out.println("Testing remote load file");
            // Create a URL for the desired page
            URL url = new URL(urlAddress);
            //URL url = new URL("https://dl.dropboxusercontent.com/u/20278793/aw2m/maps/SpannIsland/SpannIsland.csv");
            br = new BufferedReader(new InputStreamReader(url.openStream()));
            String line;
            while ((line = br.readLine()) != null) {
                //DEBUG
            	//System.out.println(line);
                processCSVLine(line, Yindex);

                Yindex++;
            }
            br.close();
        }
        catch (MalformedURLException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
            System.err.println(e.getLocalizedMessage());
        }
        catch (IOException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
            System.err.println(e.getLocalizedMessage());
        }
        catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getMessage());
            System.err.println("");
            System.err.println(e.getLocalizedMessage());
        }
    }

    private static void processCSVLine(String line, byte y) {
        //DEBUG
    	//System.out.println("on processCSVLine");
        //System.out.println("sizeX is " + sizeX);
        for (byte x = 0; x < MapLoader.sizeX; x++) {
            //DEBUG
            //System.out.println("Map Loader.sizeX: " + MapLoader.sizeX);
            //System.out.println("byte x: " + x);
            line = MapLoader.findNextCSV(line, x, y);
        }
    }

    /**
     * Searches for a CSV along a given line. If a CSV is found, it represents
     * Terrain object bound to a GridCell, and should be processed to load it
     * into the game map. x and y coordinates are given as paramters to execute
     * such process
     *
     * @param line
     * @param x
     * @param y
     * @return
     */
    public static String findNextCSV(String line, byte x, byte y) {
        int index;
        //DEBUG
        //System.out.println("On findNextCSV");
        //If there are still CSVs remaining to be processed
        if (line != null) {
            //Find the end of the next CSV
            index = line.indexOf(",");
            switch (index) {
                //There are no CSVs left to be processed
                case -1:
                    //DEBUG
                    //System.out.println("There are no CSVs left to be processed, maybe the last");
                    //Unless, it's the last value, which has no coma, but it has a number
                    if (line.length() > 0) {
                        //DEBUG
                        //System.out.println("Doing something with found csv");
                        MapLoader.processCSV(line, x, y);
                    }
                    return null;
                //There is an error
                //There can't be empty CSV (i.e. two straight commas in a row)
                //The process should be aborted
                case 0:
                    //DEBUG
                    System.err.println("This is an empty CSV");
                    System.err.println("On findNextCSV (" + line + ", " + x + ", " + y + ")");
                    return null;
                //Otherwise, there is a non-empty CSV to be processed
                default:
                    //Do something with the found CSV
                    //DEBUG
                    //System.out.println("Doing something with found csv");
                    MapLoader.processCSV(line.substring(0, index), x, y);
                    //Return a chopped version of the line,
                    //removing the new found CSV
                    index++;
                    return line.substring(index);
            }
        }
        return null;
    }

    private static void processCSV(String csv, byte x, byte y) {
        //Find if there is a "." character or not
        //Separate the part TERRAIN from the part PLAYER
        //Use the TERRAIN part to assign a terrain to the GridCell on the
        //X,Y specified location
        //Use the PLAYER part to create a property and assign it a given player
        //using the players[] array, which holds references to players, 
        //indexing them
        int index;
        //DEBUG
        //System.out.println("On process CSV(" + csv + "," + x + "," + y + ")");
        byte terrainType;
        byte spriteID;
        if (csv != null) {
            //Find the TERRAIN and PLAYER part of the csv
            index = csv.indexOf(".");
            switch (index) {
                //There is an error, there can't be a "." after a ","
                //Process should be aborted
                case 0:
                    //DEBUG
                    System.err.println("There is an error, there can't be a . after a ,");
                    System.err.println("On process CSV(" + csv + "," + x + "," + y + ")");
                    return;

                //There is only a TERRAIN part, (i.e. this is not a property)
                case -1:
                    //DEBUG
                    //System.out.println("There is only a TERRAIN part, (i.e. this is not a property)");
                    //System.out.println("Get the Terrain part");
                	//Get the Terrain part
                    terrainType = Byte.parseByte(csv);
                    MapLoader.assignTerrain(terrainType, x, y);
                    return;

                //Otherwise, there is a TERRAIN and a PLAYER part
                default:
                    //Get the Terrain part
                    //DEBUG
                    //System.out.println("Otherwise, there is a TERRAIN and a PLAYER part");
                    terrainType = Byte.parseByte(csv.substring(0, index));
                    MapLoader.assignTerrain(terrainType, x, y);
                    //Get property's PLAYER part
                    //DEBUG
                    //System.out.println("Get property's PLAYER part, or the corresponding terrain SPRITE");
                    spriteID = Byte.parseByte(csv.substring(++index));
                    //System.out.println("Sprite ID: " + spriteID);
                    MapLoader.assignPlayerToProperty(spriteID, terrainType, x, y);
            }
        }
    }

    public static void assignTerrain(byte terrainType, byte x, byte y) {
        Terrain t = null;
        //DEBUG
        //System.out.println("ON assignTerrain(" + terrainType + "," + x + "," + y + ")");
        if (MapLoader.game.map[x][y] != null) {
            switch (terrainType) {
                case Terrain.HQ:
                    MapLoader.game.map[x][y].terrain = Terrain.getHq();
                    MapLoader.game.map[x][y].isProperty = true;
                    break;
                case Terrain.CITY:
                    MapLoader.game.map[x][y].terrain = Terrain.getCity();
                    MapLoader.game.map[x][y].isProperty = true;
                    break;
                case Terrain.BASE:
                    MapLoader.game.map[x][y].terrain = Terrain.getBase();
                    MapLoader.game.map[x][y].isProperty = true;
                    break;
                case Terrain.AIRPORT:
                    MapLoader.game.map[x][y].terrain = Terrain.getAirport();
                    MapLoader.game.map[x][y].isProperty = true;
                    break;
                case Terrain.PORT:
                    MapLoader.game.map[x][y].terrain = Terrain.getPort();
                    MapLoader.game.map[x][y].isProperty = true;
                    break;
                case Terrain.BRIDGE:
                    MapLoader.game.map[x][y].terrain = Terrain.getBridge();
                    break;
                case Terrain.ROAD:
                    MapLoader.game.map[x][y].terrain = Terrain.getRoad();
                    break;
                case Terrain.REEF:
                    MapLoader.game.map[x][y].terrain = Terrain.getReef();
                    break;
                case Terrain.SHOAL:
                    MapLoader.game.map[x][y].terrain = Terrain.getShoal();
                    break;
                case Terrain.RIVER:
                    MapLoader.game.map[x][y].terrain = Terrain.getRiver();
                    break;
                case Terrain.WOOD:
                    MapLoader.game.map[x][y].terrain = Terrain.getWood();
                    break;
                case Terrain.MOUNTAIN:
                    MapLoader.game.map[x][y].terrain = Terrain.getMountain();
                    break;
                case Terrain.SEA:
                    MapLoader.game.map[x][y].terrain = Terrain.getSea();
                    break;
                case Terrain.PLAIN:
                    MapLoader.game.map[x][y].terrain = Terrain.getPlain();
                    break;
                case Terrain.SILO:
                case Terrain.PIPE:
                case Terrain.PIPE_JOINTS:
            }
            //DEBUG
            //System.out.println("Terrain assigned: " + terrainType + " @(" + x + "," + y + ")");
        }
    }

    static void assignPlayerToProperty(byte playerID, byte propertyType, byte x, byte y) {
        //DEBUG
        //System.out.println("on assignPlayerToProperty(" + playerID + "," + propertyType + "," + x + "," + y + ")");
        if (MapLoader.game.map[x][y] != null) {
            if (MapLoader.game.map[x][y].isProperty) {
                //DEBUG
                //System.out.println("map[" + x + "][" + y + "] is a property!!");
                MapLoader.game.map[x][y].property = Property.getAbstractProperty(propertyType);
                MapLoader.game.map[x][y].property.propertyType = propertyType;
                //Player 0 is Gaia
                MapLoader.game.map[x][y].propertyInstance = new PropertyInstance(game.players[playerID]);
            }
            else {
                //DEBUG
                //System.out.println("map[" + x + "][" + y + "] is NOT a property!!");

            }
        }
    }
}
