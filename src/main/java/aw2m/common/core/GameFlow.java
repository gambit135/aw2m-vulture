package aw2m.common.core;

import aw2m.common.serialize.Deserialize;
import aw2m.common.serialize.MakePostRequest;
import aw2m.common.serialize.Serialize;
import aw2m.remote.creator.maploader.MapCatalog;
import aw2m.remote.creator.maploader.MapLoader;

/**
 * The main method of this class will host the mainGameInstance flow, with
 * players taking turns and actions within them.
 *
 * @author Alejandro Téllez G. <java.util.fck@hotmail.com>
 * @date 23/07/2013 - 08:21:44 PM
 */
public class GameFlow {

    static GameInstance mainGameInstance;

    /**
     *
     *
     * @param args
     */
    public static void main(String args[]) {
        //Game Started
        System.out.println("Gameflow starated");

        //First, an instance of GameInstance must be created
        //Specify which map to play
        System.out.println("Creating GameInstance");
        mainGameInstance = new GameInstance(MapCatalog.SPANN_ISLAND);
        //Players are initialized
        //A map must be loaded unto that mainGameInstance
        System.out.println("Loading map");

        MapLoader.loadMapFromCatalog(mainGameInstance, mainGameInstance.mapChosen);
        //game.map = MapLoader.map;

        //DEBUG
        System.out.println("Number of players on map: " + MapCatalog.getNoOfPlayers(mainGameInstance.mapChosen));


        //BEGGINING OF TESTS
        /*
         * Data to be serialized:
         * + Size X and Y of the map OR the predefined map chosen
         * + Players, units, terrain
         */

        //Testing Serialize
        Serialize s = new Serialize();

        //Testing serialize players
        System.out.println("Serialized players");
        System.out.println(s.serializePlayers(mainGameInstance));

        //Testing serialize terrain
        System.out.println("Serialized terrain");
        System.out.println(
                s.serializeTerrain(mainGameInstance));

        //Testing serialize properties
        System.out.println("Serialized properties");
        System.out.println(s.serializeProperties(mainGameInstance));

        //Testing serialize units
        Unit u1 = new Unit();
        u1.location = mainGameInstance.map[9][9];
        u1.unitType = Unit.ROCKETS;
        u1.player = mainGameInstance.players[1];
        u1.currentHP = 10;
        u1.currentFuel = Unit.getTotalFuel(u1.unitType);
        u1.currentAmmo = 4;
        mainGameInstance.players[1].units.add(u1);

        Unit u2 = new Unit();
        u2.location = mainGameInstance.map[8][4];
        u2.unitType = Unit.T_COPTER;
        u2.player = mainGameInstance.players[2];
        u2.currentHP = 2;
        u2.currentFuel = Unit.getTotalFuel(u2.unitType);
        u2.currentAmmo = Unit.getTotalAmmo(u2.unitType);
        mainGameInstance.players[2].units.add(u2);

        System.out.println("Serialized units");
        System.out.println(s.serializeUnits(mainGameInstance));

        //Testing deserialize


        Deserialize d = new Deserialize();

        //Testing deserealize players
        System.out.println("Deseralized players:");
        Player[] players;
        players = d.deserializePlayers(s.serializePlayers(mainGameInstance));

        //Testing deserealize terrain
        System.out.println("Deseralized terrain:");
        GridCell[][] map = d.deserializeTerrain(
                s.serializeTerrain(mainGameInstance),
                (byte) mainGameInstance.map.length,
                (byte) mainGameInstance.map[0].length);

        //Testing deserealize properties
        System.out.println("Deseralized properties:");
        /*
         d.deserializeProperties(s.serializeProperties(mainGameInstance),
         d.deserializeTerrain(
         s.serializeTerrain(mainGameInstance),
         MapCatalog.getXsize(mainGameInstance.mapChosen),
         MapCatalog.getYsize(mainGameInstance.mapChosen)),
         d.deserializePlayers(
         s.serializePlayers(mainGameInstance)));
         */
        d.deserializeProperties(s.serializeProperties(mainGameInstance), map, players);
        //Testing deserealize units
        System.out.println("Deserealized units:");
        /*
         d.deserializeUnits(s.serializeUnits(mainGameInstance),
         d.deserializeTerrain(
         s.serializeTerrain(mainGameInstance),
         MapCatalog.getXsize(mainGameInstance.mapChosen),
         MapCatalog.getYsize(mainGameInstance.mapChosen)),
         d.deserializePlayers(
         s.serializePlayers(mainGameInstance)));
         */
        d.deserializeUnits(s.serializeUnits(mainGameInstance), map, players);

        //To proof the point: first, create a new instance of a game, with reconstructed info.

        //Creating another instance of a game
        GameInstance rebuiltGame = new GameInstance(players, map);

        //Then, serialize the info contained in the rebuilt game.

        //Testing serialize players
        System.out.println("Serialized REBUILT players");
        System.out.println(s.serializePlayers(rebuiltGame));

        //Testing serialize terrain
        System.out.println("Serialized REBUILT terrain");
        System.out.println(s.serializeTerrain(rebuiltGame));

        //Testing serialize properties
        System.out.println("Serialized REBUILT properties");
        System.out.println(s.serializeProperties(rebuiltGame));

        //Testing serialize units
        System.out.println("Serialized REBUILT units");
        System.out.println(s.serializeUnits(rebuiltGame));

        //A reconstructed game should also know who's turn it is to move.

        System.out.println("TEST: Getting sizeX and sizeY only with GridCell[][]");
        System.out.println("Size of map.length " + mainGameInstance.map.length);
        System.out.println("Size of map[0].length " + mainGameInstance.map[0].length);

        System.out.println("Size X of SPANN ISLAND is: " + MapCatalog.getXsize(mainGameInstance.mapChosen));
        System.out.println("Size Y of SPANN ISLAND is: " + MapCatalog.getYsize(mainGameInstance.mapChosen));

        //Testing booleans to String
        boolean bulean = false;
        System.out.println("Boolean value to String: " + bulean);


        //First, deconstruct and reconstruct the game, calling the app on the cloud

        
        //DEBUG
        /* Comment the next line to switch between 
         * not predefined and predefined modes of the map
         * To test two different types of reconstruction on the server side.
         */
        //mainGameInstance.mapChosen = MapCatalog.UNDEFINED;
        
        
        //LAST CHANGE!:
        //Create a new object for executing the HTTP POST request
        MakePostRequest request = new MakePostRequest(mainGameInstance);
        String result = request.executePostRequest();
        System.out.println("THIS SHOULD PRINT THE RESPONSE BODY");
        System.out.println(result);


        //END OF TESTS

        //Then, design the API for the UI

        //A program is a flow...       
        System.out.println("Is player " + mainGameInstance.currentPlayer.id + "'s turn to move");

    }
}
