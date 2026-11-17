package net.swordie.ms.client.character.quest.requirement;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Arrays;

/**
 * Created on 3/8/2018.
 */
public enum QuestStartRequirementType {

    QUEST(0),
    ITEM(1),
    JOB(2),
    MARRIAGE(3),
    MAX_LEVEL(4),
    MIN_STAT(5),
    RECORD(6),
    SKILL(7),
    EQUIP(8),
    WORLD(9),
    TIME(10),
    PET(11)
    ;

    private byte val;

    QuestStartRequirementType(int val) {
        this.val = (byte) val;
    }

    public byte getVal() {
        return val;
    }

    public static QuestStartRequirementType getQPRTByObj(Object o) {
        return o instanceof QuestStartCompletionRequirement ? QUEST :
                o instanceof QuestStartItemRequirement ? ITEM :
                o instanceof QuestStartJobRequirement ? JOB :
                o instanceof QuestStartMarriageRequirement ? MARRIAGE :
                o instanceof QuestStartMaxLevelRequirement ? MAX_LEVEL :
                o instanceof QuestStartMinStatRequirement ? MIN_STAT :
                o instanceof QuestRecordRequirement ? RECORD :
                o instanceof QuestSkillRequirement ? SKILL :
                o instanceof QuestEquipRequirement ? EQUIP :
                o instanceof QuestWorldRequirement ? WORLD :
                o instanceof QuestTimeRequirement ? TIME :
                o instanceof QuestPetRequirement ? PET
                : null;
    }

    public static QuestStartRequirementType getQPRTByVal(byte val) {
        return Arrays.stream(QuestStartRequirementType.values())
                .filter(qprt -> qprt.getVal() == val).findFirst().orElse(null);
    }


    public QuestStartRequirement load(RandomAccessFile dis) throws IOException {
        switch (this) {
            case QUEST:
                return (QuestStartRequirement) new QuestStartCompletionRequirement().load(dis);
            case ITEM:
                return (QuestStartRequirement) new QuestStartItemRequirement().load(dis);
            case JOB:
                return (QuestStartRequirement) new QuestStartJobRequirement().load(dis);
            case MARRIAGE:
                return (QuestStartRequirement) new QuestStartMarriageRequirement().load(dis);
            case MAX_LEVEL:
                return (QuestStartRequirement) new QuestStartMaxLevelRequirement().load(dis);
            case MIN_STAT:
                return (QuestStartRequirement) new QuestStartMinStatRequirement().load(dis);
            case RECORD:
                return (QuestStartRequirement) new QuestRecordRequirement().load(dis);
            case SKILL:
                return (QuestStartRequirement) new QuestSkillRequirement().load(dis);
            case EQUIP:
                return (QuestStartRequirement) new QuestEquipRequirement().load(dis);
            case WORLD:
                return (QuestStartRequirement) new QuestWorldRequirement().load(dis);
            case TIME:
                return (QuestStartRequirement) new QuestTimeRequirement().load(dis);
            case PET:
                return (QuestStartRequirement) new QuestPetRequirement().load(dis);
            default:
                return null;
        }
    }
}
