package aw2m.common.core;

/**
 * This class represents an instance of a Property owned by a player, storing
 * info such as the Player object that owns it and it's capture points.
 *
 * @author Alejandro Téllez G. <java.util.fck@hotmail.com>
 * @date 18/06/2013 - 03:04:11 AM
 */
public class PropertyInstance {

    /**
     * The player that owns this property. If no player owns it, this reference
     * equals null; 0. Neutral 1. Orange 2. Blue 3. Green 4. Yellow 5. Black etc
     */
    public Player player;
    /**
     * The amount of capture points available for this property. All start with
     * 20 and lower if an inftry-type unit starts to capture it.
     */
    public byte capture;

    /**
     * The abstract property from which this instance is based.
     */
    //Property property;
    public PropertyInstance(Player player) {
        this.player = player;
        this.capture = 20;
    }

    /**
     * Used when reconstructing a game from deserialization.
     *
     * @param player
     * @param capture
     */
    public PropertyInstance(Player player, byte capture) {
        this.player = player;
        this.capture = capture;
    }
}
