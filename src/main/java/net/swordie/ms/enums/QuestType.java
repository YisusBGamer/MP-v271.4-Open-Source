package net.swordie.ms.enums;

import java.util.Arrays;

/**
 * Created on 5/26/2018.
 */
public enum QuestType {
    QuestReq_LostItem(0),
    QuestReq_AcceptQuest(1),
    QuestReq_CompleteQuest(2),
    QuestReq_ResignQuest(3),
    QuestReq_OpeningScript(4),
    QuestReq_CompleteScript(5),
    QuestReq_LaterStep(6),
    // v265: the client sends this with the quest id while handling QuestRes_Act_Success
    QuestReq_AfterActSuccess(7),

    // v265 moved the results up by one: the client reads timers at 8-11 and the success at 12 (the rest follow)
    QuestRes_Start_QuestTimer(8),
    QuestRes_End_QuestTimer(9),
    QuestRes_Start_TimeKeepQuestTimer(10),
    QuestRes_End_TimeKeepQuestTimer(11),

    QuestRes_Act_Success(12),
    QuestRes_Act_Failed_Unknown(13),
    QuestRes_Act_Failed_Inventory(14),
    QuestRes_Act_Failed_Meso(15),
    QuestRes_Act_Failed_OverflowMeso(16),
    QuestRes_Act_Failed_Pet(17),
    QuestRes_Act_Failed_Equipped(18),
    QuestRes_Act_Failed_OnlyItem(19),
    QuestRes_Act_Failed_TimeOver(20),
    QuestRes_Act_Failed_State(21),
    QuestRes_Act_Failed_Quest(22),
    QuestRes_Act_Failed_Block(23),
    QuestRes_Act_Failed_Universe(24),
    QuestRes_Act_Reset_QuestTimer(25),

    MakingRes_Success_SoSo(25),
    MakingRes_Success_Good(26),
    MakingRes_Success_Cool(27),
    MakingRes_Fail_Unknown(28),
    MakingRes_Fail_Prob(29),
    MakingRes_Fail_NoDecomposer(30),
    MakingRes_Fail_MesoOverflow(31),
    MakingRes_Fail_TooHighFee(32),
    MakingRes_Fail_NotEnoughMeso(33),
    ;

    private byte val;

    QuestType(int val) {
        this.val = (byte) val;
    }

    public byte getVal() {
        return val;
    }

    public static QuestType getQTFromByte(byte type) {
        return Arrays.stream(values()).filter(qt -> qt.getVal() == type).findAny().orElse(null);
    }
}
