package net.swordie.ms.client.character;

import net.swordie.ms.ServerConstants;
import net.swordie.ms.constants.ServerFeatures;
import net.swordie.ms.enums.BossCooldown;
import net.swordie.ms.enums.ChatType;
import net.swordie.ms.ext.ServerHooks;
import net.swordie.ms.loaders.ItemData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

/**
 * One rule for what a character may enter without its story quests, shared by both play styles, scripts and bots:
 * <ul>
 * <li>Express (and, while players choose, a character that hasn't chosen yet): every boss and area, as on v265.</li>
 * <li>Classic, the Classic-only mode and the Default mode: by quest, as on the official servers.</li>
 * </ul>
 * Users: boss entry NPCs and the boss window (BossPrereqModule, BossUiAccess), Monster Park, the Express story skips
 * of Arcane River and Grandis (with the per-region opt-out of @story). Also the Express arrival kit, given once.
 */
public final class ContentAccess {

    private static final Logger log = LogManager.getLogger(ContentAccess.class);

    /** Server-only record: the regions whose story an Express character keeps (@story), comma separated. */
    private static final int STORY_KEEP_QR = 89999965;
    /** Server-only record: "1" once the Express arrival kit was given. */
    private static final int EXPRESS_KIT_QR = 89999964;

    public static final String ARCANE = "arcane";
    public static final String GRANDIS = "grandis";

    private ContentAccess() {
    }

    // ------------------------------------------------------------------------------------------------- the rule

    /** @return whether every boss and area is open to this character without the story quests */
    public static boolean unlocksAll(Char chr) {
        if (chr == null || chr.getQuestManager() == null) {
            return false;
        }
        PlayStyleModule.PlayStyle eff = PlayStyleModule.effective(chr);
        if (eff == PlayStyleModule.PlayStyle.EXPRESS) {
            return true;
        }
        // not chosen yet while players choose: open, as before the choice existed
        return eff == PlayStyleModule.PlayStyle.UNSET && PlayStyleModule.asks(chr);
    }

    /** @return whether a gate that asks for this quest is open: the quest is done, or the character unlocks all */
    public static boolean questGateOpen(Char chr, int questId) {
        return questId == 0 || unlocksAll(chr)
                || (chr != null && chr.getQuestManager() != null && chr.getQuestManager().hasQuestCompleted(questId));
    }

    /** @return the quest this character still needs before the boss, or 0 when the boss is open */
    public static int missingBossQuest(Char chr, BossCooldown bc) {
        return BossPrereqModule.getMissingQuest(chr, bc);
    }

    // ------------------------------------------------------------------------------------------------- stories

    /** @return "arcane" / "grandis" for a region name or alias, or null */
    public static String regionKey(String name) {
        String n = name == null ? "" : name.toLowerCase(Locale.ROOT).replaceAll("[^a-z]", "");
        return switch (n) {
            case "arcane", "arcaneriver", "ar", "vj", "vanishingjourney" -> ARCANE;
            case "grandis", "sacred", "cernium" -> GRANDIS;
            default -> null;
        };
    }

    private static Set<String> keptRegions(Char chr) {
        Set<String> res = new LinkedHashSet<>();
        String rec = chr.getQuestManager().getHiddenRecord(STORY_KEEP_QR);
        if (rec != null) {
            for (String s : rec.split(",")) {
                if (!s.isBlank()) {
                    res.add(s.trim());
                }
            }
        }
        return res;
    }

    /** @return whether the character keeps this region's story (@story), so the Express skip leaves it alone */
    public static boolean keepsStory(Char chr, String region) {
        return chr != null && chr.getQuestManager() != null && keptRegions(chr).contains(region);
    }

    public static void setKeepsStory(Char chr, String region, boolean keep) {
        Set<String> kept = keptRegions(chr);
        if (keep ? kept.add(region) : kept.remove(region)) {
            chr.getQuestManager().setHiddenRecord(STORY_KEEP_QR, String.join(",", kept));
        }
    }

    /** @return whether reaching an area of this region skips its story for this character (Express, not kept) */
    public static boolean autoSkipsStory(Char chr, String region) {
        return !ServerHooks.isAutomated(chr) && PlayStyleModule.autoSkips(chr) && !keepsStory(chr, region);
    }

    /** @story: lines for the chat (no client tags). */
    public static List<String> storyCommand(Char chr, String arg) {
        List<String> out = new ArrayList<>();
        boolean express = PlayStyleModule.effective(chr) == PlayStyleModule.PlayStyle.EXPRESS;
        String region = arg == null || arg.isBlank() ? null : regionKey(arg.split("\\s+")[0]);
        if (arg != null && !arg.isBlank() && region == null) {
            out.add("Regions: arcane (Arcane River), grandis. @story <region> keeps its story, @story <region> skip "
                    + "lets Express skip it again.");
            return out;
        }
        if (region == null) {
            for (String r : new String[]{ARCANE, GRANDIS}) {
                out.add(describeRegion(chr, r, express));
            }
            out.add(express ? "@story <arcane|grandis> keeps a region's story for you to play; @story <region> skip "
                    + "lets Express skip it again." : "Your play style plays every story; Spiegelmann and Legato "
                    + "offer to skip what you've outleveled.");
            return out;
        }
        if (!express) {
            out.add("Your character plays every story already (" + PlayStyleModule.getName(chr) + ").");
            return out;
        }
        boolean skipAgain = arg.toLowerCase(Locale.ROOT).contains("skip");
        setKeepsStory(chr, region, !skipAgain);
        String name = ARCANE.equals(region) ? "Arcane River" : "Grandis";
        if (skipAgain) {
            out.add("Express skips the " + name + " story again when you reach each new area.");
        } else {
            out.add("You keep the " + name + " story: Express won't skip it when you reach its areas.");
            out.add(ARCANE.equals(region) ? "It starts with Chief Legato in Nameless Town (Vanishing Journey, "
                    + "level 200, after the 5th job)." : "It starts in Cernium (level 260).");
        }
        out.add(describeRegion(chr, region, true));
        return out;
    }

    private static String describeRegion(Char chr, String region, boolean express) {
        boolean arcane = ARCANE.equals(region);
        List<ArcaneRiverSkipModule.Area> areas = new ArrayList<>(arcane ? ArcaneRiverSkipModule.AREAS
                : ArcaneRiverSkipModule.GRANDIS_AREAS);
        if (arcane) {
            areas.addAll(ArcaneRiverSkipModule.ARCANE_SIDE_AREAS);
        }
        StringBuilder sb = new StringBuilder(arcane ? "Arcane River" : "Grandis");
        if (express) {
            sb.append(keepsStory(chr, region) ? " (kept)" : " (skipped by Express on arrival)");
        }
        sb.append(":");
        var qm = chr.getQuestManager();
        for (var a : areas) {
            int[] qs = a.getQuests();
            int done = 0;
            for (int q : qs) {
                if (qm.hasQuestCompleted(q)) {
                    done++;
                }
            }
            sb.append(" ").append(a.getName()).append(" ").append(done).append("/").append(qs.length).append(",");
        }
        sb.setLength(sb.length() - 1);
        return sb.toString();
    }

    // ------------------------------------------------------------------------------------------------- Express kit

    /**
     * Gives the Express arrival kit once (setting express_kit): the lines of resources/express-kit.tsv
     * ({@code item <id> <qty> [ifmissing]}, {@code quest <id>}, {@code record <questId> <value>}) and a note on what
     * Express opens. Called on login and map changes; cheap when already given.
     */
    public static void giveExpressKitIfDue(Char chr) {
        try {
            if (chr == null || chr.getQuestManager() == null || ServerHooks.isAutomated(chr)
                    || !ServerFeatures.on(ServerFeatures.Feature.EXPRESS_KIT)
                    || "1".equals(chr.getQuestManager().getHiddenRecord(EXPRESS_KIT_QR))
                    || PlayStyleModule.effective(chr) != PlayStyleModule.PlayStyle.EXPRESS
                    || chr.getField() == null || chr.getInstance() != null) {
                return;
            }
            chr.getQuestManager().setHiddenRecord(EXPRESS_KIT_QR, "1");
            int given = 0;
            net.swordie.ms.client.character.quest.QuietQuests.begin(chr); // kit quests: no notice each
            try {
                for (String[] row : readKit()) {
                    given += applyKitRow(chr, row) ? 1 : 0;
                }
            } finally {
                net.swordie.ms.client.character.quest.QuietQuests.end(chr, true);
            }
            chr.chatMessage(ChatType.SystemNotice, "[Express] Every boss, the boss window and Monster Park are open "
                    + "without their story quests; Arcane River and Grandis stories are skipped when you reach each "
                    + "area (@story keeps one)." + (given > 0 ? " Your arrival kit is in your inventory." : ""));
            log.info(String.format("Express kit: %s (%d), %d entries", chr.getName(), chr.getId(), given));
        } catch (Exception e) {
            log.error("Express kit for " + (chr == null ? "?" : chr.getName()), e);
        }
    }

    private static List<String[]> readKit() {
        List<String[]> rows = new ArrayList<>();
        try {
            for (String line : Files.readAllLines(Path.of(ServerConstants.RESOURCES_DIR, "express-kit.tsv"),
                    StandardCharsets.UTF_8)) {
                String t = line.strip();
                if (!t.isEmpty() && !t.startsWith("#")) {
                    rows.add(t.split("\\s+"));
                }
            }
        } catch (Exception e) {
            log.warn("Express kit: resources/express-kit.tsv not read (" + e.getMessage() + ")");
        }
        return rows;
    }

    private static boolean applyKitRow(Char chr, String[] row) {
        try {
            switch (row[0].toLowerCase(Locale.ROOT)) {
                case "item" -> {
                    int id = Integer.parseInt(row[1]);
                    int qty = row.length > 2 ? Integer.parseInt(row[2]) : 1;
                    boolean ifMissing = row.length > 3 && row[3].equalsIgnoreCase("ifmissing");
                    if ((ifMissing && chr.hasItem(id)) || ItemData.getItemDeepCopy(id) == null) {
                        return false;
                    }
                    chr.addItemToInventory(id, qty);
                    return true;
                }
                case "quest" -> {
                    int q = Integer.parseInt(row[1]);
                    return !chr.getQuestManager().hasQuestCompleted(q) && chr.getQuestManager().completeQuest(q, false);
                }
                case "record" -> {
                    chr.getScriptManager().setQRValue(Integer.parseInt(row[1]), row.length > 2 ? row[2] : "");
                    return true;
                }
                default -> {
                    return false;
                }
            }
        } catch (Exception e) {
            log.warn("Express kit: bad line " + String.join(" ", row) + " (" + e.getMessage() + ")");
            return false;
        }
    }
}
