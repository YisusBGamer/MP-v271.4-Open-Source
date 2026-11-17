package net.swordie.ms.client.character.quest;

import net.swordie.ms.loaders.DataTable;
import net.swordie.ms.connection.packet.WvsContext;
import net.swordie.ms.connection.packet.model.MessagePacket;
import net.swordie.ms.constants.GameConstants;
import net.swordie.ms.constants.QuestConstants;
import net.swordie.ms.enums.MobSetType;
import net.swordie.ms.life.mob.Mob;
import net.swordie.ms.loaders.QuestData;

import java.util.Arrays;

/**
 * Created on 31/01/2022.
 *
 * @author Asura
 */
public class QuestManagerHandler {

    /**
     * Invoked when the owner of the QuestManager (chr) has gained money.
     *
     * @param manager the quest manager that handles the money gained
     * @param money money that has been earned
     */
    public static void handleMoneyGain(QuestManager manager, int money) {
        var chr = manager.getChr();
        for (Quest q : manager.getQuestsInProgress()) {
            if (q.hasMoneyReq()) {
                boolean wasComplete = q.isComplete(chr);
                q.addMoney(money);
                chr.write(WvsContext.message(MessagePacket.questRecordMessage(q)));
                if (!wasComplete) {
                    manager.notifyIfReady(q);
                }
            }
        }
    }

    /**
     * Invoked when a Mob is killed.
     *
     * @param manager the quest manager that handles the mob kill
     * @param mob mob that has been killed
     */
    public static void handleMobKill(QuestManager manager, Mob mob) {
        var chr = manager.getChr();
        for (int questID : mob.getMobInfo().getQuests()) {
            Quest q = manager.getQuestById(questID);
            if (q != null && !q.isComplete(chr)) {
                q.handleMobKill(mob.getTemplateId());
                if (QuestConstants.M0_QR_QUESTS.contains(q.getQRKey())) {
                    chr.write(WvsContext.message(MessagePacket.questRecordExMessage(q)));
                } else {
                    chr.write(WvsContext.message(MessagePacket.questRecordMessage(q)));
                }
                manager.notifyIfReady(q); // was not complete before this kill
            }
        }

        // Quests that have template id of a mob that is a generalisation of many mobs. e.g. Lv13-30 mobs
        for (var quest : manager.getQuestsWithMobSetProgressRequirement()) {
            for (var mobReq : quest.getMobReqs()) {
                var mobId = mobReq.getMobID();
                var mobSetType = MobSetType.getByVal(mobId);

                if (mobSetType == null) {
                    continue;
                }

                var handleMobKill = false;
                switch (mobSetType) {
                    case Lv13_30Monsters:
                        handleMobKill = mob.getLevel() >= 13 && mob.getLevel() <= 30;
                        break;
                    case Lv31_50Monsters:
                        handleMobKill = mob.getLevel() >= 31 && mob.getLevel() <= 50;
                        break;
                    case Lv51_70Monsters:
                        handleMobKill = mob.getLevel() >= 51 && mob.getLevel() <= 70;
                        break;
                    case Lv71_90Monsters:
                        handleMobKill = mob.getLevel() >= 71 && mob.getLevel() <= 90;
                        break;
                    case Lv91_110Monsters:
                        handleMobKill = mob.getLevel() >= 91 && mob.getLevel() <= 110;
                        break;
                    case Lv111_130Monsters:
                        handleMobKill = mob.getLevel() >= 111 && mob.getLevel() <= 130;
                        break;
                    case Lv131_150Monsters:
                        handleMobKill = mob.getLevel() >= 131 && mob.getLevel() <= 150;
                        break;
                    case Lv151_plus_Monsters:
                        handleMobKill = mob.getLevel() >= 151;
                        break;
                    case MonsterParkBossLv60_114: // TODO

                        break;
                    case MonsterParkBossLv115_159: // TODO

                        break;
                    case MonsterParkBossLv160_plus: // TODO

                        break;
                    case Lv10_30Monsters:
                        handleMobKill = mob.getLevel() >= 10 && mob.getLevel() <= 30;
                        break;
                    case MonstersNearYourLevel:
                        var chrLv = chr.getLevel();
                        handleMobKill = Math.abs(mob.getLevel() - chrLv) < GameConstants.MOBS_NEAR_YOUR_LEVEL_RANGE;
                        break;
                    case EliteChampion:
                        handleMobKill = Arrays.stream(GameConstants.ELITE_BOSS_TEMPLATES).anyMatch(templateId -> templateId == mob.getTemplateId());
                        break;
                    case EliteMonsterOrEliteChampion:
                        handleMobKill = mob.getEliteType() == 1 || Arrays.stream(GameConstants.ELITE_BOSS_TEMPLATES).anyMatch(templateId -> templateId == mob.getTemplateId());
                        break;
                }

                if (handleMobKill) {
                    boolean wasComplete = quest.isComplete(chr);
                    quest.handleMobKill(mobId);
                    chr.write(WvsContext.message(MessagePacket.questRecordMessage(quest)));
                    if (!wasComplete) {
                        manager.notifyIfReady(quest);
                    }
                }
            }
        }
    }

    /**
     * Quest counters that stand for "monsters in an area": Quest.wz asks for kills of a stand-in mob id that never
     * spawns, and every monster killed in the area counts: {counter mob id, first field id, last field id}.
     */
    private static final int[][] AREA_KILL_COUNTERS = DataTable.read("area-kill-counters").rows().stream()
            .map(r -> new int[]{r.i("mob"), r.i("first_field"), r.i("last_field")}).toArray(int[][]::new);

    /** Invoked when a Mob is killed, after {@link #handleMobKill}: area kill counters. */
    /**
     * Phantom Forest (Taggrin's and Joko's quests, 64876-64893): the elite knights and the ancient masters appear after
     * many monsters are hunted in the forest, as on the official servers. {quest, the mob it needs}
     */
    private static final int[][] PHANTOM_FOREST_TARGETS = DataTable.read("phantom-forest-targets").rows().stream()
            .map(r -> new int[]{r.i("quest"), r.i("mob")}).toArray(int[][]::new);
    private static final int PHANTOM_FOREST_FIRST_FIELD = 610010000;
    private static final int PHANTOM_FOREST_LAST_FIELD = 610040999;
    private static final int PHANTOM_FOREST_KILLS = 100;
    private static final java.util.Map<Integer, Integer> phantomForestKills =
            new java.util.concurrent.ConcurrentHashMap<>();

    private static void handlePhantomForestTargets(QuestManager manager, Mob mob) {
        var field = mob.getField();
        int fieldId = field.getId();
        if (fieldId < PHANTOM_FOREST_FIRST_FIELD || fieldId > PHANTOM_FOREST_LAST_FIELD) {
            return;
        }
        for (int[] t : PHANTOM_FOREST_TARGETS) {
            if (t[1] == mob.getTemplateId()) {
                return; // the targets themselves don't count
            }
        }
        var chr = manager.getChr();
        int target = 0;
        for (int[] t : PHANTOM_FOREST_TARGETS) {
            var quest = manager.getQuestById(t[0]);
            if (quest != null && manager.hasQuestInProgress(t[0]) && !quest.isComplete(chr)) {
                target = t[1];
                break;
            }
        }
        if (target == 0) {
            return;
        }
        int kills = phantomForestKills.merge(chr.getId(), 1, Integer::sum);
        if (kills < PHANTOM_FOREST_KILLS) {
            return;
        }
        phantomForestKills.remove(chr.getId());
        int targetId = target;
        if (field.getMobs().stream().anyMatch(m -> m.getTemplateId() == targetId)) {
            return;
        }
        field.spawnMob(targetId, mob.getPosition().getX(), mob.getPosition().getY(), false, 0);
        chr.chatMessage("A powerful foe appears in the forest!");
    }

    /**
     * Bosses this server runs with one template for every difficulty: a kill in a difficulty's maps also counts for the
     * quests that ask for that difficulty's own template. {killed template, first field, last field, credited template}
     * (Lucid's Normal and Hard modes spawn their own templates, so they need no aliases.)
     */
    private static final int[][] KILL_ALIASES = DataTable.read("kill-aliases").rows().stream()
            .map(r -> new int[]{r.i("killed"), r.i("first_field"), r.i("last_field"), r.i("credited")}).toArray(int[][]::new);

    private static void handleKillAliases(QuestManager manager, Mob mob) {
        var chr = manager.getChr();
        int fieldId = mob.getField().getId();
        for (int[] a : KILL_ALIASES) {
            if (mob.getTemplateId() != a[0] || fieldId < a[1] || fieldId > a[2]) {
                continue;
            }
            for (var quest : manager.getQuestsInProgress()) {
                if (!quest.hasMobReq(a[3]) || quest.isComplete(chr)) {
                    continue;
                }
                quest.handleMobKill(a[3]);
                chr.write(WvsContext.message(MessagePacket.questRecordMessage(quest)));
                manager.notifyIfReady(quest);
            }
        }
    }

    /**
     * For automated quest runs: how a kill counter that isn't a real monster's own kills gets counted
     * here (area counters, kill aliases, the Phantom Forest targets, the level-range sets), or null.
     */
    public static String countedBy(int mobId) {
        for (int[] c : AREA_KILL_COUNTERS) {
            if (c[0] == mobId) {
                return "counts any monster killed in maps " + c[1] + "-" + c[2];
            }
        }
        for (int[] a : KILL_ALIASES) {
            if (a[3] == mobId) {
                return "counts " + a[0] + " killed in maps " + a[1] + "-" + a[2];
            }
        }
        for (int[] t : PHANTOM_FOREST_TARGETS) {
            if (t[1] == mobId) {
                return "appears after " + PHANTOM_FOREST_KILLS + " kills in the Phantom Forest";
            }
        }
        MobSetType set = MobSetType.getByVal(mobId);
        if (set != null && set != MobSetType.MonsterParkBossLv60_114 && set != MobSetType.MonsterParkBossLv115_159
                && set != MobSetType.MonsterParkBossLv160_plus) {
            return "counts monsters of a kind (" + set + ")";
        }
        return null;
    }

    public static void handleAreaKillCounters(QuestManager manager, Mob mob) {
        var chr = manager.getChr();
        if (mob.getField() == null) {
            return;
        }
        handlePhantomForestTargets(manager, mob);
        handleKillAliases(manager, mob);
        int fieldId = mob.getField().getId();
        for (int[] counter : AREA_KILL_COUNTERS) {
            if (fieldId < counter[1] || fieldId > counter[2] || mob.getTemplateId() == counter[0]) {
                continue;
            }
            for (var quest : manager.getQuestsInProgress()) {
                if (!quest.hasMobReq(counter[0]) || quest.isComplete(chr)) {
                    continue;
                }
                quest.handleMobKill(counter[0]);
                chr.write(WvsContext.message(MessagePacket.questRecordMessage(quest)));
                manager.notifyIfReady(quest);
            }
        }
    }

    public static void handleRuneAct(QuestManager manager) {
        var chr = manager.getChr();

        var qrExInfo = "runeAct";
        for (var quest : manager.getQuestsInProgress()) {
            var qi = QuestData.getQuestInfoById(quest.getQRKey());
            if (qi == null || !qi.getExProgressRequirements().containsKey(qrExInfo)) {
                continue;
            }

            var runeAct = quest.getIntPropertyEx("RunAct");
            quest.setPropertyEx("RunAct", runeAct + 1);

            chr.write(WvsContext.message(MessagePacket.questRecordExMessage(quest)));
        }
    }

    public static void handleMultiKillCount(QuestManager manager) {
        var chr = manager.getChr();

        var qrExInfo = "multiKillCount";
        for (var quest : manager.getQuestsInProgress()) {
            var qi = QuestData.getQuestInfoById(quest.getQRKey());
            if (qi == null || !qi.getExProgressRequirements().containsKey(qrExInfo)) {
                continue;
            }

            var currentMultiKill = quest.getIntPropertyEx("MultiKC");
            quest.setPropertyEx("MultiKC", currentMultiKill + 1);
            chr.write(WvsContext.message(MessagePacket.questRecordExMessage(quest)));
        }
    }

    public static void handleComboKill(QuestManager manager, int combo) {
        var chr = manager.getChr();

        var qrExInfo = "comboKill";
        for (var quest : manager.getQuestsInProgress()) {
            var qi = QuestData.getQuestInfoById(quest.getQRKey());
            if (qi == null || !qi.getExProgressRequirements().containsKey(qrExInfo)) {
                continue;
            }

            var currentCombo = quest.getIntPropertyEx("ComboK");
            if (combo > currentCombo) {
                quest.setPropertyEx("ComboK", combo);
                chr.write(WvsContext.message(MessagePacket.questRecordExMessage(quest)));
            }
        }
    }
}
