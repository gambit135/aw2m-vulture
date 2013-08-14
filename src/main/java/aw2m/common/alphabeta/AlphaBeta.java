package aw2m.common.alphabeta;

/**
 *
 *
 * @author Alejandro Téllez G. <java.util.fck@hotmail.com>
 * @date 20/04/2013 - 09:25:20 PM
 */
public class AlphaBeta {

    static int alphaBeta(Node n, int alpha, int beta, int remainingDepth) {
        if (remainingDepth == 0) {
            return n.evaluate();
        }
        //n.generateChildren(remainingDepth);
        if (n.isMax) {  //n is a MAX node
            System.out.println("Node (" + n.getPly() + ", " + n.getChildSecuenceNumber() + ") is MAX");
            for (;;) {
                if (n.children.size() < n.getNumberOfChildren()) {
                    n.getNextChild(remainingDepth);
                    n.setAlpha(Math.max(n.getAlpha(), alphaBeta(n.children.getLast(), alpha, beta, remainingDepth - 1)));
                    if (n.getAlpha() >= n.getBeta()) {
                        //Beta Prunning
                        System.out.println("Beta prunning at Node (" + n.getPly() + ", " + n.getChildSecuenceNumber() + ")");
                        return n.getBeta();
                    }
                }
                else {
                    break;
                }
            }
            return n.getAlpha();
        }
        else {  //n is a MIN node
            System.out.println("Node (" + n.getPly() + ", " + n.getChildSecuenceNumber() + ") is MIN");
            for (;;) {
                if (n.children.size() < n.getNumberOfChildren()) {
                    n.getNextChild(remainingDepth);
                    n.setBeta(Math.min(beta, alphaBeta(n.children.getLast(), alpha, beta, remainingDepth - 1)));
                    if (n.getBeta() <= n.getAlpha()) {
                        //Alpha Prunning
                        System.out.println("Alpha prunning at Node (" + n.getPly() + ", " + n.getChildSecuenceNumber() + ")");
                        return n.getAlpha();
                    }
                }
                else {
                    break;
                }
            }
            return n.getBeta();
        }
    }
}
