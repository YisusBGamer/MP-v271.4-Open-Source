package net.swordie.ms.client.character.items;

import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.JobPathModule;
import net.swordie.ms.constants.ItemConstants;
import net.swordie.ms.constants.JobConstants;
import net.swordie.ms.enums.WeaponType;
import net.swordie.ms.loaders.DataTable;
import net.swordie.ms.loaders.ItemData;

import java.util.*;
import java.util.function.Supplier;

import static net.swordie.ms.enums.WeaponType.*;

/**
 * Decides whether a dropped equip is one a character can use: its gender, the equip's required job, and for weapons
 * and secondary weapons whether the character's class can fight with it. Throwing stars, arrows and bullets only drop
 * for classes whose weapon fires them (claws, bows, crossbows and guns) (a Bowmaster can wear a crossbow, but none of
 * its skills work with one). Level and stat requirements are not checked, so an equip a character will grow into
 * still drops. Used by {@link net.swordie.ms.life.mob.Mob#dropDrops} when
 * {@link net.swordie.ms.constants.GameConstants#EQUIP_DROPS_FOR_KILLER_ONLY} is on.
 * <p>
 * The class is the one the character is heading for: a character that chose its path with @job counts as its
 * path's class from the 1st job on (an Archer heading for Marksman gets crossbows, not bows). A 1st job explorer
 * without a path counts as every class of its branch.
 */
public class EquipDropFilter {

    // required-job flags of Item.wz (reqJob)
    private static final int REQ_WARRIOR = 1;
    private static final int REQ_MAGICIAN = 1 << 1;
    private static final int REQ_BOWMAN = 1 << 2;
    private static final int REQ_THIEF = 1 << 3;
    private static final int REQ_PIRATE = 1 << 4;

    private enum Cls {
        HERO, PALADIN, DARK_KNIGHT, FP, IL, BISHOP, BOWMASTER, MARKSMAN, PATHFINDER, NIGHT_LORD, SHADOWER, DUAL_BLADE,
        BUCCANEER, CORSAIR, CANNONEER, JETT,
        DAWN_WARRIOR, BLAZE_WIZARD, WIND_ARCHER, NIGHT_WALKER, THUNDER_BREAKER, MIHILE,
        ARAN, EVAN, MERCEDES, PHANTOM, SHADE, LUMINOUS,
        DEMON_SLAYER, DEMON_AVENGER, BATTLE_MAGE, WILD_HUNTER, MECHANIC, XENON, BLASTER,
        HAYATO, KANNA, KAISER, KAIN, CADENA, ANGELIC_BUSTER, ZERO, BEAST_TAMER, KINESIS,
        ADELE, ILLIUM, ARK, LARA, HO_YOUNG, KHALI, REN, MO_XUAN, LYNN, SIA_ASTELLE, EREL_LIGHT
    }

    /** Main weapon types each class's skills work with. */
    private static final Map<Cls, Set<WeaponType>> WEAPONS = new EnumMap<>(Cls.class);
    /** Secondary weapons (prefix 135) per class, as item id ranges [from, to] (resources/data/secondary-weapons.tsv). */
    private static final Supplier<Map<Cls, int[][]>> SECONDARIES = DataTable.cached("secondary-weapons", t -> {
        Map<Cls, List<int[]>> ranges = new EnumMap<>(Cls.class);
        for (DataTable.Row r : t.rows()) {
            ranges.computeIfAbsent(Cls.valueOf(r.str("class").trim()), k -> new ArrayList<>())
                    .add(new int[]{r.i("from"), r.i("to")});
        }
        Map<Cls, int[][]> m = new EnumMap<>(Cls.class);
        ranges.forEach((cls, list) -> m.put(cls, list.toArray(new int[0][])));
        return m;
    });

    static {
        w(Cls.HERO, OneHandedSword, OneHandedAxe, TwoHandedSword, TwoHandedAxe);
        w(Cls.PALADIN, OneHandedSword, OneHandedMace, TwoHandedSword, TwoHandedMace);
        w(Cls.DARK_KNIGHT, Spear, Polearm);
        w(Cls.FP, Wand, Staff);
        w(Cls.IL, Wand, Staff);
        w(Cls.BISHOP, Wand, Staff);
        w(Cls.BOWMASTER, Bow);
        w(Cls.MARKSMAN, Crossbow);
        w(Cls.PATHFINDER, AncientBow);
        w(Cls.NIGHT_LORD, Claw);
        w(Cls.SHADOWER, Dagger);
        w(Cls.DUAL_BLADE, Dagger);
        w(Cls.BUCCANEER, Knuckle);
        w(Cls.CORSAIR, Gun);
        w(Cls.CANNONEER, HandCannon);
        w(Cls.JETT, Gun);
        w(Cls.DAWN_WARRIOR, OneHandedSword, TwoHandedSword);
        w(Cls.BLAZE_WIZARD, Wand, Staff);
        w(Cls.WIND_ARCHER, Bow);
        w(Cls.NIGHT_WALKER, Claw);
        w(Cls.THUNDER_BREAKER, Knuckle);
        w(Cls.MIHILE, OneHandedSword);
        w(Cls.ARAN, Polearm);
        w(Cls.EVAN, Wand, Staff);
        w(Cls.MERCEDES, DualBowgun);
        w(Cls.PHANTOM, Cane);
        w(Cls.SHADE, Knuckle);
        w(Cls.LUMINOUS, ShiningRod);
        w(Cls.DEMON_SLAYER, OneHandedAxe, OneHandedMace);
        w(Cls.DEMON_AVENGER, Desperado);
        w(Cls.BATTLE_MAGE, Staff);
        w(Cls.WILD_HUNTER, Crossbow);
        w(Cls.MECHANIC, Gun);
        w(Cls.XENON, ChainSword);
        w(Cls.BLASTER, ArmCannon);
        w(Cls.HAYATO, Katana);
        w(Cls.KANNA, Fan, SpiritWalkerFan);
        w(Cls.KAISER, TwoHandedSword);
        w(Cls.KAIN, Whispershot);
        w(Cls.CADENA, Chain);
        w(Cls.ANGELIC_BUSTER, SoulShooter);
        w(Cls.ZERO, LongSword, BigSword);
        w(Cls.BEAST_TAMER, Scepter);
        w(Cls.KINESIS, PsyLimiter);
        w(Cls.ADELE, Bladecaster);
        w(Cls.ILLIUM, Gauntlet);
        w(Cls.ARK, Knuckle);
        w(Cls.LARA, Wand);
        w(Cls.HO_YOUNG, RitualFan);
        w(Cls.KHALI, Chakram);
        w(Cls.REN, RenSword);
        w(Cls.MO_XUAN, MartialBrace);
        w(Cls.LYNN, Scepter);
        w(Cls.SIA_ASTELLE, CelestialLight);
        w(Cls.EREL_LIGHT, Gram);
    }

    private static void w(Cls cls, WeaponType... types) {
        WEAPONS.put(cls, EnumSet.copyOf(Arrays.asList(types)));
    }

    /**
     * @return true if any of the given characters can use the equip (non-equips always pass)
     */
    public static boolean isUsableByAny(int itemId, Collection<Char> chars) {
        if (!ItemConstants.isEquip(itemId) && !ItemConstants.isProjectile(itemId)) {
            return true;
        }
        for (Char chr : chars) {
            if (chr != null && isUsableBy(itemId, chr)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isUsableBy(int itemId, Char chr) {
        if (ItemConstants.isProjectile(itemId)) {
            return isProjectileUsableBy(itemId, chr);
        }
        if (!ItemConstants.isEquip(itemId)) {
            return true;
        }
        int gender = ItemConstants.getGenderFromId(itemId);
        if (gender != 2 && gender != chr.getAvatarData().getCharacterStat().getGender()) {
            return false;
        }
        var info = ItemData.getEquipInfoById(itemId);
        if (info == null) {
            return true; // unknown item: don't hide it
        }
        List<Integer> path = JobPathModule.getActivePath(chr);
        int job = path.isEmpty() ? classJob(chr.getJob()) : path.get(path.size() - 1);
        if (!matchesRequiredJob(info.getrJob(), classJob(chr.getJob())) && !matchesRequiredJob(info.getrJob(), job)) {
            return false;
        }
        // thief + pirate together is Xenon gear: only a Xenon can wear it
        if (info.getrJob() == (REQ_THIEF | REQ_PIRATE) && chr.getJob() / 100 != 36 && job / 100 != 36) {
            return false;
        }
        Set<Cls> classes = getClasses(job);
        if (classes.isEmpty()) {
            return true; // beginners and classes not listed: the required job decides
        }
        int prefix = itemId / 10000;
        if (prefix == 134) { // Kataras
            return classes.contains(Cls.DUAL_BLADE);
        }
        if (prefix == 135 || prefix == 172) {
            return isSecondaryFor(itemId, classes);
        }
        if (prefix == 109) { // shields: the special ones belong to one class
            int sub = itemId / 1000;
            if (sub == 1098) {
                return classes.contains(Cls.MIHILE);
            }
            if (sub == 1099) {
                return classes.contains(Cls.DEMON_SLAYER) || classes.contains(Cls.DEMON_AVENGER);
            }
            // a plain shield is for the Explorer warriors, mages and thieves (not Dual Blades) and Lynn only
            for (Cls c : new Cls[]{Cls.HERO, Cls.PALADIN, Cls.DARK_KNIGHT, Cls.FP, Cls.IL, Cls.BISHOP,
                    Cls.NIGHT_LORD, Cls.SHADOWER, Cls.LYNN}) {
                if (classes.contains(c)) {
                    return true;
                }
            }
            return false;
        }
        if (ItemConstants.isWeapon(itemId)) {
            WeaponType type = getWeaponType(itemId);
            if (type == null) {
                return true;
            }
            for (Cls cls : classes) {
                if (WEAPONS.getOrDefault(cls, Collections.emptySet()).contains(type)) {
                    return true;
                }
            }
            return false;
        }
        return true;
    }

    /**
     * Throwing stars, arrows and bullets: only for a class whose weapon fires them (the same pairing
     * {@link Char#calculateBulletIDForAttack()} uses). Beginners and classes not listed get them all.
     */
    public static boolean isProjectileUsableBy(int itemId, Char chr) {
        List<Integer> path = JobPathModule.getActivePath(chr);
        int job = path.isEmpty() ? classJob(chr.getJob()) : path.get(path.size() - 1);
        Set<Cls> classes = getClasses(job);
        if (classes.isEmpty()) {
            return true;
        }
        WeaponType type;
        if (ItemConstants.isThrowingStar(itemId)) {
            type = Claw;
        } else if (ItemConstants.isBowArrow(itemId)) {
            type = Bow;
        } else if (ItemConstants.isXBowArrow(itemId)) {
            type = Crossbow;
        } else {
            type = Gun;
        }
        for (Cls cls : classes) {
            if (WEAPONS.getOrDefault(cls, Collections.emptySet()).contains(type)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @return the names of the class(es) the character counts as (its @job path's class), e.g. "DEMON_SLAYER"; empty
     * for beginners and classes not listed
     */
    public static Set<String> getClassNames(Char chr) {
        List<Integer> path = JobPathModule.getActivePath(chr);
        int job = path.isEmpty() ? chr.getJob() : path.get(path.size() - 1);
        Set<String> res = new HashSet<>();
        for (Cls cls : getClasses(job)) {
            res.add(cls.name());
        }
        return res;
    }

    private static boolean isSecondaryFor(int itemId, Set<Cls> classes) {
        for (Cls cls : classes) {
            for (int[] range : SECONDARIES.get().getOrDefault(cls, new int[0][])) {
                if (itemId >= range[0] && itemId <= range[1]) {
                    return true;
                }
            }
        }
        return false; // a secondary of another class, or one that isn't known: don't drop it
    }

    /** A class's own beginner job counts as its 1st job (an Aran before 1st job only gets polearm gear). */
    private static int classJob(int job) {
        return switch (job) {
            case 2000 -> 2100; // Aran
            case 2001 -> 2200; // Evan
            case 2002 -> 2300; // Mercedes
            case 2003 -> 2400; // Phantom
            case 2004 -> 2700; // Luminous
            case 2005 -> 2500; // Shade
            case 3002 -> 3600; // Xenon
            case 5000 -> 5100; // Mihile
            case 6000 -> 6100; // Kaiser
            case 6001 -> 6500; // Angelic Buster
            case 10000 -> 10100; // Zero
            case 11000 -> 11200; // Beast Tamer
            case 14000 -> 14200; // Kinesis
            default -> job;
        };
    }

    private static boolean matchesRequiredJob(int rJob, int job) {
        if (rJob == 0) {
            return true; // every class
        }
        if (rJob < 0) {
            return JobConstants.isBeginnerJob(job); // beginner-only equips
        }
        return (rJob & REQ_WARRIOR) != 0 && JobConstants.isWarriorEquipJob(job)
                || (rJob & REQ_MAGICIAN) != 0 && JobConstants.isMageEquipJob(job)
                || (rJob & REQ_BOWMAN) != 0 && JobConstants.isArcherEquipJob(job)
                || (rJob & REQ_THIEF) != 0 && JobConstants.isThiefEquipJob(job)
                || (rJob & REQ_PIRATE) != 0 && JobConstants.isPirateEquipJob(job);
    }

    private static WeaponType getWeaponType(int itemId) {
        // prefix 121 holds three weapon types: 1212xxx Shining Rods, 1213xxx Bladecasters (Adele), 1214xxx
        // Whispershots (Kain)
        if (itemId / 10000 == 121) {
            int sub = (itemId / 1000) % 1000;
            return sub == 212 ? ShiningRod : WeaponType.getByVal(sub);
        }
        return ItemConstants.getWeaponType(itemId);
    }

    /**
     * The class(es) a job belongs to. Empty for beginners and classes not listed.
     */
    private static Set<Cls> getClasses(int job) {
        Set<Cls> res = EnumSet.noneOf(Cls.class);
        if (job >= 100 && job < 600) {
            switch (job) {
                case 100 -> res.addAll(List.of(Cls.HERO, Cls.PALADIN, Cls.DARK_KNIGHT));
                case 200 -> res.addAll(List.of(Cls.FP, Cls.IL, Cls.BISHOP));
                case 300 -> res.addAll(List.of(Cls.BOWMASTER, Cls.MARKSMAN));
                case 301 -> res.add(Cls.PATHFINDER);
                case 400 -> res.addAll(List.of(Cls.NIGHT_LORD, Cls.SHADOWER, Cls.DUAL_BLADE));
                case 500 -> res.addAll(List.of(Cls.BUCCANEER, Cls.CORSAIR));
                case 501 -> res.add(Cls.CANNONEER);
                case 508 -> res.add(Cls.JETT);
                default -> {
                    Cls cls = switch (job / 10) {
                        case 11 -> Cls.HERO;
                        case 12 -> Cls.PALADIN;
                        case 13 -> Cls.DARK_KNIGHT;
                        case 21 -> Cls.FP;
                        case 22 -> Cls.IL;
                        case 23 -> Cls.BISHOP;
                        case 31 -> Cls.BOWMASTER;
                        case 32 -> Cls.MARKSMAN;
                        case 33 -> Cls.PATHFINDER;
                        case 41 -> Cls.NIGHT_LORD;
                        case 42 -> Cls.SHADOWER;
                        case 43 -> Cls.DUAL_BLADE;
                        case 51 -> Cls.BUCCANEER;
                        case 52 -> Cls.CORSAIR;
                        case 53 -> Cls.CANNONEER;
                        case 57 -> Cls.JETT;
                        default -> null;
                    };
                    if (cls != null) {
                        res.add(cls);
                    }
                }
            }
            return res;
        }
        if (job == 3101 || job / 10 == 312) {
            res.add(Cls.DEMON_AVENGER);
            return res;
        }
        // single-path classes count as their class from creation on
        Cls base = switch (job) {
            case 15003 -> Cls.KHALI;
            case 16002 -> Cls.REN;
            case 17000 -> Cls.MO_XUAN;
            case 17001 -> Cls.LYNN;
            case 18000 -> Cls.SIA_ASTELLE;
            case 18001 -> Cls.EREL_LIGHT;
            default -> null;
        };
        if (base != null) {
            res.add(base);
            return res;
        }
        if (JobConstants.isBeginnerJob(job)) {
            return res;
        }
        Cls cls = switch (job / 100) {
            case 11 -> Cls.DAWN_WARRIOR;
            case 12 -> Cls.BLAZE_WIZARD;
            case 13 -> Cls.WIND_ARCHER;
            case 14 -> Cls.NIGHT_WALKER;
            case 15 -> Cls.THUNDER_BREAKER;
            case 51 -> Cls.MIHILE;
            case 21 -> Cls.ARAN;
            case 22 -> Cls.EVAN;
            case 23 -> Cls.MERCEDES;
            case 24 -> Cls.PHANTOM;
            case 25 -> Cls.SHADE;
            case 27 -> Cls.LUMINOUS;
            case 31 -> Cls.DEMON_SLAYER;
            case 32 -> Cls.BATTLE_MAGE;
            case 33 -> Cls.WILD_HUNTER;
            case 35 -> Cls.MECHANIC;
            case 36 -> Cls.XENON;
            case 37 -> Cls.BLASTER;
            case 41 -> Cls.HAYATO;
            case 42 -> Cls.KANNA;
            case 61 -> Cls.KAISER;
            case 63 -> Cls.KAIN;
            case 64 -> Cls.CADENA;
            case 65 -> Cls.ANGELIC_BUSTER;
            case 101 -> Cls.ZERO;
            case 112 -> Cls.BEAST_TAMER;
            case 142 -> Cls.KINESIS;
            case 151 -> Cls.ADELE;
            case 152 -> Cls.ILLIUM;
            case 155 -> Cls.ARK;
            case 162 -> Cls.LARA;
            case 164 -> Cls.HO_YOUNG;
            case 154 -> Cls.KHALI;
            case 161 -> Cls.REN;
            case 175 -> Cls.MO_XUAN;
            case 172 -> Cls.LYNN;
            case 182 -> Cls.SIA_ASTELLE;
            case 181 -> Cls.EREL_LIGHT;
            default -> null;
        };
        if (cls != null) {
            res.add(cls);
        }
        return res;
    }
}
