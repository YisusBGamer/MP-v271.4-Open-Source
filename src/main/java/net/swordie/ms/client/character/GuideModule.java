package net.swordie.ms.client.character;

import net.swordie.ms.client.Account;
import net.swordie.ms.client.character.items.Equip;
import net.swordie.ms.client.character.items.Item;
import net.swordie.ms.client.character.modules.LinkSkillsModule;
import net.swordie.ms.constants.GameConstants;
import net.swordie.ms.constants.ItemConstants;
import net.swordie.ms.constants.QuestConstants;
import net.swordie.ms.constants.SkillConstants;
import net.swordie.ms.enums.Stat;

import java.util.ArrayList;
import java.util.List;

/**
 * @menu > Gear > How do I get stronger? (scripts/npc/guide.py): looks at a character and lists what would raise its
 * boss damage the most, most worthwhile first. The gear guide covers the empty equipment slots and who drops items for
 * them; this covers everything else (enhancing, potentials, flames, character systems, buffs) and the gear pieces that
 * come from shops and quests instead of bosses (Android hearts, pocket items, badges, medals, emblems, sets).
 * <p>
 * Every tip names where to go on this server.
 */
public final class GuideModule {

    private GuideModule() {
    }

    /** One piece of advice: a short title and what to do. */
    public static final class Tip {
        public final String section;
        public final String title;
        public final String text;

        Tip(String section, String title, String text) {
            this.section = section;
            this.title = title;
            this.text = text;
        }

        public String getSection() {
            return section;
        }

        public String getTitle() {
            return title;
        }

        public String getText() {
            return text;
        }
    }

    public static final String QUICK = "Quick wins";
    public static final String GEAR = "Your gear";
    public static final String CHARACTER = "Character systems";
    public static final String BUFFS = "Before a boss";

    /** @return the tips for this character, most worthwhile first within each section */
    public static List<Tip> getTips(Char chr) {
        List<Tip> tips = new ArrayList<>();
        int level = chr.getLevel();
        var qm = chr.getQuestManager();
        boolean fifthJob = qm != null && qm.hasQuestCompleted(QuestConstants.FIFTH_JOB_QUEST);

        // ------------------------------------------------------------------------------------- quick wins
        int ap = chr.getStat(Stat.ap);
        if (ap > 0) {
            tips.add(new Tip(QUICK, ap + " unspent ability points",
                    "Put them in your main stat: #b@str/@dex/@int/@luk <amount>#k (a minus amount takes points back)."));
        }
        if (level >= 140) {
            int hyperStat = net.swordie.ms.client.character.hyperstats.HyperStatsModule.getTotalHyperStatSp(chr)
                    - chr.getSpentHyperSp(); // includes a reborn character's points from earlier lives
            int passive = Math.max(0, SkillConstants.getTotalPassiveHyperSpByLevel((short) level)
                    - chr.getSpentPassiveHyperSkillSp());
            int active = Math.max(0, SkillConstants.getTotalActiveHyperSpByLevel((short) level)
                    - chr.getSpentActiveHyperSkillSp());
            if (hyperStat > 0) {
                tips.add(new Tip(QUICK, hyperStat + " unspent Hyper Stat points",
                        "Stat window > Hyper Stat. For bosses: Boss Damage, Ignore DEF, Damage, Critical Damage."));
            }
            if (passive + active > 0) {
                tips.add(new Tip(QUICK, (passive + active) + " unspent Hyper Skill points",
                        "Skill window > Hyper Skill tab."));
            }
        }
        if (level >= 200 && !fifthJob) {
            tips.add(new Tip(QUICK, "5th job",
                    "Talk to the Memory Keeper in the Temple of Time: V Matrix skills and Arcane Symbols."));
        }
        if (fifthJob && chr.getAvailableMatrixPoints() > 0) {
            tips.add(new Tip(QUICK, chr.getAvailableMatrixPoints() + " unspent Matrix points",
                    "V Matrix window: put them into your best nodes' slots."));
        }
        if (level >= GameConstants.REBIRTH_MIN_LEVEL) {
            tips.add(new Tip(QUICK, "Rebirth",
                    "@menu > My character > Rebirth: back to level 1 with bonus AP, keeping your skills."));
        }

        // ------------------------------------------------------------------------------------------ gear
        List<String> missing = new ArrayList<>();
        for (GearGuideModule.Category cat : GearGuideModule.getCategories()) {
            int have = GearGuideModule.countEquipped(chr, cat);
            if (have < cat.getSlots()) {
                missing.add(cat.getTitle() + (cat.getSlots() > 1 ? " (" + have + "/" + cat.getSlots() + ")" : ""));
            }
        }
        if (!missing.isEmpty()) {
            tips.add(new Tip(GEAR, "Empty equipment slots: " + missing.size(),
                    String.join(", ", missing) + ". #b@gear#k shows who drops them; #b@upgrades#k puts on better gear "
                            + "you carry."));
        }

        int stars = 0;
        int scrolls = 0;
        int failed = 0;
        for (AutoEnhanceModule.Plan plan : AutoEnhanceModule.getPlans(chr)) {
            stars += plan.stars;
            scrolls += plan.scrolls;
        }
        int noPotential = 0;
        int notLegendary = 0;
        int noBonus = 0;
        int noFlame = 0;
        boolean android = false;
        boolean heart = false;
        boolean pocket = false;
        boolean badge = false;
        boolean medal = false;
        boolean emblem = false;
        Equip weapon = null;
        for (Item item : chr.getEquippedInventory().getItems()) {
            if (!(item instanceof Equip equip) || item.getBagIndex() >= 100 && !ItemConstants.isAndroid(item.getItemId())
                    && !ItemConstants.isTotem(item.getItemId())) {
                continue;
            }
            int id = equip.getItemId();
            android |= ItemConstants.isAndroid(id);
            heart |= ItemConstants.isMechanicalHeart(id);
            pocket |= id / 10000 == 116;
            badge |= id / 10000 == 118;
            medal |= id / 10000 == 114;
            emblem |= ItemConstants.isEmblem(id);
            if (equip.isCash()) {
                continue;
            }
            if (ItemConstants.isWeapon(id) && !ItemConstants.isSecondary(id) && weapon == null) {
                weapon = equip;
            }
            failed += AutoEnhanceModule.getFailedSlots(equip);
            if (ItemConstants.canEquipHavePotential(equip)) {
                if (equip.getBaseGrade() < 17) {
                    noPotential++;
                } else if (equip.getBaseGrade() < 20) {
                    notLegendary++;
                }
                if (equip.getBonusGrade() < 17) {
                    noBonus++;
                }
            }
            if (ItemConstants.canEquipHaveFlame(equip) && !hasFlame(equip)) {
                noFlame++;
            }
        }
        if (scrolls + stars + failed > 0) {
            tips.add(new Tip(GEAR, "Scrolls and stars to add",
                    String.format("%d free slot%s, %d star%s%s. #b@enhance#k does them all at once.", scrolls,
                            scrolls == 1 ? "" : "s", stars, stars == 1 ? "" : "s",
                            failed > 0 ? ", " + failed + " failed slot" + (failed == 1 ? "" : "s")
                                    + " (Clean Slate in @enhance)" : "")));
        }
        if (noPotential + notLegendary + noBonus > 0) {
            List<String> parts = new ArrayList<>();
            if (noPotential > 0) {
                parts.add(noPotential + " without potential");
            }
            if (notLegendary > 0) {
                parts.add(notLegendary + " not Legendary");
            }
            if (noBonus > 0) {
                parts.add(noBonus + " without bonus potential");
            }
            tips.add(new Tip(GEAR, "Potentials: " + String.join(", ", parts),
                    "#b@enhance#k adds potentials for mesos (never fails) and cubes worn items up to a goal with "
                            + "Red/Bonus Cubes or NX. Weapon, secondary and emblem: Boss Damage, Ignore DEF, ATT %."));
        }
        if (noFlame > 0) {
            tips.add(new Tip(GEAR, noFlame + " item" + (noFlame == 1 ? "" : "s") + " without flames"
                            + (weapon != null && !hasFlame(weapon) ? " (your weapon too)" : ""),
                    "#b@enhance#k rerolls flames for mesos. On a weapon they can roll Boss Damage, Damage % and ATT."));
        }
        if (!pocket) {
            int charm = GameConstants.getTraitLevel(chr.getStat(Stat.charmEXP));
            tips.add(new Tip(GEAR, "No pocket item",
                    "Nave (Henesys Market) sells them for 50,000 mesos. The slot opens at Charm 30"
                            + (charm < 30 ? " (yours: " + charm + "): every boss clear gives 2,000 Charm EXP, about "
                            + "six clears." : ".")));
        }
        List<String> small = new ArrayList<>();
        if (!badge) {
            small.add("badge");
        }
        if (!medal) {
            small.add("medal");
        }
        if (!small.isEmpty()) {
            tips.add(new Tip(GEAR, "Missing: " + String.join(", ", small),
                    "Laku in Monster Park trades Spiegelmann's Badge Chest for 30 Monster Park coins; medals come "
                            + "from quests."));
        }
        if (!emblem && level >= 100) {
            tips.add(new Tip(GEAR, "No emblem",
                    "Shiro (Free Market) sells class emblems for 10,000,000 mesos. They take weapon potentials."));
        }
        if (!heart || !android) {
            tips.add(new Tip(GEAR, !heart && !android ? "No Android or Android Heart"
                            : !heart ? "No Android Heart" : "No Android",
                    "@menu > Shops > Android Shop sells both (a heart's grade can't be above the android's). Better "
                            + "androids come from Hard Damien, Hard Lucid and Chaos Papulatus."));
        }
        if (level >= 140) {
            tips.add(new Tip(GEAR, "Sets without bosses",
                    "Commerci gear and Sweetwater accessories: trade voyages in San Commerci (Maestra Fiametta), "
                            + "Commerci Denaro to Javert."));
        }
        if (level >= 120) {
            Item w = weapon;
            boolean enchanted = chr.hasSoulWeapon();
            boolean hasSoul = enchanted && w instanceof Equip we && we.getSoulOptionId() > 0;
            if (!enchanted || !hasSoul) {
                tips.add(new Tip(GEAR, !enchanted ? "Soul weapon" : "Your soul weapon has no soul",
                        (!enchanted ? "A Soul Enchanter (bosses drop them; Lao in Mu Lung Dojo sells one) opens your "
                                + "weapon to a soul. " : "")
                                + "Bosses drop Soul Shards (30% a clear): 10 make that boss's soul, with a bonus "
                                + "potential, a skill and up to +20 ATT in fights."));
            }
        }

        // ------------------------------------------------------------------------- character systems
        if (fifthJob) {
            int symbols = 0;
            int maxed = 0;
            for (Item item : chr.getEquippedInventory().getItems()) {
                if (item instanceof Equip e && ItemConstants.isArcaneSymbol(e.getItemId())) {
                    symbols++;
                    if (e.getSymbolLevel() >= ItemConstants.MAX_ARCANE_SYMBOL_LEVEL) {
                        maxed++;
                    }
                }
            }
            int unlocked = level >= 235 ? 6 : level >= 230 ? 5 : level >= 225 ? 4 : level >= 220 ? 3 : level >= 210 ? 2 : 1;
            if (symbols < unlocked || maxed < symbols) {
                tips.add(new Tip(CHARACTER, "Arcane Symbols: " + symbols + " of " + unlocked + ", " + maxed
                                + " maxed",
                        "Arcane River monsters and dailies give them; a new one merges into the one you wear. Arcane "
                                + "Force (" + chr.getTotalAf() + ") decides your damage in Arcane River and at Lucid, "
                                + "Will and later bosses."));
            }
        }
        if (level >= 260) {
            int sacred = 0;
            int sacredMaxed = 0;
            for (Item item : chr.getEquippedInventory().getItems()) {
                if (item instanceof Equip e && ItemConstants.isAuthenticForceSymbol(e.getItemId())) {
                    sacred++;
                    if (e.getSymbolLevel() >= ItemConstants.MAX_AUTH_SYMBOL_LEVEL) {
                        sacredMaxed++;
                    }
                }
            }
            // Cernium 260, Arcus 265, Odium 270, Shangri-La 275, Arteria 280, Carcion 285
            int open = Math.min(6, (level - 260) / 5 + 1);
            if (sacred == 0) {
                tips.add(new Tip(CHARACTER, "Sacred Symbols",
                        "At 260 the Cernium story starts by itself (#b@go Cernium#k); it ends with Sacred Symbol: "
                                + "Cernium. Grandis monsters drop more (as often as Arcane Symbols) and the Flame "
                                + "Priest's Cernium Research dailies level it up."));
            } else if (sacred < open || sacredMaxed < sacred) {
                tips.add(new Tip(CHARACTER, "Sacred Symbols: " + sacred + " of " + open + ", " + sacredMaxed
                                + " maxed",
                        "Each Grandis area (Cernium, Arcus, Odium, Shangri-La, Arteria, Carcion) has its own symbol "
                                + "from its monsters and dailies. Authentic Force (" + chr.getTotalStat(
                                net.swordie.ms.enums.BaseStat.authForce) + ") decides your damage there: "
                                + "#b@next#k says what each hunting map asks for."));
            }
        }
        if (net.swordie.ms.client.character.hexa.HexaModule.hasSixthJob(chr)) {
            int solErda = net.swordie.ms.client.character.hexa.HexaModule.getSolErda(chr);
            if (solErda > 0) {
                tips.add(new Tip(CHARACTER, "HEXA Matrix: " + solErda + " Sol Erda to spend",
                        "Skill window > HEXA Matrix: your Origin skill first, then the mastery and boost nodes. "
                                + "Grandis monsters drop Sol Erda energy once you have the 6th job."));
            }
        }
        if (qm != null && !qm.hasQuestCompleted(QuestConstants.UNION_START_QUEST) && level >= 60) {
            tips.add(new Tip(CHARACTER, "Legion isn't started",
                    "Dame Appropriation (most towns) once your characters add up to 500 levels: Legion's Might and "
                            + "the Legion shop."));
        } else if (chr.getUnion() != null && chr.getUnion().getUnionRank() > 0) {
            tips.add(new Tip(CHARACTER, "Legion: " + chr.getUnion().getUnionRankName(),
                    "Level more characters for more board spots; take the Boss Damage and Ignore DEF areas."));
        }
        Account acc = chr.getAccount();
        if (acc != null) {
            int available = LinkSkillsModule.unappliedCount(chr);
            if (available > 0) {
                tips.add(new Tip(CHARACTER, available + " link skill" + (available == 1 ? "" : "s")
                                + " not equipped",
                        "Skill window > Link Skill tab."));
            } else if (LinkSkillsModule.appliedLinks(chr).isEmpty()) {
                tips.add(new Tip(CHARACTER, "No link skills",
                        "Level other classes on this account (70, 120, 210 raise the link): Demon Avenger, Phantom, "
                                + "Kaiser and Cadena give damage."));
            }
        }
        if (level >= LevelUnlockModule.INNER_ABILITY_LEVEL && chr.getPotentials().size() < 3) {
            tips.add(new Tip(CHARACTER, "Inner Ability lines: " + chr.getPotentials().size() + " of 3",
                    "All three open by themselves at level " + LevelUnlockModule.INNER_ABILITY_LEVEL
                            + "; log in again if one is missing."));
        } else if (level >= LevelUnlockModule.INNER_ABILITY_LEVEL) {
            tips.add(new Tip(CHARACTER, String.format("Inner Ability (%,d Honor EXP)", chr.getHonorExp()),
                    "Character window > Ability: reroll for Boss Damage, Damage or ATT. Medals of Honor turn into "
                            + "Honor EXP when picked up: monsters drop them, and bosses drop Boss Medals (1,000) and "
                            + "Large Boss Medals (5,000)."));
        }
        var fcm = chr.getFamiliarCodexManager();
        if (fcm != null && !fcm.isFamiliarsSummoned()) {
            tips.add(new Tip(CHARACTER, "Familiars aren't summoned",
                    "Cards go into the Familiar Codex when picked up; summon familiars for their Boss Damage lines."));
        }
        if (chr.getGuild() == null) {
            tips.add(new Tip(CHARACTER, "No guild", "Guild skills include boss damage buffs."));
        }

        // ----------------------------------------------------------------------------------- buffs
        tips.add(new Tip(BUFFS, "Buffs to bring",
                "Sparkling Red (+20% Boss Damage) and Blue (+20% Ignore DEF) Star Potions from bosses, Extreme "
                        + "potions (Laku, Monster Park), Onyx Apples (@menu > Shops > Regular Shop) and Legion's Might."
                        + (chr.getPets().isEmpty() ? " A pet with Auto Buff (@menu > Shops > Pets) keeps them up." : "")
                        + " Check your odds in #b@menu#k > Bosses."));
        return tips;
    }

    private static boolean hasFlame(Equip e) {
        return e.getfSTR() != 0 || e.getfDEX() != 0 || e.getfINT() != 0 || e.getfLUK() != 0 || e.getfATT() != 0
                || e.getfMATT() != 0 || e.getfAllStat() != 0 || e.getfBoss() != 0 || e.getfDamage() != 0
                || e.getfHP() != 0;
    }
}
