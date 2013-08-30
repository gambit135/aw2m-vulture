package aw2m.common.core;

import aw2m.common.core.GridCell;
import aw2m.common.core.GridCell;

/**
 *
 * @author Alejandro Téllez G. <java.util.fck@hotmail.com>
 * @date 12/04/2013 - 09:39:02 PM
 */
public class DijkstraElement implements Comparable<DijkstraElement> {

    GridCell gridCell;
    byte movementPointsLeft;

    DijkstraElement(GridCell gridCell) {
        this.gridCell = gridCell;
    }

    DijkstraElement(GridCell gridCell, byte movementPointsLeft) {
        this.gridCell = gridCell;
        this.movementPointsLeft = movementPointsLeft;
    }

    @Override
    public boolean equals(Object o) {
        //DEBUG
        //System.out.println("Called to equals @DijkstraElement");
        if (o instanceof DijkstraElement) {
            DijkstraElement e = (DijkstraElement) o;
            if (e.gridCell.equals(this.gridCell)) {
                //DEBUG
                //System.out.println(this.toString() + " equals " + o.toString());
                return true;
            }
        }
        //DEBUG
        //System.out.println(this.toString() + " != " + o.toString());
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + (this.gridCell != null ? this.gridCell.hashCode() : 0);
        hash = 53 * hash + this.movementPointsLeft;
        return hash;
    }

    public int compareTo(DijkstraElement o) {
        //If THIS is greater than o, return a positive value
        //If THIs is smaller than o, return negative value
        //If THIS is equal to o, return 0
        return this.movementPointsLeft - o.movementPointsLeft;
    }

    @Override
    public String toString() {
        return this.gridCell.toString() + " M = " + this.movementPointsLeft;
    }
}
