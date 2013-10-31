package aw2m.common.ai.model;

import aw2m.common.core.Unit;

/**
 * This class represents a Node within or potentially within a branch of the AI
 * algorithm. A node stores a reference to an attacking unit, to a defending
 * unit, and a two byte "short" value
 *
 * @author Alejandro Téllez G. <java.util.fck@hotmail.com>
 * @date 14/10/2013 - 04:40:50 AM
 */
public class Node {

    public Unit attacker;
    public Unit defender;
    public short value;

    public Node(Unit attacker, Unit defender, short value) {
        this.attacker = attacker;
        this.defender = defender;
        this.value = value;
    }

    @Override
    public String toString() {
        return "NODE { \nAttacker: "
                + this.attacker.toString() + "\n"
                + "Defender: "
                + this.defender.toString() + "\n"
                + "Value: " + this.value + " }\n";
    }
}
