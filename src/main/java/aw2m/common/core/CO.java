package aw2m.common.core;

/**
 * Abstraction of a Commanding Officer (CO)
 *
 * @author Alejandro Téllez G. <java.util.fck@hotmail.com>
 */
public class CO {
    //Classifiers
    //Commanding Officers
    //
    //Orange Star

    public static final byte ANDY = 0;
    public static final byte MAX = 1;
    public static final byte SAMI = 2;
    public static final byte NELL = 3;
    public static final byte HACHI = 4;
    public static final byte JAKE = 5;
    public static final byte RACHEL = 6;
    //Blue Moon
    public static final byte OLAF = 7;
    public static final byte GRIT = 8;
    public static final byte COLIN = 9;
    public static final byte SASHA = 10;
    //Green Earth
    public static final byte EAGLE = 11;
    public static final byte DRAKE = 12;
    public static final byte JESS = 13;
    public static final byte JAVIER = 14;
    //Yellow Comet
    public static final byte KANBEI = 15;
    public static final byte SONJA = 16;
    public static final byte SENSEI = 17;
    public static final byte GRIMM = 18;
    //Black Hole
    public static final byte FLAK = 19;
    public static final byte LASH = 20;
    public static final byte ADDER = 21;
    public static final byte HAWKE = 22;
    public static final byte STURM = 23;
    public static final byte JUGGER = 24;
    public static final byte KOAL = 25;
    public static final byte KINDLE = 26;
    public static final byte VON_BOLT = 27;
    public byte id;
    String name;
    String about;
    String hit, miss;
    String description;
    //Power power;
    //Power superPower;
    double energy;
    /**
     * If true, uses the AW1 CO version (power and day-to-day abilities)
     */
    public boolean awVersion;
    /**
     * If true, uses the AW2 CO version (power and day-to-day abilities)
     */
    public boolean aw2Version;
    /**
     * If true, uses the AWDS CO version(power and day-to-day abilities)
     */
    public boolean awdsVersion;

    public CO() {
    }

    public CO(byte id) {
        this.id = id;
    }
    public static byte getCOID(CO co){
        
        return -1;
    }
}
