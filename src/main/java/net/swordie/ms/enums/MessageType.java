package net.swordie.ms.enums;

import net.swordie.ms.util.Util;

/**
 * Created on 1/25/2018.
 */
public enum MessageType {
    DROP_PICKUP_MESSAGE(0),
    QUEST_RECORD_MESSAGE(1),
    CASH_ITEM_EXPIRE_MESSAGE(2),
    INC_EXP_MESSAGE(3),
    INC_SP_MESSAGE(4),
    INC_POP_MESSAGE(5),
    INC_MONEY_MESSAGE(6),
    INC_GP_MESSAGE(7),
    INC_COMMITMENT_MESSAGE(8),
    GIVE_BUFF_MESSAGE(9),
    GENERAL_ITEM_EXPIRE_MESSAGE(10),
    SYSTEM_MESSAGE(11),
    UNK_MESSAGE(12),
    QUEST_RECORD_EX_MESSAGE(13),
    WORLD_SHARE_RECORD_MESSAGE(14),
    ITEM_PROTECT_EXPIRE_MESSAGE(15),
    ITEM_EXPIRE_REPLACE_MESSAGE(16),
    ITEM_ABILITY_TIME_LIMITED_EXPIRE_MESSAGE(17),
    SKILL_EXPIRE_MESSAGE(18),
    INC_NON_COMBAT_STAT_EXP_MESSAGE(19),
    LIMIT_NON_COMBAT_STAT_EXP_MESSAGE(21),
    ANDROID_MACHINE_HEART_ALSET_MESSAGE(23),
    INC_FATIGUE_BY_REST_MESSAGE(24),
    INC_PVP_POINT_MESSAGE(-1),
    PVP_ITEM_USE_MESSAGE(-1),
    WEDDING_PORTAL_ERROR(-1),
    INC_HARDCORE_EXP_MESSAGE(28),
    NOTICE_AUTO_LINE_CHANGED(29),
    ENTRY_RECORD_MESSAGE(-1),
    EVOLVING_SYSTEM_MESSAGE(-1),
    EVOLVING_SYSTEM_MESSAGE_WITH_NAME(-1),
    CORE_INVEN_OPERATION_MESSAGE(-1),
    NX_RECORD_MESSAGE(31),
    BLOCKED_BEHAVIOR_MESSAGE(-1),
    INC_WP_MESSAGE(-1),
    MAX_WP_MESSAGE(-1),
    STYLISH_KILL_MESSAGE(36), // v271: a new type 32 moves v265 32-49 up by one (OnMessage 0x145a5bc60)
    BARRIER_EFFECT_IGNORE_MESSAGE(37),
    EXPIRED_CASH_ITEM_RESULT_MESSAGE(38),
    COLLECTION_RECORD_MESSAGE(40),
    RANDOM_CHANCE_MESSAGE(41),
    QUEST_EXPIRE_MESSAGE(43), // v271: case 43 decodes int + string; the client's OnMessage has no case 42

    ACHIEVEMENT_INIT(45),
    ACHIEVEMENT_COMPLETE(46), // v200.1 updated

    SET_CURRENT_TIME(50), // v271 50 is a diagnostics handler (byte + strings); no current-time case found in 43-54 yet, so this stays gated
    ;

    private byte val;

    MessageType(int val) {
        this.val = (byte) val;
    }

    public static MessageType getByVal(byte type) {
        return Util.findWithPred(values(), v -> v.getVal() == type);
    }

    public byte getVal() {
        return val;
    }
}
