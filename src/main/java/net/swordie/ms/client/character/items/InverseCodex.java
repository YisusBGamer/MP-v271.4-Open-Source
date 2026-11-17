package net.swordie.ms.client.character.items;

import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.quest.Quest;
import net.swordie.ms.enums.EquipBaseStat;
import net.swordie.ms.loaders.QuestData;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

/**
 * Inverse Codex (1162018, Kritias pocket item): anti-magic fed to it is its EXP (1 anti-magic = 1 EXP). Each level
 * needs its own amount (not cumulative) and gives the stats of Character.wz 01162018.img/info/level/info/<level>;
 * every day the codex loses that level's decPoint of EXP and drops a level when it runs out. Level and EXP are kept
 * in quest record {@link #QR}; the level stats are put on the codex itself (its i-stats, boss damage, IED).
 */
public class InverseCodex {
    public static final int ITEM = 1162018;
    public static final int QR = 500785;
    public static final int MAX_LEVEL = 13;
    private static final DateTimeFormatter DAY = DateTimeFormatter.BASIC_ISO_DATE;

    // Character.wz 01162018.img/info/level/info: point (EXP for the next level) and decPoint (daily loss)
    private static final int[] POINT = {0, 1000, 1600, 2560, 4096, 6554, 10486, 16777, 26844, 42950, 68719, 109951, 175922, 0};
    private static final int[] DEC = {0, 100, 150, 225, 338, 506, 759, 1139, 1709, 2563, 3844, 5767, 8650, 12975};
    // stats each level adds (incPDD/incMDD, incMHP/incMMP, all stats, PAD/MAD, IMDR %, BDR %)
    private static final int[][] GAIN = {
            {}, {50, 0, 0, 0, 0, 0}, {100, 0, 0, 0, 0, 0}, {0, 100, 0, 0, 0, 0}, {0, 200, 0, 0, 0, 0},
            {0, 0, 1, 0, 0, 0}, {0, 0, 2, 0, 0, 0}, {0, 0, 0, 1, 0, 0}, {0, 0, 0, 2, 0, 0}, {0, 0, 0, 0, 2, 0},
            {0, 0, 0, 0, 3, 0}, {0, 0, 0, 0, 0, 2}, {0, 0, 0, 0, 0, 3}, {}};

    public int level = 1, exp;
    public String day = "";

    public static InverseCodex of(Char chr) {
        InverseCodex c = new InverseCodex();
        Quest q = chr.getQuestManager() == null ? null : chr.getQuestManager().getQuestById(QR);
        if (q != null && q.getQRValue() != null) {
            Map<String, String> m = new HashMap<>();
            for (String kv : q.getQRValue().split(";")) {
                String[] p = kv.split("=", 2);
                if (p.length == 2) {
                    m.put(p[0], p[1]);
                }
            }
            try {
                c.level = Math.max(1, Math.min(MAX_LEVEL, Integer.parseInt(m.getOrDefault("lv", "1"))));
                c.exp = Math.max(0, Integer.parseInt(m.getOrDefault("exp", "0")));
                c.day = m.getOrDefault("day", "");
            } catch (NumberFormatException ignored) {
            }
        }
        return c;
    }

    private void save(Char chr) {
        var qm = chr.getQuestManager();
        Quest q = qm.getQuestById(QR);
        if (q == null) {
            q = QuestData.createQuestFromId(QR);
            qm.addQuest(q);
        }
        q.setQrValue(String.format("lv=%d;exp=%d;day=%s", level, exp, day));
    }

    /** The daily losses since the last day it was looked at. */
    private void decay() {
        LocalDate today = net.swordie.ms.util.ResetClock.today();
        if (day.isEmpty()) {
            day = today.format(DAY);
            return;
        }
        long days;
        try {
            days = ChronoUnit.DAYS.between(LocalDate.parse(day, DAY), today);
        } catch (RuntimeException e) {
            days = 0;
        }
        for (long d = 0; d < Math.min(days, 400); d++) {
            exp -= DEC[level];
            while (exp < 0 && level > 1) {
                level--;
                exp += POINT[level];
            }
            if (exp < 0) {
                exp = 0;
            }
        }
        day = today.format(DAY);
    }

    public void add(int antiMagic) {
        exp += Math.max(0, antiMagic);
        while (level < MAX_LEVEL && exp >= POINT[level]) {
            exp -= POINT[level];
            level++;
        }
        if (level >= MAX_LEVEL) {
            exp = 0;
        }
    }

    /** EXP still needed for the next level (0 at the top) */
    public int needed() {
        return level >= MAX_LEVEL ? 0 : POINT[level] - exp;
    }

    /** The character's codex, worn first */
    public static Equip findCodex(Char chr) {
        for (Item i : chr.getEquippedInventory().getItems()) {
            if (i.getItemId() == ITEM && i instanceof Equip e) {
                return e;
            }
        }
        for (Item i : chr.getEquipInventory().getItems()) {
            if (i.getItemId() == ITEM && i instanceof Equip e) {
                return e;
            }
        }
        return null;
    }

    /** Puts the level's stats on the codex (base item stats + every level's gain up to this one). */
    public void apply(Char chr, Equip codex) {
        int def = 0, hpmp = 0, all = 0, att = 0, imdr = 0, bdr = 0;
        for (int l = 1; l < level && l < GAIN.length; l++) {
            if (GAIN[l].length == 6) {
                def += GAIN[l][0];
                hpmp += GAIN[l][1];
                all += GAIN[l][2];
                att += GAIN[l][3];
                imdr += GAIN[l][4];
                bdr += GAIN[l][5];
            }
        }
        codex.setBaseStat(EquipBaseStat.iDEF, def);
        codex.setBaseStat(EquipBaseStat.iMaxHP, hpmp);
        codex.setBaseStat(EquipBaseStat.iMaxMP, hpmp);
        codex.setBaseStat(EquipBaseStat.iStr, all);
        codex.setBaseStat(EquipBaseStat.iDex, all);
        codex.setBaseStat(EquipBaseStat.iInt, all);
        codex.setBaseStat(EquipBaseStat.iLuk, all);
        codex.setBaseStat(EquipBaseStat.iPAD, att);
        codex.setBaseStat(EquipBaseStat.iMAD, att);
        codex.setBaseStat(EquipBaseStat.imdr, imdr);
        codex.setBaseStat(EquipBaseStat.bdr, bdr);
        codex.updateToChar(chr);
    }

    /** Daily losses and the stats, when the character logs in. */
    public static void onLogin(Char chr) {
        Equip codex = findCodex(chr);
        if (codex == null) {
            return;
        }
        InverseCodex c = of(chr);
        c.decay();
        c.save(chr);
        c.apply(chr, codex);
    }

    /**
     * Feeds anti-magic to the worn codex.
     *
     * @return the codex after feeding, or null if the character doesn't wear one
     */
    public static InverseCodex feed(Char chr, int antiMagic) {
        Equip codex = null;
        for (Item i : chr.getEquippedInventory().getItems()) {
            if (i.getItemId() == ITEM && i instanceof Equip e) {
                codex = e;
            }
        }
        if (codex == null) {
            return null;
        }
        InverseCodex c = of(chr);
        c.decay();
        c.add(antiMagic);
        c.save(chr);
        c.apply(chr, codex);
        return c;
    }
}
