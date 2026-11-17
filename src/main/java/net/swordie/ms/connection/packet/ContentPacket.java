package net.swordie.ms.connection.packet;

import net.swordie.ms.connection.OutPacket;
import net.swordie.ms.client.character.MonsterPark;
import net.swordie.ms.handlers.header.OutHeader;

import java.util.List;

/** Builders for content systems (professions, Monster Park, ...). */
public class ContentPacket {

    /** Gathering finished: int chrId (user pool prefix, router 0x1450d70d0), byte success (handler 0x144aec970). */
    public static OutPacket gatherResult(int chrId, boolean success) {
        OutPacket outPacket = new OutPacket(OutHeader.GATHER_RESULT);
        outPacket.encodeInt(chrId);
        outPacket.encodeByte(success);
        return outPacket;
    }

    /** Free clears shown in the Monster Park window when no character is given. */
    public static final int MONSTER_PARK_SHOWN_FREE = MonsterPark.WORLD_FREE_CLEARS;

    /**
     * Opens the Monster Park window for one gate: MONSTER_PARK_RESULT kind 1: int window (1 = Monster Park), int gate,
     * 6 ints (free and ticket clears, an int new in v271 (0x1426cf440: sent 0, meaning unknown), the two ticket items,
     * the Maple Point price), int n, n x (int gate, int index, S name, S map mark, int min level, int max level, int
     * story quest).
     */
    public static OutPacket monsterParkWindow(int gate) {
        return monsterParkWindow(gate, MONSTER_PARK_SHOWN_FREE, 0);
    }

    /** The window with this character's clears left today (free, and with the tickets it holds). */
    public static OutPacket monsterParkWindow(net.swordie.ms.client.character.Char chr, int gate) {
        int free = !MonsterPark.unlimited() && MonsterPark.getClearsToday(chr) >= MonsterPark.CHAR_CLEARS ? 0 : MonsterPark.getFreeClearsLeft(chr);
        return monsterParkWindow(gate, free, MonsterPark.getTicketClearsLeft(chr));
    }

    private static OutPacket monsterParkWindow(int gate, int free, int ticket) {
        OutPacket outPacket = new OutPacket(OutHeader.MONSTER_PARK_RESULT);
        outPacket.encodeInt(1);
        outPacket.encodeInt(1);
        outPacket.encodeInt(gate);
        outPacket.encodeInt(free);
        outPacket.encodeInt(ticket);
        outPacket.encodeInt(0);
        outPacket.encodeInt(MonsterPark.FREE_TICKET);
        outPacket.encodeInt(MonsterPark.CASH_TICKET);
        outPacket.encodeInt(0);
        List<MonsterPark.Dungeon> dungeons = MonsterPark.getDungeons(gate);
        outPacket.encodeInt(dungeons.size());
        for (int i = 0; i < dungeons.size(); i++) {
            MonsterPark.Dungeon d = dungeons.get(i);
            outPacket.encodeInt(gate);
            outPacket.encodeInt(i);
            outPacket.encodeString(d.name());
            outPacket.encodeString(d.mapMark());
            outPacket.encodeInt(d.minLevel());
            outPacket.encodeInt(d.maxLevel());
            outPacket.encodeInt(d.quest());
        }
        return outPacket;
    }

    /**
     * Answers the window's Enter (703): MONSTER_PARK_RESULT kind 2, int result (1 = fine, no dialog), S message shown
     * by Spiegelmann otherwise. It also lets the window send again.
     */
    public static OutPacket monsterParkResult(int result, String message) {
        OutPacket outPacket = new OutPacket(OutHeader.MONSTER_PARK_RESULT);
        outPacket.encodeInt(2);
        outPacket.encodeInt(result);
        outPacket.encodeString(message == null ? "" : message);
        return outPacket;
    }

    /**
     * UNION_CHAMPION_RESULT 592 (client 0x1421e6560): int type (0 register, 1 release, 2 rank up), int code
     * (0 = done; register 1 duplicate, 2 level, 3 live value, 4 job, else common; release 7 not this character,
     * 8 live value, else common; rank up 7 not this character, 9 rank max, 10 live value, 11 busy, else common),
     * and for a done rank up two bytes for the result dialog (old rank, new rank).
     */
    public static OutPacket championResult(int type, int code, int oldRank, int newRank) {
        OutPacket outPacket = new OutPacket(OutHeader.UNION_CHAMPION_RESULT);
        outPacket.encodeInt(type);
        outPacket.encodeInt(code);
        if (type == 2 && code == 0) {
            outPacket.encodeByte(oldRank);
            outPacket.encodeByte(newRank);
        }
        return outPacket;
    }

    /**
     * UNION_CHAMPION_INFO 626 (client 0x1421e8e40 -> 0x1421fe6b0): int part, int (ignored), int slot (< 6), then the
     * part body (read only when bytes remain). Part 0 is the champion's CharacterData (DecodeCharacterData
     * 0x140680fe0: the window's avatar and stats). Returns null when the character can't be encoded.
     */
    public static OutPacket championCharacterData(int slot, net.swordie.ms.client.character.Char champion) {
        OutPacket body = new OutPacket(OutHeader.UNION_CHAMPION_INFO);
        body.encodeInt(0);
        body.encodeInt(0);
        body.encodeInt(slot);
        try {
            // the same v271 CharacterData as SET_FIELD (0x1427867f0 -> 0x1407c9950); the v265 full encoder breaks it
            net.swordie.ms.client.character.CharEncoder.encodeV271Minimal(champion, body);
        } catch (Exception e) {
            return null;
        }
        return body;
    }

    /**
     * Everything the Champion Raid's member for this slot needs: part 0 CharacterData, 1 the champion's key maps
     * (4 x FuncKeyMap in v271), 2 its quick slots (byte has + 32 ints, 0x1421feb30), 3 its macros (byte n <= 5,
     * n x (str, byte, 3 ints), 0x14065f550), 4-6 empty.
     */
    public static java.util.List<OutPacket> championInfoAll(int slot, net.swordie.ms.client.character.Char c) {
        java.util.List<OutPacket> list = new java.util.ArrayList<>();
        OutPacket data = championCharacterData(slot, c);
        if (data != null) {
            list.add(data);
        }
        OutPacket keys = championPart(1, slot);
        var maps = c.getFuncKeyMap();
        var map = maps == null || maps.isEmpty() ? null : maps.get(0);
        for (int i = 0; i < 4; i++) { // v271 reads four key maps
            if (map == null) {
                keys.encodeByte(1);
            } else {
                map.encode(keys);
            }
        }
        list.add(keys);
        OutPacket quick = championPart(2, slot);
        var slots = c.getQuickslotKeys();
        boolean has = slots != null && !slots.isEmpty();
        quick.encodeByte(has);
        if (has) {
            for (int i = 0; i < 32; i++) {
                quick.encodeInt(i < slots.size() ? slots.get(i) : 0);
            }
        }
        list.add(quick);
        OutPacket macros = championPart(3, slot);
        var ms = c.getMacros() == null ? new java.util.ArrayList<net.swordie.ms.client.character.Macro>()
                : new java.util.ArrayList<>(c.getMacros());
        ms.sort(java.util.Comparator.comparingInt(net.swordie.ms.client.character.Macro::getMacroPos));
        int n = Math.min(5, ms.size());
        macros.encodeByte(n);
        for (int i = 0; i < n; i++) {
            ms.get(i).encode(macros);
        }
        list.add(macros);
        for (int part = 4; part <= 6; part++) {
            list.add(championInfoDefault(part, slot));
        }
        return list;
    }

    private static OutPacket championPart(int part, int slot) {
        OutPacket outPacket = new OutPacket(OutHeader.UNION_CHAMPION_INFO);
        outPacket.encodeInt(part);
        outPacket.encodeInt(0);
        outPacket.encodeInt(slot);
        return outPacket;
    }

    private static OutPacket championRaid(int type) {
        OutPacket outPacket = new OutPacket(OutHeader.UNION_CHAMPION_RAID_RESULT);
        outPacket.encodeInt(type);
        return outPacket;
    }

    /** 627 type 1: int kept by the raid manager; GMS sends 0 before a squad's 626 parts. */
    public static OutPacket championRaidReset() {
        OutPacket outPacket = championRaid(1);
        outPacket.encodeInt(0);
        return outPacket;
    }

    /** 627 type 3: shows the tag timer from the last type 4 config (GMS sends it after each config). */
    public static OutPacket championRaidTagTimer() {
        return championRaid(3);
    }

    /** 627 type 0: the squad window (members from 626). */
    public static OutPacket championRaidOpen() {
        return championRaid(0);
    }

    /** 627 type 4 (0x140f52570): 4 ints; the 2nd freezes every clock when non-zero, the 3rd is the tag cooldown. */
    public static OutPacket championRaidConfig(int a, int frozen, int tagCooldownMs, int d) {
        OutPacket outPacket = championRaid(4);
        outPacket.encodeInt(a);
        outPacket.encodeInt(frozen);
        outPacket.encodeInt(tagCooldownMs);
        outPacket.encodeInt(d);
        return outPacket;
    }

    /** 627 type 6: swap the local user to the slot's member (-1: back to the original character, raid over). */
    public static OutPacket championRaidSwap(int slot, int value) {
        OutPacket outPacket = championRaid(6);
        outPacket.encodeInt(slot);
        outPacket.encodeInt(value);
        outPacket.encodeInt(0);
        return outPacket;
    }

    /** 627 type 7: the swap is done; the tag cooldown starts. */
    public static OutPacket championRaidSwapDone() {
        return championRaid(7);
    }

    /** 627 type 8: this slot has no time left. */
    public static OutPacket championRaidTimeOver(int slot) {
        OutPacket outPacket = championRaid(8);
        outPacket.encodeInt(slot);
        return outPacket;
    }

    /** 627 type 9: the clocks stand still for this long (0 runs them again). */
    public static OutPacket championRaidPause(int ms) {
        OutPacket outPacket = championRaid(9);
        outPacket.encodeInt(ms);
        return outPacket;
    }

    /** 627 type 10 (0x1421ed3e0): the member with this character id loses ms of play time (negative adds). */
    public static OutPacket championRaidTime(int charId, int ms) {
        OutPacket outPacket = championRaid(10);
        outPacket.encodeInt(charId);
        outPacket.encodeInt(ms);
        return outPacket;
    }

    /**
     * CHAMPION_RAID_FIELD 2215 (raid field OnPacket 0x141caab90 -> 0x141caad50): int type. 0 = 4 ints (element 0 acid,
     * 1 frozen, 2 flame; the rest stored) and makes the stage and squad windows; 1 = 3 ints stored by the field.
     */
    public static OutPacket championRaidField(int type, int... values) {
        OutPacket outPacket = new OutPacket(OutHeader.CHAMPION_RAID_FIELD);
        outPacket.encodeInt(type);
        for (int v : values) {
            outPacket.encodeInt(v);
        }
        return outPacket;
    }

    /**
     * UNION_CHAMPION_INFO parts 1-6 with default / empty bodies (layouts from the client, re-next): 1 = 3 x key map
     * (byte isDefault = 1), 2 = quick slots (byte has = 0), 3 = byte n = 0, 4 = 3 bytes + int n = 0,
     * 5 = byte n = 0 + 3 bytes, 6 = 2 bytes + int n = 0.
     */
    public static OutPacket championInfoDefault(int part, int slot) {
        OutPacket outPacket = new OutPacket(OutHeader.UNION_CHAMPION_INFO);
        outPacket.encodeInt(part);
        outPacket.encodeInt(0);
        outPacket.encodeInt(slot);
        switch (part) {
            case 1 -> {
                for (int i = 0; i < 4; i++) { // v271 reads four key maps (each: byte 1 = default)
                    outPacket.encodeByte(1);
                }
            }
            case 2 -> outPacket.encodeByte(0); // 0 = default quick slots (0x1421feb30); non-zero is followed by 32 ints
            case 3 -> outPacket.encodeByte(0);
            case 4 -> {
                outPacket.encodeByte(0);
                outPacket.encodeByte(0);
                outPacket.encodeByte(0);
                outPacket.encodeInt(0);
            }
            case 5 -> {
                outPacket.encodeByte(0);
                outPacket.encodeByte(0);
                outPacket.encodeByte(0);
                outPacket.encodeByte(0);
            }
            case 6 -> {
                outPacket.encodeByte(0);
                outPacket.encodeByte(0);
                outPacket.encodeInt(0);
            }
            default -> {
            }
        }
        return outPacket;
    }

    /** MONSTER_PARK_RESULT kind 1, window 2: the Monster Park Extreme window (names of this week's clears come from quest-ex 502345). */
    public static OutPacket monsterParkExtremeWindow() {
        OutPacket outPacket = new OutPacket(OutHeader.MONSTER_PARK_RESULT);
        outPacket.encodeInt(1);
        outPacket.encodeInt(2);
        return outPacket;
    }

    /**
     * MONSTER_PARK_EXTREME_START 2510 (client 0x142487f20): int seconds left (field timer), then the altar: int hold
     * time in ms, int x, int y, and its key area as offsets from x/y (left, top, right, bottom). The altar starts
     * disabled; the list and the boss bar are cleared.
     */
    public static OutPacket monsterParkExtremeStart(int seconds, int holdMs, int x, int y, int left, int top,
                                                    int right, int bottom) {
        OutPacket outPacket = new OutPacket(OutHeader.MONSTER_PARK_EXTREME_START);
        outPacket.encodeInt(seconds);
        outPacket.encodeInt(holdMs);
        outPacket.encodeInt(x);
        outPacket.encodeInt(y);
        outPacket.encodeInt(left);
        outPacket.encodeInt(top);
        outPacket.encodeInt(right);
        outPacket.encodeInt(bottom);
        return outPacket;
    }

    /** MONSTER_PARK_EXTREME_RESET 2511: clears the hunt list. */
    public static OutPacket monsterParkExtremeReset() {
        return new OutPacket(OutHeader.MONSTER_PARK_EXTREME_RESET);
    }

    /**
     * MONSTER_PARK_EXTREME_HUNT 2512 (client 0x142488110): one row of the hunt list, keyed by mob id (icon and name
     * from the mob): int mob, int killed, int needed (ticked once killed >= needed), int (unused by the list).
     */
    public static OutPacket monsterParkExtremeHunt(int mobId, int killed, int needed) {
        OutPacket outPacket = new OutPacket(OutHeader.MONSTER_PARK_EXTREME_HUNT);
        outPacket.encodeInt(mobId);
        outPacket.encodeInt(killed);
        outPacket.encodeInt(needed);
        outPacket.encodeInt(0);
        return outPacket;
    }

    /**
     * MONSTER_PARK_EXTREME_BOSS 2513 (client 0x142488240): the boss bar: int, int, FT time it leaves, int, int HP
     * in 1/1000, int time on the map in ms (0 = no timer).
     */
    public static OutPacket monsterParkExtremeBoss(int mobId, int appearance, long leaveAtMillis, int hpPerMille,
                                                   int durationMs) {
        OutPacket outPacket = new OutPacket(OutHeader.MONSTER_PARK_EXTREME_BOSS);
        outPacket.encodeInt(mobId);
        outPacket.encodeInt(appearance);
        outPacket.encodeFT(net.swordie.ms.util.FileTime.fromEpochMillis(leaveAtMillis));
        outPacket.encodeInt(0);
        outPacket.encodeInt(hpPerMille);
        outPacket.encodeInt(durationMs);
        return outPacket;
    }

    /**
     * MONSTER_PARK_EXTREME_ALTAR 2514 (client 0x142488280): int value (sent back in the altar request), int state:
     * 0 disabled, 1 enabled (hold the NPC chat key on it), 2 activating, 3 activated.
     */
    public static OutPacket monsterParkExtremeAltar(int value, int state) {
        OutPacket outPacket = new OutPacket(OutHeader.MONSTER_PARK_EXTREME_ALTAR);
        outPacket.encodeInt(value);
        outPacket.encodeInt(state);
        return outPacket;
    }

    /** One Mu Lung Dojo ranking row: job, level, point (floor * 1000 + 1000 - seconds), rank, name. */
    public record DojoRankRow(int job, int level, int point, int rank, String name) {
    }

    /**
     * One tab of the Mu Lung Dojo ranking window. Record: job, level, this week's point, rank and top %, last week's
     * point, rank and top % (101 = none).
     */
    public record DojoRankTab(int type, int[] record, List<DojoRankRow> rows) {
    }

    /**
     * DOJANG_RANKING_RESULT 298 (client 0x1453c6460 -> window 0x143fb6210, UIWindow4.img/muruengRank): byte mode
     * (0; anything else hides every tab), int, int n, n x (byte type, 8 ints record), int n, n x (byte type, int k,
     * k x (int job, int level, int point, int rank, S name, byte has look [+ 120-byte look])). Types: 0 the Novice
     * tab, 1 (no button), 2 Master, 3-49 / 94-99 Master by job.
     */
    public static OutPacket dojoRanking(List<DojoRankTab> tabs) {
        OutPacket outPacket = new OutPacket(OutHeader.DOJANG_RANKING_RESULT);
        outPacket.encodeByte(0);
        outPacket.encodeInt(0);
        outPacket.encodeInt(tabs.size());
        for (DojoRankTab t : tabs) {
            outPacket.encodeByte(t.type());
            for (int i = 0; i < 8; i++) {
                outPacket.encodeInt(t.record()[i]);
            }
        }
        outPacket.encodeInt(tabs.size());
        for (DojoRankTab t : tabs) {
            outPacket.encodeByte(t.type());
            outPacket.encodeInt(t.rows().size());
            for (DojoRankRow r : t.rows()) {
                outPacket.encodeInt(r.job());
                outPacket.encodeInt(r.level());
                outPacket.encodeInt(r.point());
                outPacket.encodeInt(r.rank());
                outPacket.encodeString(r.name());
                outPacket.encodeByte(0);
            }
        }
        return outPacket;
    }
}
