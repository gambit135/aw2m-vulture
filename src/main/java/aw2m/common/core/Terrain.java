package aw2m.common.core;

/**
 * A class to abstract all terrain generation and identification methods, as
 * well as Terrain objects used by GridCell objects to represent the terrain
 * they contain. Terrain objects follow the Singleton pattern: i.e., if two
 * different cells on a mapboard (GridCells) have a terrain of Mountain type,
 * then both have a Terrain reference to the same object: a Terrain object
 * specified as a Mountain, obtained via the getMountain() method within this
 * class.
 *
 * N.B.: When rendering a map graphically, a method must sweep through the
 * mapboard to select the appropriate sprite: e.g., when grouping pipes with
 * other pipes and pipe joints, as well as grouping woods and joining rivers
 * with seas via a waterfall.
 *
 * @author Alejandro Téllez G. <java.util.fck@hotmail.com>
 */
public class Terrain {

    /**
     * terrainType
     */
    public static final byte HQ = 0;
    public static final byte CITY = 1;
    public static final byte BASE = 2;
    public static final byte AIRPORT = 3;
    public static final byte PORT = 4;
    public static final byte BRIDGE = 5;
    public static final byte ROAD = 6;
    public static final byte REEF = 7;
    public static final byte SHOAL = 8;
    public static final byte RIVER = 9;
    public static final byte WOOD = 10;
    public static final byte MOUNTAIN = 11;
    public static final byte SEA = 12;
    public static final byte PLAIN = 13;
    public static final byte SILO = 14;
    public static final byte PIPE = 15;
    public static final byte PIPE_JOINTS = 16;
    //etc
    /**
     * Reference to a Terrain object with the characteristics of a mountain.
     * Following the Singleton pattern, only one instance of a mountain-type
     * Terrain is kept.
     */
    private static Terrain mountain;
    private static Terrain wood;
    private static Terrain plain;
    private static Terrain river;
    private static Terrain reef;
    private static Terrain sea;
    private static Terrain road;
    private static Terrain bridge;
    private static Terrain shoal;
    //etc.
    /*Properties should be defined as abstract entities, only to manage Name, ShortName, MovementCost and CanTransverse attributes.*/
    private static Terrain hq;
    private static Terrain city;
    private static Terrain base;
    private static Terrain airport;
    private static Terrain port;
    private static Terrain silo;
    private static Terrain pipe;
    private static Terrain pipeJoint;
    //Terrain
    public String name;
    public String shortName;
    public String description;
    //String superShortName;
    /**
     * The terrainType identifier, as specified by constants Terrain.PLAIN,
     * Terrain.SILO, etc
     */
    public byte terrainType;
    /**
     * The number of defense stars this terrain has Warning: Do not change this
     * with LASH or KINDLE CO powers Instead, call this number and show a
     * modified version, keeping the original intact
     */
    public byte defenseStars;

    /*
     * Which units with certain movement type can transverse this terrain
     *  movementType:
     *   1. Infntry
     *   2. Mech
     *   3. Tires
     *   4. Treads
     *   5. Air
     *   6. Sea
     *   7.Coast
     *
     */
    private boolean canTransverse[];
    /**
     * Movement cost for each type of movement. Relies on the index movementType
     */
    public byte movementCost[];

    public Terrain() {
        this.canTransverse = new boolean[7];
        this.movementCost = new byte[7];
    }

    /**
     *
     * @param terrainType
     * @param defenseStars
     */
    public Terrain(byte terrainType, byte defenseStars) {
        this.terrainType = terrainType;
        this.defenseStars = defenseStars;
        this.canTransverse = new boolean[7];
        this.movementCost = new byte[7];
    }

    /**
     *
     * @param terrainType
     * @param defenseStars
     * @param name
     * @param shortName
     */
    public Terrain(byte terrainType, byte defenseStars, String name, String shortName) {
        this.terrainType = terrainType;
        this.defenseStars = defenseStars;
        this.name = name;
        this.shortName = shortName;
        this.canTransverse = new boolean[7];
        this.movementCost = new byte[7];
    }

    /**
     * Returns the terrain characteristics for a common land property (i.e., the
     * default 3 defense stars, and not transversable by sea units. May be used
     * for creating cities, bases, ports, airports and silos. Special care must
     * be taken when creating ports, as transversable properties of the Terrain
     * object must be changed.
     *
     * @return a Terrain object with the characteristics of a land property
     */
    public static Terrain getAbstractProperty(byte terrainType) {
        Terrain t = new Terrain();
        t.terrainType = terrainType;
        t.defenseStars = Byte.parseByte("3");
        //canTransverse is set to the parameters of a standard Base
        t.canTransverse[Unit.INFANTRY_MOV] = true;  //Inftry
        t.canTransverse[Unit.MECH_MOV] = true;  //Mech
        t.canTransverse[Unit.TIRES_MOV] = true;  //Tires
        t.canTransverse[Unit.TREADS_MOV] = true;  //Treads
        t.canTransverse[Unit.AIR_MOV] = true;  //Air
        /*SEA units cannot transverse a Property, unless it's a port.
         If it's a Port, modify this from the class Property or from the method that called this method.*/
        t.canTransverse[Unit.SEA_MOV] = false; //Sea
        t.canTransverse[Unit.COAST_MOV] = false; //Coast

        //Movement cost
        t.movementCost[Unit.INFANTRY_MOV] = 1;  //Inftry
        t.movementCost[Unit.MECH_MOV] = 1;  //Mech
        t.movementCost[Unit.TIRES_MOV] = 1;  //Tires
        t.movementCost[Unit.TREADS_MOV] = 1;  //Treads
        t.movementCost[Unit.AIR_MOV] = 1;  //Air
        return t;
    }

    /**
     * Returns a reference to a Terrain HQ object. If none exists, a new one is
     * created. Noteworthy is that this Terrain object only stores information
     * such as name, and shortName of the terrain info displayed on-screen, as
     * well as which movement type can transverse it and the cost of such
     * movement. Information regarding to which player this property belongs, or
     * whether what units or if this property can deploy, is not stored.
     *
     * @return a reference to the stored Terrain object with the characteristics
     *         of a HQ, or a new one if none exists.
     */
    public static Terrain getHq() {
        if (Terrain.hq == null) {
            Terrain.hq = getNewHq();
        }
        return Terrain.hq;
    }

    /**
     * Creates a new Terrain object of HQ type. This method is private and only
     * invoked if there exist no other HQ object.
     *
     * @return a newly created Terrain object with the characteristics of a HQ.
     */
    private static Terrain getNewHq() {
        Terrain t = Terrain.getAbstractProperty(Terrain.HQ);
        //HQ has 4 defense stars
        t.defenseStars = Byte.parseByte("4");
        t.name = "HQ";
        t.shortName = "HQ";
        return t;
    }

    /**
     * Returns a reference to a Terrain city object. If none exists, a new one
     * is created. Noteworthy is that this Terrain object only stores
     * information such as name, and shortName of the terrain info displayed
     * on-screen, as well as which movement type can transverse it and the cost
     * of such movement. Information regarding to which player this property
     * belongs, or whether what units or if this property can deploy, is not
     * stored.
     *
     * @return a reference to the stored Terrain object with the characteristics
     *         of a city, or a new one if none exists.
     */
    public static Terrain getCity() {
        if (Terrain.city == null) {
            Terrain.city = getNewCity();
        }
        return Terrain.city;
    }

    /**
     * Creates a new Terrain object of city type. This method is private and
     * only invoked if there exist no other city object.
     *
     * @return a newly created Terrain object with the characteristics of a
     *         city.
     */
    private static Terrain getNewCity() {
        Terrain t = Terrain.getAbstractProperty(Terrain.CITY);
        t.name = "City";
        t.shortName = "City";
        return t;
    }

    /**
     * Returns a reference to a Terrain base object. If none exists, a new one
     * is created. Noteworthy is that this Terrain object only stores
     * information such as name, and shortName of the terrain info displayed
     * on-screen, as well as which movement type can transverse it and the cost
     * of such movement. Information regarding to which player this property
     * belongs, or whether what units or if this property can deploy, is not
     * stored.
     *
     * @return a reference to the stored Terrain object with the characteristics
     *         of a base, or a new one if none exists.
     */
    public static Terrain getBase() {
        if (Terrain.base == null) {
            Terrain.base = getNewBase();
        }
        return Terrain.base;
    }

    /**
     * Creates a new Terrain object of base type. This method is private and
     * only invoked if there exist no other base object.
     *
     * @return a newly created Terrain object with the characteristics of a
     *         base.
     */
    private static Terrain getNewBase() {
        Terrain t = Terrain.getAbstractProperty(Terrain.BASE);
        t.name = "Base";
        t.shortName = "Base";
        return t;
    }

    /**
     * Returns a reference to a Terrain airport object. If none exists, a new
     * one is created. Noteworthy is that this Terrain object only stores
     * information such as name, and shortName of the terrain info displayed
     * on-screen, as well as which movement type can transverse it and the cost
     * of such movement. Information regarding to which player this property
     * belongs, or whether what units or if this property can deploy, is not
     * stored.
     *
     * @return a reference to the stored Terrain object with the characteristics
     *         of an airport, or a new one if none exists.
     */
    public static Terrain getAirport() {
        if (Terrain.airport == null) {
            Terrain.airport = getNewAirport();
        }
        return Terrain.airport;
    }

    /**
     * Creates a new Terrain object of airport type. This method is private and
     * only invoked if there exist no other airport object.
     *
     * @return a newly created Terrain object with the characteristics of an
     *         airport.
     */
    private static Terrain getNewAirport() {
        Terrain t = Terrain.getAbstractProperty(Terrain.AIRPORT);
        t.name = "Airport";
        t.shortName = "Airport";
        return t;
    }

    /**
     * Returns a reference to a Terrain port object. If none exists, a new one
     * is created. Noteworthy is that this Terrain object only stores
     * information such as name, and shortName of the terrain info displayed
     * on-screen, as well as which movement type can transverse it and the cost
     * of such movement. Information regarding to which player this property
     * belongs, or whether what units or if this property can deploy, is not
     * stored.
     *
     * @return a reference to the stored Terrain object with the characteristics
     *         of a port, or a new one if none exists.
     */
    public static Terrain getPort() {
        if (Terrain.port == null) {
            Terrain.port = getNewPort();
        }
        return Terrain.port;
    }

    /**
     * Creates a new Terrain object of port type. This method is private and
     * only invoked if there exist no other port object.
     *
     * @return a newly created Terrain object with the characteristics of a
     *         port.
     */
    private static Terrain getNewPort() {
        Terrain t = Terrain.getAbstractProperty(Terrain.PORT);
        t.name = "Port";
        t.shortName = "Port";
        /*SEA units cannot transverse a Property, unless it's a port.*/
        t.canTransverse[Unit.SEA_MOV] = true; //Sea
        t.canTransverse[Unit.COAST_MOV] = true; //Coast
        //Movement cost.
        t.movementCost[Unit.SEA_MOV] = 1; //Sea
        t.movementCost[Unit.COAST_MOV] = 1; //Coast
        return t;
    }

    /**
     * Returns a reference to a Terrain bridge object. If none exists, a new one
     * is created.
     *
     * @return a reference to the stored Terrain object with the characteristics
     *         of a bridge, or a new one if none exists.
     */
    public static Terrain getBridge() {
        if (Terrain.bridge == null) {
            Terrain.bridge = getNewBridge();
        }
        return Terrain.bridge;
    }

    /**
     * Creates a new Terrain object of bridge type. This method is private and
     * only invoked if there exist no other bridge object.
     *
     * @return a newly created Terrain object with the characteristics of a
     *         bridge.
     */
    private static Terrain getNewBridge() {
        Terrain t = new Terrain(
                Terrain.BRIDGE, //terrainType
                Byte.parseByte("0"), //defenseStars
                "Bridge", //name
                "Brdg" //shortName
                );
        //Which units can transverse bridges
        t.canTransverse[Unit.INFANTRY_MOV] = true;  //Inftry
        t.canTransverse[Unit.MECH_MOV] = true;  //Mech
        t.canTransverse[Unit.TIRES_MOV] = true;  //Tires
        t.canTransverse[Unit.TREADS_MOV] = true;  //Treads
        t.canTransverse[Unit.AIR_MOV] = true;  //Air
        t.canTransverse[Unit.SEA_MOV] = false; //Sea
        t.canTransverse[Unit.COAST_MOV] = false; //Coast

        t.movementCost[Unit.INFANTRY_MOV] = 1;  //Inftry
        t.movementCost[Unit.MECH_MOV] = 1;  //Mech
        t.movementCost[Unit.TIRES_MOV] = 1;  //Tires
        t.movementCost[Unit.TREADS_MOV] = 1;  //Treads
        t.movementCost[Unit.AIR_MOV] = 1;  //Air

        //Sea units cannot transverse bridges
        //Not necessary to assign values to these
        //t.movementCost[Unit.SEA_MOV] = 20; //Sea
        //t.movementCost[Unit.COAST_MOV] = 20; //Coast
        return t;
    }

    /**
     * Returns a reference to a Terrain road object. If none exists, a new one
     * is created.
     *
     * @return a reference to the stored Terrain object with the characteristics
     *         of a road, or a new one if none exists.
     */
    public static Terrain getRoad() {
        if (Terrain.road == null) {
            Terrain.road = getNewRoad();
        }
        return Terrain.road;
    }

    /**
     * Creates a new Terrain object of road type. This method is private and
     * only invoked if there exist no other road object.
     *
     * @return a newly created Terrain object with the characteristics of a
     *         road.
     */
    private static Terrain getNewRoad() {
        Terrain t = new Terrain(
                Terrain.ROAD, //terrainType
                Byte.parseByte("0"), //defenseStars
                "Road", //name
                "Road" //shortName
                );
        //Which units can transverse roads
        t.canTransverse[Unit.INFANTRY_MOV] = true;  //Inftry
        t.canTransverse[Unit.MECH_MOV] = true;  //Mech
        t.canTransverse[Unit.TIRES_MOV] = true;  //Tires
        t.canTransverse[Unit.TREADS_MOV] = true;  //Treads
        t.canTransverse[Unit.AIR_MOV] = true;  //Air
        t.canTransverse[Unit.SEA_MOV] = false; //Sea
        t.canTransverse[Unit.COAST_MOV] = false; //Coast

        t.movementCost[Unit.INFANTRY_MOV] = 1;  //Inftry
        t.movementCost[Unit.MECH_MOV] = 1;  //Mech
        t.movementCost[Unit.TIRES_MOV] = 1;  //Tires
        t.movementCost[Unit.TREADS_MOV] = 1;  //Treads
        t.movementCost[Unit.AIR_MOV] = 1;  //Air

        //Sea units cannot transverse roads
        //t.movementCost[Unit.SEA_MOV] = 20; //Sea
        //t.movementCost[Unit.COAST_MOV] = 20; //Coast
        return t;
    }

    /**
     * Returns a reference to a Terrain reef object. If none exists, a new one
     * is created.
     *
     * @return a reference to the stored Terrain object with the characteristics
     *         of a reef, or a new one if none exists.
     */
    public static Terrain getReef() {
        if (Terrain.reef == null) {
            Terrain.reef = getNewReef();
        }
        return Terrain.reef;
    }

    /**
     * Creates a new Terrain object of reef type. This method is private and
     * only invoked if there exist no other reef object.
     *
     * @return a newly created Terrain object with the characteristics of a
     *         reef.
     */
    private static Terrain getNewReef() {
        Terrain t = new Terrain(
                Terrain.REEF, //terrainType
                Byte.parseByte("1"), //defenseStars
                "Reef", //name
                "Reef" //shortName
                );
        //Which units can transverse reefs
        t.canTransverse[Unit.INFANTRY_MOV] = false;  //Inftry
        t.canTransverse[Unit.MECH_MOV] = false;  //Mech
        t.canTransverse[Unit.TIRES_MOV] = false;  //Tires
        t.canTransverse[Unit.TREADS_MOV] = false;  //Treads
        t.canTransverse[Unit.AIR_MOV] = true;  //Air
        t.canTransverse[Unit.SEA_MOV] = true; //Sea
        t.canTransverse[Unit.COAST_MOV] = true; //Coast

        //NO need to fill these
        //t.movementCost[Unit.INFANTRY_MOV] = 20;  //Inftry
        //t.movementCost[Unit.MECH_MOV] = 20;  //Mech
        //t.movementCost[Unit.TIRES_MOV] = 20;  //Tires
        //t.movementCost[Unit.TREADS_MOV] = 20;  //Treads

        t.movementCost[Unit.AIR_MOV] = 1;  //Air
        t.movementCost[Unit.SEA_MOV] = 2; //Sea
        t.movementCost[Unit.COAST_MOV] = 2; //Coast
        return t;
    }

    /**
     * Returns a reference to a Terrain shoal object. If none exists, a new one
     * is created.
     *
     * @return a reference to the stored Terrain object with the characteristics
     *         of a shoal, or a new one if none exists.
     */
    public static Terrain getShoal() {
        if (Terrain.shoal == null) {
            Terrain.shoal = getNewShoal();
        }
        return Terrain.shoal;
    }

    /**
     * Creates a new Terrain object of shoal type. This method is private and
     * only invoked if there exist no other shoal object.
     *
     * @return a newly created Terrain object with the characteristics of a
     *         shoal.
     */
    private static Terrain getNewShoal() {
        Terrain t = new Terrain(
                Terrain.SHOAL, //terrainType
                Byte.parseByte("0"), //defenseStars
                "Shoal", //name
                "Shoal" //shortName
                );
        //Which units can transverse shoals
        t.canTransverse[Unit.INFANTRY_MOV] = true;  //Inftry
        t.canTransverse[Unit.MECH_MOV] = true;  //Mech
        t.canTransverse[Unit.TIRES_MOV] = true;  //Tires
        t.canTransverse[Unit.TREADS_MOV] = true;  //Treads
        t.canTransverse[Unit.AIR_MOV] = true;  //Air
        t.canTransverse[Unit.SEA_MOV] = false; //Sea
        t.canTransverse[Unit.COAST_MOV] = true; //Coast

        t.movementCost[Unit.INFANTRY_MOV] = 1;  //Inftry
        t.movementCost[Unit.MECH_MOV] = 1;  //Mech
        t.movementCost[Unit.TIRES_MOV] = 1;  //Tires
        t.movementCost[Unit.TREADS_MOV] = 1;  //Treads
        t.movementCost[Unit.AIR_MOV] = 1;  //Air

        //Sea units cannot transverse shoals
        //No need to fill these
        //t.movementCost[Unit.SEA_MOV] = 20; //Sea

        t.movementCost[Unit.COAST_MOV] = 1; //Coast
        return t;
    }

    /**
     * Returns a reference to a Terrain river object. If none exists, a new one
     * is created.
     *
     * @return a reference to the stored Terrain object with the characteristics
     *         of a river, or a new one if none exists.
     */
    public static Terrain getRiver() {
        if (Terrain.river == null) {
            Terrain.river = getNewRiver();
        }
        return Terrain.river;
    }

    /**
     * Creates a new Terrain object of river type. This method is private and
     * only invoked if there exist no other river object.
     *
     * @return a newly created Terrain object with the characteristics of a
     *         river.
     */
    private static Terrain getNewRiver() {
        Terrain t = new Terrain(
                Terrain.RIVER, //terrainType
                Byte.parseByte("0"), //defenseStars
                "River", //name
                "River" //shortName
                );
        //Which units can transverse rivers
        t.canTransverse[Unit.INFANTRY_MOV] = true;  //Inftry
        t.canTransverse[Unit.MECH_MOV] = true;  //Mech
        t.canTransverse[Unit.TIRES_MOV] = false; //Tires
        t.canTransverse[Unit.TREADS_MOV] = false; //Treads
        t.canTransverse[Unit.AIR_MOV] = true;  //Air
        t.canTransverse[Unit.SEA_MOV] = false; //Sea
        t.canTransverse[Unit.COAST_MOV] = false; //Coast

        t.movementCost[Unit.INFANTRY_MOV] = 2;  //Inftry
        t.movementCost[Unit.MECH_MOV] = 1;  //Mech
        t.movementCost[Unit.AIR_MOV] = 1;  //Air

        //Vehicle units cannot transverse Rivers
        // No need to fill these
        //t.movementCost[Unit.TIRES_MOV] = 20;  //Tires
        //t.movementCost[Unit.TREADS_MOV] = 20;  //Treads
        //Sea units cannot transverse Rivers
        //t.movementCost[Unit.SEA_MOV] = 20; //Sea
        //t.movementCost[Unit.COAST_MOV] = 20; //Coast
        return t;
    }

    /**
     * Returns a reference to a Terrain wood object. If none exists, a new one
     * is created.
     *
     * @return a reference to the stored Terrain object with the characteristics
     *         of a wood, or a new one if none exists.
     */
    public static Terrain getWood() {
        if (Terrain.wood == null) {
            Terrain.wood = getNewWood();
        }
        return Terrain.wood;
    }

    /**
     * Creates a new Terrain object of wood type. This method is private and
     * only invoked if there exist no other wood object.
     *
     * @return a newly created Terrain object with the characteristics of a
     *         wood.
     */
    private static Terrain getNewWood() {
        Terrain t = new Terrain(
                Terrain.WOOD, //terrainType
                Byte.parseByte("2"), //defenseStars
                "Wood", //name
                "Wood" //shortName
                );
        //Which units can transverse woods
        t.canTransverse[Unit.INFANTRY_MOV] = true;  //Inftry
        t.canTransverse[Unit.MECH_MOV] = true;  //Mech
        t.canTransverse[Unit.TIRES_MOV] = true;  //Tires
        t.canTransverse[Unit.TREADS_MOV] = true;  //Treads
        t.canTransverse[Unit.AIR_MOV] = true;  //Air
        t.canTransverse[Unit.SEA_MOV] = false; //Sea
        t.canTransverse[Unit.COAST_MOV] = false; //Coast

        t.movementCost[Unit.INFANTRY_MOV] = 1;  //Inftry
        t.movementCost[Unit.MECH_MOV] = 1;  //Mech
        t.movementCost[Unit.TIRES_MOV] = 3;  //Tires
        t.movementCost[Unit.TREADS_MOV] = 2;  //Treads
        t.movementCost[Unit.AIR_MOV] = 1;  //Air

        //Sea units cannot transverse plains
        //No need to fill these
        //t.movementCost[Unit.SEA_MOV] = 20; //Sea
        //t.movementCost[Unit.COAST_MOV] = 20; //Coast
        return t;
    }

    /**
     * Returns a reference to a Terrain mountain object. If none exists, a new
     * one is created.
     *
     * @return a reference to the stored Terrain object with the characteristics
     *         of a mountain, or a new one if none exists.
     */
    public static Terrain getMountain() {
        if (Terrain.mountain == null) {
            Terrain.mountain = getNewMountain();
        }
        return Terrain.mountain;
    }

    /**
     * Creates a new Terrain object of mountain type. This method is private and
     * only invoked if there exist no other mountain object.
     *
     * @return a newly created Terrain object with the characteristics of a
     *         mountain.
     */
    private static Terrain getNewMountain() {
        Terrain t = new Terrain(
                Terrain.MOUNTAIN, //terrainType
                Byte.parseByte("3"), //defenseStars
                "Mountain", //name
                "Mtn" //shortName
                );
        //Which units can transverse mountains
        t.canTransverse[Unit.INFANTRY_MOV] = true;  //Inftry
        t.canTransverse[Unit.MECH_MOV] = true;  //Mech
        t.canTransverse[Unit.TIRES_MOV] = false; //Tires
        t.canTransverse[Unit.TREADS_MOV] = false; //Treads
        t.canTransverse[Unit.AIR_MOV] = true;  //Air
        t.canTransverse[Unit.SEA_MOV] = false; //Sea
        t.canTransverse[Unit.COAST_MOV] = false; //Coast

        t.movementCost[Unit.INFANTRY_MOV] = 2;  //Inftry
        t.movementCost[Unit.MECH_MOV] = 1;  //Mech

        //Vehicle units cannot transverse Mountains
        //No need to fill these
        //t.movementCost[Unit.TIRES_MOV] = 20;  //Tires
        //t.movementCost[Unit.TREADS_MOV] = 20;  //Treads

        t.movementCost[Unit.AIR_MOV] = 1;  //Air

        //Sea units cannot transverse Mountains
        //t.movementCost[Unit.SEA_MOV] = 20; //Sea
        //t.movementCost[Unit.COAST_MOV] = 20; //Coast
        return t;
    }

    /**
     * Returns a reference to a Terrain mountain object. If none exists, a new
     * one is created.
     *
     * @return a reference to the stored Terrain object with the characteristics
     *         of a mountain, or a new one if none exists.
     */
    public static Terrain getSea() {
        if (Terrain.sea == null) {
            Terrain.sea = getNewSea();
        }
        return Terrain.sea;
    }

    /**
     * Creates a new Terrain object of sea type. This method is private and only
     * invoked if there exist no other mountain object.
     *
     * @return a newly created Terrain object with the characteristics of a sea.
     */
    private static Terrain getNewSea() {
        Terrain t = new Terrain(
                Terrain.SEA, //terrainType
                Byte.parseByte("0"), //defenseStars
                "Sea", //name
                "Sea" //shortName
                );
        //Which units can transverse roads
        t.canTransverse[Unit.INFANTRY_MOV] = false;  //Inftry
        t.canTransverse[Unit.MECH_MOV] = false;  //Mech
        t.canTransverse[Unit.TIRES_MOV] = false;  //Tires
        t.canTransverse[Unit.TREADS_MOV] = false;  //Treads
        t.canTransverse[Unit.AIR_MOV] = true;  //Air
        t.canTransverse[Unit.SEA_MOV] = true; //Sea
        t.canTransverse[Unit.COAST_MOV] = true; //Coast

        //NO need to fill these
        //t.movementCost[Unit.INFANTRY_MOV] = 20;  //Inftry
        //t.movementCost[Unit.MECH_MOV] = 20;  //Mech
        //t.movementCost[Unit.TIRES_MOV] = 20;  //Tires
        //t.movementCost[Unit.TREADS_MOV] = 20;  //Treads
        t.movementCost[Unit.AIR_MOV] = 1;  //Air

        t.movementCost[Unit.SEA_MOV] = 1; //Sea
        t.movementCost[Unit.COAST_MOV] = 1; //Coast
        return t;
    }

    /**
     * Returns a reference to a Terrain plain object. If none exists, a new one
     * is created.
     *
     * @return a reference to the stored Terrain object with the characteristics
     *         of a plain, or a new one if none exists.
     */
    public static Terrain getPlain() {
        if (Terrain.plain == null) {
            Terrain.plain = getNewPlain();
        }
        return Terrain.plain;
    }

    /**
     * Creates a new Terrain object of plain type. This method is private and
     * only invoked if there exist no other plain object.
     *
     * @return a newly created Terrain object with the characteristics of a
     *         plain.
     */
    private static Terrain getNewPlain() {
        Terrain t = new Terrain(
                Terrain.PLAIN, //terrainType
                Byte.parseByte("1"), //defenseStars
                "Plain", //name
                "Plain" //shortName
                );
        //Which units can transverse plains
        t.canTransverse[Unit.INFANTRY_MOV] = true;  //Inftry
        t.canTransverse[Unit.MECH_MOV] = true;  //Mech
        t.canTransverse[Unit.TIRES_MOV] = true;  //Tires
        t.canTransverse[Unit.TREADS_MOV] = true;  //Treads
        t.canTransverse[Unit.AIR_MOV] = true;  //Air
        t.canTransverse[Unit.SEA_MOV] = false; //Sea
        t.canTransverse[Unit.COAST_MOV] = false; //Coast

        t.movementCost[Unit.INFANTRY_MOV] = 1;  //Inftry
        t.movementCost[Unit.MECH_MOV] = 1;  //Mech
        t.movementCost[Unit.TIRES_MOV] = 2;  //Tires
        t.movementCost[Unit.TREADS_MOV] = 1;  //Treads
        t.movementCost[Unit.AIR_MOV] = 1;  //Air
        //Sea units cannot transverse plains
        //NO need to fill these
        //t.movementCost[Unit.SEA_MOV] = 20; //Sea
        //t.movementCost[Unit.COAST_MOV] = 20; //Coast
        return t;
    }

    /**
     * Returns true if the received movement type can transverse this terrrain
     *
     * @param movementType The movementType of the unit that wants to transverse
     *                     this terrain
     *
     * @return true if the movementType can transverse this terrain. False
     *         otherwise.
     */
    public boolean getCanTransverse(byte movementType) {
        return this.canTransverse[movementType];
    }

    public void setCanTransverse(byte movementType, boolean value) {
        this.canTransverse[movementType] = value;
    }

    public byte getMovementCost(Unit unit) {
        if (unit.player.currentCO.id == CO.STURM) {
            return 1;
        }
        if (unit.player.currentCO.id == CO.LASH && unit.player.hasNoPower == false) {
            return 1;
        }
        return getMovementCost(unit.unitType);
    }

    public byte getMovementCost(byte b) {
        return this.movementCost[b];
    }

    /**
     * Returns an instance of a Terrain object, based on the ID pased as
     * parameter. This method follows the Singleton pattern and, as such, only
     * one instance of a Terrain object is kept. This method creates an instance
     * of a terrain type if none exists, or returns the current instance.
     *
     * @param terrainID
     * @return
     */
    public static Terrain getTerrainById(byte terrainID) {
        switch (terrainID) {
            case HQ:
                return Terrain.getHq();
            case CITY:
                return Terrain.getCity();
            case BASE:
                return Terrain.getBase();
            case AIRPORT:
                return Terrain.getAirport();
            case PORT:
                return Terrain.getPort();
            case BRIDGE:
                return Terrain.getBridge();
            case ROAD:
                return Terrain.getRoad();
            case REEF:
                return Terrain.getReef();
            case SHOAL:
                return Terrain.getShoal();
            case RIVER:
                return Terrain.getRiver();
            case WOOD:
                return Terrain.getWood();
            case MOUNTAIN:
                return Terrain.getMountain();
            case SEA:
                return Terrain.getSea();
            case PLAIN:
                return Terrain.getPlain();
            case SILO:
                //return Terrain.getSilo();
            case PIPE:
                //return Terrain.getPipe();
            case PIPE_JOINTS:
                //return Terrain.getPipeJoint();                
            default:
                break;
        }
        return null;
    }
}
