package aw2m.common.core;

import aw2m.remote.creator.maploader.MapCatalog;

/**
 *
 * @author Alejandro Téllez G. <java.util.fck@hotmail.com>
 */
public class GameInstance {

    /**
     * The player in turn
     */
    Player currentPlayer;
    byte currentPlayerID;
    /**
     * The number of players (i.e, colors available), as determined by map
     * conditions. By default, 2 players (1 and 2) will be assumed. A third
     * player (player 0) will act as GAIA, not taking turns but only existing to
     * solve property owning.
     */
    public byte numberOfPlayers;
    //Should be initialized before the map is loaded
    public Player players[];
    public byte weather;
    public byte currentWeather;
    /**
     * Appoints to a map chosen. If it equals MapCatalog.UNDEFINED, it is not a
     * predefined map.
     */
    public byte mapChosen;
    public GridCell[][] map;

    /**
     * Default constructor. May be used during remote game reconstruction
     */
    public GameInstance() {
    }

    /**
     * GameInstance constructor takes as parameter a predefined map to load and
     * initializes players. Another constructor for not predefined maps should
     * be created.
     */
    public GameInstance(byte mapChosen) {
        //Setting number of players on the map
        this.mapChosen = mapChosen;
        System.out.println("Initial value of 'mapChosen: " + mapChosen);
        this.numberOfPlayers = MapCatalog.getNoOfPlayers(mapChosen);
        //Initializing players
        System.out.println("Default Constructor");
        System.out.println("Initializing players");
        //+1 cause of GAIA
        this.players = new Player[numberOfPlayers + 1];
        for (byte c = 0; c < players.length; c++) {
            players[c] = Player.getGenericPlayer(c);
        }
        System.out.println("Number of total players: (including GAIA) " + players.length);
        mapChosen = MapCatalog.SPANN_ISLAND;

        /*
         //Loading a map
         MapLoader.loadMapFromCatalog(mapChosen);
         this.map = MapLoader.map;
         */
        //Debug


        //Player 1 is Human
        this.players[1].isHuman = true;

        //Map is ready to be played. Set first turn
        this.currentPlayer = players[1];
        this.currentPlayerID = 1;
        //Start playing
        //Game.play();         
    }

    /**
     * Creates a new GameInstance object, receiving player and map info as
     * parameters. This method is used when reconstructing a deserialized game.
     *
     * @param players
     * @param map
     */
    public GameInstance(Player[] players, GridCell[][] map) {
        System.out.println("Constructor used for rebuilding");
        this.players = players;
        this.map = map;
    }

    /**
     * The main method of this class will host the game dynamics.
     *
     * @param args
     */
    public void main(String args[]) {
    }

    void play() {
        do {
            //Request player to move, whether the player is human or AI.
            playerMove();

            //Change turn
            nextTurn();
        }
        while (true);
    }

    void nextTurn() {
        this.currentPlayerID++;
        if (currentPlayerID > this.numberOfPlayers) {
            this.currentPlayerID = 1;
        }
        this.currentPlayer = this.players[this.currentPlayerID];
    }

    void playerMove() {
        if (this.currentPlayer.isHuman) {
        }
        else {
        }
    }
}
