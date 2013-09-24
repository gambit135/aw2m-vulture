
package aw2m;

/**
 * @author Alejandro Téllez G. <java.util.fck@hotmail.com>
 */
public class AbstractLocation {
    private byte x,y;

    public byte getX() {
        return x;
    }

    public void setX(byte x) {
        this.x = x;
    }

    public byte getY() {
        return y;
    }

    public void setY(byte y) {
        this.y = y;
    }

    public AbstractLocation(byte x, byte y) {
        this.x = x;
        this.y = y;
    }
    @Override
    public String toString(){
        return "(" + this.x + "," + this.y + ")";
    }

    

    
}
