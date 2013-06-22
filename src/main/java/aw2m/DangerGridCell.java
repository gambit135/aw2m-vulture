package aw2m;

/**
 * This class stores a reference to a GridCell object, and a danger value,
 * relative to the unit that called the getDangerZone, for what this object was
 * created. There exists a one to one correspondence between the movement radius
 * and danger zone.
 *
 * @author Alejandro Téllez G. <java.util.fck@hotmail.com>
 * @date 21/04/2013 - 02:15:23 AM
 */
public class DangerGridCell implements Comparable<DangerGridCell> {

    public GridCell gridCell;
    public double dangerValue;

    DangerGridCell(GridCell gridCell, double danger) {
        this.gridCell = gridCell;
        this.dangerValue = danger;
    }

    public int compareTo(DangerGridCell o) {
        if (this.dangerValue < o.dangerValue) {
            return -1;
        }
        if (this.dangerValue == o.dangerValue) {
            return 0;
        }
        if (this.dangerValue > o.dangerValue) {
            return 1;
        }
        return (int) (this.dangerValue - o.dangerValue);
    }

    @Override
    public boolean equals(Object o) {

        if (o instanceof DangerGridCell) {
            DangerGridCell d = (DangerGridCell) o;
            if (d.gridCell.equals(this.gridCell)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + (this.gridCell != null ? this.gridCell.hashCode() : 0);
        hash = 83 * hash + (int) (Double.doubleToLongBits(this.dangerValue) ^ (Double.doubleToLongBits(this.dangerValue) >>> 32));
        return hash;
    }
}
