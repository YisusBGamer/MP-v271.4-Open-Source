package net.swordie.ms.connection.api;

import net.swordie.ms.util.Util;

/**
 * @author Sjonnie
 * Created on 10/5/2018.
 */
public enum ApiOutHeader {
    REQUEST_TOKEN_RESULT(100),
    CREATE_ACCOUNT_RESULT(101),
    REQUEST_TOKEN_RESULT_X64(102),
    ACCOUNT_DATA_RESULT_X64(103),
    WORLD_STATS_RESULT(105),
    ACCOUNT_UPDATE_RESULT(106),
    ;

    private int val;

    ApiOutHeader(int val) {
        this.val = val;
    }

    public int getVal() {
        return val;
    }

    public static ApiOutHeader getByVal(int val) {
        return Util.findWithPred(values(), header -> header.getVal() == val);
    }
}
