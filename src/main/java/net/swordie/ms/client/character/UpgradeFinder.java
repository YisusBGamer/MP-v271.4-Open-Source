package net.swordie.ms.client.character;

import net.swordie.ms.client.character.items.BodyPart;
import net.swordie.ms.client.character.items.Equip;
import net.swordie.ms.client.character.items.EquipDropFilter;
import net.swordie.ms.client.character.items.Item;
import net.swordie.ms.constants.ItemConstants;
import net.swordie.ms.constants.JobConstants;
import net.swordie.ms.enums.BaseStat;
import net.swordie.ms.enums.InvType;
import net.swordie.ms.handlers.item.InventoryHandler;
import net.swordie.ms.loaders.EtcData;
import net.swordie.ms.loaders.ItemData;
import net.swordie.ms.loaders.StringData;
import net.swordie.ms.loaders.containerclasses.SetItemInfo;

import java.util.*;

/**
 * @upgrades: finds the equips in a character's Equip inventory that beat what it wears, without hovering over every
 * item. Each equip gets a score for the character's class, compared with the equip in the slot it would go in.
 * <p>
 * The score is in "main stat points": 1 per point of the class's main stat, 4 per point of ATT (Magic ATT for mages),
 * and the percent lines converted with the character's own totals (1% ATT is worth 1% of its ATT, 1% main stat 1% of
 * its main stat), plus damage %, boss damage %, ignore defense %, critical damage % and a little for the secondary
 * stat. Every equip is scored from a fresh copy (base stats only, no potential, flames or past scrolls) as fully
 * enhanced (its free upgrade slots filled with 100% Spell Traces and Star Forced to its maximum, what @enhance does),
 * so gear is ranked by what it becomes. It's a rule of thumb, not exact damage math, but it ranks gear the way
 * players do.
 */
public class UpgradeFinder {

    /** Gear slots compared (no cash, pet, mount or other non-combat special slots). */
    private static final int[] GEAR_SLOTS = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 15, 16, 17, 49, 50, 51, 52, 56,
            53, 54, 61, 65};
    private static final double MIN_GAIN = 0.5;

    private static final double ATT_WEIGHT = 4;
    private static final double SECONDARY_WEIGHT = 0.1;
    private static final double DAMAGE_WEIGHT = 6;
    private static final double IED_WEIGHT = 4;
    private static final double CRIT_DAMAGE_WEIGHT = 15;
    private static final double CRIT_RATE_WEIGHT = 1; // hardcode-ok
    /** A small tie-breaker on top of the set's actual stats, so a real progression piece is not treated as generic. */
    private static final double SET_TIER_WEIGHT = 20;

    private static final Set<Integer> ROOT_ABYSS_SETS = Set.of(247, 248, 249, 250, 251);
    private static final Set<Integer> ABSOLAB_SETS = Set.of(504, 505, 506, 507, 508);
    private static final Set<Integer> ARCANE_UMBRA_SETS = Set.of(617, 618, 619, 620, 621);

    // ------------------------------------------------------------------------------------------------- results

    public static class Entry {
        private final Equip item;
        private final int bodyPart;
        private final Equip current;
        private final double gain;
        private final String changes;

        /** the pieces of a whole-set suggestion (empty for a single item) */
        private final List<Entry> pieces;
        private final String setName;

        Entry(Equip item, int bodyPart, Equip current, double gain, String changes) {
            this(item, bodyPart, current, gain, changes, Collections.emptyList(), null);
        }

        private Entry(Equip item, int bodyPart, Equip current, double gain, String changes, List<Entry> pieces,
                      String setName) {
            this.item = item;
            this.bodyPart = bodyPart;
            this.current = current;
            this.gain = gain;
            this.changes = changes;
            this.pieces = pieces;
            this.setName = setName;
        }

        /** a group of set pieces to equip together (for the set bonus) instead of one item */
        public boolean isSetGroup() {
            return !pieces.isEmpty();
        }

        public List<Entry> getPieces() {
            return pieces;
        }

        /** e.g. "AbsoLab Bandit" (null for a single item) */
        public String getSetName() {
            return setName;
        }

        public Equip getItem() {
            return item;
        }

        public int getItemId() {
            return item.getItemId();
        }

        /** the item's slot in the Equip inventory */
        public int getSlot() {
            return item.getBagIndex();
        }

        public int getBodyPart() {
            return bodyPart;
        }

        public Equip getCurrent() {
            return current;
        }

        public int getCurrentItemId() {
            return current == null ? 0 : current.getItemId();
        }

        /** score difference, rounded */
        public int getGain() {
            return (int) Math.round(gain);
        }

        /** e.g. "+500 ATT, -10 STR, +9% Boss Damage" */
        public String getChanges() {
            return changes;
        }

        public int getRequiredLevel() {
            int lv = item.getRequiredLevel();
            for (Entry p : pieces) {
                lv = Math.max(lv, p.getRequiredLevel());
            }
            return lv;
        }

        /** the item becomes untradeable once it's equipped */
        public boolean isBindsOnEquip() {
            for (Entry p : pieces) {
                if (p.isBindsOnEquip()) {
                    return true;
                }
            }
            var info = item.getInfo();
            return info != null && info.isEquipTradeBlock() && !item.isEquipTradeBlock();
        }

        public String getSlotName() {
            return slotName(bodyPart);
        }
    }

    // ------------------------------------------------------------------------------------------------- scoring

    /** The stats of one equip that the score uses, for one character. */
    private static final class Stats {
        double main, secondary, mainR, att, attR, dam, boss, ied, critDmg, critRate;
    }

    private static final class Scorer {
        private final BaseStat[] mains;
        private final BaseStat[] mainRs;
        private final String[] mainNames;
        private final BaseStat secondary;
        private final BaseStat att;
        private final BaseStat attR;
        private final double mainWeight;
        private final double mainPct; // score of 1% main stat
        private final double attPct; // score of 1% ATT
        private final double damPct; // score of 1% damage or boss damage
        private final double iedPct; // score of 1% ignore enemy DEF
        private final boolean mage;
        private final Char chr;

        Scorer(Char chr) {
            this.chr = chr;
            int job = chr.getJob();
            mage = JobConstants.isMageEquipJob(job);
            att = mage ? BaseStat.mad : BaseStat.pad;
            attR = mage ? BaseStat.madR : BaseStat.padR;
            if (JobConstants.isXenon(job)) {
                mains = new BaseStat[]{BaseStat.str, BaseStat.dex, BaseStat.luk};
                mainRs = new BaseStat[]{BaseStat.strR, BaseStat.dexR, BaseStat.lukR};
                mainNames = new String[]{"STR", "DEX", "LUK"};
                secondary = null;
                mainWeight = 1;
            } else if (JobConstants.isDemonAvenger(job)) {
                mains = new BaseStat[]{BaseStat.mhp};
                mainRs = new BaseStat[]{BaseStat.mhpR};
                mainNames = new String[]{"Max HP"};
                secondary = BaseStat.str;
                mainWeight = 1 / 17.5D; // about 17.5 HP is worth 1 point of a main stat for Demon Avengers
            } else {
                BaseStat main = JobConstants.getMainStatByJob(job);
                if (main == null) {
                    main = BaseStat.str; // beginners
                }
                mains = new BaseStat[]{main};
                mainRs = new BaseStat[]{percentOf(main)};
                mainNames = new String[]{statName(main)};
                secondary = secondaryOf(job, main);
                mainWeight = 1;
            }
            double totalMain = 0;
            for (BaseStat m : mains) {
                totalMain += chr.getTotalStat(m);
            }
            mainPct = Math.max(10, totalMain * mainWeight / 100D);
            attPct = Math.max(12, chr.getTotalStat(att) * ATT_WEIGHT / 100D);
            // percentages grow with the character like ATT%: 1% damage is 1% ATT shrunk by the damage already there
            double damTotal = chr.getTotalStat(BaseStat.damR) + chr.getTotalStat(BaseStat.bd);
            damPct = Math.max(DAMAGE_WEIGHT, attPct * 100D / (100D + Math.max(0, damTotal)));
            iedPct = Math.max(IED_WEIGHT, attPct / 2D);
        }

        /** stats(e) per item for this scorer's lifetime (one window or one equip run): the copies are the slow part */
        private final Map<Equip, Stats> statsCache = new IdentityHashMap<>();

        Stats stats(Equip e) {
            if (e == null) {
                return new Stats();
            }
            Stats cached = statsCache.get(e);
            if (cached == null) {
                cached = fullStats(e);
                statsCache.put(e, cached);
            }
            return cached;
        }

        private Stats fullStats(Equip e) {
            // compared as fully enhanced (free slots scrolled, max stars), like @enhance would make them: an item with
            // more slots or stars to go isn't ranked below a finished one that's weaker in the end. Each item is a fresh copy
            // first (its own base stats: no potential, flames or past scrolls), so the comparison is like for like.
            Equip fresh = e.isCash() ? null : net.swordie.ms.loaders.ItemData.getEquipDeepCopy(e.getItemId(), false);
            if (fresh != null) {
                fresh.setOption(0, 0, false);
                fresh.setOption(1, 0, false);
                fresh.setOption(2, 0, false);
                fresh.setOption(0, 0, true);
                fresh.setOption(1, 0, true);
                fresh.setOption(2, 0, true);
                e = fresh;
            }
            return read(AutoEnhanceModule.maxedCopy(chr, e));
        }

        /** The stats the score uses, read off the item as it is (its stars, scrolls, potentials and flames). */
        Stats read(Equip e) {
            Stats s = new Stats();
            double allStat = e.getBaseStat(BaseStat.allStat);
            for (BaseStat m : mains) {
                s.main += e.getBaseStat(m) + (m == BaseStat.mhp ? 0 : allStat);
            }
            double allStatR = e.getBaseStat(BaseStat.allStatR);
            for (BaseStat m : mainRs) {
                s.mainR += e.getBaseStat(m) + (m == BaseStat.mhpR ? 0 : allStatR);
            }
            s.mainR /= mainRs.length;
            if (secondary != null) {
                s.secondary = e.getBaseStat(secondary) + allStat;
            }
            s.att = e.getBaseStat(att);
            s.attR = e.getBaseStat(attR);
            s.dam = e.getBaseStat(BaseStat.damR);
            s.boss = e.getBaseStat(BaseStat.bd);
            s.ied = e.getBaseStat(BaseStat.ied);
            for (int v : e.getNonAddBaseStat(BaseStat.ied)) {
                s.ied += v;
            }
            s.critDmg = e.getBaseStat(BaseStat.crDmg);
            s.critRate = e.getBaseStat(BaseStat.cr);
            return s;
        }

        double score(Stats s) {
            return s.main * mainWeight + s.secondary * SECONDARY_WEIGHT + s.mainR * mainPct + s.att * ATT_WEIGHT
                    + s.attR * attPct + s.dam * damPct + s.boss * damPct + s.ied * iedPct
                    + s.critDmg * CRIT_DAMAGE_WEIGHT + s.critRate * CRIT_RATE_WEIGHT;
        }

        double score(Equip e) {
            return e == null ? 0 : score(stats(e));
        }

        /** Score of the set effects active on a complete equipment layout. */
        double setScore(Map<Integer, Integer> counts) {
            Stats total = new Stats();
            for (var entry : counts.entrySet()) {
                SetItemInfo set = EtcData.getSetItemInfoById(entry.getKey());
                if (set == null) {
                    continue;
                }
                for (int pieces = 1; pieces <= entry.getValue(); pieces++) {
                    Map<BaseStat, Integer> add = set.getEffectStats().get(pieces);
                    if (add != null) {
                        addSetStats(total, add);
                    }
                    Map<BaseStat, List<Integer>> nonAdd = set.getNonAddEffectStats().get(pieces);
                    if (nonAdd != null) {
                        for (int value : nonAdd.getOrDefault(BaseStat.ied, Collections.emptyList())) {
                            total.ied += value;
                        }
                    }
                }
            }
            return score(total);
        }

        private void addSetStats(Stats s, Map<BaseStat, Integer> add) {
            double all = add.getOrDefault(BaseStat.allStat, 0);
            for (BaseStat main : mains) {
                s.main += add.getOrDefault(main, 0) + (main == BaseStat.mhp ? 0 : all);
            }
            double allR = add.getOrDefault(BaseStat.allStatR, 0);
            double mainRToAdd = 0;
            for (BaseStat mainR : mainRs) {
                mainRToAdd += add.getOrDefault(mainR, 0) + (mainR == BaseStat.mhpR ? 0 : allR);
            }
            s.mainR += mainRToAdd / mainRs.length;
            if (secondary != null) {
                s.secondary += add.getOrDefault(secondary, 0) + all;
            }
            s.att += add.getOrDefault(att, 0);
            s.attR += add.getOrDefault(attR, 0);
            s.dam += add.getOrDefault(BaseStat.damR, 0);
            s.boss += add.getOrDefault(BaseStat.bd, 0);
            s.ied += add.getOrDefault(BaseStat.ied, 0);
            s.critDmg += add.getOrDefault(BaseStat.crDmg, 0);
            s.critRate += add.getOrDefault(BaseStat.cr, 0);
        }

        /** what changes when "now" replaces "before" (before may be null), biggest effects first, at most 5 */
        String changes(Stats now, Stats before) {
            List<String> parts = new ArrayList<>();
            String mainName = mains.length > 1 ? "STR/DEX/LUK" : mainNames[0];
            add(parts, now.att - before.att, mage ? " Magic ATT" : " ATT", false);
            add(parts, now.attR - before.attR, mage ? " Magic ATT" : " ATT", true);
            add(parts, now.main - before.main, " " + mainName, false);
            add(parts, now.mainR - before.mainR, " " + mainName, true);
            add(parts, now.boss - before.boss, " Boss Damage", true);
            add(parts, now.ied - before.ied, " Ignore DEF", true);
            add(parts, now.dam - before.dam, " Damage", true);
            add(parts, now.critDmg - before.critDmg, " Critical Damage", true);
            add(parts, now.critRate - before.critRate, " Critical Rate", true);
            if (parts.isEmpty()) {
                return "about the same stats";
            }
            return String.join(", ", parts.subList(0, Math.min(5, parts.size())));
        }

        private static void add(List<String> parts, double diff, String name, boolean percent) {
            long v = Math.round(diff);
            if (v != 0) {
                parts.add((v > 0 ? "+" : "") + v + (percent ? "%" : "") + name);
            }
        }
    }

    private static BaseStat percentOf(BaseStat stat) {
        switch (stat) {
            case dex:
                return BaseStat.dexR;
            case inte:
                return BaseStat.intR;
            case luk:
                return BaseStat.lukR;
            case mhp:
                return BaseStat.mhpR;
            default:
                return BaseStat.strR;
        }
    }

    private static BaseStat secondaryOf(int job, BaseStat main) {
        if (JobConstants.isMageEquipJob(job)) {
            return BaseStat.luk;
        }
        switch (main) {
            case str:
                return BaseStat.dex;
            case dex:
                return BaseStat.str;
            case luk:
                return BaseStat.dex;
            default:
                return null;
        }
    }

    private static String statName(BaseStat stat) {
        switch (stat) {
            case dex:
                return "DEX";
            case inte:
                return "INT";
            case luk:
                return "LUK";
            case mhp:
                return "Max HP";
            default:
                return "STR";
        }
    }

    // ------------------------------------------------------------------------------------------------- finding

    /** where an equip would go: its best slot, the item there, and the gain */
    private static Entry evaluate(Char chr, Scorer scorer, Equip e) {
        int gender = chr.getAvatarData().getCharacterStat().getGender();
        List<Integer> parts = ItemConstants.getBodyPartArrayFromItem(e.getItemId(), gender);
        var info = ItemData.getEquipInfoById(e.getItemId());
        boolean unique = info != null && info.isOnlyEquip();
        Integer uniqueSlot = null;
        if (unique) {
            for (Item worn : chr.getEquippedInventory().getItems()) {
                if (worn.getItemId() == e.getItemId()) {
                    uniqueSlot = Math.abs(worn.getBagIndex()); // only that copy can be replaced
                }
            }
        }
        Stats now = scorer.stats(e);
        double score = scorer.score(now);
        Entry best = null;
        for (int part : parts) {
            if (!isGearSlot(part) || (uniqueSlot != null && part != uniqueSlot)) {
                continue;
            }
            Equip current = equippedAt(chr, part);
            Stats before = scorer.stats(current);
            double beforeScore = scorer.score(before);
            if (ItemConstants.isOverall(e.getItemId())) {
                // an overall takes the place of the top and the bottom
                Equip bottom = equippedAt(chr, BodyPart.Bottom.getVal());
                if (bottom != null) {
                    Stats b = scorer.stats(bottom);
                    beforeScore += scorer.score(b);
                    before = sum(before, b);
                }
            } else if (ItemConstants.isBottom(e.getItemId())) {
                Equip top = equippedAt(chr, BodyPart.Top.getVal());
                if (top != null && ItemConstants.isOverall(top.getItemId())) {
                    // a bottom replaces a worn overall (the top slot ends up empty)
                    current = top;
                    before = scorer.stats(top);
                    beforeScore = scorer.score(before);
                }
            }
            double gain = score - beforeScore;
            String changes = scorer.changes(now, before);
            double progression = progressionValue(e) - progressionValue(current);
            if (ItemConstants.isOverall(e.getItemId())) {
                progression -= progressionValue(equippedAt(chr, BodyPart.Bottom.getVal()));
            }
            double setGain = scorer.setScore(setCountsAfterSwap(chr, e, part))
                    - scorer.setScore(setCounts(chr, Collections.emptySet(), null));
            gain += progression + setGain;
            if (setGain != 0) {
                changes = appendChange(changes, (setGain > 0 ? "+" : "") + Math.round(setGain)
                        + " set bonus value");
            }
            if (progression != 0) {
                changes = appendChange(changes, progression > 0 ? "higher-tier set piece" : "lower-tier set piece");
            }
            if (ItemConstants.isAndroid(e.getItemId())) {
                int gradeGain = androidGrade(e) - androidGrade(current);
                gain += gradeGain;
                if (gradeGain != 0) {
                    String gradeChange = (gradeGain > 0 ? "+" : "") + gradeGain + " Android grade";
                    changes = "about the same stats".equals(changes) ? gradeChange : changes + ", " + gradeChange;
                }
            }
            if (best == null || gain > best.gain) {
                best = new Entry(e, part, current, gain, changes);
            }
        }
        return best;
    }

    /** A character's gear score, used to compare gear (the client shows Combat Power to players). */
    public record GearScore(long total, long items, long sets, int pieces) {
    }

    /**
     * The @upgrades score of every worn gear piece (the same slots and weights: main stat 1, ATT 4, 1% main stat and 1%
     * ATT at the character's own totals, damage and boss 6, ignore defense 4, crit damage 15, crit rate 1, secondary
     * stat 0.1), read off each item as it is now (its stars, scrolls, potential, bonus potential and flames: @upgrades
     * instead ranks what an item can become), plus the score of the set effects that are on.
     */
    public static GearScore gearScore(Char chr) {
        Scorer scorer = new Scorer(chr);
        double items = 0;
        int pieces = 0;
        boolean zero = JobConstants.isZero(chr.getJob());
        for (int part : GEAR_SLOTS) {
            if (zero && part == BodyPart.Shield.getVal()) {
                continue; // Zero's second weapon mirrors the first
            }
            Equip e = equippedAt(chr, part);
            if (e == null || e.isCash()) {
                continue;
            }
            items += scorer.score(scorer.read(e));
            pieces++;
        }
        double sets = scorer.setScore(setCounts(chr, Collections.emptySet(), null));
        return new GearScore(Math.round(items + sets), Math.round(items), Math.round(sets), pieces);
    }

    private static String appendChange(String changes, String extra) {
        return "about the same stats".equals(changes) ? extra : changes + ", " + extra;
    }

    private static Map<Integer, Integer> setCountsAfterSwap(Char chr, Equip candidate, int bodyPart) {
        Set<Integer> removed = new HashSet<>();
        removed.add(bodyPart);
        if (ItemConstants.isOverall(candidate.getItemId())) {
            removed.add(BodyPart.Bottom.getVal());
        } else if (ItemConstants.isBottom(candidate.getItemId())) {
            Equip top = equippedAt(chr, BodyPart.Top.getVal());
            if (top != null && ItemConstants.isOverall(top.getItemId())) {
                removed.add(BodyPart.Top.getVal());
            }
        }
        return setCounts(chr, removed, candidate);
    }

    private static Map<Integer, Integer> setCounts(Char chr, Set<Integer> removedParts, Equip added) {
        Map<Integer, Integer> counts = new HashMap<>();
        for (Item item : chr.getEquippedInventory().getItems()) {
            if (!(item instanceof Equip equip) || item.isCash() || removedParts.contains(Math.abs(item.getBagIndex()))
                    || ItemConstants.isNotSetItemApplyBodyPart(item.getBagIndex())) {
                continue;
            }
            addSet(counts, equip);
        }
        if (added != null) {
            addSet(counts, added);
        }
        return counts;
    }

    private static void addSet(Map<Integer, Integer> counts, Equip equip) {
        var info = equip.getInfo();
        int setId = info == null ? 0 : info.getSetItemID();
        if (setId != 0) {
            counts.merge(setId, 1, Integer::sum);
        }
    }

    /** Recognized gear path: Boss/Gollux accessories, CRA, AbsoLab, Arcane Umbra and Pitched Boss. */
    private static double progressionValue(Equip equip) {
        if (equip == null || equip.getInfo() == null) {
            return 0;
        }
        int setId = equip.getInfo().getSetItemID();
        int tier;
        if (ARCANE_UMBRA_SETS.contains(setId) || setId == 677) {
            tier = 5;
        } else if (ABSOLAB_SETS.contains(setId) || setId == 318) {
            tier = 4;
        } else if (ROOT_ABYSS_SETS.contains(setId) || setId == 317) {
            tier = 3;
        } else if (setId == 462 || setId == 316) {
            tier = 2;
        } else if (setId == 315) {
            tier = 1;
        } else {
            tier = 0;
        }
        return tier * SET_TIER_WEIGHT;
    }

    private static Stats sum(Stats a, Stats b) {
        Stats s = new Stats();
        s.main = a.main + b.main;
        s.secondary = a.secondary + b.secondary;
        s.mainR = a.mainR + b.mainR;
        s.att = a.att + b.att;
        s.attR = a.attR + b.attR;
        s.dam = a.dam + b.dam;
        s.boss = a.boss + b.boss;
        s.ied = a.ied + b.ied;
        s.critDmg = a.critDmg + b.critDmg;
        s.critRate = a.critRate + b.critRate;
        return s;
    }

    private static boolean isGearSlot(int part) {
        for (int slot : GEAR_SLOTS) {
            if (slot == part) {
                return true;
            }
        }
        return false;
    }

    private static Equip equippedAt(Char chr, int bodyPart) {
        Item item = chr.getEquippedInventory().getItemBySlot(bodyPart);
        return item instanceof Equip ? (Equip) item : null;
    }

    /** gear in the Equip inventory this class (and gender, weapon type) can use */
    private static List<Equip> candidates(Char chr) {
        List<Equip> res = new ArrayList<>();
        for (Item item : chr.getEquipInventory().getItems()) {
            if (!(item instanceof Equip) || item.isCash() || ItemConstants.isSymbol(item.getItemId())
                    || ((Equip) item).getInfo() == null || !EquipDropFilter.isUsableBy(item.getItemId(), chr)) {
                continue;
            }
            res.add((Equip) item);
        }
        return res;
    }

    /** level and stat requirements (class, gender and weapon type are checked by the caller) */
    public static boolean meetsRequirements(Char chr, Equip e) {
        if (chr.getLevel() < e.getRequiredLevel()) {
            return false;
        }
        if (ItemConstants.isPocketItem(e.getItemId()) && !chr.isPocketSlotOpen()) {
            return false; // pocket slot still locked
        }
        var info = e.getInfo();
        var cs = chr.getAvatarData().getCharacterStat();
        boolean strOk = info.getrStr() <= cs.getStr() || JobConstants.isDemonAvenger(chr.getJob());
        return strOk && info.getrDex() <= cs.getDex() && info.getrInt() <= cs.getInt() && info.getrLuk() <= cs.getLuk();
    }

    /** equips the character can wear now that beat what it wears, best first */
    public static List<Entry> findUpgrades(Char chr) {
        Scorer scorer = new Scorer(chr);
        List<Equip> wearable = wearableNow(chr);
        List<Entry> res = new ArrayList<>();
        for (Equip e : wearable) {
            Entry entry = evaluate(chr, scorer, e);
            if (entry != null && entry.gain >= MIN_GAIN) {
                res.add(entry);
            }
        }
        // whole sets: carried pieces worn together for the set bonus, where that beats the one-slot picks
        res.addAll(findSetGroups(chr, scorer, wearable, res));
        res.sort((a, b) -> Double.compare(b.gain, a.gain));
        return res;
    }

    /** equips the character can wear now (shields only with a weapon that takes one) */
    private static List<Equip> wearableNow(Char chr) {
        List<Equip> res = new ArrayList<>();
        for (Equip e : candidates(chr)) {
            if (meetsRequirements(chr, e)
                    && !(ItemConstants.isShield(e.getItemId()) && !canWearShieldWithCurrentWeapon(chr))) {
                res.add(e);
            }
        }
        return res;
    }

    // ------------------------------------------------------------------------------------------------- whole sets

    /**
     * For each set with 2+ carried pieces: the best pieces to wear together (one per slot, rings and pendants over
     * their several slots), scored with the set bonus they reach, against the current gear. Kept when it beats the
     * one-slot picks for the same slots and isn't just those picks.
     */
    private static List<Entry> findSetGroups(Char chr, Scorer scorer, List<Equip> wearable, List<Entry> singles) {
        Map<Integer, List<Equip>> bySet = new HashMap<>();
        for (Equip e : wearable) {
            int setId = e.getInfo() == null ? 0 : e.getInfo().getSetItemID();
            if (setId != 0 && !ItemConstants.isAndroid(e.getItemId())) {
                bySet.computeIfAbsent(setId, k -> new ArrayList<>()).add(e);
            }
        }
        List<Entry> res = new ArrayList<>();
        if (bySet.values().stream().noneMatch(l -> l.size() >= 2)) {
            return res;
        }
        Map<Integer, Entry> bestSingle = new HashMap<>();
        for (Entry e : singles) {
            bestSingle.merge(e.bodyPart, e, (a, b) -> a.gain >= b.gain ? a : b);
        }
        double setsNow = scorer.setScore(setCounts(chr, Collections.emptySet(), null));
        int gender = chr.getAvatarData().getCharacterStat().getGender();
        for (var set : bySet.entrySet()) {
            if (set.getValue().size() < 2) {
                continue;
            }
            Entry group = bestGroup(chr, scorer, set.getKey(), set.getValue(), bestSingle, setsNow, gender);
            if (group != null) {
                res.add(group);
            }
        }
        return res;
    }

    /** One carried piece in one slot: what comes off, and the score change of the items alone. */
    private record Placement(Equip item, int part, Set<Integer> removed, Equip current, double itemGain) {
    }

    private static Placement place(Char chr, Scorer scorer, Equip e, int part) {
        Set<Integer> removed = new HashSet<>();
        removed.add(part);
        Equip current = equippedAt(chr, part);
        if (ItemConstants.isOverall(e.getItemId())) {
            removed.add(BodyPart.Bottom.getVal());
        } else if (ItemConstants.isBottom(e.getItemId())) {
            Equip top = equippedAt(chr, BodyPart.Top.getVal());
            if (top != null && ItemConstants.isOverall(top.getItemId())) {
                removed.add(BodyPart.Top.getVal());
                current = top;
            }
        }
        double before = 0;
        double progressionBefore = 0;
        for (int r : removed) {
            Equip worn = equippedAt(chr, r);
            before += scorer.score(worn);
            progressionBefore += progressionValue(worn);
        }
        double gain = scorer.score(e) - before + progressionValue(e) - progressionBefore;
        return new Placement(e, part, removed, current, gain);
    }

    private static Entry bestGroup(Char chr, Scorer scorer, int setId, List<Equip> pieces,
                                   Map<Integer, Entry> bestSingle, double setsNow, int gender) {
        // every piece in every slot it fits, best first; then each piece and slot taken once
        List<Placement> options = new ArrayList<>();
        for (Equip e : pieces) {
            var info = e.getInfo();
            boolean unique = info != null && info.isOnlyEquip();
            for (int part : ItemConstants.getBodyPartArrayFromItem(e.getItemId(), gender)) {
                if (!isGearSlot(part)) {
                    continue;
                }
                Equip worn = equippedAt(chr, part);
                if (unique && chr.getEquippedInventory().getItems().stream()
                        .anyMatch(w -> w.getItemId() == e.getItemId() && w != worn)) {
                    continue; // only the worn copy's slot could take it
                }
                options.add(place(chr, scorer, e, part));
            }
        }
        options.sort((a, b) -> Double.compare(b.itemGain, a.itemGain));
        List<Placement> chosen = new ArrayList<>();
        Set<Integer> usedParts = new HashSet<>();
        Set<Equip> usedItems = Collections.newSetFromMap(new IdentityHashMap<>());
        Set<Integer> usedIds = new HashSet<>();
        for (Placement p : options) {
            var info = p.item.getInfo();
            boolean unique = info != null && info.isOnlyEquip();
            if (usedItems.contains(p.item) || (unique && usedIds.contains(p.item.getItemId()))
                    || !Collections.disjoint(usedParts, p.removed)) {
                continue;
            }
            chosen.add(p);
            usedParts.addAll(p.removed);
            usedItems.add(p.item);
            usedIds.add(p.item.getItemId());
        }
        if (chosen.size() < 2) {
            return null;
        }
        // the best number of pieces: the item gains fall as more go on, the set bonus grows
        double bestGain = Double.NEGATIVE_INFINITY;
        int bestK = 0;
        double bestSetGain = 0;
        for (int k = 2; k <= chosen.size(); k++) {
            List<Placement> group = chosen.subList(0, k);
            double items = 0;
            for (Placement p : group) {
                items += p.itemGain;
            }
            double setGain = scorer.setScore(countsWith(chr, group)) - setsNow;
            if (items + setGain > bestGain) {
                bestGain = items + setGain;
                bestK = k;
                bestSetGain = setGain;
            }
        }
        if (bestK < 2 || bestGain < MIN_GAIN) {
            return null;
        }
        List<Placement> group = new ArrayList<>(chosen.subList(0, bestK));
        // against the one-slot picks for the same slots: shown only when it does better and isn't just those picks
        double singlesGain = 0;
        boolean sameAsSingles = true;
        Set<Integer> counted = new HashSet<>();
        for (Placement p : group) {
            for (int r : p.removed) {
                Entry single = bestSingle.get(r);
                if (single != null && counted.add(r)) {
                    singlesGain += single.gain;
                }
            }
            Entry single = bestSingle.get(p.part);
            if (single == null || single.item != p.item) {
                sameAsSingles = false;
            }
        }
        if (sameAsSingles || bestGain <= singlesGain + MIN_GAIN) {
            return null;
        }
        group.sort(Comparator.comparingInt(p -> p.part));
        List<Entry> entries = new ArrayList<>();
        Stats now = new Stats();
        Stats before = new Stats();
        for (Placement p : group) {
            Stats ps = scorer.stats(p.item);
            Stats b = new Stats();
            for (int r : p.removed) {
                b = sum(b, scorer.stats(equippedAt(chr, r)));
            }
            now = sum(now, ps);
            before = sum(before, b);
            entries.add(new Entry(p.item, p.part, p.current, p.itemGain, scorer.changes(ps, b)));
        }
        int worn = countsWith(chr, group).getOrDefault(setId, 0);
        int tier = 0;
        SetItemInfo info = EtcData.getSetItemInfoById(setId);
        if (info != null) {
            for (int n : info.getEffectStats().keySet()) {
                if (n <= worn && n > tier) {
                    tier = n;
                }
            }
        }
        String name = setName(setId, group);
        String changes = name + " set (" + bestK + " pieces): +" + Math.round(bestGain)
                + (tier >= 2 ? ", includes the " + tier + "-set bonus" : "") + "; " + scorer.changes(now, before)
                + (bestSetGain != 0 ? ", " + (bestSetGain > 0 ? "+" : "") + Math.round(bestSetGain)
                + " set bonus value" : "");
        Entry first = entries.get(0);
        return new Entry(first.item, first.bodyPart, first.current, bestGain, changes, entries, name);
    }

    private static Map<Integer, Integer> countsWith(Char chr, List<Placement> group) {
        Set<Integer> removed = new HashSet<>();
        for (Placement p : group) {
            removed.addAll(p.removed);
        }
        Map<Integer, Integer> counts = setCounts(chr, removed, null);
        for (Placement p : group) {
            addSet(counts, p.item);
        }
        return counts;
    }

    private static String setName(int setId, List<Placement> group) {
        if (ABSOLAB_SETS.contains(setId)) {
            return "AbsoLab";
        }
        if (ARCANE_UMBRA_SETS.contains(setId)) {
            return "Arcane Umbra";
        }
        if (ROOT_ABYSS_SETS.contains(setId)) {
            return "Root Abyss";
        }
        // the words the pieces' names start with ("Eagle Eye", "Meister")
        String[] common = null;
        for (Placement p : group) {
            String[] words = itemName(p.item.getItemId()).split(" ");
            if (common == null) {
                common = words;
                continue;
            }
            int n = 0;
            while (n < common.length && n < words.length && common[n].equals(words[n])) {
                n++;
            }
            common = Arrays.copyOf(common, n);
        }
        return common == null || common.length == 0 ? "Gear" : String.join(" ", common);
    }

    /**
     * How much an equip not owned yet (a shop's) would improve the character, as @upgrades scores it: the gain over
     * what it wears in that slot, or 0 when it isn't an upgrade or can't be worn.
     */
    public static double upgradeGain(Char chr, Equip e) {
        if (e == null || !meetsRequirements(chr, e)
                || (ItemConstants.isShield(e.getItemId()) && !canWearShieldWithCurrentWeapon(chr))) {
            return 0;
        }
        Entry entry = evaluate(chr, new Scorer(chr), e);
        return entry == null || entry.gain < MIN_GAIN ? 0 : entry.gain;
    }

    /** The pieces of a gear combination and what wearing them all would add (items and set bonuses). */
    public record Combination(List<Equip> pieces, double gain) {
    }

    /**
     * The best gear combination out of these choices, as @upgrades scores it: per group (a slot or two), one of its
     * pieces or what is worn there now, the items scored together with every set bonus they make. Pieces that would
     * share a slot (an overall and a bottom) aren't taken together.
     */
    public static Combination bestCombination(Char chr, List<List<Equip>> groups) {
        Scorer scorer = new Scorer(chr);
        int gender = chr.getAvatarData().getCharacterStat().getGender();
        List<List<Placement>> opts = new ArrayList<>();
        for (List<Equip> group : groups) {
            List<Placement> ps = new ArrayList<>();
            for (Equip e : group) {
                for (int part : ItemConstants.getBodyPartArrayFromItem(e.getItemId(), gender)) {
                    if (isGearSlot(part)) {
                        ps.add(place(chr, scorer, e, part));
                        break;
                    }
                }
            }
            if (!ps.isEmpty()) {
                opts.add(ps);
            }
        }
        Map<Integer, Integer> wornCounts = setCounts(chr, Collections.emptySet(), null);
        Map<Integer, Integer> wornSets = new HashMap<>(); // slot -> set id of what's worn there
        for (Item item : chr.getEquippedInventory().getItems()) {
            if (item instanceof Equip equip && !item.isCash()
                    && !ItemConstants.isNotSetItemApplyBodyPart(item.getBagIndex())) {
                int setId = equip.getInfo() == null ? 0 : equip.getInfo().getSetItemID();
                if (setId != 0) {
                    wornSets.put(Math.abs(item.getBagIndex()), setId);
                }
            }
        }
        double setsNow = scorer.setScore(wornCounts);
        List<Placement> best = new ArrayList<>();
        double[] bestGain = {0};
        combine(scorer, opts, 0, new ArrayList<>(), new HashSet<>(), 0, wornCounts, wornSets, setsNow, best,
                bestGain);
        List<Equip> pieces = new ArrayList<>();
        for (Placement p : best) {
            pieces.add(p.item);
        }
        return new Combination(pieces, bestGain[0]);
    }

    private static void combine(Scorer scorer, List<List<Placement>> opts, int i, List<Placement> cur,
                                Set<Integer> used, double items, Map<Integer, Integer> wornCounts,
                                Map<Integer, Integer> wornSets, double setsNow, List<Placement> best,
                                double[] bestGain) {
        if (i == opts.size()) {
            Map<Integer, Integer> counts = new HashMap<>(wornCounts);
            for (Placement p : cur) {
                for (int r : p.removed) {
                    Integer setId = wornSets.get(r);
                    if (setId != null) {
                        counts.merge(setId, -1, Integer::sum);
                    }
                }
                addSet(counts, p.item);
            }
            double gain = items + scorer.setScore(counts) - setsNow;
            if (gain > bestGain[0] + MIN_GAIN) {
                bestGain[0] = gain;
                best.clear();
                best.addAll(cur);
            }
            return;
        }
        combine(scorer, opts, i + 1, cur, used, items, wornCounts, wornSets, setsNow, best, bestGain); // as worn
        for (Placement p : opts.get(i)) {
            if (!Collections.disjoint(used, p.removed)) {
                continue;
            }
            cur.add(p);
            used.addAll(p.removed);
            combine(scorer, opts, i + 1, cur, used, items + p.itemGain, wornCounts, wornSets, setsNow, best,
                    bestGain);
            used.removeAll(p.removed);
            cur.remove(cur.size() - 1);
        }
    }

    /** equips that would be upgrades but need a higher level (or more stats), lowest level first */
    public static List<Entry> findLaterUpgrades(Char chr) {
        Scorer scorer = new Scorer(chr);
        List<Entry> res = new ArrayList<>();
        for (Equip e : candidates(chr)) {
            if (meetsRequirements(chr, e)) {
                continue;
            }
            Entry entry = evaluate(chr, scorer, e);
            if (entry != null && entry.gain >= MIN_GAIN) {
                res.add(entry);
            }
        }
        res.sort(Comparator.comparingInt(Entry::getRequiredLevel).thenComparing((a, b) -> Double.compare(b.gain, a.gain)));
        return res;
    }

    /**
     * equips the character can wear that are no better than what it wears in every slot they fit (candidates to sell
     * or store), worst first
     */
    public static List<Entry> findOutclassed(Char chr) {
        Scorer scorer = new Scorer(chr);
        List<Entry> res = new ArrayList<>();
        // pieces of a whole set worth wearing together aren't vendor gear
        List<Equip> wearable = wearableNow(chr);
        List<Entry> singles = new ArrayList<>();
        for (Equip e : wearable) {
            Entry entry = evaluate(chr, scorer, e);
            if (entry != null && entry.gain >= MIN_GAIN) {
                singles.add(entry);
            }
        }
        Set<Equip> inGroups = Collections.newSetFromMap(new IdentityHashMap<>());
        for (Entry group : findSetGroups(chr, scorer, wearable, singles)) {
            for (Entry p : group.pieces) {
                inGroups.add(p.item);
            }
        }
        for (Equip e : candidates(chr)) {
            if (!meetsRequirements(chr, e) || ItemConstants.isSecondary(e.getItemId()) || inGroups.contains(e)) {
                continue; // class secondaries can't be bought back
            }
            Entry entry = evaluate(chr, scorer, e);
            // Keep a higher progression-tier piece for a later multi-piece set transition even when equipping that
            // one piece immediately would temporarily lose a completed lower-tier set bonus.
            if (entry != null && entry.gain < MIN_GAIN
                    && progressionValue(e) <= progressionValue(entry.current)) {
                res.add(entry);
            }
        }
        res.sort(Comparator.comparingDouble(a -> a.gain));
        return res;
    }

    /** how many equips in the Equip inventory aren't for this character's class, gender or weapon */
    public static int countNotForClass(Char chr) {
        int count = 0;
        for (Item item : chr.getEquipInventory().getItems()) {
            if (item instanceof Equip && !item.isCash() && !ItemConstants.isSymbol(item.getItemId())
                    && !EquipDropFilter.isUsableBy(item.getItemId(), chr)) {
                count++;
            }
        }
        return count;
    }

    // ------------------------------------------------------------------------------------------------- equipping

    /**
     * Equips an upgrade found by {@link #findUpgrades(Char)}, the same way dragging it onto the slot does.
     *
     * @return a message for the player
     */
    public static String equip(Char chr, Entry entry) {
        if (entry.isSetGroup()) {
            return equipGroup(chr, entry);
        }
        Equip item = entry.getItem();
        String name = itemName(item.getItemId());
        if (chr.getEquipInventory().getItemBySlot(item.getBagIndex()) != item || !meetsRequirements(chr, item)) {
            return name + " isn't in your Equip inventory anymore, or you can't wear it.";
        }
        int part = entry.getBodyPart();
        String androidWarning = androidCompatibilityWarning(chr, item);
        if (androidWarning != null) {
            return androidWarning;
        }
        // A two handed or otherwise incompatible weapon cannot replace the weapon while a shield is worn. The slot
        // also holds secondary weapons and katara, which stay.
        Equip shield = equippedAt(chr, BodyPart.Shield.getVal());
        if (ItemConstants.isWeapon(item.getItemId()) && shield != null && ItemConstants.isShield(shield.getItemId())) {
            var weaponType = ItemConstants.getWeaponType(item.getItemId());
            if ((weaponType == null || !ItemConstants.canWearShield(weaponType))
                    && !takeOff(chr, BodyPart.Shield.getVal())) {
                return "Make room in your Equip inventory to remove your shield for " + name + ".";
            }
        }
        // overalls and bottoms: take off what's in the way first (the client does the same)
        if (ItemConstants.isOverall(item.getItemId())) {
            if (!takeOff(chr, BodyPart.Bottom.getVal())) {
                return "Make room in your Equip inventory to swap your bottom for " + name + ".";
            }
        } else if (ItemConstants.isBottom(item.getItemId())) {
            Equip top = equippedAt(chr, BodyPart.Top.getVal());
            if (top != null && ItemConstants.isOverall(top.getItemId()) && !takeOff(chr, BodyPart.Top.getVal())) {
                return "Make room in your Equip inventory to swap your overall for " + name + ".";
            }
        }
        InventoryHandler.moveSlot(chr, InvType.EQUIP, (short) item.getBagIndex(), (short) -part, (short) 1);
        if (chr.getEquippedInventory().getItemBySlot(part) == item) {
            return "Equipped #b" + name + "#k (" + entry.getChanges() + ").";
        }
        return name + " couldn't be equipped there.";
    }

    /** Equips every piece of a whole-set suggestion. */
    private static String equipGroup(Char chr, Entry group) {
        List<String> done = new ArrayList<>();
        List<String> failed = new ArrayList<>();
        for (Entry p : group.pieces) {
            String msg = equip(chr, p);
            if (chr.getEquippedInventory().getItemBySlot(p.getBodyPart()) == p.getItem()) {
                done.add("#i" + p.getItemId() + "# " + itemName(p.getItemId()));
            } else {
                failed.add(msg);
            }
        }
        if (done.isEmpty()) {
            return "Nothing was equipped: " + String.join(" ", failed);
        }
        return "Equipped the " + group.setName + " set (" + done.size() + " of " + group.pieces.size() + " pieces):\r\n"
                + String.join("\r\n", done) + (failed.isEmpty() ? "" : "\r\n\r\n" + String.join("\r\n", failed));
    }

    /** @return the equipped pieces of an entry (none when it couldn't be put on) */
    private static List<Entry> equippedPieces(Char chr, Entry entry) {
        List<Entry> res = new ArrayList<>();
        for (Entry p : entry.isSetGroup() ? entry.pieces : List.of(entry)) {
            if (chr.getEquippedInventory().getItemBySlot(p.getBodyPart()) == p.getItem()) {
                res.add(p);
            }
        }
        return res;
    }

    private static boolean canWearShieldWithCurrentWeapon(Char chr) {
        Equip weapon = equippedAt(chr, BodyPart.Weapon.getVal());
        if (weapon == null) {
            return true;
        }
        var weaponType = ItemConstants.getWeaponType(weapon.getItemId());
        return weaponType != null && ItemConstants.canWearShield(weaponType);
    }

    private static int androidGrade(Equip equip) {
        return equip == null || equip.getInfo() == null ? 0 : equip.getInfo().getAndroidGrade();
    }

    /**
     * Refuse an Android that would not function with the character's equipped heart or a better carried heart.
     */
    private static String androidCompatibilityWarning(Char chr, Equip android) {
        if (ItemConstants.isMechanicalHeart(android.getItemId())) {
            return heartCompatibilityWarning(chr, android);
        }
        if (!ItemConstants.isAndroid(android.getItemId())) {
            return null;
        }
        int androidGrade = androidGrade(android);
        Equip equippedHeart = equippedAt(chr, BodyPart.MechanicalHeart.getVal());
        if (equippedHeart != null && androidGrade(equippedHeart) > androidGrade) {
            return "Android not equipped: its grade " + androidGrade + " is too low for your equipped heart "
                    + "#b#t" + equippedHeart.getItemId() + "##k (grade " + androidGrade(equippedHeart)
                    + "). Choose an Android of grade " + androidGrade(equippedHeart) + " or higher.";
        }

        Scorer scorer = new Scorer(chr);
        Entry incompatibleHeart = null;
        for (Equip heart : candidates(chr)) {
            if (!ItemConstants.isMechanicalHeart(heart.getItemId()) || !meetsRequirements(chr, heart)
                    || androidGrade(heart) <= androidGrade) {
                continue;
            }
            Entry heartEntry = evaluate(chr, scorer, heart);
            if (heartEntry != null && heartEntry.gain >= MIN_GAIN
                    && (incompatibleHeart == null || heartEntry.gain > incompatibleHeart.gain)) {
                incompatibleHeart = heartEntry;
            }
        }
        if (incompatibleHeart != null) {
            Equip heart = incompatibleHeart.getItem();
            return "Android not equipped: its grade " + androidGrade + " is too low for the better heart "
                    + "#b#t" + heart.getItemId() + "##k in your Equip inventory (grade " + androidGrade(heart)
                    + "). Keep the heart and choose an Android of grade " + androidGrade(heart) + " or higher.";
        }
        return null;
    }

    /** Refuse a heart the worn Android can't run (the heart's grade is above the Android's). */
    private static String heartCompatibilityWarning(Char chr, Equip heart) {
        Equip android = equippedAt(chr, BodyPart.Android.getVal());
        int heartGrade = androidGrade(heart);
        if (android == null || androidGrade(android) >= heartGrade) {
            return null;
        }
        return "Heart not equipped: #b#t" + heart.getItemId() + "##k needs an Android of grade " + heartGrade
                + " or higher, and your #b#t" + android.getItemId() + "##k is grade " + androidGrade(android) + ".";
    }

    private static boolean isCompatibilityWarning(String msg) {
        return msg.startsWith("Android not equipped:") || msg.startsWith("Heart not equipped:");
    }

    private static boolean takeOff(Char chr, int bodyPart) {
        Equip worn = equippedAt(chr, bodyPart);
        if (worn == null) {
            return true;
        }
        int free = chr.getEquipInventory().getFirstOpenSlot();
        if (free <= 0 || chr.getEquipInventory().isFull()) {
            return false;
        }
        InventoryHandler.moveSlot(chr, InvType.EQUIP, (short) -bodyPart, (short) free, (short) 1);
        return equippedAt(chr, bodyPart) == null;
    }

    /**
     * Equips the best upgrade for every slot, one at a time (an item that comes off can still be the best choice for
     * another slot, e.g. rings).
     *
     * @return a message for the player
     */
    public static String equipAll(Char chr) {
        Set<Equip> failed = new HashSet<>();
        List<String> done = new ArrayList<>();
        List<String> warnings = new ArrayList<>();
        for (int i = 0; i < 40; i++) {
            Entry next = null;
            for (Entry e : findUpgrades(chr)) {
                if (!failed.contains(e.getItem())) {
                    next = e;
                    break;
                }
            }
            if (next == null) {
                break;
            }
            String msg = equip(chr, next);
            List<Entry> on = equippedPieces(chr, next);
            if (!on.isEmpty()) {
                for (Entry p : on) {
                    done.add("#i" + p.getItemId() + "# " + itemName(p.getItemId()));
                }
            } else {
                failed.add(next.getItem());
                if (isCompatibilityWarning(msg)) {
                    warnings.add(msg);
                }
            }
        }
        if (done.isEmpty()) {
            return "Nothing was equipped: none of your upgrades could be put on."
                    + (warnings.isEmpty() ? "" : "\r\n\r\n" + String.join("\r\n", warnings));
        }
        return "Equipped " + done.size() + " upgrade" + (done.size() == 1 ? "" : "s") + ":\r\n"
                + String.join("\r\n", done)
                + (failed.size() <= warnings.size() ? "" : "\r\n\r\n" + (failed.size() - warnings.size())
                + " couldn't be equipped.")
                + (warnings.isEmpty() ? "" : "\r\n\r\n" + String.join("\r\n", warnings));
    }

    // ------------------------------------------------------------------------------------------------- names

    public static String itemName(int itemId) {
        String name = StringData.getItemStringById(itemId);
        return name == null ? String.valueOf(itemId) : name;
    }

    public static String slotName(int bodyPart) {
        switch (bodyPart) {
            case 1:
                return "Hat";
            case 2:
                return "Face Accessory";
            case 3:
                return "Eye Accessory";
            case 4:
                return "Earrings";
            case 5:
                return "Top";
            case 6:
                return "Bottom";
            case 7:
                return "Shoes";
            case 8:
                return "Gloves";
            case 9:
                return "Cape";
            case 10:
                return "Secondary";
            case 11:
                return "Weapon";
            case 12:
            case 13:
            case 15:
            case 16:
                return "Ring";
            case 17:
            case 65:
                return "Pendant";
            case 49:
                return "Medal";
            case 50:
                return "Belt";
            case 51:
                return "Shoulder";
            case 52:
                return "Pocket";
            case 53:
                return "Android";
            case 54:
                return "Mechanical Heart";
            case 56:
                return "Badge";
            case 61:
                return "Emblem";
            default:
                return "Slot " + bodyPart;
        }
    }
}
