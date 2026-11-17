package net.swordie.ms.client.character.union;

import net.swordie.ms.client.Account;
import net.swordie.ms.client.character.Char;
import net.swordie.ms.connection.InPacket;
import net.swordie.ms.connection.db.DatabaseManager;
import net.swordie.ms.scripts.ScriptManagerImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Legion Champions (v265). The client keeps the whole system in quest records (CWvsContext champion list builder
 * 0x140f4f7c0, slot count 0x140f4f6d0):
 * <ul>
 *     <li>504775 "[Legion] Champion's Path": start=1 unlocks the window, num = open slots (client default 2, at
 *     most 6).</li>
 *     <li>504782 + slot (0-5): cid = the champion's character id, rank = 0 (C) to 5 (SSS), -1 when empty, time,
 *     power (combat power).</li>
 *     <li>102198 on each character: isChampion=1 lets it take the rank trials (102241, 102187-102190), clear=1 once
 *     the trial boss is beaten.</li>
 * </ul>
 * Requests come on UNION_CHAMPION_REQUEST 1127: int type, byte slot, then type 0 (register, button 13 in
 * 0x1421db650): long, the current slot list (byte n, n x (byte has, [long])), int character id, int; type 1
 * (unregister, only by the champion itself: 0x1421de8f0, SID_UNION_CHAMPION_UNREGISTER_*); type 2 (rank up after
 * that rank's trial: 0x1421de7a0, checks 0x140f509d0).
 * The champions are kept per account in union_champions.
 */
public class ChampionModule {

    private static final Logger log = LogManager.getLogger();

    public static final int PATH_QUEST = 504775;
    public static final int SLOT_QUEST_BASE = 504782;
    public static final int TRIAL_RECORD = 102198;
    public static final int MAX_SLOTS = 6;
    public static final int BASE_SLOTS = 2;
    public static final int MAX_RANK = 5;
    /** Etc/unionChampion.img info/slotUnlockChampionGrade: each champion at this rank or above opens a slot. */
    public static final int SLOT_UNLOCK_RANK = 2;
    /** Trial quest for the rank up from rank i to i + 1 (Etc/unionChampion.img advancement/i/questID). */
    public static final int[] TRIAL_QUESTS = {102241, 102187, 102188, 102189, 102190};

    private static final int REG_DUPLICATE = 1;
    private static final int NOT_THIS_CHAR = 7;
    private static final int RANK_MAX = 9;
    private static final int RES_COMMON = 99;

    private static void result(Char chr, int type, int code) {
        chr.write(net.swordie.ms.connection.packet.ContentPacket.championResult(type, code, 0, 0));
    }

    private static final DateTimeFormatter TIME = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

    public static final class Slot {
        public int charId;
        public int rank;
        public long regTime;
    }

    private static final Map<Integer, Slot[]> accounts = new ConcurrentHashMap<>();

    // ------------------------------------------------------------------------------------------------------ state

    public static Slot[] getSlots(Account acc) {
        return accounts.computeIfAbsent(acc.getId(), id -> {
            Slot[] slots = new Slot[MAX_SLOTS];
            for (Map<String, Object> row : DatabaseManager.queryRows("SELECT slot AS s, charid AS c, `rank` AS r, "
                    + "regtime AS t FROM union_champions WHERE accid = ?", id)) {
                int s = ((Number) row.get("s")).intValue();
                if (s < 0 || s >= MAX_SLOTS) {
                    continue;
                }
                Slot slot = new Slot();
                slot.charId = ((Number) row.get("c")).intValue();
                slot.rank = ((Number) row.get("r")).intValue();
                slot.regTime = ((Number) row.get("t")).longValue();
                slots[s] = slot;
            }
            return slots;
        });
    }

    private static void saveSlot(Account acc, int s, Slot slot) {
        if (slot == null) {
            DatabaseManager.executeQuery("DELETE FROM union_champions WHERE accid = ? AND slot = ?", acc.getId(), s);
        } else {
            DatabaseManager.executeQuery("INSERT INTO union_champions (accid, slot, charid, `rank`, regtime) VALUES "
                            + "(?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE charid = VALUES(charid), `rank` = VALUES(`rank`), "
                            + "regtime = VALUES(regtime)", acc.getId(), s, slot.charId, slot.rank, slot.regTime);
        }
    }

    public static boolean isUnlocked(Char chr) {
        return chr.getQuestManager().hasQuestCompleted(PATH_QUEST);
    }

    /** Open slots: 2, one more for each champion of rank A or above, at most 6. */
    public static int getOpenSlots(Account acc) {
        int n = BASE_SLOTS;
        for (Slot s : getSlots(acc)) {
            if (s != null && s.rank >= SLOT_UNLOCK_RANK) {
                n++;
            }
        }
        return Math.min(MAX_SLOTS, n);
    }

    /** The slot this character holds, or -1. */
    public static int getSlotOf(Char chr) {
        Slot[] slots = getSlots(chr.getAccount());
        for (int i = 0; i < slots.length; i++) {
            if (slots[i] != null && slots[i].charId == chr.getId()) {
                return i;
            }
        }
        return -1;
    }

    /** Champions at or above a rank (Champion Insignia levels). */
    public static int countAtRank(Account acc, int rank) {
        int n = 0;
        for (Slot s : getSlots(acc)) {
            if (s != null && s.rank >= rank) {
                n++;
            }
        }
        return n;
    }

    // ---------------------------------------------------------------------------------------------------- records

    /** Sends every champion record to this character (login, and after each change). */
    public static void sendRecords(Char chr) {
        if (chr == null || chr.getAccount() == null || !isUnlocked(chr)) {
            return;
        }
        ScriptManagerImpl sm = chr.getScriptManager();
        Account acc = chr.getAccount();
        sm.setQRValue(PATH_QUEST, "start", "1");
        sm.setQRValue(PATH_QUEST, "num", String.valueOf(getOpenSlots(acc)));
        Slot[] slots = getSlots(acc);
        for (int i = 0; i < MAX_SLOTS; i++) {
            Slot s = slots[i];
            int q = SLOT_QUEST_BASE + i;
            if (s == null) {
                sm.setQRValue(q, "cid", "0");
                sm.setQRValue(q, "rank", "-1");
            } else {
                Char c = acc.getCharById(s.charId);
                sm.setQRValue(q, "cid", String.valueOf(s.charId));
                sm.setQRValue(q, "rank", String.valueOf(s.rank));
                sm.setQRValue(q, "time", net.swordie.ms.util.FileTime.clientNow().withNano(0).format(TIME)); // the client's clock
                sm.setQRValue(q, "power", String.valueOf(c == null ? 0 : c.getCombatPower()));
            }
        }
        sm.setQRValue(TRIAL_RECORD, "isChampion", getSlotOf(chr) >= 0 ? "1" : "0");
    }

    // --------------------------------------------------------------------------------------------------- requests

    /** UNION_CHAMPION_REQUEST 1127 */
    public static void handleRequest(Char chr, InPacket in) {
        int type = in.decodeInt();
        int slot = in.decodeByte();
        Account acc = chr.getAccount();
        if (acc == null || !isUnlocked(chr) || slot < 0 || slot >= getOpenSlots(acc)) {
            chr.chatMessage("You can't do that with the Legion Champions right now.");
            chr.dispose();
            return;
        }
        Slot[] slots = getSlots(acc);
        switch (type) {
            case 0 -> {
                in.decodeLong();
                int n = in.decodeByte();
                for (int i = 0; i < n && in.getUnreadAmount() > 0; i++) {
                    if (in.decodeByte() != 0) {
                        in.decodeLong();
                    }
                }
                int charId = in.getUnreadAmount() >= 4 ? in.decodeInt() : 0;
                register(chr, slots, slot, charId);
            }
            case 1 -> unregister(chr, slots, slot);
            case 2 -> rankUp(chr, slots, slot);
            default -> log.debug("UNION_CHAMPION_REQUEST type " + type);
        }
        sendRecords(chr);
        chr.dispose();
    }

    private static void register(Char chr, Slot[] slots, int slot, int charId) {
        Account acc = chr.getAccount();
        Char target = acc.getCharById(charId);
        if (target == null) {
            result(chr, 0, RES_COMMON);
            return;
        }
        for (Slot s : slots) {
            if (s != null && s.charId == charId) {
                result(chr, 0, REG_DUPLICATE);
                return;
            }
        }
        if (slots[slot] != null) {
            result(chr, 0, RES_COMMON);
            return;
        }
        Slot s = new Slot();
        s.charId = charId;
        s.rank = 0;
        s.regTime = System.currentTimeMillis();
        slots[slot] = s;
        saveSlot(acc, slot, s);
        result(chr, 0, 0);
        onChampionsChanged(chr);
    }

    private static void unregister(Char chr, Slot[] slots, int slot) {
        Slot s = slots[slot];
        if (s == null || s.charId != chr.getId()) {
            result(chr, 1, s == null ? RES_COMMON : NOT_THIS_CHAR);
            return;
        }
        slots[slot] = null;
        saveSlot(chr.getAccount(), slot, null);
        result(chr, 1, 0);
        onChampionsChanged(chr);
    }

    private static void rankUp(Char chr, Slot[] slots, int slot) {
        Slot s = slots[slot];
        if (s == null || s.charId != chr.getId()) {
            result(chr, 2, s == null ? RES_COMMON : NOT_THIS_CHAR);
            return;
        }
        if (s.rank >= MAX_RANK) {
            result(chr, 2, RANK_MAX);
            return;
        }
        if (!chr.getQuestManager().hasQuestCompleted(TRIAL_QUESTS[s.rank])) {
            chr.chatMessage("Complete the Champion Trial for the next rank first.");
            result(chr, 2, RES_COMMON);
            return;
        }
        s.rank++;
        chr.write(net.swordie.ms.connection.packet.ContentPacket.championResult(2, 0, s.rank - 1, s.rank));
        saveSlot(chr.getAccount(), slot, s);
        chr.getScriptManager().setQRValue(TRIAL_RECORD, "clear", "0");
        chr.chatMessage(String.format("Your Champion rank rose to %s!", rankName(s.rank)));
        onChampionsChanged(chr);
    }

    public static String rankName(int rank) {
        return switch (rank) {
            case 0 -> "C";
            case 1 -> "B";
            case 2 -> "A";
            case 3 -> "S";
            case 4 -> "SS";
            case 5 -> "SSS";
            default -> "-";
        };
    }

    /** After a register, release or rank up: insignia stats of the account's online champions. */
    private static void onChampionsChanged(Char chr) {
        ChampionInsignia.refreshAccount(chr.getAccount());
    }

    // ------------------------------------------------------------------------------------------------------ hooks

    public static void onLogin(Char chr) {
        if (chr == null || chr.getAccount() == null) {
            return;
        }
        try {
            sendRecords(chr);
            ChampionInsignia.refresh(chr);
            if (isUnlocked(chr)) {
                ChampionRaid.sendCoins(chr);
            }
        } catch (Exception e) {
            log.error("Could not send the Legion Champions of " + chr.getName(), e);
        }
    }
}
