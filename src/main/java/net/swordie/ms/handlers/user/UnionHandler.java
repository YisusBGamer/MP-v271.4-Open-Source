package net.swordie.ms.handlers.user;

import net.swordie.ms.client.Account;
import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.quest.Quest;
import net.swordie.ms.client.character.quest.QuestManager;
import net.swordie.ms.client.character.union.Union;
import net.swordie.ms.client.character.union.UnionBoard;
import net.swordie.ms.client.character.union.UnionMember;
import net.swordie.ms.connection.InPacket;
import net.swordie.ms.connection.packet.CUIHandler;
import net.swordie.ms.connection.packet.WvsContext;
import net.swordie.ms.connection.packet.model.MessagePacket;
import net.swordie.ms.constants.QuestConstants;
import net.swordie.ms.handlers.Handler;
import net.swordie.ms.handlers.header.InHeader;
import net.swordie.ms.loaders.QuestData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class UnionHandler {
    private static final int DRAGONS_DOMAIN_FIELD = 921172100;
    private static final int LEGION_RAID_EXIT_FIELD = 921172200;
    private static final int LEGION_RAID_EXIT_FIELD_2 = 921172201;

    private static final Logger log = LogManager.getLogger();


    @Handler(op = InHeader.UNION_REQUEST)
    public static void handleUnionRequest(Char chr, InPacket inPacket) {
        Union union = chr.getUnion();
        if (chr.getHP() <= 0) {
            chr.write(CUIHandler.unionRequestFail(3));
            return;
        }
        chr.getUnionRaid().updateTotalDamageDone(chr, true);
        // only take lv 60+ 3rd job characters
        var eligibleChars = union.getEligibleUnionChars();
        UnionBoard activeBoard = union.getBoardByPreset(chr.getActiveUnionPreset());
        chr.getUnionRaid().setWindowOpened(true);
        chr.getUnionRaid().sendCoinState(chr, false);
        chr.write(CUIHandler.unionResult(chr.getUnionRaid().getUnclaimedCoins(), union.getUnionRank(), eligibleChars, activeBoard,
                null, null, null)); // todo: decide how u want to handle mobile/lab members
        chr.write(CUIHandler.unionPresets(union));
        Quest quest = chr.getQuestManager().getOrCreateQuestById(QuestConstants.UNION_COIN);
        quest.setProperty("point", union.getUnionCoin());
        chr.write(WvsContext.message(MessagePacket.questRecordExMessage(quest)));
        chr.write(WvsContext.message(MessagePacket.questRecordMessage(quest)));
    }

    @Handler(op = InHeader.UNION_PRESET_CHANGED)
    public static void handleUnionPresetChanged(Char chr, InPacket inPacket) {
        // v265 (0x144621090): int, int n (the client sends only when n == 5), n ints. Not a board: the old body
        // (preset, grid, members) underflowed. Preset switching goes through UNION_ASSIGN_REQUEST type 2.
        int first = inPacket.decodeInt();
        int n = inPacket.getUnreadAmount() >= 4 ? inPacket.decodeInt() : 0;
        List<Integer> values = new ArrayList<>();
        for (int i = 0; i < n && inPacket.getUnreadAmount() >= 4; i++) {
            values.add(inPacket.decodeInt());
        }
        log.debug("UNION_PRESET_CHANGED " + first + " " + values);
    }

    @Handler(op = InHeader.UNION_ASSIGN_REQUEST)
    public static void handleUnionAssignRequest(Char chr, InPacket inPacket) {
        Union union = chr.getUnion();
        chr.getUnionRaid().updateTotalDamageDone(chr, true);
        // v265 (0x143d97d70 + 0x141200db0): int type (0 save, 2 apply preset), int preset (0-based, "presetNo"),
        // byte has board, [8 ints synergy grid, int n, n members]
        inPacket.decodeInt(); // type
        int preset = inPacket.decodeInt();
        if (!union.hasPresetUnlocked(preset)) {
            chr.getOffenseManager().addOffense("Adding union preset they do not have (" + preset + ", max preset " + union.getPresets() + ")");
            chr.dispose();
            return;
        }
        UnionBoard ub = union.getBoardByPreset(preset);
        chr.setActiveUnionPreset(preset);
        boolean hasBoard = inPacket.getUnreadAmount() > 0 && inPacket.decodeByte() != 0;
        if (!hasBoard || inPacket.getUnreadAmount() < 4 * Union.MAX_STATS + 4) {
            union.updateUnionBonuses(chr);
            chr.write(CUIHandler.unionAssignResult(union.getUnionRank(), union.getEligibleUnionChars(),
                    union.getBoardByPreset(chr.getActiveUnionPreset()), null, null, null));
            return;
        }

        List<Integer> synGrid = new ArrayList<>();
        for (int i = 0; i < Union.MAX_STATS; i++) {
            int syn = inPacket.decodeInt();
            if (syn < 0 || syn >= Union.MAX_STATS || synGrid.contains(syn)) {
                chr.getOffenseManager().addOffense("Added invalid or duplicate union stat (expected 0~7, got " +
                        syn + ", grid up til this point = " + synGrid + ")");
                chr.dispose();
                return;
            }
            synGrid.add(syn);
        }
        ub.setSynergyGrid(synGrid);

        int count = inPacket.decodeInt();
        Account account = chr.getAccount();
        ub.clearActiveMembers();
        // member record (client 0x1411fef70): 8 ints, a long, a string, and a second string when the first int is 2
        try {
            for (int i = 0; i < count && inPacket.getUnreadAmount() > 0; i++) {
                UnionMember.setCharGridPos(inPacket, preset, account);
            }
        } catch (RuntimeException e) {
            log.warn("Union assign: member list misread (" + count + " members): " + e);
        }
        union.updateUnionBonuses(chr);

        var eligibleChars = union.getEligibleUnionChars();
        UnionBoard activeBoard = union.getBoardByPreset(chr.getActiveUnionPreset());

        // GMS answers a save with the saved preset first, so the window's preset list shows the new board
        chr.write(WvsContext.unionPresetInfoResult(preset, true, ub));
        chr.write(CUIHandler.unionAssignResult(union.getUnionRank(), eligibleChars, activeBoard,
                null, null, null));
    }

    @Handler(op = InHeader.UNION_PRESET_INFO_REQUEST)
    public static void handleUnionPresetInfoRequest(Char chr, InPacket inPacket) {
        int preset = inPacket.decodeInt();
        Union union = chr.getUnion();
        UnionBoard ub = union.getBoardByPreset(preset);
        boolean unlocked = union.hasPresetUnlocked(preset);
        chr.getUnionRaid().updateTotalDamageDone(chr, true);
        chr.write(WvsContext.unionPresetInfoResult(preset, unlocked, ub));
    }

    @Handler(op = InHeader.UNION_RAID_RETREAT)
    public static void handleUnionRaidRetreat(Char chr, InPacket inPacket) {
        inPacket.decodeInt(); // tRequestTime if i had to guess
        inPacket.decodeInt(); // the v271 client sends two ints; v265.3 only read the first

        if (chr.getUnionRaid() != null) {
            chr.getUnionRaid().retreat(chr);
        }

/*        if (chr.getFieldID() == 921172000) {
            chr.getScriptManager().warpInstanceOut(LEGION_RAID_EXIT_FIELD);
        } else if (chr.getFieldID() == DRAGONS_DOMAIN_FIELD) {
            chr.getScriptManager().warpInstanceOut(LEGION_RAID_EXIT_FIELD_2);
        }*/
    }
}
