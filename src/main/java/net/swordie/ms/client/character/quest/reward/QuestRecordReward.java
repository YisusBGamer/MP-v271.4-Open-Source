package net.swordie.ms.client.character.quest.reward;

import net.swordie.ms.constants.QuestConstants;

import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.quest.Quest;
import net.swordie.ms.client.character.quest.QuestManager;
import net.swordie.ms.connection.packet.WvsContext;
import net.swordie.ms.connection.packet.model.MessagePacket;
import net.swordie.ms.loaders.DatSerializable;
import net.swordie.ms.loaders.QuestData;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Quest record reward. Covers two Act.img actions:
 * <ul>
 *     <li><code>info</code>: sets the quest record value of the quest itself (<code>key</code> is empty)</li>
 *     <li><code>setEx/[n]</code> (qrID, key, value): sets <code>key=value</code> in the record-ex of quest qrID</li>
 * </ul>
 */
public class QuestRecordReward implements QuestReward {

    private int status;
    private int questID;
    private String key = "";
    private String value = "";

    public QuestRecordReward() {
    }

    public QuestRecordReward(int status, int questID, String key, String value) {
        this.status = status;
        this.questID = questID;
        this.key = key == null ? "" : key;
        this.value = value == null ? "" : value;
    }

    @Override
    public int getStatus() {
        return status;
    }

    public int getQuestID() {
        return questID;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public boolean isExRecord() {
        return !getKey().isEmpty();
    }

    @Override
    public void giveReward(Char chr) {
        QuestManager qm = chr.getQuestManager();
        Quest quest = qm.getQuestById(getQuestID());
        if (quest == null) {
            quest = QuestData.createQuestFromId(getQuestID());
            qm.addCustomQuest(quest);
        }
        if (isExRecord()) {
            // key=value records live in the quest's normal record here (that's what is saved, and what the ex record
            // packet sends), except for the quests that really keep two records
            if (QuestConstants.is2QrQuest(getQuestID())) {
                quest.setPropertyEx(getKey(), getValue());
            } else {
                quest.setProperty(getKey(), getValue());
            }
            chr.write(WvsContext.message(MessagePacket.questRecordExMessage(quest)));
        } else {
            if (!quest.getMobReqs().isEmpty()) {
                // the record of quests with mob requirements holds the kill counts, don't overwrite them
                return;
            }
            quest.setQrValue(getValue());
            chr.write(WvsContext.message(MessagePacket.questRecordMessage(quest)));
        }
    }

    @Override
    public void write(RandomAccessFile dos) throws IOException {
        dos.writeInt(getStatus());
        dos.writeInt(getQuestID());
        dos.writeUTF(getKey());
        dos.writeUTF(getValue());
    }

    @Override
    public DatSerializable load(RandomAccessFile dis) throws IOException {
        return new QuestRecordReward(dis.readInt(), dis.readInt(), dis.readUTF(), dis.readUTF());
    }
}
