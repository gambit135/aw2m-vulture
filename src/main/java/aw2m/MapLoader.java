package aw2m;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * This class reviews all the existing maps and shows info about them, prior to
 * be chosen (and therefore, rendered)
 *
 * @author Alejandro Téllez G. <java.util.fck@hotmail.com>
 */
public class MapLoader {

    static BufferedReader br;
    static BufferedWriter bw;
    static GridCell[][] map;
    static byte Xindex;
    static byte Yindex;

    static {
        MapLoader.Xindex = 0;
        MapLoader.Yindex = 0;
        //MapLoader.map =
    }
    /**
     * The sizes of the map to load, as specified by the MAP file
     */
    public static byte sizeX, sizeY;

    public static boolean readMap(String archivo) {

        //DEBUG
        File f = new File(archivo);
        String ruta = f.getAbsolutePath();
        //System.out.println("Ruta: " + ruta);
        try {
            br = new BufferedReader(new FileReader(archivo));
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
                //System.out.println("Can't read the CSV line!");
                return false;
            }
        }
        // Cierra el lector de entrada abierto.
        try {
            br.close();
        }
        catch (IOException exc2) {
            System.out.println("IO Error at closing file!");
            return false;
        }
        return true;
    }

    /**
     * Processes a given line, interating along it, searching for CSVs. Uses the
     * sizeX of the MAP file to iterate a finite number of times
     *
     * @param line A line of the CSV file to be processed, that may contain CSV
     *             values
     */
    private static void processCSVLine(String line, byte y) {
        for (byte x = 0; x < MapLoader.sizeX; x++) {
            //DEBUG
            //  System.out.println("Map Loader.sizeX: " + MapLoader.sizeX);
            // System.out.println("byte x: " + x);
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
        System.out.println("On findNextCSV");
        //If there are still CSVs remaining to be processed
        if (line != null) {
            //Find the end of the next CSV
            index = line.indexOf(",");
            switch (index) {
                //There are no CSVs left to be processed
                case -1:
                    //          System.out.println("There are no CSVs left to be processed");
                    return null;
                //There is an error
                //There can't be empty CSV (i.e. two straight commas in a row)
                //The process should be aborted
                case 0:
                    //        System.out.println("This is an empty CSV");
                    return null;
                //Otherwise, there is a non-empty CSV to be processed
                default:
                    //Do something with the found CSV
                    //DEBUG
                    System.out.println("Doing something with found csv");
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
        byte playerID;
        if (csv != null) {
            //Find the TERRAIN and PLAYER part of the csv
            index = csv.indexOf(".");
            switch (index) {
                //There is an error, there can't be a "." after a ","
                //Process should be aborted
                case 0:
                    //DEBUG
                    System.out.println("There is an error, there can't be a . after a ,");
                    return;

                //There is only a TERRAIN part, (i.e. this is not a property)
                case -1:
                    //DEBUG
                    System.out.println("There is only a TERRAIN part, (i.e. this is not a property)");
                    //Get the Terrain part
                    System.out.println("Get the Terrain part");
                    terrainType = Byte.parseByte(csv);
                    MapLoader.assignTerrain(terrainType, x, y);
                    return;

                //Otherwise, there is a TERRAIN and a PLAYER part
                default:
                    //Get the Terrain part
                    //    System.out.println("Otherwise, there is a TERRAIN and a PLAYER part");
                    terrainType = Byte.parseByte(csv.substring(0, index));
                    MapLoader.assignTerrain(terrainType, x, y);
                    //Get property's PLAYER part
                    //  System.out.println("Get property's PLAYER part");
                    playerID = Byte.parseByte(csv.substring(++index));
                    MapLoader.assignPlayerToProperty(playerID, terrainType, x, y);
                    return;
            }
        }
    }

    public static void assignTerrain(byte terrainType, byte x, byte y) {
        Terrain t = null;
        //DEBUG
        //System.out.println("ON assignTerrain(" + terrainType + "," + x + "," + y + ")");
        if (MapLoader.map[x][y] != null) {
            switch (terrainType) {
                case Terrain.HQ:
                case Terrain.CITY:
                case Terrain.BASE:
                case Terrain.AIRPORT:
                case Terrain.PORT:
                    //If its a property
                    MapLoader.map[x][y].isProperty = true;
                    MapLoader.map[x][y].terrain = Terrain.getAbstractProperty(terrainType);
                    //If it's an HQ, plus one star defense
                    if (terrainType == Terrain.HQ) {
                        MapLoader.map[x][y].terrain.defenseStars++;
                    }
                    //If it's a PORT, sea and coast movement units can transverse it
                    if (terrainType == Terrain.PORT) {
                        MapLoader.map[x][y].terrain.setCanTransverse(Unit.SEA_MOV, true);
                        MapLoader.map[x][y].terrain.setCanTransverse(Unit.COAST_MOV, true);
                        //at the cost of 1
                        MapLoader.map[x][y].terrain.movementCost[Unit.SEA_MOV] = 1;
                        MapLoader.map[x][y].terrain.movementCost[Unit.COAST_MOV] = 1;
                    }
                case Terrain.BRIDGE:
                    MapLoader.map[x][y].terrain = Terrain.getBridge();
                    break;
                case Terrain.ROAD:
                    MapLoader.map[x][y].terrain = Terrain.getRoad();
                    break;
                case Terrain.REEF:
                    MapLoader.map[x][y].terrain = Terrain.getReef();
                    break;
                case Terrain.SHOAL:
                    MapLoader.map[x][y].terrain = Terrain.getShoal();
                    break;
                case Terrain.RIVER:
                    MapLoader.map[x][y].terrain = Terrain.getRiver();
                    break;
                case Terrain.WOOD:
                    MapLoader.map[x][y].terrain = Terrain.getWood();
                    break;
                case Terrain.MOUNTAIN:
                    MapLoader.map[x][y].terrain = Terrain.getMountain();
                    break;
                case Terrain.SEA:
                    MapLoader.map[x][y].terrain = Terrain.getSea();
                    break;
                case Terrain.PLAIN:
                    MapLoader.map[x][y].terrain = Terrain.getPlain();
                    break;
                case Terrain.SILO:
                case Terrain.PIPE:
                case Terrain.PIPE_JOINTS:
            }
            System.out.println("Terrain assigned: " + terrainType + " @(" + x + "," + y + ")");
        }
    }

    static void assignPlayerToProperty(byte playerID, byte propertyType, byte x, byte y) {
        //  System.out.println("on assignPlayerToProperty(" + playerID + "," + propertyType + "," + x + "," + y + ")");
        if (MapLoader.map[x][y] != null) {
            if (MapLoader.map[x][y].isProperty) {
                //DEBUG
                System.out.println("map[" + x + "][" + y + "] is a property!!");
                //MapLoader.map[x][y].property = new Property();
                //MapLoader.map[x][y].property.propertyType = propertyType;
                //MapLoader.map[x][y].property.captures = 20;
                //MapLoader.map[x][y].property.player = Game.players[playerID];
                switch (propertyType) {
                    case Terrain.HQ:
                    case Terrain.CITY:
                    case Terrain.BASE:
                        MapLoader.map[x][y].property.repairs = Unit.LAND_CLASS;
                        break;
                    case Terrain.AIRPORT:
                        MapLoader.map[x][y].property.repairs = Unit.SEA_CLASS;
                        break;
                    case Terrain.PORT:
                        MapLoader.map[x][y].property.repairs = Unit.LAND_CLASS;
                        break;
                }
            }
            else {
                //DEBUG
                System.out.println("map[" + x + "][" + y + "] is NOT a property!!");

            }
        }
    }

    void readGraphicsMap() {
    }
}
