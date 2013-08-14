package aw2m.common.alphabeta;

import aw2m.common.core.DijkstraElement;
import java.util.LinkedList;
import java.util.Random;

/**
 *
 * @author Alejandro Téllez G. <java.util.fck@hotmail.com>
 * @date 20/04/2013 - 09:25:25 PM
 */
public class Node {

    //private int value;
    //public DangerGridCell dangerGridCell;
    private int alpha;
    private int beta;
    private int ply;
    private int childSecuenceNumber;
    /**
     * The number of children this node can have.
     */
    private int numberOfChildren;
    public LinkedList<Node> children;
    public boolean isMax;
    public LinkedList<DijkstraElement> movementList;
    // public LinkedList<DangerGridCell> dangerZone;

    public Node(int alpha, int beta, boolean isMax) {
        this.children = new LinkedList<Node>();
        this.alpha = alpha;
        this.beta = beta;
        this.isMax = isMax;
    }

    public Node(int alpha, int beta, boolean isMax, int remainingDepth, int childNumber) {
        this(alpha, beta, isMax);
        this.ply = Main.depth - remainingDepth + 1;
        this.childSecuenceNumber = childNumber;
        //A node can have from 1 to 4 children
        this.numberOfChildren = new Random().nextInt(4) + 1;
    }

    public Node(int remainingDepth, int secuence) {
    }
    /*
     void generateChildren(int remainingDepth) {
     //Generates up to 4 children
     /*
     boolean childIsMax = true;
     if (this.isMax) {
     childIsMax = false;
     }
     for (int i = 0; i < new Random().nextInt(5); i++) {
     this.children.add(new Node(this.alpha, this.beta, childIsMax, remainingDepth, i + 1));
     }
     }*/

    /**
     * Creates a child for this node and appends it to the end of it's children
     * list.
     *
     * @param remainingDepth
     * @return
     */
    boolean getNextChild(int remainingDepth) {
        boolean childIsMax = true;
        if (this.isMax) {
            childIsMax = false;
        }
        this.children.add(new Node(this.alpha, this.beta, childIsMax, remainingDepth, this.children.size() + 1));
        return true;
    }

    int evaluate() {
        Random r = new Random();
        switch (r.nextInt(2)) {
            case 1:
                return -r.nextInt(31);
            default:
                return r.nextInt(31);
        }
        /*
         int unsafeGridCells = 0;
         int safeGridCells = 0;
         for (DangerGridCell d : this.dangerZone) {
         if (d.dangerValue > 0) {
         unsafeGridCells++;
         }
         else {
         safeGridCells++;
         }
         }
         return safeGridCells - unsafeGridCells;*/
    }

    public int getAlpha() {
        return alpha;
    }

    public void setAlpha(int alpha) {
        this.alpha = alpha;
    }

    public int getBeta() {
        return beta;
    }

    public void setBeta(int beta) {
        this.beta = beta;
    }

    public LinkedList<Node> getChildren() {
        return children;
    }

    public void setChildren(LinkedList<Node> children) {
        this.children = children;
    }

    public int getPly() {
        return ply;
    }

    public void setPly(int ply) {
        this.ply = ply;
    }

    public int getChildSecuenceNumber() {
        return childSecuenceNumber;
    }

    public void setChildSecuenceNumber(int childSecuenceNumber) {
        this.childSecuenceNumber = childSecuenceNumber;
    }

    public int getNumberOfChildren() {
        return numberOfChildren;
    }

    public void setNumberOfChildren(int numberOfChildren) {
        this.numberOfChildren = numberOfChildren;
    }
}
