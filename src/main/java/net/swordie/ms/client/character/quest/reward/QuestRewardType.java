package net.swordie.ms.client.character.quest.reward;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Arrays;

/**
 * Created on 3/8/2018.
 */
public enum QuestRewardType {
    EXP(0),
    ITEM(1),
    MONEY(2),
    POP(3),
    BUFFITEM(4),
    SKILL(5),
    SP(6),
    TRAIT_EXP(7),
    RECORD(8),
    EXP_TABLE(9),
    QUEST_STATE(10),
    PET(11)
    ;

    private byte val;

    QuestRewardType(int val) {
        this.val = (byte) val;
    }

    public byte getVal() {
        return val;
    }

    public static QuestRewardType getQPRTByObj(Object o) {
        return o instanceof QuestExpReward ? EXP :
                o instanceof QuestItemReward ? ITEM :
                o instanceof QuestMoneyReward ? MONEY :
                o instanceof QuestPopReward ? POP :
                o instanceof QuestBuffItemReward ? BUFFITEM :
                o instanceof QuestSkillReward ? SKILL :
                o instanceof QuestSpReward ? SP :
                o instanceof QuestTraitExpReward ? TRAIT_EXP :
                o instanceof QuestRecordReward ? RECORD :
                o instanceof QuestExpTableReward ? EXP_TABLE :
                o instanceof QuestStateReward ? QUEST_STATE :
                o instanceof QuestPetReward ? PET : null;
    }

    public static QuestRewardType getQPRTByVal(byte val) {
        return Arrays.stream(QuestRewardType.values())
                .filter(qprt -> qprt.getVal() == val).findFirst().orElse(null);
    }


    public QuestReward load(RandomAccessFile dis) throws IOException {
        switch(this) {
            case EXP:
                return (QuestReward) new QuestExpReward().load(dis);
            case ITEM:
                return (QuestReward) new QuestItemReward().load(dis);
            case MONEY:
                return (QuestReward) new QuestMoneyReward().load(dis);
            case POP:
                return (QuestReward) new QuestPopReward().load(dis);
            case BUFFITEM:
                return (QuestReward) new QuestBuffItemReward().load(dis);
            case SKILL:
                return (QuestReward) new QuestSkillReward().load(dis);
            case SP:
                return (QuestReward) new QuestSpReward().load(dis);
            case TRAIT_EXP:
                return (QuestReward) new QuestTraitExpReward().load(dis);
            case RECORD:
                return (QuestReward) new QuestRecordReward().load(dis);
            case EXP_TABLE:
                return (QuestReward) new QuestExpTableReward().load(dis);
            case QUEST_STATE:
                return (QuestReward) new QuestStateReward().load(dis);
            case PET:
                return (QuestReward) new QuestPetReward().load(dis);
            default:
                return null;
        }
    }
}
