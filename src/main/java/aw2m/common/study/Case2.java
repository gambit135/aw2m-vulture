package aw2m.common.study;

/**
 * This class depicts the second case of tests: An average case.
 *
 * To fully achieve an average case, it will be assumed a 25 vs 25, all of them
 * in range. This can be achieved getting units with ranges of approximately 7
 * on a 8x8 square.
 *
 * @author Alejandro Téllez G. <java.util.fck@hotmail.com>
 * @date 31/10/2013 - 12:21:48 AM
 */
public class Case2 {

    public static void main(String args[]) {
        //A test is divided unto stages:
        //Stage 1: Create a game instance and load a map
        //Stage 2: Create and deploy units.
        //  A submodule that creates units on demand and deploys them on valid terrain is needed
        //Stage 2.5 - If its remote - serialize and send. Wait and receive
        //Stage 3: Combat tests, local or remote.
        //Stage 4: Account Stats
    }
}
