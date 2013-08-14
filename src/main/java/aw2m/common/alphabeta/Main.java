package aw2m.common.alphabeta;

import aw2m.common.core.CO;
import aw2m.common.core.DijkstraElement;
import aw2m.common.core.GridCell;
import aw2m.common.core.Logic;
import aw2m.common.core.Player;
import aw2m.common.core.Terrain;
import aw2m.common.core.Unit;
import java.util.LinkedList;

/**
 *
 * @author Alejandro Téllez G. <java.util.fck@hotmail.com>
 * @date 20/04/2013 - 04:19:13 PM
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static int depth = 6;
    // public static LinkedList<DangerGridCell> dangerZone;

    public static void main(String[] args) {

        GridCell map[][] = new GridCell[33][38];
        System.out.println("La longitud X del mapa es " + map.length);
        System.out.println("La longitud Y del mapa es " + map[0].length);
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

        //TEST FOR TERRAIN affecting movement
        map[6][9].terrain = Terrain.getWood();
        map[8][9].terrain = Terrain.getWood();
        map[10][9].terrain = Terrain.getWood();
        map[12][9].terrain = Terrain.getWood();

        /*Testing the movement crawl algorithm*/
        //Requires previously set map on Logic
        Unit u = new Unit();
        u.location = map[9][9];
        u.unitType = Unit.FIGHTER;
        u.player = new Player((byte)1);
        u.player.currentCO = new CO(CO.ANDY);

        LinkedList<DijkstraElement> movementList = Logic.calculateMovementRadiusUsingAStar(u, map);
        System.out.println("movementList\n" + movementList);
        System.out.println("Iterations: " + Logic.iterations);
        System.out.println("List Size: " + movementList.size());

        //dangerZone = Logic.getDangerZone(movementList);


        //Create a new MAX Node
        Node n = new Node(-500, 500, true, 6, 1);

        //n.movementList = movementList;
        //n.dangerZone = Logic.getDangerZone(movementList);
        //n.setNumberOfChildren(Logic.iterations);

        AlphaBeta.alphaBeta(n, -500, 500, Main.depth);
    }
}
