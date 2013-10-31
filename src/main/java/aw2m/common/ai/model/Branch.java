package aw2m.common.ai.model;

import java.util.LinkedList;

/**
 * A branch object stores a list of nodes, representing the branch itself as a
 * succession of nodes; and a two bytes "short" evaluation value.
 *
 * @author Alejandro Téllez G. <java.util.fck@hotmail.com>
 * @date 14/10/2013 - 05:46:34 AM
 */
public class Branch {

    public LinkedList<Node> branch;
    public int evalValue;

    public Branch(LinkedList<Node> Branch) {
        this.branch = Branch;
    }

    @Override
    public String toString() {
        String nodes = "";
        for (Node n : this.branch) {
            nodes += n.toString();
        }
        return "\nBranch: \n"
                + "Optimal Nodes on Branch: \n"
                + nodes + "\n"
                + "Branch eval value: "
                + this.evalValue
                + "\n";
    }
}
