package net.swordie.ms.client.guild;

import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.quest.Quest;
import net.swordie.ms.connection.packet.WvsContext;
import net.swordie.ms.connection.packet.model.MessagePacket;
import net.swordie.ms.constants.GameConstants;
import net.swordie.ms.enums.QuestStatus;
import net.swordie.ms.world.field.Field;

/**
 * The guild window's Guild HQ button (USER_FIELD_TRANSFER_REQUEST 26015). GMS answers with quest 26015 "link",
 * quest-ex 26015 "returnMap=&lt;map&gt;" and quest 26010 "&lt;map&gt;" before the warp, and the exit takes the
 * character back to that map.
 */
public final class GuildBaseReturn {

    public static final int TRANSFER_KEY = 26015;
    /** The client's return-map record; kept with the character, so the exit reads it back. */
    private static final int RETURN_RECORD = 26010;
    private static final int TOWN_FIELD = 200000300;
    private static final int TOWN_PORTAL = 11;

    private GuildBaseReturn() {
    }

    public static void enter(Char chr) {
        int from = chr.getFieldID();
        if (from != GameConstants.GUILD_BASE) {
            chr.getQuestManager().setHiddenRecord(RETURN_RECORD, String.valueOf(from));
            chr.write(WvsContext.message(MessagePacket.questRecordMessage(started(TRANSFER_KEY, "link"))));
            chr.write(WvsContext.message(MessagePacket.questRecordExMessage(TRANSFER_KEY, "returnMap=" + from)));
            chr.write(WvsContext.message(MessagePacket.questRecordMessage(started(RETURN_RECORD, String.valueOf(from)))));
        }
        chr.warp(GameConstants.GUILD_BASE);
    }

    /** The exit (portal out_guild, or the transfer request again from inside): back where the character came from. */
    public static void leave(Char chr) {
        int to = 0;
        try {
            String rec = chr.getQuestManager().getHiddenRecord(RETURN_RECORD);
            to = rec == null || rec.isEmpty() ? 0 : Integer.parseInt(rec);
        } catch (NumberFormatException ignored) {
        }
        Field field = to > 0 && to != GameConstants.GUILD_BASE ? chr.getOrCreateFieldByCurrentInstanceType(to) : null;
        if (field != null && !net.swordie.ms.constants.FieldConstants.teleportWouldBreak(chr, field)) {
            chr.warp(field);
        } else {
            Field town = chr.getOrCreateFieldByCurrentInstanceType(TOWN_FIELD);
            if (town == null) {
                chr.dispose();
                return;
            }
            chr.warp(town, town.getInfo().getPortalByID(TOWN_PORTAL));
        }
    }

    private static Quest started(int id, String value) {
        Quest q = new Quest();
        q.setQRKey(id);
        q.setStatus(QuestStatus.Started);
        q.setQrValue(value);
        return q;
    }
}
