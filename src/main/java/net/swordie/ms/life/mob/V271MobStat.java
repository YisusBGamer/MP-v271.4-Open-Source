package net.swordie.ms.life.mob;

import java.util.Collection;

/**
 * v271 MobStat mask bits. The client's reader (0x140d5f7e0) dropped 14 of v265's n/r/t stats, so every bit after
 * 0x40 moved down; the order of the n/r/t block and of the fields after it is unchanged. Mapped from both readers'
 * n/r/t declaration order and their tail order (identical after the mapping), checked against the client's global
 * masks (movement set 0x148c27b98 = v265 0x147f3c238 mapped).
 */
public final class V271MobStat {
    private V271MobStat() {
    }

    /** v271 bit of a v265 bit, or -1 when v271 has no such stat or the mapping is not known. */
    public static int bit(int v265) {
        if (v265 < 0) {
            return -1;
        }
        if (v265 <= 0x40) {
            return v265;
        }
        if (v265 <= 0x42) {
            return -1;
        }
        if (v265 <= 0x45) {
            return v265 - 2;
        }
        if (v265 <= 0x48) {
            return -1;
        }
        if (v265 <= 0x51) {
            return v265 - 5;
        }
        if (v265 == 0x52) {
            return -1;
        }
        if (v265 <= 0x55) {
            return v265 - 6;
        }
        if (v265 <= 0x57) {
            return v265 - 6; // medium: one of 0x56..0x58 was dropped, struct gaps point at 0x58
        }
        if (v265 <= 0x5a) {
            return -1;
        }
        if (v265 <= 0x5c) {
            return v265 - 9;
        }
        if (v265 == 0x5d) {
            return -1;
        }
        if (v265 <= 0x67) {
            return v265 - 10;
        }
        if (v265 <= 0x6e) {
            return -1; // four of 0x68..0x6e were dropped (0x6d one of them); the other three are not placed
        }
        if (v265 <= 0x8d) {
            return v265 - 14;
        }
        return -1;
    }

    public static int bit(MobStat ms) {
        return bit(ms.getBitPos());
    }

    public static boolean has(MobStat ms) {
        return bit(ms) >= 0;
    }

    public static int[] mask(Collection<MobStat> stats) {
        int[] res = new int[MobStat.LENGTH];
        for (MobStat ms : stats) {
            int b = bit(ms);
            if (b >= 0) {
                res[b / 32] |= 1 << (31 - b % 32);
            }
        }
        return res;
    }
}
