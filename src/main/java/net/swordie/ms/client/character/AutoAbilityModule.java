package net.swordie.ms.client.character;

import net.swordie.ms.client.character.quest.Quest;
import net.swordie.ms.connection.packet.WvsContext;
import net.swordie.ms.connection.packet.model.MessagePacket;
import net.swordie.ms.constants.JobConstants;
import net.swordie.ms.enums.BaseStat;
import net.swordie.ms.enums.Stat;
import net.swordie.ms.handlers.user.UserStatHandler;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * The stat window's "auto assign at level up" toggle. The client reads it from quest-ex record 25995
 * (instantap=1) and asks the server to flip it with USER_RUN_SCRIPT type 115 (v271; v265 was 114).
 * GMS turns it off at once and asks a yes/no question (Maple Administrator) before turning it on.
 */
public class AutoAbilityModule {
    public static final int QUEST_ID = 25995;
    public static final String KEY = "instantap";
    public static final short RUN_SCRIPT_TOGGLE = 115;
    public static final String ASK_SCRIPT = "autoAssignAp";
    private static final int SPEAKER = 9010000;

    public static boolean isOn(Char chr) {
        Quest q = chr.getQuestManager().getQuestById(QUEST_ID);
        return q != null && "1".equals(q.getProperty(KEY));
    }

    public static void toggle(Char chr) {
        if (isOn(chr)) {
            set(chr, false);
            chr.dispose();
            return;
        }
        if (chr.isTalkingToNpc()) {
            chr.dispose();
            return;
        }
        chr.getScriptManager().startScript(SPEAKER, ASK_SCRIPT, net.swordie.ms.scripts.ScriptType.Npc);
    }

    /** Script autoAssignAp: the GMS question before turning it on. */
    public static void askEnable(net.swordie.ms.scripts.ScriptManagerImpl sm, Char chr) {
        sm.setSpeakerID(SPEAKER);
        if (sm.sendAskYesNo("Would you like to enable the level-up instant distribution option? \r\n"
                + "This will automatically allocate AP, according to your current job, at each level up and job "
                + "advancement.\r\n\r\n#r* For beginner jobs, AP will be automatically assigned to STR and will "
                + "be redistributed, according to your job, at the first job advancement.\r\n"
                + "* For Xenon, AP will be distributed to the stat with the highest investment.")) {
            set(chr, true);
        }
    }

    private static void set(Char chr, boolean on) {
        var qm = chr.getQuestManager();
        if (qm.getQuestById(QUEST_ID) == null) {
            qm.setHiddenRecord(QUEST_ID, "");
        }
        Quest q = qm.getQuestById(QUEST_ID);
        q.convertQRValueToProperties();
        q.setProperty(KEY, on ? 1 : 0);
        chr.write(WvsContext.message(MessagePacket.questRecordExMessage(q)));
    }

    /** New characters start with it on, as on GMS. */
    public static void setDefaultOn(Char chr) {
        if (chr.getQuestManager() != null && chr.getQuestManager().getQuestById(QUEST_ID) == null) {
            chr.getQuestManager().setHiddenRecord(QUEST_ID, KEY + "=1");
        }
    }

    public static void onLevelUp(Char chr) {
        if (isOn(chr)) {
            assign(chr);
        }
    }

    /** All unspent AP into the main stat; Xenon into its highest of STR/DEX/LUK; beginners into STR. */
    private static void assign(Char chr) {
        int ap = chr.getStat(Stat.ap);
        if (ap <= 0) {
            return;
        }
        int job = chr.getJob();
        Map<Stat, Integer> alloc = new LinkedHashMap<>();
        if (JobConstants.isXenon(job)) {
            Stat best = Stat.str;
            for (Stat st : new Stat[]{Stat.dex, Stat.luk}) {
                if (chr.getStat(st) > chr.getStat(best)) {
                    best = st;
                }
            }
            alloc.put(best, ap);
        } else {
            Stat main = toStat(JobConstants.getMainStatByJob(job));
            if (main == null) {
                main = Stat.str; // beginners: STR, as GMS
            }
            alloc.put(main, ap);
        }
        // only what fits under the client stat limit (the rest stays as AP), so the chat line shows what is added
        alloc.replaceAll((st, pts) -> Math.min(pts, Math.max(0, st == Stat.mhp
                ? (net.swordie.ms.constants.GameConstants.MAX_HP_MP - chr.getStat(st)) / 20
                : Short.MAX_VALUE - chr.getStat(st))));
        alloc.values().removeIf(pts -> pts <= 0);
        if (alloc.isEmpty()) {
            return;
        }
        // GMS: the chat line comes before the stat change
        StringBuilder sb = new StringBuilder("Auto-Assign allocated points as follows: ");
        boolean first = true;
        for (Map.Entry<Stat, Integer> e : alloc.entrySet()) {
            sb.append(first ? "" : ", ").append(statName(e.getKey())).append(" +").append(e.getValue());
            first = false;
        }
        chr.chatMessage(sb.toString());
        UserStatHandler.assignAbilityPoints(chr, alloc);
    }

    private static String statName(Stat st) {
        return switch (st) {
            case str -> "STR";
            case dex -> "DEX";
            case inte -> "INT";
            case luk -> "LUK";
            case mhp -> "Max HP";
            default -> st.name();
        };
    }

    /** First-job stat requirement the main stat is set to before the rest goes back into AP (GMS: DEX 25 for archers). */
    private static int firstJobRequirement(Stat main) {
        return switch (main) {
            case str -> 35;
            case inte -> 20;
            default -> 25;
        };
    }

    /**
     * Beginner -> 1st job with the toggle on (GMS, Wild Hunter): STR/DEX/INT/LUK go back to 4, the main stat
     * to the job requirement, the rest becomes AP; then the usual auto-assign and the stat window's tutorial bubble.
     */
    public static void onFirstJobAdvance(Char chr) {
        if (!isOn(chr) || JobConstants.isXenon(chr.getJob())) {
            return;
        }
        Stat main = toStat(JobConstants.getMainStatByJob(chr.getJob()));
        if (main == null || main == Stat.mhp) {
            return;
        }
        Stat[] four = {Stat.str, Stat.dex, Stat.inte, Stat.luk};
        int points = 0;
        for (Stat st : four) {
            points += Math.max(0, chr.getStat(st) - 4);
        }
        int toMain = Math.min(points, firstJobRequirement(main) - 4);
        for (Stat st : four) {
            int value = st == main ? 4 + toMain : 4;
            if (chr.getStat(st) != value) {
                chr.setStat(st, value);
                Map<Stat, Object> one = new java.util.HashMap<>();
                one.put(st, (short) value);
                chr.write(WvsContext.statChanged(one, false));
            }
        }
        int ap = chr.getStat(Stat.ap) + points - toMain;
        chr.setStat(Stat.ap, ap);
        Map<Stat, Object> apMap = new java.util.HashMap<>();
        apMap.put(Stat.ap, (short) ap);
        chr.write(WvsContext.statChanged(apMap, false));
        assign(chr);
        chr.write(WvsContext.statWindowGuide(2, "apInstantTutorial"));
    }

    private static Stat toStat(BaseStat bs) {
        if (bs == null) {
            return null;
        }
        return switch (bs) {
            case str -> Stat.str;
            case dex -> Stat.dex;
            case inte -> Stat.inte;
            case luk -> Stat.luk;
            case mhp -> Stat.mhp;
            default -> null;
        };
    }
}
