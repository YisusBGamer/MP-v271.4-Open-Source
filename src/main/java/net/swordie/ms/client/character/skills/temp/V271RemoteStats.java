package net.swordie.ms.client.character.skills.temp;

import net.swordie.ms.ServerConstants;
import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.skills.Option;
import net.swordie.ms.client.character.skills.atom.forceatom.StopForceAtom;
import net.swordie.ms.connection.OutPacket;
import net.swordie.ms.enums.BaseStat;
import net.swordie.ms.enums.TSIndex;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

/**
 * Another player's buffs for the v271 client: the block inside USER_ENTER_FIELD and REMOTE_SET_TEMPORARY_STAT, and the
 * REMOTE_RESET_TEMPORARY_STAT mask.
 * Set block: 160-byte mask (as the local one), per-stat values, 1 1 1 4, the stop-force-atom record, a counted list of
 * grouped stats, the counted Indie section, more per-stat values, the speed byte, more per-stat values. Only the
 * stats marked sendable in resources/v271-cts-remote.tsv go into the mask: every value they have comes before the
 * fixed 1 1 1 4, so the later per-stat parts read nothing. Indie stats are never sent (count 0); grouped stats (mounts, dash, ...)
 * go in the mask at 200 + group id and in the counted group list, as in the local packet.
 * Reset: the mask, then one byte when a mount group (202/206) is in it; Indie indices, 567 and 816 are never sent.
 */
public final class V271RemoteStats {

    private static final Logger log = LogManager.getLogger(V271RemoteStats.class);
    private static final String FILE = ServerConstants.RESOURCES_DIR + "/v271-cts-remote.tsv";
    /** The client's floor for a character's speed (%), also its value before any gear. */
    private static final int BASE_SPEED = 100;
    private static final int MAX_SPEED_BYTE = 0xff;

    /** One per-stat value block in client order: its stat and value source (an entry of RemoteSecondaryStat, or -1). */
    private record Block(int index, int[] widths, CharacterTemporaryStat cts, int entry) {
    }

    private static final List<Block> BLOCKS = new ArrayList<>();
    private static final Map<CharacterTemporaryStat, Integer> INDEX = new EnumMap<>(CharacterTemporaryStat.class);

    static {
        load();
    }

    private V271RemoteStats() {
    }

    private static void load() {
        try {
            for (String line : Files.readAllLines(Path.of(FILE), StandardCharsets.UTF_8)) {
                if (line.isBlank() || line.startsWith("#")) {
                    continue;
                }
                String[] p = line.split("\t", -1);
                if ("S".equals(p[0])) {
                    CharacterTemporaryStat cts = stat(p[2]);
                    if (cts != null && !cts.isIndie() && !TSIndex.isTwoState(cts)) {
                        INDEX.put(cts, Integer.parseInt(p[1]));
                    }
                } else if ("B".equals(p[0])) {
                    int[] w = Arrays.stream(p[2].split(" ")).mapToInt(Integer::parseInt).toArray();
                    CharacterTemporaryStat cts = "-".equals(p[3]) ? null : stat(p[3]);
                    int entry = p[4].startsWith("E") ? Integer.parseInt(p[4].substring(1)) : -1;
                    if (cts != null && "-".equals(p[4])) {
                        cts = null; // never sent
                    }
                    BLOCKS.add(new Block(Integer.parseInt(p[1]), w, cts, entry));
                }
            }
        } catch (Exception e) {
            log.error("v271 remote buffs: cannot read " + FILE + "; other players' buffs are not sent", e);
            INDEX.clear();
            BLOCKS.clear();
        }
        for (Block b : BLOCKS) {
            if (b.cts != null && b.entry >= 0 && (b.entry >= RemoteSecondaryStat.ENTRIES.length
                    || RemoteSecondaryStat.ENTRIES[b.entry].cts() != b.cts)) {
                log.error("v271 remote buffs: " + FILE + " does not match RemoteSecondaryStat (" + b.cts
                        + "); other players' buffs are not sent");
                INDEX.clear();
                return;
            }
        }
    }

    private static CharacterTemporaryStat stat(String name) {
        try {
            return CharacterTemporaryStat.valueOf(name);
        } catch (IllegalArgumentException e) {
            log.warn("v271 remote buffs: unknown stat " + name);
            return null;
        }
    }

    public static boolean isSent(CharacterTemporaryStat cts) {
        return INDEX.containsKey(cts);
    }

    private static int[] mask(Collection<CharacterTemporaryStat> stats) {
        int[] mask = new int[V271LocalStats.MASK_INTS];
        for (CharacterTemporaryStat cts : stats) {
            Integer i = V271LocalStats.isGroup(cts) ? Integer.valueOf(V271LocalStats.groupIndex(cts)) : INDEX.get(cts);
            if (i != null) {
                mask[i / 32] |= 1 << (i % 32);
            }
        }
        return mask;
    }

    private static boolean hasMount(Collection<CharacterTemporaryStat> stats) {
        return stats.stream().anyMatch(c -> V271LocalStats.isGroup(c)
                && V271LocalStats.isMount(V271LocalStats.groupIndex(c)));
    }

    /** The set block for the given stats (all current stats on enter, the new ones on a set). */
    public static void encode(OutPacket out, TemporaryStatManager tsm, Map<CharacterTemporaryStat, List<Option>> stats) {
        Map<CharacterTemporaryStat, Option> send = new EnumMap<>(CharacterTemporaryStat.class);
        for (Map.Entry<CharacterTemporaryStat, List<Option>> e : stats.entrySet()) {
            List<Option> options = e.getValue();
            if (isSent(e.getKey()) && options != null && !options.isEmpty() && options.get(0) != null) {
                send.put(e.getKey(), options.get(0));
            }
        }
        Map<CharacterTemporaryStat, List<Option>> groups = new EnumMap<>(CharacterTemporaryStat.class);
        for (Map.Entry<CharacterTemporaryStat, List<Option>> e : stats.entrySet()) {
            if (V271LocalStats.isGroup(e.getKey()) && e.getValue() != null && !e.getValue().isEmpty()) {
                groups.put(e.getKey(), e.getValue());
            }
        }
        Set<CharacterTemporaryStat> inMask = new HashSet<>(send.keySet());
        inMask.addAll(groups.keySet());
        for (int m : mask(inMask)) {
            out.encodeInt(m);
        }
        for (Block b : BLOCKS) {
            Option o = b.cts == null ? null : send.get(b.cts);
            if (o == null) {
                continue;
            }
            if (b.entry >= 0) {
                RemoteSecondaryStat.ENTRIES[b.entry].enc().encode(out, o);
            } else {
                for (int k = 0; k < b.widths.length; k++) {
                    int v = k == 0 ? o.nOption : o.rOption;
                    switch (b.widths[k]) {
                        case 1 -> out.encodeByte(v);
                        case 2 -> out.encodeShort(v);
                        default -> out.encodeInt(v);
                    }
                }
            }
        }
        out.encodeByte(tsm.getDefenseAtt());
        out.encodeByte(tsm.getDefenseState());
        out.encodeByte(tsm.getPvpDamage());
        Option ethereal = stats.get(CharacterTemporaryStat.EtherealForm) == null
                || stats.get(CharacterTemporaryStat.EtherealForm).isEmpty() ? null
                : stats.get(CharacterTemporaryStat.EtherealForm).get(0);
        out.encodeInt(ethereal == null ? 0 : ethereal.xOption);
        StopForceAtom sfa = tsm.getStopForceAtom();
        (sfa != null ? sfa : new StopForceAtom()).encode(out);
        V271LocalStats.encodeGroups(out, groups); // grouped stats: 4 n {4 group, payload}
        out.encodeInt(0); // Indie section: 4 n {4 index, options}
        out.encodeByte(speed(tsm.getChr()));
    }

    /** The movement speed (%) the client keeps for another player (it replaces the client's own 100). */
    private static int speed(Char chr) {
        return speedByte(chr == null ? 0 : chr.getTotalStat(BaseStat.speed));
    }

    static int speedByte(int speedBonus) {
        return Math.max(BASE_SPEED, Math.min(MAX_SPEED_BYTE, BASE_SPEED + speedBonus));
    }

    /** The reset mask: the removed stats the client could have got. */
    public static void encodeReset(OutPacket out, TemporaryStatManager tsm) {
        Set<CharacterTemporaryStat> removed = tsm.getRemovedStats().keySet();
        for (int m : mask(removed)) {
            out.encodeInt(m);
        }
        if (hasMount(removed)) {
            out.encodeByte(1); // 0x1407647e0: the mount groups' byte
        }
    }

    /** Whether a reset would carry anything (a removal of stats the client never got is not sent). */
    public static boolean hasRemoved(TemporaryStatManager tsm) {
        return tsm.getRemovedStats().keySet().stream().anyMatch(c -> isSent(c) || V271LocalStats.isGroup(c));
    }
}
