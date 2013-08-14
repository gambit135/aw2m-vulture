package aw2m.common.core;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    public static void main(String[] args) {
        Terrain mount1 = Terrain.getMountain();
        Terrain mount2 = Terrain.getMountain();
        if (mount1.equals(mount2)) {
            System.out.println("Son la misma montaña");
        }

        GridCell map[][] = new GridCell[33][38];
        System.out.println("La longitud X del mapa es " + map.length);
        System.out.println("La longitud Y del mapa es " + map[0].length);


        //Run along the array without considering it's dimensions
        for (GridCell gridRow[] : map) {
            for (GridCell gridCell : gridRow) {
                //System.out.println("Getting to it");
            }
        }
        try {
            ClassLoader.getSystemClassLoader().loadClass("aw2m.Unit");
        }
        catch (ClassNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }


        //DEBUG: Creación de mapa de Prueba: Terreno con planicies
        //DEBUG: Index were bytes.
        for (byte i = 0; i < 33; i++) {
            for (byte j = 0; j < 38; j++) {
                map[i][j] = new GridCell();
                map[i][j].terrain = Terrain.getPlain();
                map[i][j].x = i;
                map[i][j].y = j;
            }
        }

        //DEBUG: Creación de la unidad de prueba
        /*
         Unit infantry = Unit.newInfantry();
         infantry.setX(Byte.parseByte("5"));
         infantry.setY(Byte.parseByte("5"));

         map[infantry.getX()][infantry.getY()].setUnit(infantry);

         //DEBUG: Calcular el radio de movimiento de la infanteria
         Set<GridCell> radio = new HashSet<GridCell>();
         radio = Logic.calculateMovementRadius(infantry, map);
         System.out.println("Movimiento: \n" + radio);
         */

        /*Testing the cross algorythm**/
        /*
         Set<GridCell> cross = new HashSet<GridCell>();
         cross = Logic.getCrossArea(map[5][5], (byte) 3, map);
         System.out.println("Cross \n" + cross);
         */



        /*Testing the movement crawl algorithm*/
        //Requires previously set map on Logic
        Unit u = new Unit();
        u.location = map[9][9];
        u.unitType = Unit.FIGHTER;
        u.player = new Player((byte)1);        
        u.player.currentCO = new CO(CO.ANDY);

        if (u.location == null) {
            System.out.println("location is null!");
        }
        else {
            System.out.println("location is NOT null!");
        }


        //TEST FOR TERRAIN affecting movement
        map[6][9].terrain = Terrain.getWood();
        map[8][9].terrain = Terrain.getWood();
        map[10][9].terrain = Terrain.getWood();
        map[12][9].terrain = Terrain.getWood();


        Set<GridCell> movementSet;
        movementSet = new HashSet<GridCell>(Logic.calculateMovementRadius(u, map));
        System.out.println("movementSet \n" + movementSet);
        System.out.println("Iterations: " + Logic.iterations);
        System.out.println("Set Size: " + movementSet.size());


        //movementSet = new HashSet<GridCell>(Logic.calculateMovementRadiusUsingAStar(u, map));
        int allIterations = 0;
        LinkedList<DijkstraElement> movementList = Logic.calculateMovementRadiusUsingAStar(u, map);
        System.out.println("movementList\n" + movementList);
        System.out.println("Iterations: " + Logic.iterations);
        System.out.println("List Size: " + movementList.size());
        allIterations += Logic.iterations;
        int counter = 0;
        //Generate all possible movement ranges from selecting each of the gridCells on the actual movementList
        for (DijkstraElement d : movementList) {
            u.location = d.gridCell;
            LinkedList<DijkstraElement> calculateMovementRadiusUsingDijkstra = Logic.calculateMovementRadiusUsingAStar(u, map);
            System.out.println("movementList\n" + calculateMovementRadiusUsingDijkstra);
            System.out.println("Iterations: " + Logic.iterations);
            System.out.println("List Size: " + calculateMovementRadiusUsingDijkstra.size());
            allIterations += Logic.iterations;
            counter++;
        }
        System.out.println("ALL Iterations: " + allIterations);
        System.out.println("Counter: " + counter);
        /*

         //Spann Island
         MapLoader.sizeX = 15;
         MapLoader.sizeY = 10;
         map = new GridCell[MapLoader.sizeX][MapLoader.sizeY];
         for (byte i = 0; i < MapLoader.sizeX; i++) {
         for (byte j = 0; j < MapLoader.sizeY; j++) {
         map[i][j] = new GridCell();
         map[i][j].x = i;
         map[i][j].y = j;
         }
         }
         MapLoader.map = map;
         MapLoader.readMap("SpannIsland.csv");

         //MENU
         //noOfPlayers, map,

         System.out.println("Multiplayer");
         System.out.println("Choose Map");
         System.out.println("");
         System.out.println("");
         System.out.println("");
         System.out.println("");
         System.out.println("");
         System.out.println("");
         System.out.println("");
         */
    }
}
