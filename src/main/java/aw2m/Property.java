package aw2m;

/**
 * This class host attributes of a property that are general to all property
 * types for all players on all the map, such as which unit class can repair and
 * whether it Property objects are referenced by a GridCell object. Note that
 * there will be at most, 6 objects created from this class at a time on each
 * map.
 *
 * Alejandro Téllez G. <java.util.fck@hotmail.com>
 */
public class Property {

    public static final byte HQ = 0;
    public static final byte CITY = 1;
    public static final byte BASE = 2;
    public static final byte AIRPORT = 3;
    public static final byte PORT = 4;
    //A Silo is not a property
    //public static final byte silo = 5;
    /**
     * Singleton reference to a property representing an abstract HQ.
     */
    private static Property abstractHQ;
    private static Property abstractCity;
    private static Property abstractBase;
    private static Property abstractAirport;
    private static Property abstractPort;

    public static Property getAbstractProperty(byte propertyType) {
        switch (propertyType) {
            case Property.HQ:
                if (Property.abstractHQ == null) {
                    Property.abstractHQ = new Property(Property.HQ, Unit.LAND_CLASS);
                }
                return Property.abstractHQ;
            case Property.CITY:
                if (Property.abstractCity == null) {
                    Property.abstractCity = new Property(Property.CITY, Unit.LAND_CLASS);
                }
                return Property.abstractCity;
            case Property.BASE:
                if (Property.abstractBase == null) {
                    Property.abstractBase = new Property(Property.BASE, Unit.LAND_CLASS);
                }
                return Property.abstractBase;
            case Property.AIRPORT:
                if (Property.abstractAirport == null) {
                    Property.abstractAirport = new Property(Property.AIRPORT, Unit.AIR_CLASS);
                }
                return Property.abstractAirport;
            case Property.PORT:
                if (Property.abstractPort == null) {
                    Property.abstractPort = new Property(Property.PORT, Unit.SEA_CLASS);
                }
                return Property.abstractPort;
        }
        //ERROR
        return null;
    }

    //This methods should be moved to PLAYER
    private static MenuItem[] getBaseDeployMenu() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private static MenuItem[] getAirportDeployMenu() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private static MenuItem[] getPortDeployMenu() {
        throw new UnsupportedOperationException("Not yet implemented");
    }
    /**
     * Which unitSuperClass this property can repair
     */
    public byte repairs;
    /**
     * The type of property: 0. HQ 1. City 2. Base 3. Airport 4. Port
     */
    public byte propertyType;

    //Static Methods
    /**
     *
     * @param propertyType
     * @return
     */
    public static boolean canDeploy(byte propertyType) {
        switch (propertyType) {
            case Terrain.BASE:
            case Terrain.AIRPORT:
            case Terrain.PORT:
                return true;
        }
        return false;
    }

    /**
     * This method should invoke the deployment menus specific for each player
     * on each type of property. E.g., HACHI can deploy units from cities when
     * SuperPower is On, or not all players can deploy all kinds of units at the
     * same time: e.g., before capturing the lab maps, you can't deploy NEO
     * tanks.
     *
     * @param propertyType
     * @return
     */
    public static MenuItem[] getDeployMenu(byte propertyType) {
        MenuItem[] menu = null;
        switch (propertyType) {
            case Terrain.BASE:
                return getBaseDeployMenu();
            case Terrain.AIRPORT:
                return getAirportDeployMenu();
            case Terrain.PORT:
                return getPortDeployMenu();
        }
        return menu;
    }

    private Property(byte propertyType, byte repairs) {
        this.propertyType = propertyType;
        this.repairs = repairs;
    }
    private  Property(byte propertyType) {
        this.propertyType = propertyType;
    }
}
