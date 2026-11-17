package net.swordie.ms.client.character.familiar;

import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.quest.QuestManager;

/**
 * Familiar presets (the 3 summoned familiars) and badge presets (8 badge slots), kept in two server-only records:
 * "cur=N;p0=a,b,c;p1=...". The client picks a preset with FAMILIAR_CODEX_MODIFY_REQUEST mask 0x80 (familiar preset
 * byte) / 0x100 (badge preset byte) and sets a badge preset's slots with mask 0x400 (byte preset, 8 bytes).
 */
public class FamiliarPresets {

    public static final int FAMILIAR_QR = 89999957;
    public static final int BADGE_QR = 89999958;
    public static final int MAX_PRESETS = 5;

    private static String get(Char chr, int qr, String key) {
        String rec = chr.getQuestManager().getHiddenRecord(qr);
        if (rec == null) {
            return null;
        }
        for (String part : rec.split(";")) {
            int eq = part.indexOf('=');
            if (eq > 0 && part.substring(0, eq).equals(key)) {
                return part.substring(eq + 1);
            }
        }
        return null;
    }

    private static void set(Char chr, int qr, String key, String value) {
        QuestManager qm = chr.getQuestManager();
        String rec = qm.getHiddenRecord(qr);
        StringBuilder sb = new StringBuilder();
        boolean found = false;
        if (rec != null && !rec.isEmpty()) {
            for (String part : rec.split(";")) {
                int eq = part.indexOf('=');
                if (eq <= 0) {
                    continue;
                }
                if (sb.length() > 0) {
                    sb.append(';');
                }
                if (part.substring(0, eq).equals(key)) {
                    sb.append(key).append('=').append(value);
                    found = true;
                } else {
                    sb.append(part);
                }
            }
        }
        if (!found) {
            if (sb.length() > 0) {
                sb.append(';');
            }
            sb.append(key).append('=').append(value);
        }
        qm.setHiddenRecord(qr, sb.toString());
    }

    private static int[] parse(String s, int len, int def) {
        int[] out = new int[len];
        java.util.Arrays.fill(out, def);
        if (s == null || s.isEmpty()) {
            return null;
        }
        String[] parts = s.split(",");
        for (int i = 0; i < len && i < parts.length; i++) {
            try {
                out[i] = Integer.parseInt(parts[i]);
            } catch (NumberFormatException ignored) {
            }
        }
        return out;
    }

    private static String join(int[] a) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < a.length; i++) {
            if (i > 0) {
                sb.append(',');
            }
            sb.append(a[i]);
        }
        return sb.toString();
    }

    public static int getFamiliarPreset(Char chr) {
        String c = get(chr, FAMILIAR_QR, "cur");
        return c == null ? 0 : Integer.parseInt(c);
    }

    public static int getBadgePreset(Char chr) {
        String c = get(chr, BADGE_QR, "cur");
        return c == null ? 0 : Integer.parseInt(c);
    }

    /** A familiar preset's 3 familiar SNs (the active ones for the current preset). */
    public static int[] getFamiliars(Char chr, int preset) {
        var fcm = chr.getFamiliarCodexManager();
        if (preset == getFamiliarPreset(chr)) {
            return fcm.getActiveFamiliars();
        }
        int[] saved = parse(get(chr, FAMILIAR_QR, "p" + preset), fcm.getActiveFamiliars().length, 0);
        return saved != null ? saved : new int[fcm.getActiveFamiliars().length];
    }

    /** A badge preset's 8 slots (the equipped ones for the current preset). */
    public static int[] getBadges(Char chr, int preset) {
        var fcm = chr.getFamiliarCodexManager();
        if (preset == getBadgePreset(chr)) {
            return fcm.getActiveBadges();
        }
        int[] saved = parse(get(chr, BADGE_QR, "p" + preset), fcm.getActiveBadges().length, -1);
        if (saved == null) {
            saved = new int[fcm.getActiveBadges().length];
            java.util.Arrays.fill(saved, -1);
        }
        return saved;
    }

    /** The active familiars changed (mask 0x20): they belong to the current familiar preset. */
    public static void saveFamiliars(Char chr, int[] familiars) {
        set(chr, FAMILIAR_QR, "p" + getFamiliarPreset(chr), join(familiars));
    }

    /**
     * Once per character: released lines rolled from the old (legacy) table, which the v265 client can't show, are
     * rerolled from the v265 one. @return the familiars that changed
     */
    public static java.util.List<net.swordie.ms.life.Familiar> migrateLines(Char chr,
                                                                       java.util.Collection<net.swordie.ms.life.Familiar> familiars) {
        java.util.List<net.swordie.ms.life.Familiar> changed = new java.util.ArrayList<>();
        if ("265".equals(get(chr, FAMILIAR_QR, "opt"))) {
            return changed;
        }
        for (var f : familiars) {
            if (f.hasReleasedPotential()) {
                f.rollPotentialLines();
                changed.add(f);
            }
        }
        set(chr, FAMILIAR_QR, "opt", "265");
        return changed;
    }

    /** The equipped badges changed (mask 0x40): they belong to the current badge preset. */
    public static void saveBadges(Char chr, int[] badges) {
        set(chr, BADGE_QR, "p" + getBadgePreset(chr), join(badges));
    }

    /** One familiar preset's 3 familiars (v271 edit type 13 for a preset that isn't the current one). */
    public static void setFamiliarPreset(Char chr, int preset, int[] familiars) {
        if (preset < 0 || preset >= MAX_PRESETS) {
            return;
        }
        set(chr, FAMILIAR_QR, "p" + preset, join(familiars));
        if (preset == getFamiliarPreset(chr)) {
            chr.getFamiliarCodexManager().setActiveFamiliars(familiars);
        }
    }

    /** Mask 0x400: one badge preset's 8 slots. */
    public static void setBadgePreset(Char chr, int preset, int[] badges) {
        if (preset < 0 || preset >= MAX_PRESETS) {
            return;
        }
        set(chr, BADGE_QR, "p" + preset, join(badges));
        if (preset == getBadgePreset(chr)) {
            chr.getFamiliarCodexManager().setActiveBadges(badges);
        }
    }

    /** Mask 0x80: switch familiar preset; its familiars (if any were saved) become the active ones. */
    public static boolean selectFamiliarPreset(Char chr, int preset) {
        var fcm = chr.getFamiliarCodexManager();
        if (preset < 0 || preset >= MAX_PRESETS || preset == getFamiliarPreset(chr)) {
            return false;
        }
        saveFamiliars(chr, fcm.getActiveFamiliars());
        set(chr, FAMILIAR_QR, "cur", String.valueOf(preset));
        int[] saved = parse(get(chr, FAMILIAR_QR, "p" + preset), fcm.getActiveFamiliars().length, 0);
        if (fcm.isFamiliarsSummoned()) {
            fcm.banishFamiliars();
        }
        fcm.setActiveFamiliars(saved != null ? saved : new int[fcm.getActiveFamiliars().length]);
        return true;
    }

    /** Mask 0x100: switch badge preset; its badges (if saved) become the equipped ones. */
    public static boolean selectBadgePreset(Char chr, int preset) {
        var fcm = chr.getFamiliarCodexManager();
        if (preset < 0 || preset >= MAX_PRESETS || preset == getBadgePreset(chr)) {
            return false;
        }
        saveBadges(chr, fcm.getActiveBadges());
        set(chr, BADGE_QR, "cur", String.valueOf(preset));
        int[] saved = parse(get(chr, BADGE_QR, "p" + preset), fcm.getActiveBadges().length, -1);
        int[] none = new int[fcm.getActiveBadges().length];
        java.util.Arrays.fill(none, -1);
        fcm.setActiveBadges(saved != null ? saved : none);
        fcm.initQuestsForSlots();
        return true;
    }
}
