package aw2m;

import java.util.LinkedList;

/**
 * Player class represents the player on a game. Includes references to the CO
 * (or COs) the player is using, it's own units, funds, score, and other stat
 * information
 *
 * There is a finite and restricted number of objects created from this class.
 * The creation of such objects depends on the map loaded by the MapLoader class
 * (or the class with such responsability). No extra objects shall be created.
 *
 * @author Alejandro Téllez G. <java.util.fck@hotmail.com>
 */
public class Player {

    public static Player players[];

    /**
     * Return a generic Player object, with it's main attributes initialized. By
     * default, a generic player's CO is always Andy.
     *
     * @return A generic Player object.
     */
    public static Player getGenericPlayer() {
        Player generic = new Player();
        generic.currentCO = new CO(CO.ANDY);
        return generic;
    }
    byte id;
    /**
     * The team of the current player. It may (or may not) change during battle
     * Only four teams supported: A, B, C, D. Hypothetically ,it may be able to
     * support up to 255 teams, and a 0 team (no team). If guided by the char
     * type restriction, it might be up to 65,536 teams.
     */
    char teamSymbol;
    byte team;
    CO currentCO;
    CO tagCO;
    int funds;
    int days;
    int powerScore;
    int speedScore;
    int techniqueScore;
    /**
     * The player has no power on
     */
    boolean hasNoPower;
    /**
     * The player has called the normal Power
     */
    boolean hasPowerOn;
    /**
     * The player has called the Super Power!
     */
    boolean hasSuperPowerOn;
    /**
     * The player has called the Tag Power!! and therefore (theorically) the
     * SuperPower
     */
    boolean tagPower;
    LinkedList<Unit> units;
    /**
     * If this player can deploy Neotanks
     */
    boolean canDeployNeotanks;
    /**
     * If this player can deploy Megatanks
     */
    boolean canDeployMegatanks;
    /**
     * If this player can deploy Piperunners
     */
    boolean canDeployPipeunners;
    /**
     * If this player can deploy Stealths
     */
    boolean canDeployStealths;
    /**
     * If this player can deploy Black Bombs
     */
    boolean canDeployBlackBombs;
    /**
     * If this player can deploy Black Boats
     */
    boolean canDeployBlackBoats;
    /**
     * If this player can deploy Carriers
     */
    boolean canDeployCarriers;
    /**
     * If this player can deploy expensive units (used for campaign or
     * skirmish): Md Tank Neotank Megatank Rockets Missiles Fighter Bomber
     * Stealth Black Bomb Battleship Cruiser Submarine Carrier
     */
    boolean canDeployExpensiveUnits;

    /**
     * One player shall be in control of one army. This variable is used to
     * determine the look and feel of the sprites of units and HQ
     */
    //army;
    public Player() {
        this.funds = 0;
        this.powerScore = 0;
        this.speedScore = 0;
        this.techniqueScore = 0;
        this.team = 0;
        this.hasNoPower = true;
        this.hasPowerOn = false;
        this.hasSuperPowerOn = false;
    }

    public void setAW2StandardRulesOnPlayer() {
        this.canDeployBlackBoats = false;
        this.canDeployBlackBombs = false;
        this.canDeployCarriers = false;
        this.canDeployExpensiveUnits = true;
        this.canDeployMegatanks = false;
        this.canDeployNeotanks = true;
        this.canDeployPipeunners = false;
        this.canDeployStealths = false;
    }
}
