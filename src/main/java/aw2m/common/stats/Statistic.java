package aw2m.common.stats;

/**
 *
 * @author Alejandro Téllez G. <java.util.fck@hotmail.com>
 * @date 14/08/2013 - 01:33:09 AM
 */
public class Statistic {

    public static int calculateByteSizeOfStringOverhead(String eval) {
        return (int) (((eval.length() * 2) + 45) / 8);
    }

    public static int calculateByteSizeofString(String eval) {
        return eval.getBytes().length;
    }
}
