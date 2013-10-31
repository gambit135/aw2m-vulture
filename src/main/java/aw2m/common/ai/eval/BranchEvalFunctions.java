package aw2m.common.ai.eval;

import aw2m.common.ai.model.Branch;
import aw2m.common.ai.model.Node;

/**
 *
 * @author Alejandro Téllez G. <java.util.fck@hotmail.com>
 * @date 13/10/2013 - 06:37:52 AM
 */
public class BranchEvalFunctions {

    public static int evalBranch(Branch branch) {
        int value = 0;
        for (Node n : branch.branch) {
            value += n.value;
        }
        return value;
    }
}
