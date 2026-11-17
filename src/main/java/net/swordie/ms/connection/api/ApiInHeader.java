package net.swordie.ms.connection.api;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Sjonnie
 * Created on 10/5/2018.
 */
public enum ApiInHeader {
    REQUEST_TOKEN(100), // the v232 launcher's login: answered with WrongVersion
    CREATE_ACCOUNT_REQUEST(101),
    REQUEST_TOKEN_X64(102),
    REQUEST_ACCOUNT_DATA_X64(103),
    LOGOUT_REQUEST(104),
    REQUEST_WORLD_STATS(105),
    REQUEST_ACCOUNT_UPDATE(106),
    ;

    private int val;
    private final static Map<Integer, ApiInHeader> opToHeaderMap = new HashMap<>();

    static {
        for (ApiInHeader ih : values()) {
            opToHeaderMap.put(ih.getVal(), ih);
        }
    }

    ApiInHeader(int val) {
        this.val = val;
    }

    public static ApiInHeader getByVal(int op) {
        return opToHeaderMap.getOrDefault(op, null);
    }

    public static ApiInHeader getInHeaderByOp(int op) {
        return opToHeaderMap.getOrDefault(op, null);
    }

    public static Object getInHeaderByOp(short op) {
        return null;
    }

    public int getVal() {
        return val;
    }
}
