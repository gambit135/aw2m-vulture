package aw2m;

/**
 *
 * @author Alejandro Téllez G. <java.util.fck@hotmail.com>
 */
public class Game {

    /**
     * Theplayer in turn
     */
    Player currentPlayer;
    /**
     * The number of players (i.e, colors available), as determined by
     * map conditions
     */
    public static byte numberOfPlayers;
    //Should be initialized before the map is loaded
    public static Player players[];
    public static byte weather;
    public static byte currentWeather;
    //DEBUG
    static{
        players = new Player[4];
        players[0] = new Player();
        players[1] = new Player();
        players[2] = new Player();
        players[3] = new Player();
    }
}
