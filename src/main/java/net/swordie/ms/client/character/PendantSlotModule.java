package net.swordie.ms.client.character;

import net.swordie.ms.client.character.quest.Quest;
import net.swordie.ms.client.character.quest.QuestManager;
import net.swordie.ms.connection.packet.WvsContext;
import net.swordie.ms.connection.packet.model.MessagePacket;
import net.swordie.ms.util.FileTime;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The second pendant slot is open for every character, for good. The client reads it from quest record 122700
 * (the slot's expiry as a FileTime); every login makes sure it holds the max time.
 */
public final class PendantSlotModule {

    private static final Logger log = LogManager.getLogger(PendantSlotModule.class);

    public static final int RECORD = 122700;
    public static final String FOREVER = String.valueOf(FileTime.MAX_TIME().toLong());

    private PendantSlotModule() {
    }

    public static void onLogin(Char chr) {
        try {
            QuestManager qm = chr.getQuestManager();
            Quest q = qm.getQuestById(RECORD);
            if (q == null || !FOREVER.equals(q.getQRValue())) {
                qm.setHiddenRecord(RECORD, FOREVER); // a plain record (122700 isn't a server-only id): sent at login
                q = qm.getQuestById(RECORD);
            }
            chr.write(WvsContext.message(MessagePacket.questRecordExMessage(q)));
        } catch (Exception e) {
            log.error("Could not open the second pendant slot of " + chr.getName(), e);
        }
    }
}
