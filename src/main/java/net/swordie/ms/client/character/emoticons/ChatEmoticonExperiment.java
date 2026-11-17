package net.swordie.ms.client.character.emoticons;

import net.swordie.ms.ServerConstants;
import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.quest.Quest;
import net.swordie.ms.connection.OutPacket;
import net.swordie.ms.jwzlib.WzFile;
import net.swordie.ms.jwzlib.WzObject;
import net.swordie.ms.loaders.QuestData;
import net.swordie.ms.util.Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

/**
 * GM-only experiment for the v265 chat emoticon packs (not used for players until the client's reaction is
 * confirmed in game). What the client reads:
 * <ul>
 *   <li>EMOTICON_RESULT is out-op 541 (CWvsContext 0x145518d30): Decode1 sub-type 0..7; sub-type 4 = B2 slot, then a
 *   14-byte record {int emoticon id (group * 10000 + n), FILETIME expiry, short slot}.</li>
 *   <li>CharacterData emoticon block (reader 0x140688ab0): int n; n x (int emoticon id, 14-byte record); then
 *   0x140688890: int m; m x (short, int); short. Characters without the experiment get the 10 zero bytes as before.</li>
 * </ul>
 * The experiment state is kept in quest record {@link #QR} ("g=group;s=slot;all=0|1;on=0|1") on the GM.
 */
public class ChatEmoticonExperiment {
    private static final Logger log = LogManager.getLogger();
    public static final int OP_EMOTICON_RESULT = 541;
    public static final int QR = 500782;
    /** FILETIME the client compares the expiry with (0x1468639e0): 1900-01-01, Nexon's "no date" */
    public static final long NO_EXPIRY = 94354848000000000L;

    private static volatile Map<Integer, List<Integer>> groups;
    /** characters the probe's length test runs the experiment on (never saved) */
    public static final Set<Integer> PROBE_CHARS = java.util.concurrent.ConcurrentHashMap.newKeySet();

    /** UI.wz ChatEmoticon.img/Emoticon: group -> its emoticon ids */
    public static Map<Integer, List<Integer>> groups() {
        if (groups == null) {
            synchronized (ChatEmoticonExperiment.class) {
                if (groups == null) {
                    Map<Integer, List<Integer>> map = new TreeMap<>();
                    try {
                        WzObject<?, ?> root = new WzFile(ServerConstants.WZ_DIR + "/UI.wz")
                                .getChildByPath("ChatEmoticon.img/Emoticon");
                        if (root != null) {
                            for (WzObject<?, ?> g : root) {
                                if (!Util.isNumber(g.getName())) {
                                    continue;
                                }
                                List<Integer> ids = new ArrayList<>();
                                for (WzObject<?, ?> e : g) {
                                    if (Util.isNumber(e.getName())) {
                                        ids.add(Integer.parseInt(e.getName()));
                                    }
                                }
                                Collections.sort(ids);
                                map.put(Integer.parseInt(g.getName()), ids);
                            }
                        }
                    } catch (RuntimeException e) {
                        log.error("Could not read UI.wz ChatEmoticon.img", e);
                    }
                    groups = map;
                }
            }
        }
        return groups;
    }

    /** The emoticon ids an experiment sends: the whole group, or only its first emoticon */
    public static List<Integer> ids(int group, boolean all) {
        List<Integer> ids = groups().getOrDefault(group, List.of());
        return all || ids.isEmpty() ? ids : ids.subList(0, 1);
    }

    private static void encodeRecord(OutPacket out, int id, int slot) {
        out.encodeInt(id);
        out.encodeLong(NO_EXPIRY);
        out.encodeShort(slot);
    }

    /** 541 sub-type 4 for one emoticon */
    public static OutPacket addRecord(int id, int slot) {
        OutPacket out = new OutPacket(OP_EMOTICON_RESULT);
        out.encodeByte(4);
        out.encodeShort(slot);
        encodeRecord(out, id, slot);
        return out;
    }

    /** The CharacterData block for these ids (empty list = the 10 zero bytes every character gets) */
    public static void encodeLoginBlock(OutPacket out, List<Integer> ids, int slot) {
        out.encodeInt(ids.size());
        for (int id : ids) {
            out.encodeInt(id);
            encodeRecord(out, id, slot);
        }
        out.encodeInt(0); // no (short, int) entries
        out.encodeShort(0);
    }

    /** The block CharEncoder writes: the experiment's records for a GM that turned it on, else zeros */
    public static void encodeLoginBlock(OutPacket out, Char chr) {
        Map<String, String> state;
        try {
            state = state(chr);
        } catch (RuntimeException e) {
            log.error("Chat emoticon experiment state of " + chr.getId(), e);
            state = null;
        }
        if (state == null || !"1".equals(state.get("on"))) {
            encodeLoginBlock(out, List.of(), 0);
            return;
        }
        int group = parse(state.get("g")), slot = parse(state.get("s"));
        encodeLoginBlock(out, ids(group, "1".equals(state.get("all"))), slot);
    }

    private static int parse(String s) {
        try {
            return s == null ? 0 : Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private static Map<String, String> state(Char chr) {
        if (!(isAdmin(chr) || chr != null && PROBE_CHARS.contains(chr.getId())) || chr.getQuestManager() == null) {
            return null;
        }
        Quest q = chr.getQuestManager().getQuestById(QR);
        if (q == null || q.getQRValue() == null || q.getQRValue().isEmpty()) {
            return null;
        }
        Map<String, String> map = new HashMap<>();
        for (String kv : q.getQRValue().split(";")) {
            String[] p = kv.split("=", 2);
            if (p.length == 2) {
                map.put(p[0], p[1]);
            }
        }
        return map;
    }

    public static boolean isAdmin(Char chr) {
        return chr != null && chr.getUser() != null
                && chr.getUser().getAccountType() == net.swordie.ms.enums.AccountType.Admin;
    }

    /** Turns the login-block experiment on (for the next login) or off. */
    public static void setLoginExperiment(Char chr, int group, int slot, boolean all, boolean on) {
        var qm = chr.getQuestManager();
        Quest q = qm.getQuestById(QR);
        if (q == null) {
            q = QuestData.createQuestFromId(QR);
            qm.addQuest(q);
        }
        q.setQrValue(String.format("g=%d;s=%d;all=%d;on=%d", group, slot, all ? 1 : 0, on ? 1 : 0));
    }
}
