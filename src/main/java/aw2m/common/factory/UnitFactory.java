package aw2m.common.factory;

import aw2m.common.core.GridCell;
import aw2m.common.core.Player;
import aw2m.common.core.Unit;

/**
 * Creates and deploys units.
 *
 * @author Alejandro Téllez G. <java.util.fck@hotmail.com>
 * @date 13/06/2013 - 03:28:08 AM
 */
public class UnitFactory {

    /**
     * Returns a new unit, belonging to the player passed as parameter, and
     * located on the specified location.
     *
     * @param unitType The unit type to be built
     * @param player   The player to which belongs this unit
     * @param location The location on the map on where this unit will be
     *                 deployed
     * @return
     */
    public static Unit getNewUnit(byte unitType, Player player, GridCell location) {
        Unit unit;
        if (Unit.getTotalAmmo(unitType) == -1) {
            unit = new Unit(unitType, (byte) 10, player, Unit.getTotalFuel(unitType), true, location);
        }
        else {
            unit = new Unit(unitType, (byte) 10, player, Unit.getTotalFuel(unitType), Unit.getTotalAmmo(unitType), true, location);
        }
        return unit;
    }
}
