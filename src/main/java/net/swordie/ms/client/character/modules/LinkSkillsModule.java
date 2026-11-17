package net.swordie.ms.client.character.modules;

import java.util.function.Supplier;
import net.swordie.ms.loaders.DataTable;
import net.swordie.ms.client.Account;
import net.swordie.ms.client.LinkSkill;
import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.skills.Skill;
import net.swordie.ms.connection.OutPacket;
import net.swordie.ms.connection.db.DatabaseManager;
import net.swordie.ms.connection.packet.WvsContext;
import net.swordie.ms.constants.JobConstants;
import net.swordie.ms.constants.SkillConstants;
import net.swordie.ms.enums.LinkSkillResult;
import net.swordie.ms.loaders.SkillData;
import net.swordie.ms.util.Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * v265 link skills. Every character on the account (same world) lends its class link skill, at the level its own
 * character level gives, to the account's other characters. Each character keeps three presets of up to 12 links
 * and applies one of them; only the applied preset's links are granted.
 * <p>
 * Client model (CharacterData::Decode 0x140680fe0, CWvsContext::OnLinkedSkillInfo 0x1455302d0):
 * <ul>
 *     <li>own link: (char id, origin skill id, level); level 0 counts as 1 (0x140b06cf0), Zero excepted</li>
 *     <li>the account's links: map char id -> (char id, origin skill id, level)</li>
 *     <li>link levels: (int link id, short level). A link id is the 8000xxxx skill, or the stack skill for the
 *     Explorer / Cygnus / Resistance links, whose levels add up (0x140b06220)</li>
 *     <li>three presets of link ids; the applied one is the third byte after the inventory sizes</li>
 * </ul>
 */
public class LinkSkillsModule {

    private static final Logger log = LogManager.getLogger(LinkSkillsModule.class);

    public static final int PRESETS = 3;
    public static final int MAX_SLOTS = 12; // client 0x141e06c70

    /** linked skill -> the lending class's origin skill (client 0x140b07b20), the reverse, and every id managed here */
    private record Links(Map<Integer, Integer> originOf, Map<Integer, Integer> linkOf, Set<Integer> managed) {
    }

    /** resources/data/link-skill-pairs.tsv; HashMaps/HashSet keep the old iteration order */
    private static final Supplier<Links> LINKS = DataTable.cached("link-skill-pairs", t -> {
        Map<Integer, Integer> originOf = new HashMap<>();
        Map<Integer, Integer> linkOf = new HashMap<>();
        Set<Integer> managed = new HashSet<>();
        for (DataTable.Row r : t.rows()) {
            int origin = r.i("origin");
            if (!r.has("link")) {
                managed.add(origin);
                continue;
            }
            int link = r.i("link");
            originOf.put(link, origin);
            linkOf.put(origin, link);
            managed.add(link);
            managed.add(origin);
            int stack = SkillConstants.getStackLinkSkill(link);
            if (stack != 0) {
                managed.add(stack);
            }
        }
        return new Links(Collections.unmodifiableMap(originOf), Collections.unmodifiableMap(linkOf),
                Collections.unmodifiableSet(managed));
    });

    /** one row of link-skill-origins.tsv: job ranges (inclusive) and the sub job it also needs (-1: any) */
    private record OriginRule(int origin, int[][] jobs, int subJob) {
        boolean matches(int job, int sub) {
            if (subJob >= 0 && sub != subJob) {
                return false;
            }
            for (int[] range : jobs) {
                if (job >= range[0] && job <= range[1]) {
                    return true;
                }
            }
            return false;
        }
    }

    private static final Supplier<List<OriginRule>> ORIGIN_RULES = DataTable.cached("link-skill-origins", t ->
            t.rows().stream().map(r -> new OriginRule(r.i("origin"), Arrays.stream(r.str("jobs").trim().split(","))
                    .map(String::trim).map(LinkSkillsModule::jobRange).toArray(int[][]::new), r.i("subJob", -1)))
                    .toList());

    private static int[] jobRange(String cell) {
        int dash = cell.indexOf('-', 1);
        return dash < 0 ? new int[]{Integer.parseInt(cell), Integer.parseInt(cell)}
                : new int[]{Integer.parseInt(cell.substring(0, dash)), Integer.parseInt(cell.substring(dash + 1))};
    }

    private static class State {
        volatile int applied;
        final List<List<Integer>> presets = new ArrayList<>();
        int ownOrigin = -1, ownLevel = -1;

        State() {
            for (int i = 0; i < PRESETS; i++) {
                presets.add(new ArrayList<>());
            }
        }
    }

    private static final Map<Integer, State> states = new ConcurrentHashMap<>();

    /** A link another character of the account lends: who, which linked skill, at what level. */
    public record Source(int charId, int linkId, int level) {
    }

    // ---- class tables ---------------------------------------------------------------------------------------------

    /** The origin link skill of a job (client 0x140b07410), 0 if the class has none. */
    public static int originByJob(int job, int subJob) {
        for (OriginRule r : ORIGIN_RULES.get()) {
            if (r.matches(job, subJob)) {
                return r.origin();
            }
        }
        return 0;
    }

    public static int ownOrigin(Char chr) {
        return originByJob(chr.getJob(), chr.getSubJob());
    }

    /** The 8000xxxx link skill chr's class lends, 0 if none. */
    public static int ownLink(Char chr) {
        return LINKS.get().linkOf().getOrDefault(ownOrigin(chr), 0);
    }

    /** chr's own link as the window lists it (its stack for the stacking classes); never applied from a preset. */
    public static int ownDisplay(Char chr) {
        int own = ownLink(chr);
        return own == 0 ? 0 : displayOf(own);
    }

    public static int originOf(int linkId) {
        return LINKS.get().originOf().getOrDefault(linkId, 0);
    }

    /** What a preset holds for a link: the stack skill for the stacking links, else the link itself. */
    public static int displayOf(int linkId) {
        int stack = SkillConstants.getStackLinkSkill(linkId);
        return stack != 0 ? stack : linkId;
    }

    private static boolean isDisplayId(int id) {
        return SkillConstants.getIndividualsByStackLink(id).length > 0
                || (LINKS.get().originOf().containsKey(id) && SkillConstants.getStackLinkSkill(id) == 0);
    }

    private static int maxLevel(int skillId, int fallback) {
        var si = SkillData.getSkillInfoById(skillId);
        return si == null || si.getMaxLevel() <= 0 ? fallback : si.getMaxLevel();
    }

    /** The level a character of this job and level lends (70/120/210; Zero has its own steps). */
    public static int linkLevel(int job, int level, int skillId) {
        int lv = SkillConstants.getLinkSkillLevelByCharLevel((short) job, level);
        return Math.max(0, Math.min(lv, maxLevel(skillId, JobConstants.isZero(job) ? 5 : 3)));
    }

    /** chr's own link level as the client gets it (0 below level 70). */
    public static int ownRawLevel(Char chr) {
        int origin = ownOrigin(chr);
        return origin == 0 ? 0 : linkLevel(chr.getJob(), chr.getLevel(), origin);
    }

    // ---- account data ---------------------------------------------------------------------------------------------

    /** The links the account's other characters lend to chr (level > 0 only). */
    public static List<Source> sources(Char chr) {
        List<Source> res = new ArrayList<>();
        Account acc = chr.getAccount();
        if (acc == null) {
            return res;
        }
        for (Char c : new ArrayList<>(acc.getCharacters())) {
            if (c == null || c.getId() == chr.getId()) {
                continue;
            }
            int link = LINKS.get().linkOf().getOrDefault(originByJob(c.getJob(), c.getSubJob()), 0);
            int lv = link == 0 ? 0 : linkLevel(c.getJob(), c.getLevel(), link);
            if (lv > 0) {
                res.add(new Source(c.getId(), link, lv));
            }
        }
        res.sort(Comparator.comparingInt(Source::charId));
        return res;
    }

    /** Level of each link id the account lends, own levels added the way the client adds them. */
    public static Map<Integer, Integer> levels(Char chr, List<Source> sources) {
        Map<Integer, Integer> best = new HashMap<>(); // linked skill -> highest level
        for (Source s : sources) {
            best.merge(s.linkId(), s.level(), Math::max);
        }
        int own = ownLink(chr);
        int ownLv = ownRawLevel(chr);
        Map<Integer, Integer> res = new TreeMap<>();
        for (Source s : sources) {
            int d = displayOf(s.linkId());
            if (res.containsKey(d)) {
                continue;
            }
            int[] parts = SkillConstants.getIndividualsByStackLink(d);
            int lv = 0;
            if (parts.length > 0) {
                for (int p : parts) {
                    int l = best.getOrDefault(p, 0);
                    if (p == own) {
                        l = Math.max(l, ownLv);
                    }
                    lv += l;
                }
                lv = Math.min(lv, maxLevel(d, parts.length * 3));
            } else {
                lv = best.getOrDefault(d, 0);
                if (d == own) {
                    lv = Math.max(lv, ownLv);
                }
            }
            if (lv > 0) {
                res.put(d, lv);
            }
        }
        return res;
    }

    // ---- presets --------------------------------------------------------------------------------------------------

    private static State state(Char chr) {
        return states.computeIfAbsent(chr.getId(), id -> {
            State s = new State();
            boolean found = false;
            try {
                for (Map<String, Object> row : DatabaseManager.queryRows(
                        "SELECT applied AS a, preset1 AS p1, preset2 AS p2, preset3 AS p3 FROM char_link_presets "
                                + "WHERE charid = ?", id)) {
                    found = true;
                    s.applied = Math.max(0, Math.min(PRESETS - 1, ((Number) row.get("a")).intValue()));
                    for (int i = 0; i < PRESETS; i++) {
                        Object v = row.get("p" + (i + 1));
                        if (v != null) {
                            s.presets.set(i, clean(chr, parse(v.toString())));
                        }
                    }
                }
            } catch (Exception e) {
                log.error("Couldn't load the link skill presets of character " + id, e);
            }
            Account acc = chr.getAccount();
            if (!found && acc != null) {
                // first time: preset 1 gets the links the older account-wide system had switched on
                List<Integer> old = new ArrayList<>();
                for (LinkSkill ls : acc.getLinkSkills()) {
                    if (ls.getUsingID() == 0 && ls.getOriginID() != chr.getId()) {
                        old.add(displayOf(ls.getLinkSkillID()));
                    }
                }
                s.presets.set(0, clean(chr, old));
            }
            return s;
        });
    }

    private static List<Integer> parse(String s) {
        List<Integer> res = new ArrayList<>();
        for (String part : s.split(",")) {
            if (!part.isBlank()) {
                try {
                    res.add(Integer.parseInt(part.trim()));
                } catch (NumberFormatException ignored) {
                }
            }
        }
        return res;
    }

    private static String join(List<Integer> ids) {
        StringBuilder sb = new StringBuilder();
        for (int id : ids) {
            if (sb.length() > 0) {
                sb.append(',');
            }
            sb.append(id);
        }
        return sb.toString();
    }

    /** Known link ids only, no duplicates, not chr's own link, at most 12. */
    private static List<Integer> clean(Char chr, List<Integer> ids) {
        int own = ownDisplay(chr);
        List<Integer> res = new ArrayList<>();
        for (int id : ids) {
            if (isDisplayId(id) && id != own && !res.contains(id) && res.size() < MAX_SLOTS) {
                res.add(id);
            }
        }
        return res;
    }

    private static void save(Char chr, State s) {
        DatabaseManager.executeQuery("INSERT INTO char_link_presets (charid, applied, preset1, preset2, preset3) "
                        + "VALUES (?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE applied = VALUES(applied), "
                        + "preset1 = VALUES(preset1), preset2 = VALUES(preset2), preset3 = VALUES(preset3)",
                chr.getId(), s.applied, join(s.presets.get(0)), join(s.presets.get(1)), join(s.presets.get(2)));
    }

    public static int appliedPreset(Char chr) {
        return chr == null || chr.getId() == 0 ? 0 : state(chr).applied;
    }

    public static List<Integer> appliedLinks(Char chr) {
        State s = state(chr);
        return new ArrayList<>(s.presets.get(s.applied));
    }

    /** Links the account lends that chr's applied preset doesn't use (for tips). */
    public static int unappliedCount(Char chr) {
        var applied = appliedLinks(chr);
        int own = ownDisplay(chr);
        int n = 0;
        for (int d : levels(chr, sources(chr)).keySet()) {
            if (d != own && !applied.contains(d)) {
                n++;
            }
        }
        return n;
    }

    // ---- granting -------------------------------------------------------------------------------------------------

    /** Skill id -> level chr should have from its own link and its applied preset. */
    private static Map<Integer, Integer> wanted(Char chr) {
        Map<Integer, Integer> res = new HashMap<>();
        var sources = sources(chr);
        var levels = levels(chr, sources);
        int own = ownDisplay(chr);
        // the lending characters also hand over their part of a stacking link (v265 type 2 adds it next to the
        // stack): the effects sum chr.getSkillLevel over the parts and their origins
        // (SkillConstants.getOriginAndLinkByStackingLink), so the stack skill alone reads as level 0
        Map<Integer, Integer> parts = new HashMap<>();
        for (Source s : sources) {
            parts.merge(s.linkId(), s.level(), Math::max);
        }
        for (int d : appliedLinks(chr)) {
            int lv = levels.getOrDefault(d, 0);
            if (d == own || lv <= 0) {
                continue;
            }
            res.put(d, lv);
            for (int p : SkillConstants.getIndividualsByStackLink(d)) {
                int pLv = Math.min(parts.getOrDefault(p, 0), maxLevel(p, 3));
                if (pLv > 0) {
                    res.put(p, pLv);
                }
            }
        }
        int origin = ownOrigin(chr);
        if (origin > 0) {
            // the class's own copy, from creation: the client shows level 0 as 1 (Zero excepted) (0x140b06cf0)
            int lv = ownRawLevel(chr);
            if (!JobConstants.isZero(chr.getJob())) {
                lv = Math.max(1, lv);
            }
            if (lv > 0) {
                res.put(origin, Math.min(lv, maxLevel(origin, lv)));
            }
        }
        return res;
    }

    /** Brings chr's link skills in line with its own link and applied preset; sends the changes if asked. */
    public static void apply(Char chr, boolean send) {
        if (chr == null || chr.getId() == 0) {
            return;
        }
        var want = wanted(chr);
        List<Skill> changed = new ArrayList<>();
        for (int id : LINKS.get().managed()) {
            int lv = want.getOrDefault(id, 0);
            Skill has = chr.getSkill(id);
            if (lv <= 0) {
                if (has != null) {
                    chr.removeSkill(id);
                    has.setCurrentLevel(-1);
                    has.setMasterLevel(-1);
                    changed.add(has);
                }
            } else if (has == null || has.getCurrentLevel() != lv) {
                Skill skill = SkillData.getSkillDeepCopyById(id);
                if (skill == null) {
                    continue;
                }
                skill.setCurrentLevel(lv);
                skill.setMasterLevel(maxLevel(id, lv));
                chr.addSkill(skill);
                Skill now = chr.getSkill(id);
                changed.add(now != null ? now : skill);
            }
        }
        if (send && !changed.isEmpty()) {
            chr.write(WvsContext.changeSkillRecordResult(changed));
        }
    }

    /** At login, once the character is in the field. */
    public static void initLinkSkills(Account account, Char chr) {
        State s = state(chr);
        s.ownOrigin = ownOrigin(chr);
        s.ownLevel = ownRawLevel(chr);
        syncAccountRow(chr);
        apply(chr, true);
    }

    private static void syncAccountRow(Char chr) {
        int link = ownLink(chr);
        int raw = ownRawLevel(chr);
        Account acc = chr.getAccount();
        if (acc == null || link == 0 || raw <= 0) {
            return;
        }
        LinkSkill ls = getLinkSkillByOriginId(acc, chr.getId());
        if (ls == null) {
            acc.addLinkSkill(new LinkSkill(acc, chr.getId(), -1, link, raw));
        } else if (ls.getLinkSkillID() != link || ls.getLevel() != raw) {
            ls.setLinkSkillID(link);
            ls.setLevel(raw);
        }
    }

    /** After a level up or job change: when chr's own link changed, tells the client and re-grants. */
    public static void onLevelOrJobChange(Char chr) {
        if (chr == null || chr.getId() == 0) {
            return;
        }
        State s = state(chr);
        int origin = ownOrigin(chr);
        int raw = ownRawLevel(chr);
        if (origin == s.ownOrigin && raw == s.ownLevel) {
            return;
        }
        s.ownOrigin = origin;
        s.ownLevel = raw;
        syncAccountRow(chr);
        int link = ownDisplay(chr);
        int removed = 0;
        for (List<Integer> p : s.presets) {
            if (link != 0 && p.remove(Integer.valueOf(link))) {
                removed = link;
            }
        }
        if (removed != 0) {
            save(chr, s);
        }
        chr.write(WvsContext.linkedSkillOwnInfo(chr.getId(), origin, raw, removed));
        apply(chr, true);
    }

    // ---- requests -------------------------------------------------------------------------------------------------

    /** LINK_SKILL_REQUEST: 0 save a preset, 1 apply a preset, 2 add a link to a preset, 3 remove one. */
    public static void handleRequest(Char chr, int type, int preset, List<Integer> ids) {
        if (preset < 0 || preset >= PRESETS) {
            chr.write(WvsContext.linkSkillResult(0, LinkSkillResult.UnknownError));
            return;
        }
        State s = state(chr);
        switch (type) {
            case 0 -> {
                List<Integer> list = clean(chr, ids);
                s.presets.set(preset, list);
                save(chr, s);
                chr.write(WvsContext.linkedSkillPreset(0, preset, list));
                if (preset == s.applied) {
                    apply(chr, true);
                }
            }
            case 1 -> {
                if (preset != s.applied) {
                    s.applied = preset;
                    save(chr, s);
                    apply(chr, true);
                }
                chr.write(WvsContext.linkedSkillPreset(1, preset, null));
            }
            case 2 -> {
                int id = ids.isEmpty() ? 0 : ids.get(0);
                List<Integer> list = s.presets.get(preset);
                if (!isDisplayId(id) || id == ownDisplay(chr)) {
                    chr.write(WvsContext.linkSkillResult(id, LinkSkillResult.AlreadyTransferred));
                } else if (list.contains(id)) {
                    chr.write(WvsContext.linkSkillResult(id, LinkSkillResult.UnknownError));
                } else if (list.size() >= MAX_SLOTS) {
                    chr.write(WvsContext.linkSkillResult(id, LinkSkillResult.SkillLimitHit));
                } else {
                    list.add(id);
                    save(chr, s);
                    chr.write(WvsContext.linkedSkillPreset(2, preset, List.of(id)));
                    if (preset == s.applied) {
                        apply(chr, true);
                    }
                }
            }
            case 3 -> {
                int id = ids.isEmpty() ? 0 : ids.get(0);
                if (s.presets.get(preset).remove(Integer.valueOf(id))) {
                    save(chr, s);
                    if (preset == s.applied) {
                        apply(chr, true);
                    }
                }
                chr.write(WvsContext.linkedSkillPreset(3, preset, List.of(id)));
            }
            default -> chr.write(WvsContext.linkSkillResult(0, LinkSkillResult.InvalidRequest));
        }
    }

    // ---- CharacterData --------------------------------------------------------------------------------------------

    /** The link block after the hyper stats (client 0x140680fe0, selectors 0x147cd3c90..0x147cd3de0). */
    public static void encode(Char chr, OutPacket outPacket) {
        List<Source> sources = sources(chr);
        outPacket.encodeInt(chr.getId());
        outPacket.encodeInt(ownOrigin(chr));
        outPacket.encodeInt(ownRawLevel(chr));

        outPacket.encodeByte(1); // replaces the account's links
        outPacket.encodeShort(sources.size());
        for (Source s : sources) {
            outPacket.encodeInt(s.charId());
            outPacket.encodeInt(originOf(s.linkId()));
            outPacket.encodeInt(s.level());
        }

        var levels = levels(chr, sources);
        outPacket.encodeShort(levels.size());
        levels.forEach((id, lv) -> {
            outPacket.encodeInt(id);
            outPacket.encodeShort(lv);
        });

        State st = state(chr);
        outPacket.encodeByte(1); // every preset follows
        for (int i = 0; i < PRESETS; i++) {
            List<Integer> p = st.presets.get(i);
            outPacket.encodeInt(p.size());
            for (int id : p) {
                outPacket.encodeInt(id);
            }
        }
    }

    // ---- account rows (older callers) -----------------------------------------------------------------------------

    public static LinkSkill getActivatedLinkSkillByLinkSkillId(Account account, int linkSkillID) {
        return Util.findWithPred(account.getLinkSkills(), s -> s.getUsingID() == 0 && s.getLinkSkillID() == linkSkillID);
    }

    public static LinkSkill getLinkSkillByLinkSkillId(Account account, int linkSkillID) {
        return Util.findWithPred(account.getLinkSkills(), s -> s.getLinkSkillID() == linkSkillID);
    }

    public static LinkSkill getLinkSkillByOriginId(Account account, int originId) {
        return Util.findWithPred(account.getLinkSkills(), s -> s.getOriginID() == originId);
    }
}
