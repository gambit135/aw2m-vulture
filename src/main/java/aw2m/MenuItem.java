package aw2m;


/**
 *
 * @author Alejandro Téllez G. <java.util.fck@hotmail.com>
 */
public class MenuItem {
    /**
     * The ID of this MenuItem object. Used to know wich MenuItem was chosen,
     * and take actions over it
     */
    byte id;
    Sprite sprite;
    String text1;
    String text2;

    public MenuItem(){}
    public MenuItem(byte id){}
    public MenuItem(byte id, String text1){}
    public MenuItem(byte id, String text1, String text2){}


    @Override
    public String toString(){
        String line = "";
        if (this.text1 != null){ line += text1;}
        line += "\t";
        if (this.text2 != null){ line += text2;}
        /*
        for (String  item: text){
            line += (item + "\t");
        }*/
        return line;
    }

}
