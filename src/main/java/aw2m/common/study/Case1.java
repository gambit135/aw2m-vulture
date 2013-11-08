package aw2m.common.study;

import aw2m.common.ai.Search;
import aw2m.common.ai.eval.BranchEvalFunctions;
import aw2m.common.ai.model.Branch;
import aw2m.common.core.GameInstance;
import aw2m.common.core.GridCell;
import aw2m.common.core.Unit;
import aw2m.common.factory.UnitFactory;
import aw2m.common.serialize.MakePostRequest;
import aw2m.common.serialize.Serialize;
import aw2m.common.stats.Statistic;
import aw2m.remote.creator.maploader.MapCatalog;
import aw2m.remote.creator.maploader.MapLoader;
import java.util.Calendar;
import java.util.Date;

/**
 * This class depicts the first case of tests: 50 attackers vs 0 defenders.
 *
 * This is easily achievable by putting 50 B-Copter units on random or
 * sequential locations on Spann Island, with no enemy to attack
 *
 * @author Alejandro Téllez G. <java.util.fck@hotmail.com>
 * @date 31/10/2013 - 12:21:48 AM
 */
public class Case1 {

    static GameInstance mainGameInstance;
    static boolean isRemote;
    public static Date timeBeforeAlgorithm;
    public static Date timeAfterAlgorithm;
    public static Date dawnOfTimes;
    public static Date endOfTimes;
    public static Date timeBeforeExecutingRequest;
    public static Date timeAfterExecutingRequest;
    public static String serializedPlayers;
    public static String serializedTerrain;
    public static String serializedProperties;
    public static String serializedUnits;

    public static void main(String args[]) {

        dawnOfTimes = new Date();

        isRemote = false;

        //A test is divided unto stages:

        //Stage 1: Create a game instance and load a map

        //Load Spann Island
        System.out.println("Gameflow starated");
        // First, an instance of GameInstance must be created
        // Specify which map to play
        System.out.println("Creating GameInstance");
        mainGameInstance = new GameInstance(MapCatalog.POINT_STORMY); //
        // Players are initialized
        // A map must be loaded unto that mainGameInstance
        System.out.println("Loading map");
        MapLoader.loadMapFromCatalog(mainGameInstance,
                                     mainGameInstance.mapChosen);

        // DEBUG
        System.out.println("Number of players on map: "
                + MapCatalog.getNoOfPlayers(mainGameInstance.mapChosen));


        //Stage 2: Create and deploy units.
        //  A submodule that creates units on demand and deploys them on valid terrain is needed

        generateUnits();

        //Stage 2.5 - If its remote - serialize and send. Wait and receive
        if (isRemote) {

            //Then, assemble the request
            //Use Android Code
            MakePostRequest request = new MakePostRequest(mainGameInstance);

            //This line goes before request.executePostRequest();
            timeBeforeExecutingRequest = new Date();

            String result = request.executePostRequest();

            //This line goes after request.executePostRequest();
            timeAfterExecutingRequest = new Date();

            System.out.println("THIS SHOULD PRINT THE RESPONSE BODY");
            System.out.println(result);
        }
        else {
            //Stage 3: Combat tests - Local
            timeBeforeAlgorithm = new Date();

            Search search = new Search(mainGameInstance.map);
            Branch branch = search.createBranchFromOptimalNodes(mainGameInstance.players[1].units);
            if (branch != null) {
                branch.evalValue = BranchEvalFunctions.evalBranch(branch);
                System.out.println("Total branch eval value: " + branch.evalValue);
                System.out.println("\n Total branches on Branch list: " + search.branches.size());
                System.out.println("\nOptimal branch:" + search.findOptimalBranchFromBranches(search.branches).toString());
            }
            else {
                System.out.println("\nTotal branches on Branch list: " + search.branches.size());
                System.out.println("No optimal branch was generated");
            }
            timeAfterAlgorithm = new Date();
        }

        endOfTimes = new Date();

        //Stage 4: Account Stats

        if (isRemote) {
        }
        else {
        }
    }

    public static void generateUnits() {
        //For the first case, just create 50 B-Copters on any location.
        byte createdUnits = 0;
        //Iterate throgh array without considering dimensions
        for (GridCell gridRow[] : mainGameInstance.map) {
            for (GridCell gridCell : gridRow) {
                if (createdUnits == 50) {
                    break;
                }
                gridCell.unit = UnitFactory.getNewUnit(Unit.B_COPTER, mainGameInstance.players[1], gridCell);
                createdUnits++;
            }
        }
        //Check created units
        for (Unit unit : mainGameInstance.players[1].units) {
            System.out.println(unit.toString());
        }
        System.out.println("Number of units loaded for attacker: " + mainGameInstance.players[1].units.size());
    }

    public static void printStats(int ownUnits, int enemyUnits, int nodesGenerated, Search search) {
        System.out.println("Own Units: " + ownUnits);
        System.out.println("Enemy Units: " + enemyUnits);
        //Nodes to generate
        //Without prunning

        int nodesWithoutPrunning = ownUnits * enemyUnits * (1 + ((ownUnits - 1) * enemyUnits));
        System.out.println("Nodes without prunning: " + nodesWithoutPrunning);
        System.out.println("Nodes without prunning on bytes: " + nodesWithoutPrunning * Statistic.getNodeMemoryConsumtpionOnBytes());

        System.out.println("Nodes with prunning: " + search.nodesGenerated);
        System.out.println("Nodes with prunning on bytes: " + (search.nodesGenerated * Statistic.getNodeMemoryConsumtpionOnBytes()));

        System.out.println("Time before running AI algorithm: " + timeBeforeAlgorithm);
        System.out.println("Time before running AI algorithm: " + timeAfterAlgorithm);

        System.out.println("Total Memory Consumption of Application: " + Statistic.getTotalMemoryConsumptionOfApplicationOnBytes(mainGameInstance));

        System.out.println("Time since the dawn of times: " + dawnOfTimes);
        System.out.println("Time since the end of times: " + endOfTimes);

        if (isRemote) {
            //Serialize stuff
            Serialize s = new Serialize();

            //Serialize players
            System.out.println("Serialized players");
            serializedPlayers = s.serializePlayers(mainGameInstance);
            System.out.println(serializedPlayers);

            //Serialize terrain
            System.out.println("Serialized terrain");
            serializedTerrain = s.serializeTerrain(mainGameInstance);
            System.out.println(serializedTerrain);

            //Serialize properties
            System.out.println("Serialized properties");
            serializedProperties = s.serializeProperties(mainGameInstance);
            System.out.println(serializedProperties);

            //Serialize units
            System.out.println("Serialized units");
            serializedUnits = s.serializeUnits(mainGameInstance);
            System.out.println(serializedUnits);

            //Bytes to serialize
            int bytesToSerialize = 0;
            bytesToSerialize += Statistic.calculateByteSizeOfString(serializedPlayers);
            bytesToSerialize += Statistic.calculateByteSizeOfString(serializedProperties);
            bytesToSerialize += Statistic.calculateByteSizeOfString(serializedTerrain);
            bytesToSerialize += Statistic.calculateByteSizeOfString(serializedUnits);
            System.out.println("Total of bytes to serialize: " + bytesToSerialize);
        }







    }
}
