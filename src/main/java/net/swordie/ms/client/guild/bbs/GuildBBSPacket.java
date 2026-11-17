package net.swordie.ms.client.guild.bbs;

import net.swordie.ms.connection.Encodable;
import net.swordie.ms.connection.OutPacket;

import java.util.List;

/**
 * GUILD_BBS_RESULT (v271 703, handler 0x145a18f90). The client only reads it while its board window is open.
 * Categories: the client's defaults (type 12 with 0) are 1 = notices (master writes) and 2 = posts.
 *
 * @author Sjonnie
 * Created on 8/12/2018.
 */
public class GuildBBSPacket implements Encodable {

    public static final int CATEGORY_NOTICE = 1;
    public static final int CATEGORY_POST = 2;
    /** the client keeps 11 records per page and drops the first page * 11 of the list it gets */
    public static final int PAGE_SIZE = 11;

    private GuildBBSType type;
    private BBSRecord record;
    private int totalSize;
    private int pinned;
    private List<BBSRecord> records;

    @Override
    public void encode(OutPacket outPacket) {
        outPacket.encodeByte(type.getVal());
        switch (type) {
            case Res_LoadPages -> {
                // 4 (not used), 4 total (gates the list), 4 (window +0x18, also set by 16)
                outPacket.encodeInt(0);
                outPacket.encodeInt(totalSize);
                outPacket.encodeInt(pinned);
                if (totalSize > 0) {
                    outPacket.encodeInt(records.size());
                    for (var r : records) {
                        r.encodeForPagesLoad(outPacket);
                    }
                }
            }
            case Res_LoadRecord -> {
                // 4 post, 1, 4 reply count, 4 n replies, 1 has poll (creates an empty one), 4 (record +0x48 if > 0)
                outPacket.encodeInt(record.getIdForBbs());
                outPacket.encodeByte(0);
                outPacket.encodeInt(record.getReplies().size());
                outPacket.encodeInt(record.getReplies().size());
                for (var reply : record.getReplies()) {
                    reply.encode(outPacket);
                }
                outPacket.encodeByte(0);
                outPacket.encodeInt(0);
            }
            case Res_MaybeLoadUpvotes -> {
                // 4 post, 4 n character ids (the like count; "liked" when this character is in it)
                outPacket.encodeInt(record.getIdForBbs());
                outPacket.encodeInt(record.getUpvotedIds().size());
                for (int id : record.getUpvotedIds()) {
                    outPacket.encodeInt(id);
                }
            }
            case Res_Categories -> outPacket.encodeByte(0); // 0: the client's two default categories
            case Res_Pin -> {
                // 4 post, 1 done, 1 pinned, 4 (window +0x18)
                outPacket.encodeInt(record == null ? 0 : record.getIdForBbs());
                outPacket.encodeByte(0);
                outPacket.encodeByte(0);
                outPacket.encodeInt(0);
            }
            default -> {
            }
        }
    }

    public static GuildBBSPacket loadPages(int totalSize, List<BBSRecord> pageRecords, int pinned) {
        GuildBBSPacket gbp = new GuildBBSPacket();

        gbp.type = GuildBBSType.Res_LoadPages;
        gbp.totalSize = totalSize;
        gbp.pinned = pinned;
        gbp.records = pageRecords;

        return gbp;
    }

    public static GuildBBSPacket loadRecord(BBSRecord record) {
        GuildBBSPacket gbp = new GuildBBSPacket();

        gbp.type = GuildBBSType.Res_LoadRecord;
        gbp.record = record;

        return gbp;
    }

    public static GuildBBSPacket upvotes(BBSRecord record) {
        GuildBBSPacket gbp = new GuildBBSPacket();

        gbp.type = GuildBBSType.Res_MaybeLoadUpvotes;
        gbp.record = record;

        return gbp;
    }

    public static GuildBBSPacket categories() {
        GuildBBSPacket gbp = new GuildBBSPacket();

        gbp.type = GuildBBSType.Res_Categories;

        return gbp;
    }

    /** Pinning is not kept: always answered "not done". */
    public static GuildBBSPacket pinRefused(BBSRecord record) {
        GuildBBSPacket gbp = new GuildBBSPacket();

        gbp.type = GuildBBSType.Res_Pin;
        gbp.record = record;

        return gbp;
    }
}
