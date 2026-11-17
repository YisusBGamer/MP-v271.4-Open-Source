package net.swordie.ms.client.character.potential;

import net.swordie.ms.client.character.Char;
import net.swordie.ms.connection.packet.WvsContext;
import net.swordie.ms.constants.GameConstants;
import net.swordie.ms.enums.CharPotGrade;
import net.swordie.ms.loaders.SkillData;
import net.swordie.ms.util.Util;
import net.swordie.orm.dao.CharacterPotentialDao;
import net.swordie.orm.dao.SworDaoFactory;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Inner Ability: 3 presets of up to 3 lines each; the active preset is the one that counts.
 * Created on 5/27/2018.
 */
public class CharacterPotentialMan {

    public static final int PRESETS = 3;
    /** Server-side record holding the active preset (preset=N) */
    public static final int PRESET_QR = 89999994; // server-only record: active Inner Ability preset

    private Char chr;

    private static final CharacterPotentialDao characterPotentialDao = (CharacterPotentialDao) SworDaoFactory.getByClass(CharacterPotential.class);

    // a Miracle Circulator result waiting for the player's keep/discard pick
    private List<CharacterPotential> pendingLines;
    private int pendingPreset = -1;
    private int pendingItemId;

    public CharacterPotentialMan(Char chr) {
        this.chr = chr;
    }

    private Set<CharacterPotential> getPotentials() {
        return chr.getPotentials();
    }

    // ------------------------------------------------------------------- presets

    public int getActivePreset() {
        var q = chr.getQuestManager() == null ? null : chr.getQuestManager().getQuestById(PRESET_QR);
        if (q == null || !q.hasProperty("preset")) {
            return 0;
        }
        try {
            int p = Integer.parseInt(q.getProperty("preset"));
            return p >= 0 && p < PRESETS ? p : 0;
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    /** Switches the active preset and tells the client. @return false for a preset that doesn't exist */
    public boolean changePreset(int preset) {
        if (preset < 0 || preset >= PRESETS) {
            return false;
        }
        chr.getQuestManager().getOrCreateQuestById(PRESET_QR).setProperty("preset", preset);
        fillPresets();
        chr.write(WvsContext.characterPotentialChangePreset(preset));
        return true;
    }

    public static boolean isValidPreset(int preset) {
        return preset >= 0 && preset < PRESETS;
    }

    /** @return the lines of one preset, by line number */
    public List<CharacterPotential> getPotentials(int preset) {
        List<CharacterPotential> res = new ArrayList<>();
        for (CharacterPotential cp : getPotentials()) {
            if (cp.getPreset() == preset) {
                res.add(cp);
            }
        }
        res.sort(Comparator.comparingInt(CharacterPotential::getKey));
        return res;
    }

    /** @return the lines that count (the active preset's) */
    public List<CharacterPotential> getActivePotentials() {
        return getPotentials(getActivePreset());
    }

    /**
     * Gives every preset the lines it is missing, copied from the preset with the most lines (presets start as
     * copies, and a line unlocked later reaches all of them). @return whether anything was added
     */
    public boolean fillPresets() {
        int source = 0;
        for (int p = 1; p < PRESETS; p++) {
            if (getPotentials(p).size() > getPotentials(source).size()) {
                source = p;
            }
        }
        return fillPresetsFrom(source);
    }

    private boolean fillPresetsFrom(int source) {
        boolean added = false;
        for (CharacterPotential cp : getPotentials(source)) {
            for (int p = 0; p < PRESETS; p++) {
                if (p != source && getPotentialByKey(p, cp.getKey()) == null) {
                    getPotentials().add(cp.copyTo(p));
                    added = true;
                }
            }
        }
        return added;
    }

    // ------------------------------------------------------------------- lines

    public CharacterPotential getPotentialByKey(byte key) {
        return getPotentialByKey(getActivePreset(), key);
    }

    public CharacterPotential getPotentialByKey(int preset, byte key) {
        return getPotentials().stream().filter(pot -> pot.getPreset() == preset && pot.getKey() == key)
                .findAny().orElse(null);
    }

    /**
     * Adds a line to its preset, replacing the line with the same key there, and sends the preset to the client.
     * A preset missing that line number gets a copy (an unlocked line reaches every preset).
     */
    public void addPotential(CharacterPotential potential) {
        removePotential(potential.getPreset(), potential.getKey());
        getPotentials().add(potential);
        boolean filled = fillPresetsFrom(potential.getPreset());
        if (filled) {
            chr.write(WvsContext.characterPotentialReset(chr));
        } else {
            chr.write(WvsContext.characterPotentialSet(chr, potential.getPreset()));
        }
    }

    public void removePotential(byte key) {
        removePotential(getActivePreset(), key);
    }

    /** Removes a line from a preset (nothing happens if there is none). */
    public void removePotential(int preset, byte key) {
        CharacterPotential cp = getPotentialByKey(preset, key);
        if (cp != null) {
            getPotentials().remove(cp);
            characterPotentialDao.delete(cp);
        }
    }

    /** @return the active preset's grade (its highest line grade) */
    public byte getGrade() {
        return getGrade(getActivePreset());
    }

    public byte getGrade(int preset) {
        int max = 0;
        for (CharacterPotential cp : getPotentials(preset)) {
            if (cp.getGrade() > max) {
                max = cp.getGrade();
            }
        }
        return (byte) max;
    }

    /** A new random line for the active preset, at its current grade. */
    public CharacterPotential generateRandomPotential(byte key) {
        int preset = getActivePreset();
        return generateRandomPotential(preset, key, getGrade(preset), skillsOutside(preset, key));
    }

    private Set<Integer> skillsOutside(int preset, byte key) {
        Set<Integer> res = new HashSet<>();
        for (CharacterPotential pot : getPotentials(preset)) {
            if (pot.getKey() != key) {
                res.add(pot.getSkillID());
            }
        }
        return res;
    }

    /**
     * Generates a line of the given grade.
     * @param exclude abilities the preset already has on other lines
     */
    public CharacterPotential generateRandomPotential(int preset, byte key, byte grade, Set<Integer> exclude) {
        // slv are 1-40 inclusive, split up into 4 "tiers" of 10 slv per grade (0-3 inclusive)
        int baseSlv = grade * 10;
        int maxSlv = baseSlv + 10;
        int slv = 1 + Util.getRandom(baseSlv, maxSlv);

        var possibleSkillIds = new HashSet<Integer>();
        for (int skill = GameConstants.CHAR_POT_BASE_ID; skill <= GameConstants.CHAR_POT_END_ID; skill++) {
            possibleSkillIds.add(skill);
        }
        possibleSkillIds.removeAll(exclude);

        int skillID = Util.getRandomFromCollection(possibleSkillIds);

        var si = SkillData.getSkillInfoById(skillID);
        if (si != null) {
            slv = Math.min(si.getMaxLevel(), slv);
        }
        return new CharacterPotential(preset, key, skillID, (byte) slv, grade);
    }

    /** The grade a reroll lands on: one up at the rank-up chance, never past Legendary. */
    private byte rollGrade(byte grade) {
        var grades = CharPotGrade.values();
        var gradeVal = grades[Math.max(0, Math.min(grade, grades.length - 1))];
        if (grade < CharPotGrade.Legendary.ordinal() && Util.succeedProp(GameConstants.boosted(GameConstants.getCharPotRankUpChance(gradeVal)))) {
            grade++;
        }
        return grade;
    }

    /** New lines for every unlocked line of a preset at a rolled grade; nothing is applied. */
    private List<CharacterPotential> rollLines(int preset, Set<Integer> lockedLines) {
        byte grade = rollGrade(getGrade(preset));
        List<CharacterPotential> lines = getPotentials(preset);
        Set<Integer> taken = new HashSet<>();
        for (CharacterPotential cp : lines) {
            if (lockedLines.contains((int) cp.getKey())) {
                taken.add(cp.getSkillID());
            }
        }
        List<CharacterPotential> res = new ArrayList<>();
        for (CharacterPotential cp : lines) {
            CharacterPotential line;
            if (lockedLines.contains((int) cp.getKey())) {
                line = new CharacterPotential(preset, cp.getKey(), cp.getSkillID(), cp.getSlv(), grade);
            } else {
                line = generateRandomPotential(preset, cp.getKey(), grade, taken);
                taken.add(line.getSkillID());
            }
            res.add(line);
        }
        return res;
    }

    /** Writes rolled lines into their preset and sends it. */
    private void applyLines(int preset, List<CharacterPotential> lines) {
        for (CharacterPotential line : lines) {
            CharacterPotential cp = getPotentialByKey(preset, line.getKey());
            if (cp == null) {
                getPotentials().add(line);
            } else {
                cp.setSkillID(line.getSkillID());
                cp.setSlv(line.getSlv());
                cp.setGrade(line.getGrade());
            }
        }
        chr.write(WvsContext.characterPotentialSet(chr, preset));
    }

    // ------------------------------------------------------------------- Miracle Circulator (keep or discard)

    /**
     * Rolls a Circulator on a preset and holds the result until the player keeps or discards it.
     * @return the new lines, for the result window
     */
    public List<CharacterPotential> rollCirculator(int preset, int itemId) {
        pendingLines = rollLines(preset, Set.of());
        pendingPreset = preset;
        pendingItemId = itemId;
        return pendingLines;
    }

    public boolean hasPendingCirculator() {
        return pendingLines != null;
    }

    /** Applies (keepNew) or drops the held Circulator result. @return false if nothing was held for that preset */
    public boolean resolveCirculator(boolean keepNew, int preset) {
        if (pendingLines == null || (preset >= 0 && preset != pendingPreset)) {
            return false;
        }
        var lines = pendingLines;
        int p = pendingPreset;
        pendingLines = null;
        pendingPreset = -1;
        pendingItemId = 0;
        if (keepNew) {
            applyLines(p, lines);
        } else {
            chr.write(WvsContext.characterPotentialSet(chr, p));
        }
        return true;
    }

    public int getPendingItemId() {
        return pendingItemId;
    }

    // ------------------------------------------------------------------- Honor EXP rerolls (window and @menu)

    private CharPotGrade gradeEnum(int preset) {
        var grades = CharPotGrade.values();
        return grades[Math.max(0, Math.min(getGrade(preset), grades.length - 1))];
    }

    /** @return the Honor EXP a reroll costs with this many lines locked (what the Inner Ability window charges) */
    public int getRerollCost(int lockedLines) {
        return getRerollCost(getActivePreset(), lockedLines);
    }

    public int getRerollCost(int preset, int lockedLines) {
        var grade = gradeEnum(preset);
        return GameConstants.getCharPotResetCost(grade) + GameConstants.getCharPotLockCost(lockedLines, grade);
    }

    /** @return the chance (%) a reroll raises the grade */
    public int getRankUpChance() {
        return GameConstants.getCharPotRankUpChance(gradeEnum(getActivePreset()));
    }

    public String getGradeName() {
        return gradeEnum(getActivePreset()).name();
    }

    /** Honor EXP reroll of the active preset (@menu). */
    public String rerollWithHonor(Set<Integer> lockedLines) {
        return rerollWithHonor(getActivePreset(), lockedLines);
    }

    /**
     * Rerolls a preset for Honor EXP: the grade may go up, and every line that isn't locked gets a new ability
     * (the Inner Ability window's reset, and @menu's). It applies at once.
     *
     * @param lockedLines the line numbers (1-3) to keep
     * @return why it couldn't be done, or null when it was
     */
    public String rerollWithHonor(int preset, Set<Integer> lockedLines) {
        if (!isValidPreset(preset) || getPotentials(preset).isEmpty()) {
            return "Your Inner Ability isn't open yet.";
        }
        int cost = getRerollCost(preset, lockedLines.size());
        if (cost > chr.getHonorExp()) {
            return String.format("That costs %,d Honor EXP (you have %,d).", cost, chr.getHonorExp());
        }
        chr.addHonorExp(-cost);
        applyLines(preset, rollLines(preset, lockedLines));
        int grade = 0;
        Map<Integer, Integer> lines = new HashMap<>();
        for (CharacterPotential cp : getPotentials(preset)) {
            grade = Math.max(grade, cp.getGrade());
            lines.merge(cp.getSkillID(), cp.getSlv(), Math::max);
        }
        net.swordie.ms.client.character.achievement.MapleAchievements.onAbilityReset(chr, cost, grade, lines);
        return null;
    }

    private static final Pattern STAT_TOKEN = Pattern.compile("#([a-zA-Z][a-zA-Z0-9]*)");

    /** @return a line of the active preset as the window shows it, e.g. "STR: +20" ("-" if it has none) */
    public static String describeLine(Char chr, int key) {
        CharacterPotential cp = chr.getPotentialMan().getPotentialByKey((byte) key);
        if (cp == null) {
            return "-";
        }
        var ssi = net.swordie.ms.loaders.StringData.getSkillStringById(cp.getSkillID());
        var si = SkillData.getSkillInfoById(cp.getSkillID());
        if (ssi == null) {
            return "?";
        }
        String h = ssi.getH();
        if (h == null || h.isBlank() || si == null) {
            return ssi.getName();
        }
        Matcher m = STAT_TOKEN.matcher(h.replace("\\n", " ").replace("\n", " "));
        StringBuffer sb = new StringBuffer();
        while (m.find()) {
            String value = "?";
            try {
                value = String.valueOf(si.getValue(
                        net.swordie.ms.client.character.skills.SkillStat.valueOf(m.group(1)), cp.getSlv()));
            } catch (IllegalArgumentException ignored) {
                // not a stat
            }
            m.appendReplacement(sb, Matcher.quoteReplacement(value));
        }
        m.appendTail(sb);
        return sb.toString().replace("#", "").trim();
    }
}
