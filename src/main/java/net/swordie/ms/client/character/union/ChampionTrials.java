package net.swordie.ms.client.character.union;

import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.quest.QuestManager;
import net.swordie.ms.constants.BossConstants;
import net.swordie.ms.enums.ChatType;
import net.swordie.ms.life.mob.Mob;
import net.swordie.ms.world.field.Field;
import net.swordie.ms.world.field.instance.Instance;

import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * Champion Trials (quests 102241, 102187-102190): the champion clears the trial boss solo within 20 minutes, which
 * sets record 102198 clear=1 so the quest can be handed in, and then ranks up in the Legion window (1127 type 2).
 * Rank C -> B: Hard Lotus, B -> A: Hard Verus Hilla, A -> S: Hard Black Mage, S -> SS: Hard Chosen Seren,
 * SS -> SSS: Normal Kalos the Guardian (Quest.wz texts). The client's separate "Champion Mode" entry isn't used: any
 * solo clear by a champion with the trial running counts.
 */
public class ChampionTrials {

    public static final long TIME_LIMIT_MS = 20 * 60 * 1000L;
    /** Black Mage's trial allows 45 minutes (quest 102188). */
    private static final long BLACK_MAGE_LIMIT_MS = 45 * 60 * 1000L;
    private static final int LOTUS_HARD = BossConstants.LOTUS_HARD_P3;
    private static final int VERUS_HILLA_HARD = BossConstants.VERUS_HILLA_HARD;
    private static final int BLACK_MAGE_HARD = 8880519; // the level 275 set's last phase (280 is Extreme)

    private static final Map<Instance, Long> starts = Collections.synchronizedMap(new WeakHashMap<>());

    /** The trial (index into ChampionModule.TRIAL_QUESTS) this character is on, or -1. */
    private static int activeTrial(Char chr) {
        if (chr == null || chr.getAccount() == null) {
            return -1;
        }
        int slot = ChampionModule.getSlotOf(chr);
        if (slot < 0) {
            return -1;
        }
        int rank = ChampionModule.getSlots(chr.getAccount())[slot].rank;
        if (rank < 0 || rank >= ChampionModule.TRIAL_QUESTS.length) {
            return -1;
        }
        QuestManager qm = chr.getQuestManager();
        int quest = ChampionModule.TRIAL_QUESTS[rank];
        return qm.hasQuestInProgress(quest) ? rank : -1;
    }

    private static void clear(Char chr, int trial) {
        Instance inst = chr.getInstance();
        if (inst == null || inst.getChars().size() != 1) {
            return; // solo only
        }
        Long start = starts.get(inst);
        long limit = trial == 2 ? BLACK_MAGE_LIMIT_MS : TIME_LIMIT_MS;
        if (start != null && System.currentTimeMillis() - start > limit) {
            chr.chatMessage(ChatType.SystemNotice, String.format("Champion Trial: more than %d minutes passed, so it "
                    + "doesn't count.", limit / 60000));
            return;
        }
        chr.getScriptManager().setQRValue(ChampionModule.TRIAL_RECORD, "clear", "1");
        chr.chatMessage(ChatType.SystemNotice, "Champion Trial cleared! Report to Sir Hamselot.");
    }

    /** Field.onMobDeath */
    public static void onMobDeath(Field field, Mob mob) {
        int id = mob.getTemplateId();
        int need = id == LOTUS_HARD ? 0 : id == VERUS_HILLA_HARD ? 1 : id == BLACK_MAGE_HARD ? 2 : -1;
        if (need < 0) {
            return;
        }
        for (Char chr : field.getChars()) {
            if (activeTrial(chr) == need) {
                clear(chr, need);
            }
        }
    }

    /** Char warp: remembers when a boss instance began, and the Seren/Kalos reward maps count as their clears. */
    public static void onFieldEnter(Char chr, Field field) {
        if (chr == null || field == null) {
            return;
        }
        Instance inst = chr.getInstance();
        if (inst != null) {
            starts.putIfAbsent(inst, System.currentTimeMillis());
        }
        int fid = field.getId();
        int need = fid == BossConstants.serenRewardField(1) ? 3 : fid == BossConstants.KALOS_REWARD_FIELDS[1] ? 4 : -1;
        if (need >= 0 && activeTrial(chr) == need) {
            clear(chr, need);
        }
    }
}
