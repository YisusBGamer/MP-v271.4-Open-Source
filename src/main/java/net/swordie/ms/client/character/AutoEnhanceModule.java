package net.swordie.ms.client.character;

import net.swordie.ms.client.character.items.Equip;
import net.swordie.ms.client.character.items.Item;
import net.swordie.ms.client.character.items.ScrollUpgradeInfo;
import net.swordie.ms.enums.SpellTraceScrollType;
import net.swordie.ms.constants.GameConstants;
import net.swordie.ms.constants.ItemConstants;
import net.swordie.ms.constants.JobConstants;
import net.swordie.ms.constants.ServerFeatures;
import net.swordie.ms.enums.EquipBaseStat;
import net.swordie.ms.client.character.items.EquipSpecialAttribute;
import net.swordie.ms.loaders.StringData;
import net.swordie.ms.ext.ServerHooks;
import net.swordie.ms.handlers.item.ItemUpgradeHandler;
import net.swordie.ms.util.Util;
import net.swordie.ms.world.event.SunnySunday;

import java.text.NumberFormat;
import java.util.*;

/**
 * @enhance (scripts/npc/autoEnhance.py): fills an equipped item's free upgrade slots with Spell Trace scrolls, then
 * Star Forces it up to {@link #STAR_CAP} stars (higher only by hand), in one go. Three ways ({@link Mode}):
 * <ul>
 * <li>Normal: the 100% Spell Trace scroll, for what doing it by hand costs (the Spell Traces of each scroll).</li>
 * <li>Clean Slate: first gives back the upgrade slots earlier scrolls failed (a Clean Slate Scroll's Spell Traces each,
 * never failing), then fills them like Normal.</li>
 * <li>Best scrolls: the strongest Spell Trace scroll for each item (15% for weapons, 30% for the rest), for
 * {@link GameConstants#AUTO_ENHANCE_BEST_SCROLL_COST_MULTIPLIER} times that scroll's Spell Traces.</li>
 * </ul>
 * Stars cost the mesos doing them by hand costs in the Star Force window, with the Safeguard or destroy-rate slider
 * setting the player picks ({@link GameConstants#starForceCost}). For the convenience, the Spell Traces cost
 * {@link GameConstants#AUTO_ENHANCE_TRACE_MARKUP_PERCENT}% more and the mesos
 * {@link GameConstants#AUTO_ENHANCE_MESO_MARKUP_PERCENT}% more (everything is free with the setting
 * {@link ServerFeatures.Feature#FREE_ENHANCE}).
 * Hammers, potentials and flames are not touched.
 * <p>
 * Odds: while scrolls_always_succeed / star_force_always_succeeds are on, nothing fails (as by hand). While one is
 * off, a player's @enhance uses the same odds as doing it by hand: each scroll and Clean Slate rolls its chance
 * ({@link ScrollUpgradeInfo#applyTo}), and Star Force makes one attempt per star the plan shows with the window's
 * odds ({@link #starForceAttempt}), paying per attempt (never more than shown). Bots
 * ({@link ServerHooks#isAutomated}) always enhance without risk, except those that pay as players
 * ({@link ServerHooks#paysAsPlayer}).
 */
public class AutoEnhanceModule {

    /** How @enhance scrolls (see the class comment). */
    public enum Mode {
        NORMAL, CLEAN_SLATE, BEST
    }

    /** "Best scrolls" costs this many times the Spell Traces the strongest scroll costs by hand. */
    /** Spell Traces per failed slot given back with "Clean Slate" (what a Clean Slate Scroll costs by hand). */
    public static final int CLEAN_SLATE_TRACES = 200;
    /** @enhance and @optimize Star Force up to this many stars; higher only by hand in the Enhance window. */
    public static final int STAR_CAP = 22;

    /** What enhancing one equip takes. */
    public static class Plan {
        public final Equip equip;
        public final Mode mode;
        /** failed upgrade slots given back first (Clean Slate) */
        public int cleanSlates;
        /** the scroll used in every free slot, or null */
        public ScrollUpgradeInfo scroll;
        public int scrolls;
        public long traces;
        public int stars;
        public long mesos;
        /** Star Force goes up to this many stars (STAR_CAP, or the item's maximum for "full stars") */
        public int starCap = STAR_CAP;
        /** the Star Force window's destroy-rate slider (0..3 = 1-4) and Safeguard, as the player picked them */
        public int starMode;
        public boolean safeguard;

        Plan(Equip equip, Mode mode) {
            this.equip = equip;
            this.mode = mode;
        }

        public boolean isEmpty() {
            return cleanSlates == 0 && scrolls == 0 && stars == 0;
        }

        public String getName() {
            String name = StringData.getItemStringById(equip.getItemId());
            return name == null ? String.valueOf(equip.getItemId()) : name;
        }

        public String describe() {
            List<String> parts = new ArrayList<>();
            if (cleanSlates > 0) {
                parts.add(String.format("%d slot%s restored", cleanSlates, cleanSlates == 1 ? "" : "s"));
            }
            if (scrolls > 0) {
                parts.add(String.format("%d %s scroll%s", scrolls,
                        scroll == null ? "" : scroll.getChance() + "%", scrolls == 1 ? "" : "s"));
            }
            if (stars > 0) {
                parts.add(String.format("%d -> %d stars", equip.getChuc(), equip.getChuc() + stars)
                        + (safeguard ? " (Safeguard)" : starMode > 0 ? " (slider " + (starMode + 1) + ")" : ""));
            }
            return String.join(", ", parts);
        }
    }

    /**
     * @return the equipped items that can still be scrolled or Star Forced, with what that takes
     */
    public static List<Plan> getPlans(Char chr) {
        return getPlans(chr, Mode.NORMAL);
    }

    /**
     * @return the equipped items this way of scrolling can still improve, with what that takes
     */
    public static List<Plan> getPlans(Char chr, Mode mode) {
        return getPlans(chr, mode, false);
    }

    /**
     * @param fullStars Star Force up to each item's maximum instead of {@link #STAR_CAP}
     */
    public static List<Plan> getPlans(Char chr, Mode mode, boolean fullStars) {
        return getPlans(chr, mode, fullStars, 0, false);
    }

    /**
     * @param starMode  the Star Force window's destroy-rate slider, 0..3 (it changes 15-21 stars)
     * @param safeguard Safeguard at 15-17 stars (+200% mesos there, no destroy)
     */
    public static List<Plan> getPlans(Char chr, Mode mode, boolean fullStars, int starMode, boolean safeguard) {
        List<Plan> plans = new ArrayList<>();
        List<Item> items = new ArrayList<>(chr.getEquippedInventory().getItems());
        items.sort(Comparator.comparingInt(Item::getBagIndex));
        for (Item item : items) {
            if (!(item instanceof Equip equip) || equip.isCash() || isZeroSecondWeapon(chr, equip)) {
                continue;
            }
            Plan plan = plan(chr, equip, mode, fullStars, starMode, safeguard);
            if (plan != null && !plan.isEmpty()) {
                plans.add(plan);
            }
        }
        return plans;
    }

    public static Plan getPlan(Char chr, int bagIndex, Mode mode) {
        return getPlan(chr, bagIndex, mode, false);
    }

    public static Plan getPlan(Char chr, int bagIndex, Mode mode, boolean fullStars) {
        return getPlan(chr, bagIndex, mode, fullStars, 0, false);
    }

    public static Plan getPlan(Char chr, int bagIndex, Mode mode, boolean fullStars, int starMode, boolean safeguard) {
        for (Plan plan : getPlans(chr, mode, fullStars, starMode, safeguard)) {
            if (plan.equip.getBagIndex() == bagIndex) {
                return plan;
            }
        }
        return null;
    }

    private static Plan plan(Char chr, Equip equip, Mode mode, boolean fullStars, int starMode, boolean safeguard) {
        if (equip.getInfo() == null || equip.hasSpecialAttribute(EquipSpecialAttribute.Vestige)) {
            return null;
        }
        Plan plan = new Plan(equip, mode);
        plan.starCap = fullStars ? GameConstants.getMaxStars(equip) : STAR_CAP;
        plan.starMode = Math.max(0, Math.min(3, starMode));
        plan.safeguard = safeguard;
        Equip sim = equip.deepCopy();
        // Clean Slate: give back the slots earlier scrolls failed
        if (mode == Mode.CLEAN_SLATE) {
            plan.cleanSlates = getFailedSlots(sim);
            if (plan.cleanSlates > 0) {
                plan.traces += (long) CLEAN_SLATE_TRACES * plan.cleanSlates;
                sim.addStat(EquipBaseStat.tuc, plan.cleanSlates);
            }
        }
        // scrolls: the 100% (or, for Best, the strongest) Spell Trace scroll in every free slot
        ScrollUpgradeInfo scroll = mode == Mode.BEST ? getBestScroll(sim) : getLowestScroll(sim);
        if (scroll != null) {
            plan.scroll = scroll;
            plan.scrolls = sim.getTuc();
            plan.traces += (long) scroll.getCost() * plan.scrolls * (mode == Mode.BEST ? GameConstants.AUTO_ENHANCE_BEST_SCROLL_COST_MULTIPLIER : 1);
            sim.addStat(EquipBaseStat.tuc, -plan.scrolls);
        }
        // Star Force, with all slots used (as by hand)
        if (canStarForce(sim) && (sim.getTuc() == 0 || chr.getWorld().isReboot())) {
            int max = Math.min(plan.starCap, GameConstants.getMaxStars(sim));
            while (sim.getChuc() < max) {
                plan.mesos += GameConstants.starForceCost(sim, plan.starMode, plan.safeguard);
                sim.setChuc((short) (sim.getChuc() + 1), false);
                plan.stars++;
            }
        }
        // the convenience costs extra (rounded up)
        plan.traces = applyMarkup(plan.traces, net.swordie.ms.constants.ServerFeatures.getInt(net.swordie.ms.constants.ServerFeatures.Feature.ENHANCE_TRACE_MARKUP));
        plan.mesos = applyMarkup(plan.mesos, getMesoMarkupPercent(chr));
        if (ServerFeatures.on(ServerFeatures.Feature.FREE_ENHANCE)) {
            plan.traces = 0;
            plan.mesos = 0;
        }
        return plan;
    }

    /**
     * @return a copy of the equip as @enhance would leave it (100% Spell Traces in its free slots, Star Forced to its
     * maximum), for comparing gear by what it can become (@upgrades). The equip itself isn't changed.
     */
    public static Equip maxedCopy(Char chr, Equip equip) {
        if (equip == null || equip.getInfo() == null || equip.isCash()) {
            return equip;
        }
        Equip copy = equip.deepCopy();
        Plan plan = plan(chr, copy, Mode.NORMAL, false, 0, false);
        if (plan == null || plan.isEmpty()) {
            return equip;
        }
        if (plan.scroll != null) {
            for (int i = 0; i < plan.scrolls && copy.getTuc() > 0; i++) {
                for (Map.Entry<net.swordie.ms.enums.EnchantStat, Integer> e : plan.scroll.getStats().entrySet()) {
                    copy.addStat(e.getKey().getEquipBaseStat(), e.getValue());
                }
                copy.addStat(EquipBaseStat.tuc, -1);
                copy.addStat(EquipBaseStat.cuc, 1);
            }
        }
        if (plan.stars > 0) {
            copy.setChuc((short) (copy.getChuc() + plan.stars), false);
        }
        copy.recalcEnchantmentStats();
        return copy;
    }

    /**
     * GMS: an item made without upgrade slots can't be Star Forced (badges, most event rings), except the few
     * with their own star limit (Ghost Ship Exorcist, ...).
     */
    static boolean canStarForce(Equip equip) {
        if (!ItemConstants.isUpgradable(equip.getItemId())) {
            return false;
        }
        if (ItemConstants.getItemStarLimit(equip.getItemId()) != -1) {
            return true;
        }
        Equip fresh = net.swordie.ms.loaders.ItemData.getEquipDeepCopy(equip.getItemId(), false);
        return fresh == null || fresh.getTuc() > 0 || equip.getIuc() > 0 || equip.getCuc() > 0;
    }

    /** The Star Force meso markup in %: @optimize's while it runs, else @enhance's; bots keep the old one. */
    public static int getMesoMarkupPercent(Char chr) {
        if (ServerHooks.isAutomated(chr)) {
            return GameConstants.AUTO_ENHANCE_BOT_MESO_MARKUP_PERCENT;
        }
        return AutoGearModule.isOptimizing() ? net.swordie.ms.constants.ServerFeatures.getInt(net.swordie.ms.constants.ServerFeatures.Feature.OPTIMIZE_MESO_MARKUP)
                : net.swordie.ms.constants.ServerFeatures.getInt(net.swordie.ms.constants.ServerFeatures.Feature.ENHANCE_MESO_MARKUP);
    }

    /** A by-hand meso price as @optimize charges it while it runs (unchanged otherwise and for bots). */
    public static long optimizeMesos(Char chr, long mesos) {
        if (!AutoGearModule.isOptimizing() || ServerHooks.isAutomated(chr)) {
            return mesos;
        }
        return applyMarkup(mesos, net.swordie.ms.constants.ServerFeatures.getInt(net.swordie.ms.constants.ServerFeatures.Feature.OPTIMIZE_MESO_MARKUP));
    }

    /** @return the amount with the markup (in %) added, rounded up */
    public static long applyMarkup(long amount, int percent) {
        if (amount <= 0 || percent == 0) {
            return amount;
        }
        return (amount * (100L + percent) + 99) / 100;
    }

    /** @return the upgrade slots earlier scrolls failed, which a Clean Slate Scroll gives back */
    public static int getFailedSlots(Equip equip) {
        Equip fresh = net.swordie.ms.loaders.ItemData.getEquipDeepCopy(equip.getItemId(), false);
        if (fresh == null) {
            return 0;
        }
        int max = fresh.getTuc() + equip.getIuc();
        return Math.max(0, max - equip.getTuc() - equip.getCuc());
    }

    /** @return the strongest Spell Trace scroll for this item (the lowest success chance gives the most stats) */
    private static ScrollUpgradeInfo getBestScroll(Equip equip) {
        if (equip.getTuc() <= 0) {
            return null;
        }
        ScrollUpgradeInfo best = null;
        for (ScrollUpgradeInfo sui : ItemConstants.getScrollUpgradeInfosByEquip(equip, SunnySunday.scrollFeverTime())) {
            if (sui.getType() == SpellTraceScrollType.Normal && (best == null || sui.getChance() < best.getChance())) {
                best = sui;
            }
        }
        return best;
    }

    private static ScrollUpgradeInfo getLowestScroll(Equip equip) {
        if (equip.getTuc() <= 0) {
            return null;
        }
        for (ScrollUpgradeInfo sui : ItemConstants.getScrollUpgradeInfosByEquip(equip, SunnySunday.scrollFeverTime())) {
            if (sui.getType() == SpellTraceScrollType.Normal && sui.getChance() >= 100) {
                return sui;
            }
        }
        return null;
    }

    public static long getTotalTraces(List<Plan> plans) {
        return plans.stream().mapToLong(p -> p.traces).sum();
    }

    public static long getTotalMesos(List<Plan> plans) {
        return plans.stream().mapToLong(p -> p.mesos).sum();
    }

    public static long getSpellTraceCount(Char chr) {
        long n = 0;
        for (Item item : chr.getEtcInventory().getItems()) {
            if (item.getItemId() == ItemConstants.SPELL_TRACE_ID) {
                n += item.getQuantity();
            }
        }
        return n;
    }

    public static String format(long n) {
        return NumberFormat.getNumberInstance(Locale.US).format(n);
    }

    /**
     * Enhances the given equips, if the character can pay for all of them.
     *
     * @return a message for the player
     */
    public static synchronized String enhance(Char chr, List<Plan> plans) {
        // plans may be stale (the player had the window open): make them again
        List<Plan> fresh = new ArrayList<>();
        for (Plan old : plans) {
            Plan p = getPlan(chr, old.equip.getBagIndex(), old.mode, old.starCap > STAR_CAP, old.starMode,
                    old.safeguard);
            if (p != null && p.equip == old.equip) {
                fresh.add(p);
            }
        }
        if (fresh.isEmpty()) {
            return "There is nothing left to enhance.";
        }
        long traces = getTotalTraces(fresh);
        long mesos = getTotalMesos(fresh);
        if (traces > getTotalTraces(plans) || mesos > getTotalMesos(plans)) {
            // e.g. free @enhance was switched off while the window was open: never charge more than was shown
            return "The price has changed. Please check it again in @enhance.";
        }
        if (traces > Integer.MAX_VALUE || (traces > 0 && !chr.hasItemCount(ItemConstants.SPELL_TRACE_ID, (int) traces))) {
            return String.format("You need %s Spell Traces for this.", format(traces));
        }
        if (chr.getMoney() < mesos) {
            return String.format("You need %s mesos for this.", format(mesos));
        }
        // read once for the whole action; bots never take the risk (journey bots rely on @enhance)
        boolean bot = !ServerHooks.paysAsPlayer(chr);
        boolean scrollOdds = !bot && !ServerFeatures.on(ServerFeatures.Feature.SCROLLS_ALWAYS_SUCCEED);
        boolean starOdds = !bot && !ServerFeatures.on(ServerFeatures.Feature.STAR_FORCE_ALWAYS_SUCCEEDS);
        if (traces > 0) {
            chr.consumeItem(ItemConstants.SPELL_TRACE_ID, (int) traces);
        }
        if (mesos > 0 && !starOdds) {
            chr.deductMoney(mesos, ProgressionLog.Sink.STAR_FORCE);
        }
        Result r = new Result();
        for (Plan plan : fresh) {
            Equip equip = plan.equip;
            int attempts = 0; // scrolls tried
            if (plan.cleanSlates > 0) {
                ScrollUpgradeInfo slate = scrollOdds ? getCleanSlateScroll(equip) : null;
                if (scrollOdds && slate == null) {
                    // no Clean Slate to roll for this item: no free restore either; its Spell Traces are paid back
                    r.notes.add("No Clean Slate scroll exists for " + plan.getName() + ", so its failed slots stay as "
                            + "they are.");
                    if (plan.traces > 0) {
                        r.tracesBack += applyMarkup((long) CLEAN_SLATE_TRACES * plan.cleanSlates,
                                net.swordie.ms.constants.ServerFeatures.getInt(net.swordie.ms.constants.ServerFeatures.Feature.ENHANCE_TRACE_MARKUP));
                    }
                }
                for (int i = 0; i < plan.cleanSlates && (slate != null || !scrollOdds); i++) {
                    if (slate == null) {
                        equip.addStat(EquipBaseStat.tuc, 1);
                        r.restored++;
                    } else if (slate.applyTo(equip, chr)) {
                        r.restored++;
                    } else {
                        r.slatesFailed++;
                    }
                }
            }
            ScrollUpgradeInfo scroll = plan.scroll;
            if (scroll != null) {
                for (int i = 0; i < plan.scrolls && equip.getTuc() > 0; i++) {
                    attempts++;
                    if (scrollOdds) {
                        // as by hand: the scroll's chance (plus scroll bonuses), a failure uses the slot
                        if (scroll.applyTo(equip, chr)) {
                            r.scrolls++;
                        } else {
                            r.scrollsFailed++;
                        }
                        continue;
                    }
                    for (Map.Entry<net.swordie.ms.enums.EnchantStat, Integer> e : scroll.getStats().entrySet()) {
                        equip.addStat(e.getKey().getEquipBaseStat(), e.getValue());
                    }
                    equip.addStat(EquipBaseStat.tuc, -1);
                    equip.addStat(EquipBaseStat.cuc, 1);
                    r.scrolls++;
                }
                if (scrollOdds && attempts < plan.scrolls && plan.traces > 0) {
                    // failed Clean Slates left fewer slots: the scrolls not used are paid back
                    long back = applyMarkup((long) scroll.getCost() * (plan.scrolls - attempts)
                            * (plan.mode == Mode.BEST ? GameConstants.AUTO_ENHANCE_BEST_SCROLL_COST_MULTIPLIER : 1),
                            net.swordie.ms.constants.ServerFeatures.getInt(net.swordie.ms.constants.ServerFeatures.Feature.ENHANCE_TRACE_MARKUP));
                    r.tracesBack += Math.min(back, plan.traces);
                }
            }
            if (plan.stars > 0) {
                if (starOdds) {
                    starForce(chr, plan, r);
                } else {
                    equip.setChuc((short) (equip.getChuc() + plan.stars), false);
                    r.stars += plan.stars;
                }
            }
            equip.recalcEnchantmentStats();
            equip.updateToChar(chr);
            Equip other = getZeroOtherWeapon(chr, equip);
            if (other != null) {
                other.copyScrollStatsFrom(equip);
                other.copyAttributesFrom(equip);
                other.copyChucFrom(equip);
                other.recalcEnchantmentStats();
                other.updateToChar(chr);
            }
        }
        chr.initBaseStats();
        long tracesBack = Math.min(r.tracesBack, traces);
        long tracesLost = 0;
        if (tracesBack > 0) {
            if (chr.canHold(ItemConstants.SPELL_TRACE_ID, (int) tracesBack)) {
                chr.addItemToInventory(ItemConstants.SPELL_TRACE_ID, (int) tracesBack);
            } else {
                tracesLost = tracesBack;
                tracesBack = 0;
                r.notes.add(String.format("#r%s Spell Traces couldn't be given back: your Etc inventory is full.#k",
                        format(tracesLost)));
            }
        }
        long mesosSpent = starOdds ? r.mesosSpent : mesos;
        List<String> spent = new ArrayList<>();
        if (traces - tracesBack > 0) {
            spent.add(format(traces - tracesBack) + " Spell Traces");
        }
        if (mesosSpent > 0) {
            spent.add(format(mesosSpent) + " mesos");
        }
        if (!scrollOdds && !starOdds) {
            return String.format("Enhanced %d item%s: %s%d scroll%s used, %d star%s added.%s", fresh.size(),
                    fresh.size() == 1 ? "" : "s",
                    r.restored > 0 ? String.format("%d failed slot%s restored, ", r.restored, r.restored == 1 ? "" : "s") : "",
                    r.scrolls, r.scrolls == 1 ? "" : "s", r.stars, r.stars == 1 ? "" : "s",
                    spent.isEmpty() ? "" : " Spent " + String.join(" and ", spent) + ".");
        }
        List<String> parts = new ArrayList<>();
        if (r.restored + r.slatesFailed > 0) {
            parts.add(String.format("%d of %d Clean Slate%s worked", r.restored, r.restored + r.slatesFailed,
                    r.restored + r.slatesFailed == 1 ? "" : "s"));
        }
        if (r.scrolls + r.scrollsFailed > 0) {
            parts.add(String.format("%d of %d scroll%s worked", r.scrolls, r.scrolls + r.scrollsFailed,
                    r.scrolls + r.scrollsFailed == 1 ? "" : "s"));
        }
        if (r.starAttempts > 0) {
            parts.add(String.format("%d Star Force attempt%s: %d star%s added", r.starAttempts,
                    r.starAttempts == 1 ? "" : "s", r.stars, r.stars == 1 ? "" : "s")
                    + (r.starsLost > 0 ? String.format(", %d lost", r.starsLost) : ""));
        } else if (r.stars > 0) {
            parts.add(String.format("%d star%s added", r.stars, r.stars == 1 ? "" : "s"));
        }
        String msg = String.format("Enhanced %d item%s with the server's real odds: %s.", fresh.size(),
                fresh.size() == 1 ? "" : "s", parts.isEmpty() ? "nothing changed" : String.join("; ", parts));
        if (r.destroyed > 0) {
            msg += String.format(" #r%d item%s destroyed#k (a traced item can be restored in @enhance).", r.destroyed,
                    r.destroyed == 1 ? " was" : "s were");
        }
        if (!r.notes.isEmpty()) {
            msg += " " + String.join(" ", r.notes);
        }
        if (!spent.isEmpty()) {
            msg += " Spent " + String.join(" and ", spent) + ".";
        }
        if (tracesBack > 0) {
            msg += " " + format(tracesBack) + " Spell Traces for scrolls that had no slot left were given back.";
        }
        return msg;
    }

    /** What one @enhance did, for its message. */
    private static final class Result {
        int restored, slatesFailed, scrolls, scrollsFailed, stars, starsLost, starAttempts, destroyed;
        long tracesBack, mesosSpent;
        final List<String> notes = new ArrayList<>();
    }

    /** @return the Clean Slate Spell Trace the Spell Trace window offers for this equip, or null */
    private static ScrollUpgradeInfo getCleanSlateScroll(Equip equip) {
        for (ScrollUpgradeInfo sui : ItemConstants.getScrollUpgradeInfosByEquip(equip, SunnySunday.scrollFeverTime())) {
            if (sui.getType() == SpellTraceScrollType.CleanSlate) {
                return sui;
            }
        }
        return null;
    }

    /**
     * Star Force with the official odds: one attempt per star the plan shows (it can't know how many a player would
     * need), each paid as it's made at the price the plan used, so never more than shown. Stops at the maximum, when
     * the item is destroyed, or when an attempt that could destroy it can't be made safely (no free Equip slot, as
     * in the Star Force window).
     */
    private static void starForce(Char chr, Plan plan, Result r) {
        Equip equip = plan.equip;
        if (!canStarForce(equip)
                || (equip.getTuc() > 0 && !chr.getWorld().isReboot())) {
            return; // a slot is still free (e.g. an Upgrade Salvation kept one): Star Force needs every slot used
        }
        // the markup is applied to the running total of the by-hand prices (as the plan applied it to their sum), so
        // rounding per attempt can't charge more than shown or stop an attempt the price covered
        long rawSpent = 0;
        long charged = 0;
        int markup = getMesoMarkupPercent(chr);
        int max = Math.min(plan.starCap, GameConstants.getMaxStars(equip));
        for (int i = 0; i < plan.stars && equip.getChuc() < max; i++) {
            long raw = plan.mesos == 0 ? 0 : GameConstants.starForceCost(equip, plan.starMode, plan.safeguard);
            long total = raw == 0 ? charged : applyMarkup(rawSpent + raw, markup);
            long cost = total - charged;
            if (total > plan.mesos) {
                break; // the attempts left cost more than the price shown covers
            }
            if (chr.getMoney() < cost) {
                r.notes.add("Star Force stopped: not enough mesos for the next attempt.");
                break;
            }
            if (GameConstants.starForceChances(equip, plan.starMode, plan.safeguard, false)[1] > 0
                    && chr.getEquipInventory().getEmptySlots() == 0) {
                r.notes.add("Star Force stopped: make a free slot in your Equip inventory in case an item gets "
                        + "destroyed.");
                break;
            }
            if (cost > 0) {
                chr.deductMoney(cost, ProgressionLog.Sink.STAR_FORCE);
                r.mesosSpent += cost;
            }
            rawSpent += raw;
            charged = total;
            r.starAttempts++;
            int result = starForceAttempt(equip, plan.starMode, plan.safeguard);
            if (result > 0) {
                r.stars++;
            } else if (result == STAR_DESTROYED) {
                r.destroyed++;
                break;
            } else if (result == STAR_DROPPED) {
                r.starsLost++;
            }
        }
    }

    private static final int STAR_KEPT = 0, STAR_DROPPED = -1, STAR_DESTROYED = -2;

    /**
     * One Star Force attempt with the Star Force window's odds for this slider setting and Safeguard
     * ({@link GameConstants#starForceChances} rolled by {@link GameConstants#rollStarForce}: success x1.05, destroy as
     * a share of the failures), and the drop rule of {@link GameConstants#canStarForceDrop}.
     *
     * @return 1 for a star added, {@link #STAR_KEPT}, {@link #STAR_DROPPED} or {@link #STAR_DESTROYED}
     */
    private static int starForceAttempt(Equip equip, int starMode, boolean safeguard) {
        int roll = GameConstants.rollStarForce(GameConstants.starForceChances(equip, starMode, safeguard, false),
                true); // the window's rate, as in the Star Force window
        boolean canDegrade = GameConstants.canStarForceDrop(equip);
        if (roll == 1) {
            equip.setChuc((short) (equip.getChuc() + 1), false);
            equip.setDropStreak(0);
            return 1;
        }
        if (roll == 2) {
            equip.setChuc((short) GameConstants.getStarForceRecoveryStars(equip, equip.getChuc()), false);
            if (!ItemConstants.isLongOrBigSword(equip.getItemId())) { // Zero's weapons lose their stars only
                equip.makeVestige();
            }
            return STAR_DESTROYED;
        }
        if (canDegrade) {
            equip.setChuc((short) (equip.getChuc() - 1), false);
            equip.setDropStreak(equip.getDropStreak() + 1);
            return STAR_DROPPED;
        }
        return STAR_KEPT;
    }

    /**
     * @return a line for @enhance's menu about its odds for this character (autoEnhance.py): whether scrolls and Star
     * Force can fail, and when a failed attempt loses a star - asked of {@link GameConstants#canStarForceDrop}, so it
     * follows whichever Star Force table is in use
     */
    public static String getOddsText(Char chr) {
        boolean bot = !ServerHooks.paysAsPlayer(chr);
        boolean scrollOdds = !bot && !ServerFeatures.on(ServerFeatures.Feature.SCROLLS_ALWAYS_SUCCEED);
        boolean starOdds = !bot && !ServerFeatures.on(ServerFeatures.Feature.STAR_FORCE_ALWAYS_SUCCEEDS);
        if (!scrollOdds && !starOdds) {
            return "Scrolls and Star Force never fail.";
        }
        String stars = "#rStar Force uses its real odds:#k one attempt per star shown; an attempt can fail"
                + getDropText() + " or destroy the item.";
        if (!starOdds) {
            return "#rScrolls use their real odds#k (they can fail); Star Force never fails.";
        }
        return (scrollOdds ? "#rScrolls use their real odds#k (they can fail). " : "Scrolls never fail. ") + stars;
    }

    /** Whether @enhance's Star Force can fail or destroy for this character (else the slider and Safeguard only cost). */
    public static boolean starForceCanFail(Char chr) {
        return ServerHooks.paysAsPlayer(chr) && !ServerFeatures.on(ServerFeatures.Feature.STAR_FORCE_ALWAYS_SUCCEEDS);
    }

    /** @return how failed attempts lose stars under the Star Force table in use, as a clause (", ...") or "" */
    private static String getDropText() {
        Equip probe = net.swordie.ms.loaders.ItemData.getEquipDeepCopy(DROP_PROBE_ITEM, false);
        if (probe == null || probe.getInfo() == null || probe.getInfo().isSuperiorEqp()) {
            return ", lose a star on some items";
        }
        int from = -1;
        List<String> safe = new ArrayList<>();
        for (int star = 1; star < 25; star++) {
            if (dropsAt(probe, star)) {
                if (from < 0) {
                    from = star;
                }
            } else if (from >= 0) {
                safe.add(String.valueOf(star)); // a safe point after drops started (e.g. 15 and 20)
            }
        }
        if (from < 0) {
            return " (only superior items such as Tyrant gear lose a star when it does)";
        }
        return ", lose a star from " + from + " stars on" + (safe.isEmpty() ? ""
                : " (not at " + String.join(" or ", safe) + ")");
    }

    /** an ordinary weapon (Sword), only used to ask the drop rule */
    private static final int DROP_PROBE_ITEM = 1302000;

    private static boolean dropsAt(Equip probe, int stars) {
        probe.setChuc((short) stars, false);
        return GameConstants.canStarForceDrop(probe);
    }

    // Zero's two weapons (slots 10 and 11) share their upgrades: only the main one (11) is enhanced, then copied

    private static boolean isZeroSecondWeapon(Char chr, Equip equip) {
        return JobConstants.isZero(chr.getJob()) && ItemConstants.isLongOrBigSword(equip.getItemId())
                && equip.getBagIndex() == 10;
    }

    private static Equip getZeroOtherWeapon(Char chr, Equip equip) {
        if (!JobConstants.isZero(chr.getJob()) || !ItemConstants.isLongOrBigSword(equip.getItemId())) {
            return null;
        }
        Item other = chr.getEquippedInventory().getItemBySlot(equip.getBagIndex() == 10 ? 11 : 10);
        return other instanceof Equip ? (Equip) other : null;
    }

    // Potential and bonus potential ----------------------------------------------------------------------------------

    /**
     * Mesos @enhance charges to add a potential: what Fredrick sells an Epic Potential Scroll 100% for (10,000 NX at
     * 100 mesos per NX). The potential is Epic with three lines.
     */
    public static final long POTENTIAL_MESOS = 1_000_000L;
    /**
     * Mesos @enhance charges to add a bonus potential: what Fredrick sells a Bonus Potential Scroll for (Ari's old
     * 150,000 NX price at 100 mesos per NX). Unlike the scroll it never fails, and the third line is guaranteed (like
     * a Special Bonus Potential Scroll).
     */
    public static final long BONUS_POTENTIAL_MESOS = 15_000_000L;

    public static long getPotentialMesos(boolean bonus) {
        return ServerFeatures.on(ServerFeatures.Feature.FREE_ENHANCE) ? 0
                : bonus ? BONUS_POTENTIAL_MESOS : POTENTIAL_MESOS;
    }

    /** @return the equips (worn first, then the Equip inventory) that can get a (bonus) potential and have none yet */
    public static List<Equip> getPotentialCandidates(Char chr, boolean bonus) {
        List<Equip> list = new ArrayList<>();
        // worn items only
        List<Item> items = new ArrayList<>(chr.getEquippedInventory().getItems());
        items.sort(Comparator.comparingInt(Item::getBagIndex));
        for (Item item : items) {
            if (item instanceof Equip equip && !equip.isCash() && !isZeroSecondWeapon(chr, equip)
                    && ItemConstants.canEquipHavePotential(equip) && equip.getOption(0, bonus) == 0) {
                list.add(equip);
            }
        }
        return list;
    }

    public static List<Equip> getBonusPotentialCandidates(Char chr) {
        return getPotentialCandidates(chr, true);
    }

    /**
     * Gives an equip a potential (Epic, 3 lines) or a bonus potential (Rare, 3 lines), revealed, for
     * {@link #getPotentialMesos} mesos. Never fails.
     */
    // ------------------------------------------------------------------------------------------------ flames
    // Powerful flames: what Fredrick sold, at his prices (NX price x 100 mesos). The better flame is the
    // Enhance window's meso flame (Reset) when the server has it on, at its price and rolling like its flame; else
    // Fredrick's Eternal Rebirth Flame.

    /** A Powerful Rebirth Flame's roll (random tiers), or an Eternal one's (the higher tiers). */
    public static final long POWERFUL_FLAME_MESOS = 2_000_000L, ETERNAL_FLAME_MESOS = 6_000_000L;

    /** @return the flame the window's meso reset rolls like, or 0 when the server has it off */
    public static int getMesoFlameItemId() {
        return ItemUpgradeHandler.mesoFlameItemId();
    }

    public static long getFlameMesos(boolean eternal) {
        if (!eternal) {
            return POWERFUL_FLAME_MESOS;
        }
        return getMesoFlameItemId() != 0 ? ItemUpgradeHandler.MESO_FLAME_COST : ETERNAL_FLAME_MESOS;
    }

    /** Whether the better flame rolls the higher tiers (the meso flame follows its flame's createType, as by hand). */
    private static boolean betterFlameRollsHigh() {
        int id = getMesoFlameItemId();
        var info = id == 0 ? null : net.swordie.ms.loaders.ItemData.getItemInfoByID(id);
        return info == null || info.getScrollStats().getOrDefault(net.swordie.ms.enums.ScrollStat.createType, 6) >= 7;
    }

    /** @return the flame's bonus stats on one short line ("none" without any) */
    public static String describeFlame(Equip e) {
        List<String> parts = new ArrayList<>();
        addFlamePart(parts, "STR", e.getfSTR());
        addFlamePart(parts, "DEX", e.getfDEX());
        addFlamePart(parts, "INT", e.getfINT());
        addFlamePart(parts, "LUK", e.getfLUK());
        addFlamePart(parts, "All Stat %", e.getfAllStat());
        addFlamePart(parts, "ATT", e.getfATT());
        addFlamePart(parts, "M.ATT", e.getfMATT());
        addFlamePart(parts, "Boss %", e.getfBoss());
        addFlamePart(parts, "Damage %", e.getfDamage());
        addFlamePart(parts, "HP", e.getfHP());
        addFlamePart(parts, "MP", e.getfMP());
        addFlamePart(parts, "DEF", e.getfDEF());
        addFlamePart(parts, "Speed", e.getfSpeed());
        addFlamePart(parts, "Jump", e.getfJump());
        if (e.getfLevel() > 0) {
            parts.add("Level -" + e.getfLevel());
        }
        return parts.isEmpty() ? "none" : String.join(", ", parts);
    }

    private static void addFlamePart(List<String> parts, String name, int value) {
        if (value > 0) {
            parts.add(name + " +" + value);
        }
    }

    /** @return why the meso flame can't be used on this item now, or null when it can */
    public static String mesoFlameProblem(Char chr, Equip equip, int count) {
        if (!canFlame(equip)) {
            return "That item can't have bonus stats (flames).";
        }
        if (getMesoFlameItemId() == 0) {
            return "The meso flame is off on this server.";
        }
        long cost = optimizeMesos(chr, getFlameMesos(true) * (count == 3 ? 3 : 1));
        return chr.getMoney() < cost ? "You need " + format(cost) + " mesos for that." : null;
    }

    /**
     * The window's meso flame with its keep/replace choice: charges count x the price and rolls that many results on
     * copies (the item keeps its flame until {@link #keepFlame}). Count 1, or 3 for Reset x3.
     *
     * @return the results, or null when it can't be done ({@link #mesoFlameProblem})
     */
    public static List<Equip> rollMesoFlames(Char chr, Equip equip, int count) {
        if (mesoFlameProblem(chr, equip, count) != null) {
            return null;
        }
        count = count == 3 ? 3 : 1;
        long cost = optimizeMesos(chr, getFlameMesos(true) * count);
        chr.deductMoney(cost, ProgressionLog.Sink.OTHER);
        boolean high = betterFlameRollsHigh();
        List<Equip> results = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Equip copy = equip.deepCopy();
            copy.randomizeFlameStats(high);
            results.add(copy);
        }
        return results;
    }

    /** Puts a rolled flame on the item (and Zero's other weapon) and refreshes the stats it gives. */
    public static void keepFlame(Char chr, Equip equip, Equip from) {
        equip.copyFlameStatsFrom(from);
        equip.updateToChar(chr);
        Equip other = getZeroOtherWeapon(chr, equip);
        if (other != null) {
            other.copyFlameStatsFrom(equip);
            other.updateToChar(chr);
        }
        if (chr.getEquippedInventory().getItems().contains(equip)) {
            chr.getEquippedInventory().recalcBaseStats(chr);
            chr.initBaseStats();
        }
    }

    public static boolean canFlame(Equip equip) {
        return equip != null && ItemConstants.canEquipHaveFlame(equip);
    }

    /** Rerolls the item's bonus stats (flames) as a Rebirth Flame does; never fails. */
    public static String flame(Char chr, Equip equip, boolean eternal) {
        if (!canFlame(equip)) {
            return "That item can't have bonus stats (flames).";
        }
        long cost = optimizeMesos(chr, getFlameMesos(eternal));
        if (chr.getMoney() < cost) {
            return "You need " + format(cost) + " mesos for that.";
        }
        chr.deductMoney(cost, ProgressionLog.Sink.OTHER);
        equip.randomizeFlameStats(eternal && betterFlameRollsHigh());
        equip.updateToChar(chr);
        Equip other = getZeroOtherWeapon(chr, equip);
        if (other != null) {
            other.copyFlameStatsFrom(equip);
            other.updateToChar(chr);
        }
        if (chr.getEquippedInventory().getItems().contains(equip)) {
            chr.getEquippedInventory().recalcBaseStats(chr);
            chr.initBaseStats();
        }
        return "Rerolled the bonus stats of #v" + equip.getItemId() + "# #t" + equip.getItemId() + "# with "
                + (!eternal ? "a Powerful Rebirth Flame" : getMesoFlameItemId() != 0 ? "a meso flame"
                : "an Eternal Rebirth Flame") + " for " + format(cost) + " mesos.";
    }

    public static String addPotential(Char chr, Equip equip, boolean bonus) {
        String what = bonus ? "bonus potential" : "potential";
        if (equip == null || !ItemConstants.canEquipHavePotential(equip)) {
            return "That item can't have a " + what + ".";
        }
        if (equip.getOption(0, bonus) != 0) {
            return "That item already has a " + what + ". Cube it in @enhance to change it.";
        }
        long cost = getPotentialMesos(bonus);
        if (chr.getMoney() < cost) {
            return "You need " + format(cost) + " mesos for that.";
        }
        if (cost > 0) {
            chr.deductMoney(cost, ProgressionLog.Sink.CUBES);
        }
        boolean worn = chr.getEquippedInventory().getItems().contains(equip);
        if (worn && !bonus) {
            equip.updateDecentSkills(chr, false);
        }
        if (bonus) {
            equip.setHiddenOptionBonus(net.swordie.ms.enums.ItemGrade.HiddenRare.getVal(), 100);
        } else {
            equip.setHiddenOptionBase(net.swordie.ms.enums.ItemGrade.HiddenEpic.getVal(), 100);
        }
        equip.releaseOptions(bonus);
        equip.updateToChar(chr);
        Equip other = getZeroOtherWeapon(chr, equip);
        if (other != null) {
            other.copyItemOptionsFrom(equip);
            other.updateToChar(chr);
        }
        if (worn) {
            if (!bonus) {
                equip.updateDecentSkills(chr, true);
            }
            chr.getEquippedInventory().recalcBaseStats(chr);
            chr.initBaseStats();
        }
        return "Added a " + what + " to #v" + equip.getItemId() + "# #t" + equip.getItemId() + "#"
                + (cost > 0 ? " for " + format(cost) + " mesos." : ".");
    }

    public static String addBonusPotential(Char chr, Equip equip) {
        return addPotential(chr, equip, true);
    }
}
