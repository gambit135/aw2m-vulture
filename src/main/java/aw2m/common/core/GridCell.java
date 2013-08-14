package aw2m.common.core;

/**
 *
 * @author Alejandro Téllez G. <java.util.fck@hotmail.com>
 */
public class GridCell {

    /**
     * The X coordinate of this GridCell.
     */
    public byte x;
    /**
     * The Y coordinate of this GridCell.
     */
    public byte y;
    /**
     * A reference to a Unit resting upon this GridCell
     */
    public Unit unit;
    /**
     * A reference to a Terrain object, held by this GridCell.
     */
    public Terrain terrain;
    /**
     * The sprite image assigned to this GridCell. It can change due to weather,
     * a property being captured by a player, or some other extraordinary event.
     */
    public Sprite sprite;
    /**
     * The property wrapped on the GridCell A gridcell can or can't have a
     * property, depending on the map
     */
    public Property property;
    /**
     * Return true if this GridCell object has (and can be taken as) a property.
     * Return false if this GridCell object is not (and doesn't have bound) a
     * property.
     *
     * Useful too for Kindle combat tactics, as well as building maps logically.
     */
    public boolean isProperty;
    /**
     * Holds a reference for the specific property located on this GridCell,
     * such as capture points and the player who controls it.
     */
    public PropertyInstance propertyInstance;

    @Override
    public String toString() {
        return "" + this.terrain.name + " @ (" + x + "," + y + ") ";
    }

    /**
     * Is called when this GridCell object is selected by some player, with no
     * action implied at all (e.g. is not selecting a destination to a unit,
     * etc)
     *
     * Optios available are
     *
     * showCursorWindow() - returns the info that the cursorWindow is supposed
     * to show selectTopItem() - selects the item on the top + If there' a unit
     * on the GridCell, selects the unit. + If there's a deploy property, pops
     * the deploy menu of such property (to create a unit) + If there's a
     * passive property (not deploy), or other terrain, pop the game menu
     *
     */
    public void onSelect() {
        //There is a unit on the GridCell, select it by default
        if (this.unit != null) {
            this.unit.onSelect();
        }
        if (this.isProperty) {
        }

    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + this.x;
        hash = 97 * hash + this.y;
        return hash;
    }

    @Override
    public boolean equals(Object o) {
        //DEBUG
        //System.out.println("Called to equals @GridCell");
        if (o == this) {
            return true;
        }
        if (o == null || o.getClass() != this.getClass()) {
            return false;
        }
        GridCell e = (GridCell) o;
        if (this.x == e.x && this.y == e.y) {
            //DEBUG
            //System.out.println(this.toString() + " equals " + o.toString());
            return true;

        }
        //DEBUG
        //System.out.println(this.toString() + " != " + o.toString());

        return false;
    }
}
