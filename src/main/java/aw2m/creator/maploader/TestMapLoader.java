package aw2m.creator.maploader;

import aw2m.GridCell;

/**
 *
 * @author Alejandro Téllez G. <java.util.fck@hotmail.com>
 * @date 20/06/2013 - 06:42:25 PM
 */
public class TestMapLoader {

    public static void main(String args[]) {
        GridCell map[][];
        MapLoader.sizeX = 15;
        MapLoader.sizeY = 10;
        map = new GridCell[MapLoader.sizeX][MapLoader.sizeY];

        for (byte j = 0; j < MapLoader.sizeY; j++) {
            for (byte i = 0; i < MapLoader.sizeX; i++) {
                map[i][j] = new GridCell();
                map[i][j].x = i;
                map[i][j].y = j;
            }
        }
        MapLoader.map = map;
        System.out.println("Testing remote load file");
        System.out.println("sizeX " + MapLoader.sizeX);
        System.out.println("sizeY " + MapLoader.sizeY);
        System.out.println("Xindex " + MapLoader.Xindex);
        System.out.println("Yindex " + MapLoader.Yindex);
        MapLoader.loadRemoteFile("https://dl.dropboxusercontent.com/u/20278793/aw2m/maps/SpannIsland/SpannIsland.csv");

        System.out.println("\n \n \n \n");
        System.out.println("Print loaded map");
        /*

         for (byte j = 0; j < MapLoader.sizeY; j++) {
         for (byte i = 0; i < MapLoader.sizeX; i++) {
         System.out.println(MapLoader.map[i][j].toString());
         }
         }*/

        for (GridCell gridRow[] : map) {
            for (GridCell gridCell : gridRow) {
                System.out.println(gridCell.toString());
            }
        }
    }
}