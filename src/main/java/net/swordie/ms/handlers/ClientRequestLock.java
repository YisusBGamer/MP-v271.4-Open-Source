package net.swordie.ms.handlers;

import net.swordie.ms.client.Client;
import net.swordie.ms.connection.packet.WvsContext;

import java.util.BitSet;

/**
 * In packets after which the v271 client waits with its request lock set (CWvsContext 0x1459ab700 with 1 in the
 * sender) until EXCL_REQUEST or the request's own answer arrives. If the server drops one of these, the client stays
 * locked: no NPC talk, item use, portals or windows until a relog. {@link #release} answers a dropped one.
 */
public final class ClientRequestLock {
    private static final int[] OPS = {
            143, 253, 254, 256, 257, 262, 264, 265, 268, 269, 292, 308, 310, 320, 321, 322, 324, 325, 326, 327, 328,
            329, 332, 334, 336, 337, 339, 340, 341, 342, 346, 347, 348, 349, 350, 351, 352, 353, 354, 355, 356, 357,
            358, 360, 361, 362, 363, 364, 365, 366, 367, 368, 369, 370, 371, 372, 373, 374, 376, 377, 378, 379, 380,
            381, 382, 384, 385, 388, 396, 397, 398, 400, 401, 406, 407, 411, 414, 415, 416, 438, 439, 440, 450, 451,
            460, 461, 462, 471, 478, 482, 483, 496, 502, 506, 516, 522, 523, 524, 533, 538, 553, 554, 555, 561, 565,
            566, 569, 570, 571, 572, 573, 585, 589, 591, 592, 593, 633, 641, 642, 647, 669, 690, 702, 703, 705, 712,
            798, 811, 812, 827, 833, 836, 853, 854, 891, 899, 900, 901, 902, 907, 908, 909, 914, 917, 929, 930, 931,
            955, 956, 957, 960, 961, 967, 977, 989, 991, 1002, 1029, 1082, 1099, 1103, 1104, 1117, 1118, 1119, 1130,
            1131, 1134, 1137, 1165, 1167, 1169, 1215, 1216, 1219, 1220, 1235, 1236, 1237, 1238, 1242, 1248, 1283,
            1284, 1300, 1373, 1403, 1416, 1420, 1622, 1843, 1856, 1907, 1958, 1959, 1960, 1961, 1971, 1972, 1973,
            2005, 2008, 2023, 2024
    };
    private static final BitSet LOCKING = new BitSet(4096);

    static {
        for (int op : OPS) {
            LOCKING.set(op);
        }
    }

    private ClientRequestLock() {
    }

    public static boolean isLocking(int op) {
        return op >= 0 && LOCKING.get(op);
    }

    /** Unlocks the client after a dropped request it waits on; true if an answer was sent. */
    public static boolean release(Client c, int op) {
        if (c == null || c.getChr() == null || !isLocking(op)) {
            return false;
        }
        c.write(WvsContext.exclRequest());
        return true;
    }
}
