package net.swordie.ms.connection.packet;

import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.union.UnionArtifactModule;
import net.swordie.ms.connection.OutPacket;
import net.swordie.ms.handlers.header.OutHeader;
import net.swordie.ms.loaders.ArtifactData;
import net.swordie.ms.util.FileTime;

/**
 * Union Artifact packets. Layouts from the reference tree (WvsContext unionArtifact*, v265.3), checked against the
 * client's readers.
 */
public class UnionArtifactPacket {

    /** The account's crystals */
    public static OutPacket result(Char chr) {
        OutPacket outPacket = new OutPacket(OutHeader.UNION_ARTIFACT_RESULT);
        UnionArtifactModule.encode(chr, outPacket);
        return outPacket;
    }

    /** The artifact level table: max points held, EXP needed, AP */
    public static OutPacket information() {
        OutPacket outPacket = new OutPacket(OutHeader.UNION_ARTIFACT_INFORMATION);
        outPacket.encodeInt(ArtifactData.LEVELS.length);
        for (int[] level : ArtifactData.LEVELS) {
            outPacket.encodeInt(level[0]);
            outPacket.encodeInt(level[1]);
            outPacket.encodeInt(level[2]);
        }
        return outPacket;
    }

    /**
     * Answer to an UNION_ARTIFACT_UPDATE_REQUEST (type = the request's). The client reads the index only after a
     * successful type 2 or 4; a nonzero result shows its request-result message. With no artifact window open the
     * client reads nothing (the field-entry type 7 answer stays unread).
     */
    public static OutPacket update(int type, int result, int index) {
        OutPacket outPacket = new OutPacket(OutHeader.UNION_ARTIFACT_UPDATE);
        outPacket.encodeInt(type);
        outPacket.encodeInt(result);
        if (result == 0 && (type == 2 || type == 4)) {
            outPacket.encodeInt(index);
        }
        return outPacket;
    }

    /** EXP and points gained; level = the new artifact level, 0 if it didn't change */
    public static OutPacket gain(int exp, int point, int level) {
        OutPacket outPacket = new OutPacket(OutHeader.UNION_ARTIFACT_MSG);
        outPacket.encodeInt(exp);
        outPacket.encodeInt(point);
        outPacket.encodeInt(level);
        return outPacket;
    }

    /** A mission done (missionType 0 common, 1 boss, 2 special) */
    public static OutPacket missionDone(int missionType, int mission, int exp, int point) {
        OutPacket outPacket = new OutPacket(OutHeader.UNION_ARTIFACT_QUEST_MSG);
        outPacket.encodeInt(1);
        outPacket.encodeInt(missionType);
        outPacket.encodeInt(mission);
        outPacket.encodeInt(exp);
        outPacket.encodeInt(point);
        outPacket.encodeByte(0);
        outPacket.encodeByte(0);
        return outPacket;
    }

    /** A crystal's (index >= 0, type 2) or every crystal's (index < 0, type 3) new expiry */
    public static OutPacket extended(int index, FileTime expiration) {
        OutPacket outPacket = new OutPacket(OutHeader.UNION_ARTIFACT_QUEST_MSG);
        outPacket.encodeInt(index >= 0 ? 2 : 3);
        if (index >= 0) {
            outPacket.encodeInt(index);
        }
        // low half first like any FileTime (as GMS; the reference tree's source writes the high half first)
        outPacket.encodeFT(expiration);
        return outPacket;
    }
}
