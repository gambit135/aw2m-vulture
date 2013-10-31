package aw2m.common.stats;

import aw2m.common.core.GameInstance;
import aw2m.common.core.GridCell;
import aw2m.common.core.Player;
import aw2m.common.core.Unit;

/**
 *
 * @author Alejandro Téllez G. <java.util.fck@hotmail.com>
 * @date 14/08/2013 - 01:33:09 AM
 */
public class Statistic {

    public static int calculateByteSizeOfStringOverhead(String eval) {
        return (int) (8 * (((eval.length() * 2) + 45) / 8));
    }

    /**
     *
     * @param eval
     * @return
     */
    public static int calculateByteSizeOfString(String eval) {
        return eval.getBytes().length;
    }

    /**
     * Returns a unit object size on bytes
     *
     * @param u
     * @return
     */
    /**
     * Expected max: 9 bytes
     *
     * @param u
     * @return
     */
    public static byte calculateBitSizeOfUnit(Unit u) {
        byte sizeOnBits = 0;

        //byte: unit type
        sizeOnBits += 8;
        //byte: total ammo 
        sizeOnBits += 8;
        //boolean: hasMoved
        sizeOnBits += 1;
        //Reference: Player
        if (u.player != null) {
            sizeOnBits += 32;
        }
        //Reference: location
        if (u.location != null) {
            sizeOnBits += 32;
        }
        return sizeOnBits;
    }

    /**
     * Expected max: 26 bytes
     *
     * @param g
     * @return
     */
    public static byte calculateBitSizeOfGridCell(GridCell g) {
        byte sizeOnBits = 0;

        //byte x
        sizeOnBits += 8;
        //byte y
        sizeOnBits += 8;
        //Reference Unit
        //Reference: Player
        if (g.unit != null) {
            sizeOnBits += 32;
        }
        //Reference Terrain
        if (g.terrain != null) {
            sizeOnBits += 32;
        }
        //Reference Property
        if (g.property != null) {
            sizeOnBits += 32;
            //  include property info as well
            //Byte repairs
            sizeOnBits += 8;
            //Byte propertyType
            sizeOnBits += 8;
        }
        //Boolean isProperty
        sizeOnBits += 1;
        //Reference PropertyInstance
        if (g.propertyInstance != null) {
            sizeOnBits += 32;
            //  Include PropertyInstance info as well
            //Reference: player
            sizeOnBits += 32;
            //byte: capture
            sizeOnBits += 8;
        }
        return sizeOnBits;
    }

    public static int calculateBitSizeOfPlayer(Player p) {
        int bits = 0;

        //13 booleans
        bits += 13;

        //6 references
        bits += 6 * 32;

        //4 bytes
        bits += 32 * 4;

        //1 int
        bits += 32;
        return bits;
    }

    public static int convertBitsToBytes(int bits) {
        int bytes = (byte) bits / 8;
        if (bytes % 8 != 0) {
            bytes++;
        }
        return bytes;
    }

    public static void estimate(int ownUnits, int enemyUnits, int sizeX, int sizeY, double numberOfProperties) {

        System.out.println("own units: " + ownUnits);
        System.out.println("enemy units: " + enemyUnits);

        double enemyDensity = enemyUnits / ownUnits;
        System.out.println("Enemy density: " + enemyDensity);

        int branches = ownUnits * enemyUnits;
        System.out.println("Generated branches: " + branches);

        int totalNodes = branches * ownUnits;
        System.out.println("ESTIMATED of total nodes generated: " + totalNodes);


        double averageNodesPerBranch = 0;
        if (branches > 0) {
            averageNodesPerBranch = totalNodes / branches;
        }
        else {
            averageNodesPerBranch = 0;
        }

        System.out.println("Average of nodes per branch: " + averageNodesPerBranch);

        int estimatedCallsToEval = totalNodes;
        System.out.println("Estimated calls to eval function: " + estimatedCallsToEval);

        double estimatedMemoryConsumptionOfNodes = (totalNodes * 10 * 9) + (ownUnits * enemyUnits * 8);
        System.out.println("Estimated MEMORY CONSUMPTION OF NODES: " + estimatedMemoryConsumptionOfNodes);


        double estimatedMemoryConsumption = estimateBytesOfMemoryConsumption(sizeX, sizeY, (ownUnits + enemyUnits), numberOfProperties, enemyUnits);
        System.out.println("Estimated memory consumption NO NODES" + estimatedMemoryConsumption);

        estimatedMemoryConsumption += estimatedMemoryConsumptionOfNodes;


        double bytesToSendBySerialize = 8.4 * (numberOfProperties) + (18.5 * (ownUnits + enemyUnits));
        System.out.println("Estimated bytes to serialize: " + bytesToSendBySerialize);
        System.out.println("\n\n");



    }

    public static int estimateBytesOfMemoryConsumption(int sizeX, int sizeY, int totalUnits, double propertyDensity, int numberOfPlayers) {
        //Account all gridCells. Each gridcell occupies approximately 26 bytes.
        int properties = sizeX * sizeY * 26;
        int units = totalUnits * 9;
        int players = 10 * numberOfPlayers;

        return properties + units + players;
    }

    public static int estimatePlayerData(byte units) {
        return 0;
    }

    public static byte getNodeMemoryConsumtpionOnBytes() {
        return 10 + 9;
    }

    public static long getTotalMemoryConsumptionOfApplicationOnBytes(GameInstance game) {
        long bits = 0;

        //First, account for the state of the game

        //All gridCells on map, including properties and units
        for (GridCell gridRow[] : game.map) {
            for (GridCell gridCell : gridRow) {
                bits+= Statistic.calculateBitSizeOfGridCell(gridCell);
                if (gridCell.unit != null){
                    bits += Statistic.calculateBitSizeOfUnit(gridCell.unit);
                }
            }
        }

        //Plus all players on map
        for (Player player : game.players){
            bits += Statistic.calculateBitSizeOfPlayer(player);
        }

        return bits;
    }
}
