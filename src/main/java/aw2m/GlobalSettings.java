package aw2m;

/**
 * The abstraction of a match. 
 * @author Alejandro Téllez G. <java.util.fck@hotmail.com>
 */
public class GlobalSettings {

    //Constants

    /**
     * Whether powers (Super, and TAG)
     */
    boolean powers;

    boolean fow;
    byte weather;
    //Used for sprites: Normal, snowy, desert, wastelands
    byte land;
    int fundsPerProperty;
    //number of days to win
    byte daysToBattle;
    //Number of properties to capture
    byte capt;
    byte visuals;
    //Non defined explicitly by the game
    boolean tag;

    //Version of the COs in the current game
    //(differences in power, co power and day-to-day abilities
    //

    /** If true, uses the AW1 COs (power and day-to-day abilities)*/
    //public static boolean awcos;
    /** If true, uses the AW2 COs (power and day-to-day abilities)*/
    //public static boolean aw2cos;
    /** If true, uses the AWDS COs (power and day-to-day abilities)*/
    //public static boolean awdscos;

    //
    //Version of the Damage Chart used to calculate outcome of battles
    /** If true, uses the AW damage chart to calculate the outcome of battles*/
    public static boolean awChart;
    /** If true, uses the AW2 damage chart to calculate the outcome of battles*/
    public static boolean aw2Chart;
    /** If true, uses the AWDS damage chart to calculate the outcome of battles*/
    public static boolean awdsChart;

    public GlobalSettings(byte noOfPlayers) {
    }
}
