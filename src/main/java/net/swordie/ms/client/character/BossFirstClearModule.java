package net.swordie.ms.client.character;

import java.util.function.Supplier;
import net.swordie.ms.loaders.DataTable;
import net.swordie.ms.connection.db.DatabaseManager;
import net.swordie.ms.constants.BossConstants;
import net.swordie.ms.constants.GameConstants;
import net.swordie.ms.handlers.executors.EventManager;
import net.swordie.ms.life.mob.Mob;
import net.swordie.ms.loaders.StringData;
import net.swordie.ms.world.WorldNews;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

/**
 * The first time a character defeats a boss (each boss and difficulty once), the whole world is told:
 * "[First Clear] Name defeated Zakum (Chaos) for the first time!" (several names when a party clears it together).
 * The text has no color codes and always the same form, and it also goes out as data through {@link WorldNews}
 * (type BOSS_FIRST_CLEAR) for other code.
 * <p>
 * A "defeat" is a boss fight's clear, the same as the boss clear Charm EXP ({@link SwordieCoinRewards}): the
 * monster that ends the fight (last phase or reward box), credited to everyone who did damage and their party members
 * in the same map. Clears are saved in the table boss_first_clears (sql/migration V109), one row per character and
 * boss. Hidden GMs aren't announced.
 */
public final class BossFirstClearModule {

    private static final Logger log = LogManager.getLogger(BossFirstClearModule.class);

    /** A boss as players know it: its name and difficulty ("" when the boss has only one). */
    public record Boss(String name, String difficulty) {
        public String display() {
            return difficulty.isEmpty() ? name : name + " (" + difficulty + ")";
        }

        /** What boss_first_clears.boss holds. */
        public String key() {
            return difficulty.isEmpty() ? name : name + "|" + difficulty;
        }
    }

    /**
     * The monsters that end a boss fight (MobConstants.isBossClearNxMob), by boss and difficulty:
     * resources/data/boss-clear-mobs.tsv, then the mobs BossConstants owns.
     */
    private static final Supplier<Map<Integer, Boss>> BOSSES = DataTable.cached("boss-clear-mobs", t -> {
        Map<Integer, Boss> m = new HashMap<>();
        for (DataTable.Row r : t.rows()) {
            add(m, r.str("name").trim(), r.str("difficulty").trim(), r.ints("mobs"));
        }
        add(m, "Lotus", "Normal", BossConstants.lotusV265Core(0));
        add(m, "Lotus", "Hard", BossConstants.lotusV265Core(1));
        add(m, "Lotus", "Extreme", BossConstants.lotusV265Core(2));
        add(m, "Will", "Easy", BossConstants.EASY_WILL_P3, BossConstants.WILL_MIRROR[0]);
        add(m, "Will", "Normal", BossConstants.WILL_MIRROR[1]);
        add(m, "Will", "Hard", BossConstants.WILL_MIRROR[2]);
        add(m, "Verus Hilla", "Normal", BossConstants.VERUS_HILLA);
        add(m, "Verus Hilla", "Hard", BossConstants.VERUS_HILLA_HARD);
        add(m, "Chosen Seren", "Normal", BossConstants.serenMob(0, BossConstants.SEREN_TREASURE_CHEST));
        add(m, "Chosen Seren", "Hard", BossConstants.serenMob(1, BossConstants.SEREN_TREASURE_CHEST));
        add(m, "Chosen Seren", "Extreme", BossConstants.serenMob(2, BossConstants.SEREN_TREASURE_CHEST));
        // Grandis bosses end with their reward chest
        for (int i = 0; i < 4; i++) {
            add(m, "Kalos the Guardian", BossConstants.KALOS_MODE_NAMES[i], BossConstants.KALOS_CHEST[i]);
            add(m, "Kaling", BossConstants.KALING_MODE_NAMES[i], BossConstants.KALING_BASE[i] + BossConstants.KALING_CHEST);
            add(m, "First Adversary", BossConstants.ADVERSARY_MODE_NAMES[i], BossConstants.ADVERSARY_BASE[i] + 15);
        }
        for (int i = 0; i < 2; i++) {
            add(m, "Limbo", BossConstants.LIMBO_MODE_NAMES[i], BossConstants.LIMBO_BASE[i] + 34);
            add(m, "Baldrix", BossConstants.BALDRIX_MODE_NAMES[i], BossConstants.BALDRIX_BASE[i] + 24);
            add(m, "Malefic Star", BossConstants.MALEFIC_MODE_NAMES[i], BossConstants.MALEFIC_BASE[i] + BossConstants.MALEFIC_CHEST);
            add(m, "Jupiter", BossConstants.JUPITER_MODE_NAMES[i], BossConstants.JUPITER_BASE[i] + BossConstants.JUPITER_CHEST);
            add(m, "Kai", BossConstants.KAI_MODE_NAMES[i], BossConstants.KAI_BASE[i] + BossConstants.KAI_CHEST);
        }
        // others (Gigatoad, ...) use their monster name
        return Map.copyOf(m);
    });

    private static void add(Map<Integer, Boss> bosses, String name, String difficulty, int... mobIds) {
        for (int id : mobIds) {
            bosses.put(id, new Boss(name, difficulty));
        }
    }

    private BossFirstClearModule() {
    }

    /** @return the boss this monster ends, or null if it isn't a boss */
    public static Boss getBoss(int mobId) {
        Boss boss = BOSSES.get().get(mobId);
        if (boss != null) {
            return boss;
        }
        String name = StringData.getMobStringById(mobId);
        return name == null || name.isBlank() ? null : new Boss(name.trim(), "");
    }

    /**
     * Called when a boss fight is cleared (SwordieCoinRewards.onMobKilled) with everyone who gets credit for it.
     * The database work runs a moment later, off the game thread.
     */
    public static void onBossCleared(Mob mob, Collection<Char> winners) {
        if (winners != null && winners.stream().allMatch(Char::inBossPracticeRun)) {
            return;
        }
        if (!net.swordie.ms.constants.ServerFeatures.on(net.swordie.ms.constants.ServerFeatures.Feature.BOSS_FIRST_CLEAR_ANNOUNCE) || mob == null || winners.isEmpty()) {
            return;
        }
        Boss boss = getBoss(mob.getTemplateId());
        if (boss == null) {
            return;
        }
        Map<Integer, String> chars = new LinkedHashMap<>();
        Map<Integer, Char> online = new HashMap<>();
        int worldId = -1;
        for (Char chr : winners) {
            if (chr == null || chr.isHidden() || chr.getClient() == null || chr.getClient().getWorld() == null) {
                continue;
            }
            chars.put(chr.getId(), chr.getName());
            online.put(chr.getId(), chr);
            worldId = chr.getClient().getWorld().getWorldId().getVal();
        }
        if (chars.isEmpty()) {
            return;
        }
        int world = worldId;
        int mobId = mob.getTemplateId();
        EventManager.addEvent(() -> record(world, boss, mobId, chars, online), 0);
    }

    /** Fame for each boss and difficulty a character clears for the first time. */
    public static final int FIRST_CLEAR_FAME = 1;

    private static synchronized void record(int worldId, Boss boss, int mobId, Map<Integer, String> chars,
                                            Map<Integer, Char> online) {
        try {
            List<Integer> ids = new ArrayList<>();
            List<String> names = new ArrayList<>();
            for (var e : chars.entrySet()) {
                if (DatabaseManager.queryRows("SELECT 1 FROM boss_first_clears WHERE charid = ? AND boss = ?", e.getKey(),
                        boss.key()).isEmpty()) {
                    DatabaseManager.executeQuery("INSERT IGNORE INTO boss_first_clears (charid, boss) VALUES (?, ?)",
                            e.getKey(), boss.key());
                    ids.add(e.getKey());
                    names.add(e.getValue());
                }
            }
            if (ids.isEmpty()) {
                return;
            }
            for (int id : ids) {
                Char chr = online.get(id);
                if (chr == null) {
                    continue;
                }
                try {
                    chr.addStatAndSendPacket(net.swordie.ms.enums.Stat.pop, FIRST_CLEAR_FAME);
                    chr.chatMessage("+" + FIRST_CLEAR_FAME + " Fame for your first " + boss.display() + " clear.");
                } catch (Exception e) {
                    log.error("Could not give the first clear Fame to " + chr.getName(), e);
                }
            }
            String text = String.format("[First Clear] %s defeated %s for the first time!", joinNames(names),
                    boss.display());
            WorldNews.publish(worldId, WorldNews.Type.BOSS_FIRST_CLEAR, text, ids, names, Map.of(
                    "boss", boss.name(), "difficulty", boss.difficulty(), "display", boss.display(),
                    "mobId", String.valueOf(mobId)));
        } catch (Exception e) {
            log.error("Could not record the first clear of " + boss.display(), e);
        }
    }

    /** "A", "A and B", "A, B and C" */
    private static String joinNames(List<String> names) {
        if (names.size() == 1) {
            return names.get(0);
        }
        return String.join(", ", names.subList(0, names.size() - 1)) + " and " + names.get(names.size() - 1);
    }
}
