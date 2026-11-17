package net.swordie.ms.client.character;

import net.swordie.ms.constants.JobConstants;
import net.swordie.ms.enums.BossCooldown;

import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;

/**
 * The story quests GMS asks for before a boss (Etc.wz/BossParty.img QuestDemand: any one of a boss's quests will do).
 * Classic characters (chosen or set by the mode) and the Default mode need them; Express and characters that haven't
 * chosen yet don't (ContentAccess).
 * <p>
 * As in the data: Zero needs its own story (40905) for every boss, and some jobs have their own Magnus and Arkarium
 * quest ("Except"); for the bosses with a world share record ("wsr"), another character on the account that did the
 * quest is enough.
 */
public final class BossPrereqModule {

    private BossPrereqModule() {
    }

    /** Zero's story: BossParty's Except 101 asks for it at every boss. */
    public static final int ZERO_STORY = 40905;

    private static final Map<BossCooldown, int[]> QUESTS = new EnumMap<>(BossCooldown.class);
    /** Bosses whose quest also counts when another character on the account did it (wsr). */
    private static final Set<BossCooldown> ACCOUNT_WIDE = EnumSet.noneOf(BossCooldown.class);
    /** Fights the boss window (and so its Zero rule) doesn't cover. */
    private static final Set<BossCooldown> NO_BOSS_WINDOW = EnumSet.of(BossCooldown.Akechi, BossCooldown.Gollux,
            BossCooldown.Ranmaru, BossCooldown.RanmaruHard, BossCooldown.ScarlionTarga, BossCooldown.Dorothy);

    private static void q(int[] quests, boolean accountWide, BossCooldown... bosses) {
        for (BossCooldown bc : bosses) {
            QUESTS.put(bc, quests);
            if (accountWide) {
                ACCOUNT_WIDE.add(bc);
            }
        }
    }

    static {
        q(new int[]{30007}, false, BossCooldown.Pierre, BossCooldown.PierreHard, BossCooldown.VonBon,
                BossCooldown.VonBonHard, BossCooldown.Queen, BossCooldown.QueenHard, BossCooldown.Vellum,
                BossCooldown.VellumHard);
        q(new int[]{1562, 3170}, true, BossCooldown.VonLeonEasy, BossCooldown.VonLeon, BossCooldown.VonLeonHard);
        q(new int[]{31179}, true, BossCooldown.ArkariumEasy, BossCooldown.Arkarium);
        q(new int[]{38858, 31833}, true, BossCooldown.MagnusEasy, BossCooldown.Magnus, BossCooldown.MagnusHard);
        q(new int[]{3521}, true, BossCooldown.PinkBean, BossCooldown.PinkBeanHard);
        q(new int[]{31152}, true, BossCooldown.CygnusEasy, BossCooldown.Cygnus);
        q(new int[]{3496}, true, BossCooldown.OmniCln);
        q(new int[]{3470}, true, BossCooldown.PapulatusEasy, BossCooldown.Papulatus, BossCooldown.PapulatusHard);
        q(new int[]{34330}, false, BossCooldown.LucidEasy, BossCooldown.Lucid, BossCooldown.LucidHard);
        q(new int[]{37871}, false, BossCooldown.WillEasy, BossCooldown.Will, BossCooldown.WillHard);
        q(new int[]{36772}, false, BossCooldown.VerusHilla, BossCooldown.VerusHillaHard);
        q(new int[]{35632}, false, BossCooldown.Gloom, BossCooldown.GloomHard);
        q(new int[]{35815}, false, BossCooldown.Darknell, BossCooldown.DarknellHard, BossCooldown.BlackMage);
        q(new int[]{39921}, false, BossCooldown.Seren, BossCooldown.SerenHard, BossCooldown.SerenExtreme);
        q(new int[]{36013}, true, BossCooldown.GuardianAngelSlime, BossCooldown.GuardianAngelSlimeChaos);
        q(new int[]{38214}, false, BossCooldown.Kalos);
        q(new int[]{38401}, false, BossCooldown.Kaling);
        q(new int[]{38319}, false, BossCooldown.FirstAdversary);
        q(new int[]{38702}, false, BossCooldown.Limbo);
        q(new int[]{36996}, false, BossCooldown.Baldrix);
        q(new int[]{36549}, false, BossCooldown.MaleficStar);
        q(new int[]{36873}, false, BossCooldown.Jupiter);
    }

    private static boolean isMagnus(BossCooldown bc) {
        return bc == BossCooldown.MagnusEasy || bc == BossCooldown.Magnus || bc == BossCooldown.MagnusHard;
    }

    private static boolean isArkarium(BossCooldown bc) {
        return bc == BossCooldown.ArkariumEasy || bc == BossCooldown.Arkarium;
    }

    /** Whether this character must have done the bosses' story quests: everyone ContentAccess doesn't open all to. */
    public static boolean needsPrereqs(Char chr) {
        return chr != null && chr.getQuestManager() != null && !ContentAccess.unlocksAll(chr);
    }

    /** @return the boss's quests for this character's job (any one of them will do), or an empty array */
    public static int[] getQuests(Char chr, BossCooldown bc) {
        if (bc == null) {
            return new int[0];
        }
        int job = chr == null ? 0 : chr.getJob();
        if (isMagnus(bc) && JobConstants.isKaiser(job)) {
            return new int[]{38858, 31498};
        }
        if (isMagnus(bc) && JobConstants.isCadena(job)) {
            return new int[]{38858, 34735};
        }
        if (isMagnus(bc) && JobConstants.isAngelicBuster(job)) {
            return new int[]{38858, 31686};
        }
        if (isArkarium(bc) && JobConstants.isZero(job)) {
            return new int[]{31198};
        }
        return QUESTS.getOrDefault(bc, new int[0]);
    }

    private static boolean doneOnAccount(Char chr, int quest) {
        if (chr.getAccount() == null || chr.getAccount().getCharacters() == null) {
            return false;
        }
        for (Char other : chr.getAccount().getCharacters()) {
            try {
                if (other != null && other.getId() != chr.getId() && other.getQuestManager() != null
                        && other.getQuestManager().hasQuestCompleted(quest)) {
                    return true;
                }
            } catch (Exception ignored) {
                // a character whose quests aren't loaded doesn't count
            }
        }
        return false;
    }

    /** @return the quest this character still has to complete before this boss, or 0 */
    public static int getMissingQuest(Char chr, BossCooldown bc) {
        if (bc == null || !needsPrereqs(chr)) {
            return 0;
        }
        var qm = chr.getQuestManager();
        if (JobConstants.isZero(chr.getJob()) && !NO_BOSS_WINDOW.contains(bc) && !qm.hasQuestCompleted(ZERO_STORY)) {
            return ZERO_STORY;
        }
        int[] quests = getQuests(chr, bc);
        if (quests.length == 0) {
            return 0;
        }
        for (int q : quests) {
            if (qm.hasQuestCompleted(q)) {
                return 0;
            }
        }
        if (ACCOUNT_WIDE.contains(bc)) {
            for (int q : quests) {
                if (doneOnAccount(chr, q)) {
                    return 0;
                }
            }
        }
        return quests[0];
    }

    /** For chat lines (no client tags): "quest 34330 'name'". */
    public static String questName(int questId) {
        return net.swordie.ms.ext.ServerHooks.questName(questId);
    }

    /** The entry NPC's answer, as in GMS (#y shows the quest's name on the client). */
    public static String getMessage(String who, int questId) {
        return (who == null ? "You" : who) + " must complete the quest #b#y" + questId + "##k before entering.";
    }
}
