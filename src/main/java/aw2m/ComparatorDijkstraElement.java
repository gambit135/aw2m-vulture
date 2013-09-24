package aw2m;

import java.util.Comparator;

/**
 *
 * @author Alejandro Téllez G. <java.util.fck@hotmail.com>
 * @date 14/04/2013 - 03:14:26 AM
 */
public class ComparatorDijkstraElement implements Comparator<DijkstraElement> {

    public int compare(DijkstraElement o1, DijkstraElement o2) {
        return o1.movementPointsLeft - o2.movementPointsLeft;
    }
}
