package aw2m;

/**
 * This class etc.
 *
 * @author Alejandro Téllez G. <java.util.fck@hotmail.com>
 * @date 18/06/2013 - 03:04:11 AM
 */
public class PropertyInstance {

    /**
     * The player that owns this property. If no player owns it, this reference
     * equals null; 0. Neutral 1. Orange 2. Blue 3. Green 4. Yellow 5. Black etc
     */
    Player player;
    /**
     * The amount of capture points available for this property. All start with
     * 20 and lower if an inftry-type unit starts to capture it.
     */
    byte capture;
    /**
     * The abstract property from which this instance is based.
     */
    Property property;

    public PropertyInstance(Player player) {
        this.player = player;
        this.capture = 20;
    }
}
