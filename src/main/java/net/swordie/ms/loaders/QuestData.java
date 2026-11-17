package net.swordie.ms.loaders;

import net.swordie.ms.ServerConstants;
import net.swordie.ms.client.character.quest.Quest;
import net.swordie.ms.client.character.quest.progress.*;
import net.swordie.ms.client.character.quest.requirement.*;
import net.swordie.ms.client.character.quest.reward.*;
import net.swordie.ms.constants.ItemConstants;
import net.swordie.ms.enums.InvType;
import net.swordie.ms.enums.QuestStatus;
import net.swordie.ms.enums.Stat;
import net.swordie.ms.jwzlib.WzEmpty;
import net.swordie.ms.jwzlib.WzFile;
import net.swordie.ms.jwzlib.WzObject;
import net.swordie.ms.jwzlib.WzProperty;
import net.swordie.ms.loaders.containerclasses.ItemInfo;
import net.swordie.ms.loaders.containerclasses.QuestInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.swordie.ms.util.Util;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.time.DayOfWeek;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created on 3/2/2018.
 */
public class QuestData {
    private static final Logger log = LogManager.getRootLogger();
    private static final boolean LOG_UNKS = false;
    private static final String QUESTS_FILE = ServerConstants.DAT_DIR + "/quests.dat";
    /**
     * Version of the quests.dat layout. Bump this whenever the layout written by saveAllQuestInfos changes; an
     * outdated quests.dat is then regenerated from Quest.wz automatically.
     */
    private static final int QUESTS_DAT_VERSION = 10; // 10: QuestInfo blocked
    private static final int ARCANE_SYMBOL_CHU_CHU_ISLAND = 1712002;
    private static final int ARCANE_SYMBOL_LACHELEIN = 1712003;
    private static final int ARCANE_SYMBOL_ARCANA = 1712004;
    private static final int ARCANE_SYMBOL_MORASS = 1712005;
    private static final int ARCANE_SYMBOL_ESFERA = 1712006;
    private static final String QUESTS_VERSION_FILE = QUESTS_FILE + ".version";

    private static Map<Integer, QuestInfo> baseQuests = new java.util.concurrent.ConcurrentHashMap<>(); // filled lazily by many threads at once
    private static volatile Map<Integer, Integer> questOffsets;

    /** A yyyyMMddHH(mm) quest date; 0 for the "#DL|...#" placeholders newer data uses for event dates. */
    private static long dateValue(WzObject<?, ?> node) {
        try {
            return Long.parseLong(node.toString().trim());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public static void loadQuestsFromWZ() {
        WzFile file = new WzFile(ServerConstants.WZ_DIR + "/Quest.wz");
        WzObject<?, ?> checkNode = file.getChild("Check.img");
        WzProperty reqNode;

        for (WzObject<?, ?> questIdNode : checkNode) {
            int questId = Integer.parseInt(questIdNode.getName());
            QuestInfo qi = new QuestInfo();
            qi.setQuestID(questId);
            for (WzObject<?, ?> statusNode : questIdNode) {
                if (!Util.isNumber(statusNode.getName())) {
                    continue;
                }
                int status = Integer.parseInt(statusNode.getName());
                // status 0 = requirements to start the quest, status 1 = requirements to complete it
                boolean start = status == 0;
                int npc = ((WzProperty) statusNode.getChild("npc", 0)).getIntValue();
                if (start) {
                    qi.setStartNpc(npc);
                } else {
                    qi.setEndNpc(npc);
                }

                WzObject<?, ?> infoNumber = statusNode.getChild("infoNumber");
                if (infoNumber != null) {
                    qi.setInfoNumber(((WzProperty) infoNumber).getIntValue());
                }

                WzObject<?, ?> fieldsetkeeptime = statusNode.getChild("fieldsetkeeptime");
                if (fieldsetkeeptime != null) {
                    qi.setFieldSetKeepTime(((WzProperty) fieldsetkeeptime).getIntValue());
                }

                WzObject<?, ?> subJobFlags = statusNode.getChild("subJobFlags");
                if (subJobFlags != null) {
                    qi.setSubJobFlags(((WzProperty) subJobFlags).getIntValue());
                }

                WzObject<?, ?> deathCount = statusNode.getChild("deathCount");
                if (deathCount != null) {
                    qi.setDeathCount(((WzProperty) deathCount).getIntValue());
                }

                WzObject<?, ?> mobDropMeso = statusNode.getChild("mobDropMeso");
                if (mobDropMeso != null) {
                    qi.setMobDropMeso(((WzProperty) mobDropMeso).getIntValue());
                }

                WzObject<?, ?> morph = statusNode.getChild("morph");
                if (morph != null) {
                    qi.setMorph(((WzProperty) morph).getIntValue());
                }

                WzObject<?, ?> startNode = statusNode.getChild("start");
                if (startNode != null) {
                    qi.setStart(dateValue(startNode));
                }

                WzObject<?, ?> startT = statusNode.getChild("start_t");
                if (startT != null) {
                    qi.setStartT(dateValue(startT));
                }

                WzObject<?, ?> end = statusNode.getChild("end");
                if (end != null) {
                    qi.setEnd(dateValue(end));
                }

                WzObject<?, ?> endT = statusNode.getChild("end_t");
                if (endT != null) {
                    qi.setEndT(dateValue(endT));
                }

                WzObject<?, ?> startScript = statusNode.getChild("startscript");
                if (startScript != null) {
                    qi.setStartScript(startScript.toString());
                }

                WzObject<?, ?> endScript = statusNode.getChild("endscript");
                if (endScript != null) {
                    qi.setEndScript(endScript.toString());
                }

                WzObject<?, ?> fieldset = statusNode.getChild("fieldset");
                if (fieldset != null) {
                    qi.setFieldSet(fieldset.toString());
                }

                WzObject<?, ?> normalAutoStart = statusNode.getChild("normalAutoStart");
                if (normalAutoStart != null) {
                    qi.setNormalAutoStart(((WzProperty) normalAutoStart).getIntValue() != 0);
                }

                WzObject<?, ?> completeNpcAutoGuide = statusNode.getChild("completeNpcAutoGuide");
                if (completeNpcAutoGuide != null) {
                    qi.setCompleteNpcAutoGuide(((WzProperty) completeNpcAutoGuide).getIntValue() != 0);
                }

                WzObject<?, ?> autoStart = statusNode.getChild("autoStart");
                if (autoStart != null) {
                    qi.setAutoStart(((WzProperty) autoStart).getIntValue() != 0);
                }

                WzObject<?, ?> scenarioQuest = statusNode.getChild("scenarioQuest");
                if (scenarioQuest != null) {
                    qi.setScenarioQuest(((WzProperty) scenarioQuest).getIntValue() != 0);
                }

                WzObject<?, ?> secret = statusNode.getChild("secret");
                if (secret != null) {
                    qi.setSecret(((WzProperty) secret).getIntValue() != 0);
                }

                // Repeat info (only meaningful on the start node)
                if (start) {
                    WzObject<?, ?> interval = statusNode.getChild("interval");
                    if (interval != null) {
                        qi.setRepeatInterval(Math.max(0, ((WzProperty) interval).getIntValue()));
                    }
                    WzObject<?, ?> dayByDay = statusNode.getChild("dayByDay");
                    if (dayByDay != null && ((WzProperty) dayByDay).getIntValue() != 0) {
                        qi.setDayByDay(true);
                    }
                    WzObject<?, ?> weeklyRepeat = statusNode.getChild("weeklyRepeat");
                    if (weeklyRepeat != null && ((WzProperty) weeklyRepeat).getIntValue() != 0) {
                        qi.setWeeklyRepeat(true);
                    }
                }

                // Simple requirements. On the start node these must hold to start the quest, on the complete node
                // they must hold to complete it.
                List<QuestStartRequirement> reqs = new ArrayList<>();

                reqNode = (WzProperty) statusNode.getChild("marriaged");
                if (reqNode != null) {
                    reqs.add(new QuestStartMarriageRequirement());
                }

                reqNode = (WzProperty) statusNode.getChild("lvmin");
                if (reqNode != null) {
                    reqs.add(new QuestStartMinStatRequirement(Stat.level, (short) reqNode.getIntValue()));
                }

                reqNode = (WzProperty) statusNode.getChild("pop");
                if (reqNode != null) {
                    reqs.add(new QuestStartMinStatRequirement(Stat.pop, (short) reqNode.getIntValue()));
                }

                reqNode = (WzProperty) statusNode.getChild("fameGradeReq");
                if (reqNode != null) {
                    reqs.add(new QuestStartMinStatRequirement(Stat.pop, (short) reqNode.getIntValue()));
                }

                reqNode = (WzProperty) statusNode.getChild("charismaMin");
                if (reqNode != null) {
                    reqs.add(new QuestStartMinStatRequirement(Stat.charismaEXP, (short) reqNode.getIntValue()));
                }

                reqNode = (WzProperty) statusNode.getChild("insightMin");
                if (reqNode != null) {
                    reqs.add(new QuestStartMinStatRequirement(Stat.insightEXP, (short) reqNode.getIntValue()));
                }

                reqNode = (WzProperty) statusNode.getChild("willMin");
                if (reqNode != null) {
                    reqs.add(new QuestStartMinStatRequirement(Stat.willEXP, (short) reqNode.getIntValue()));
                }

                reqNode = (WzProperty) statusNode.getChild("craftMin");
                if (reqNode != null) {
                    reqs.add(new QuestStartMinStatRequirement(Stat.craftEXP, (short) reqNode.getIntValue()));
                }

                reqNode = (WzProperty) statusNode.getChild("senseMin");
                if (reqNode != null) {
                    reqs.add(new QuestStartMinStatRequirement(Stat.senseEXP, (short) reqNode.getIntValue()));
                }

                reqNode = (WzProperty) statusNode.getChild("charm");
                if (reqNode != null) {
                    reqs.add(new QuestStartMinStatRequirement(Stat.charmEXP, (short) reqNode.getIntValue()));
                }

                reqNode = (WzProperty) statusNode.getChild("charmMin");
                if (reqNode != null) {
                    reqs.add(new QuestStartMinStatRequirement(Stat.charmEXP, (short) reqNode.getIntValue()));
                }

                reqNode = (WzProperty) statusNode.getChild("level");
                if (reqNode != null) {
                    qi.addProgressRequirement(new QuestProgressLevelRequirement(reqNode.getIntValue()));
                }

                reqNode = (WzProperty) statusNode.getChild("lvmax");
                if (reqNode != null) {
                    reqs.add(new QuestStartMaxLevelRequirement((short) reqNode.getIntValue()));
                }

                reqNode = (WzProperty) statusNode.getChild("endmeso");
                if (reqNode != null) {
                    qi.addProgressRequirement(new QuestProgressMoneyRequirement(reqNode.getIntValue()));
                }

                // Other quests' states (0 = not started, 1 = in progress, 2 = completed)
                WzObject<?, ?> questNode = statusNode.getChild("quest");
                if (questNode != null) {
                    for (WzObject<?, ?> qNode : questNode) {
                        QuestStartCompletionRequirement qcr = new QuestStartCompletionRequirement();
                        qcr.setQuestID(((WzProperty) qNode.getChild("id", 0)).getIntValue());
                        // no state means "not started" (e.g. the other 2nd job branches of Way of the Fighter)
                        int state = qcr.getQuestID() == 0 ? -1 : ((WzProperty) qNode.getChild("state", 0)).getIntValue();
                        qcr.setQuestStatus((byte) state);
                        if (qcr.getQuestStatus() != -1) {
                            reqs.add(qcr);
                        }
                    }
                }
                WzObject<?, ?> itemOrOption = statusNode.getChild("ItemOrOption");
                if (itemOrOption instanceof WzProperty && ((WzProperty) itemOrOption).getIntValue() != 0) {
                    if (start) {
                        qi.setStartItemOrOption(true);
                    } else {
                        qi.setCompleteItemOrOption(true);
                    }
                }
                WzObject<?, ?> questOrOption = statusNode.getChild("QuestOrOption");
                if (questOrOption != null && ((WzProperty) questOrOption).getIntValue() != 0) {
                    if (start) {
                        qi.setStartQuestOrOption(true);
                    } else {
                        qi.setCompleteQuestOrOption(true);
                    }
                }

                // Pet requirement: one of the listed pets (any pet with allPet) summoned, with enough closeness
                WzObject<?, ?> petNode = statusNode.getChild("pet");
                WzObject<?, ?> petTamenessNode = statusNode.getChild("pettamenessmin");
                if (petNode != null || petTamenessNode != null) {
                    Set<Integer> petIds = new HashSet<>();
                    boolean allPet = ((WzProperty) statusNode.getChild("allPet", 0)).getIntValue() != 0;
                    if (petNode != null && !allPet) {
                        for (WzObject<?, ?> petIdNode : petNode) {
                            int petId = ((WzProperty) petIdNode.getChild("id", 0)).getIntValue();
                            if (petId > 0) {
                                petIds.add(petId);
                            }
                        }
                    }
                    int minTameness = petTamenessNode == null ? 0 : ((WzProperty) petTamenessNode).getIntValue();
                    reqs.add(new QuestPetRequirement(petIds, minTameness));
                }

                // Job requirement: "job" plus the global-only additions in "job_GL" (and a typo'd "job ")
                QuestStartJobRequirement qsjr = new QuestStartJobRequirement();
                for (String jobKey : new String[]{"job", "job ", "job_GL"}) {
                    WzObject<?, ?> jobNode = statusNode.getChild(jobKey);
                    if (jobNode != null && !(jobNode instanceof WzEmpty)) {
                        for (WzObject<?, ?> jobIdNode : jobNode) {
                            if (jobIdNode instanceof WzProperty) {
                                qsjr.addJobReq((short) ((WzProperty) jobIdNode).getIntValue());
                            }
                        }
                    }
                }
                if (!qsjr.getJobReq().isEmpty()) {
                    reqs.add(qsjr);
                }

                // Quest record (infoex) requirement
                WzObject<?, ?> infoExNode = statusNode.getChild("infoex");
                if (infoExNode != null) {
                    int infoQuestId = ((WzProperty) statusNode.getChild("infoNumber", questId)).getIntValue();
                    if (infoQuestId == 0) {
                        infoQuestId = questId;
                    }
                    boolean andOption = ((WzProperty) statusNode.getChild("QuestRecordAndOption", 0)).getIntValue() != 0;
                    QuestRecordRequirement qrr = new QuestRecordRequirement(infoQuestId, andOption);
                    for (WzObject<?, ?> entryNode : infoExNode) {
                        WzObject<?, ?> valueNode = entryNode.getChild("value");
                        if (valueNode == null) {
                            continue;
                        }
                        int cond = ((WzProperty) entryNode.getChild("cond", 0)).getIntValue();
                        if (cond < 0 || cond > 2) {
                            cond = QuestRecordRequirement.COND_EQUAL;
                        }
                        String exVariable = entryNode.getChild("exVariable") == null ? "" : entryNode.getChild("exVariable").toString();
                        qrr.addEntry(valueNode.toString(), cond, exVariable);
                    }
                    if (!qrr.getEntries().isEmpty()) {
                        reqs.add(qrr);
                    }
                }

                // Skill requirement
                WzObject<?, ?> skillNode = statusNode.getChild("skill");
                if (skillNode != null) {
                    for (WzObject<?, ?> sn : skillNode) {
                        int skillId = ((WzProperty) sn.getChild("id", 0)).getIntValue();
                        if (skillId == 0) {
                            continue;
                        }
                        boolean acquire = ((WzProperty) sn.getChild("acquire", 0)).getIntValue() != 0;
                        int level = ((WzProperty) sn.getChild("level", 0)).getIntValue();
                        String levelCondition = sn.getChild("levelCondition") == null ? "" : sn.getChild("levelCondition").toString();
                        boolean exact = "match".equalsIgnoreCase(levelCondition.trim());
                        reqs.add(new QuestSkillRequirement(skillId, acquire, level, exact));
                    }
                }

                // Equipped item requirements
                QuestEquipRequirement qer = new QuestEquipRequirement();
                WzObject<?, ?> equipAllNode = statusNode.getChild("equipAllNeed");
                if (equipAllNode != null) {
                    for (WzObject<?, ?> en : equipAllNode) {
                        qer.addGroup(Collections.singletonList(((WzProperty) en).getIntValue()));
                    }
                }
                WzObject<?, ?> equipSelectNode = statusNode.getChild("equipSelectNeed");
                if (equipSelectNode != null) {
                    List<Integer> group = new ArrayList<>();
                    for (WzObject<?, ?> en : equipSelectNode) {
                        group.add(((WzProperty) en).getIntValue());
                    }
                    qer.addGroup(group);
                }
                WzObject<?, ?> itemEquipNode = statusNode.getChild("itemEquip");
                if (itemEquipNode != null) {
                    Map<Integer, List<Integer>> orGroups = new LinkedHashMap<>();
                    for (WzObject<?, ?> en : itemEquipNode) {
                        int id = ((WzProperty) en.getChild("id", 0)).getIntValue();
                        if (id == 0) {
                            continue;
                        }
                        int orGroup = ((WzProperty) en.getChild("orGroup", 0)).getIntValue();
                        if (orGroup == 0) {
                            qer.addGroup(Collections.singletonList(id));
                        } else {
                            orGroups.computeIfAbsent(orGroup, k -> new ArrayList<>()).add(id);
                        }
                    }
                    orGroups.values().forEach(qer::addGroup);
                }
                if (!qer.getGroups().isEmpty()) {
                    reqs.add(qer);
                }

                // World range
                WzObject<?, ?> worldMin = statusNode.getChild("worldmin");
                WzObject<?, ?> worldMax = statusNode.getChild("worldmax");
                if (worldMin != null || worldMax != null) {
                    int min = worldMin == null ? 0 : ((WzProperty) worldMin).getIntValue();
                    int max = worldMax == null ? Integer.MAX_VALUE : ((WzProperty) worldMax).getIntValue();
                    reqs.add(new QuestWorldRequirement(min, max));
                }

                // Day of week / hour of day windows
                QuestTimeRequirement qtr = new QuestTimeRequirement();
                WzObject<?, ?> dayOfWeekNode = statusNode.getChild("dayOfWeek");
                if (dayOfWeekNode != null) {
                    for (WzObject<?, ?> dn : dayOfWeekNode) {
                        DayOfWeek day = QuestTimeRequirement.dayByWzName(dn.getName());
                        if (day != null && ((WzProperty) dn).getIntValue() != 0) {
                            qtr.addDay(day);
                        }
                    }
                }
                WzObject<?, ?> hourOfDayNode = statusNode.getChild("hourOfDay");
                if (hourOfDayNode != null) {
                    for (WzObject<?, ?> hn : hourOfDayNode) {
                        int hour = ((WzProperty) hn).getIntValue();
                        if (hour >= 0 && hour < 24) {
                            qtr.addHour(hour);
                        }
                    }
                }
                if (!qtr.isEmpty()) {
                    reqs.add(qtr);
                }

                for (QuestStartRequirement req : reqs) {
                    if (start) {
                        qi.addRequirement(req);
                    } else {
                        qi.addCompleteRequirement(req);
                    }
                }

                WzObject<?, ?> scenarioQuestListNode = statusNode.getChild("scenarioQuestList");
                if (scenarioQuestListNode != null) {
                    for (WzObject<?, ?> scenNode : scenarioQuestListNode) {
                        qi.addScenario(((WzProperty) scenNode).getIntValue());
                    }
                }

                WzObject<?, ?> fieldEnterNode = statusNode.getChild("fieldEnter");
                if (fieldEnterNode != null) {
                    for (WzObject<?, ?> feNode : fieldEnterNode) {
                        qi.addFieldEnter(((WzProperty) feNode).getIntValue());
                    }
                }

                WzObject<?, ?> mobNode = statusNode.getChild("mob");
                if (mobNode != null) {
                    for (WzObject<?, ?> mn : mobNode) {
                        QuestProgressMobRequirement qpmr = new QuestProgressMobRequirement();
                        qpmr.setOrder(Integer.parseInt(mn.getName()));
                        qpmr.setMobID(((WzProperty) mn.getChild("id", 0)).getIntValue());
                        qpmr.setRequiredCount(((WzProperty) mn.getChild("count", 0)).getIntValue());
                        qi.addProgressRequirement(qpmr);
                    }
                }

                WzObject<?, ?> itemNode = statusNode.getChild("item");
                if (itemNode != null) {
                    for (WzObject<?, ?> in : itemNode) {
                        QuestStartItemRequirement qir = new QuestStartItemRequirement();
                        QuestProgressItemRequirement qpir = new QuestProgressItemRequirement();
                        int order = Integer.parseInt(in.getName());
                        int count = ((WzProperty) in.getChild("count", 0)).getIntValue();
                        int itemId = ((WzProperty) in.getChild("id", 0)).getIntValue();

                        if (start) {
                            qir.setId(itemId);
                            qir.setQuantity(count);
                            qi.addRequirement(qir);
                        } else {
                            qpir.setOrder(order);
                            qpir.setItemID(itemId);
                            qpir.setRequiredCount(count);
                            qi.addProgressRequirement(qpir);
                        }
                    }
                }

                var exProgressRequirements = new String[] {
                        "runeAct", "multiKillCount", "comboKill", "breakTimeField"
                };
                for (var exProgressRequirement : exProgressRequirements) {
                    var exProgressRequirementNode = statusNode.getChild(exProgressRequirement);
                    if (exProgressRequirementNode != null) {
                        var name = exProgressRequirementNode.getName();
                        var value = ((WzProperty) exProgressRequirementNode).getIntValue();
                        qi.getExProgressRequirements().put(name, value);
                    }
                }

                WzObject<?, ?> speechNode = statusNode.getChild("npcSpeech");
                if (speechNode != null) {
                    String speechValue = "NpcSpeech=";
                    for (WzObject<?, ?> sn : speechNode) {
                        qi.addSpeech(((WzProperty<String>) sn.getChild("script", "")).getValue());

                        boolean hasSpeech = sn.hasChild("script");
                        int templateId = ((WzProperty) sn.getChild("id", 0)).getIntValue();
                        int order = ((WzProperty) sn.getChild("order", 0)).getIntValue();

                        if (hasSpeech && templateId != 0) {
                            speechValue += templateId + (order + "/");
                            qi.addSpeech(speechValue);
                        }
                    }
                }


            }
            getBaseQuests().put(qi.getQuestID(), qi);
            questIdNode.clear();
        }
        checkNode.clear();

        Map<String, Map<Integer, Long>> expTables = loadExpTables(file);
        WzObject<?, ?> actNode = file.getChild("Act.img");
        for (WzObject<?, ?> questIdNode : actNode) {
            int id = Integer.parseInt(questIdNode.getName());
            QuestInfo qi = getQuestInfo(id);
            if (qi == null) {
                // quest only has actions, no requirements
                qi = new QuestInfo();
                qi.setQuestID(id);
                getBaseQuests().put(id, qi);
            }
            for (WzObject<?, ?> statusNode : questIdNode) {
                if (!Util.isNumber(statusNode.getName())) {
                    continue;
                }
                int status = Integer.parseInt(statusNode.getName());

                reqNode = (WzProperty) statusNode.getChild("transferField");
                if(reqNode != null)
                {
                    qi.addTransferField(status, reqNode.getIntValue());
                }
                reqNode = (WzProperty) statusNode.getChild("nextQuest");
                if (reqNode != null && reqNode.getIntValue() != 0) {
                    qi.setNextQuest(reqNode.getIntValue());
                }

                // with their state: exp/meso/fame in Act.img/[id]/0 are given (or taken) when the quest is started
                reqNode = (WzProperty) statusNode.getChild("exp");
                if (reqNode != null) {
                    qi.addReward(new QuestExpReward(reqNode.getLongValue(), status));
                }

                WzObject<?, ?> expTableNode = statusNode.getChild("expTable");
                if (expTableNode != null) {
                    Map<Integer, Long> table = expTables.get(expTableNode.toString());
                    if (table != null && !table.isEmpty()) {
                        qi.addReward(new QuestExpTableReward(status, table));
                    } else {
                        log.warn(String.format("Quest %d uses the unknown exp table %s.", id, expTableNode));
                    }
                }

                reqNode = (WzProperty) statusNode.getChild("money");
                if (reqNode != null) {
                    qi.addReward(new QuestMoneyReward(reqNode.getLongValue(), status));
                }

                reqNode = (WzProperty) statusNode.getChild("pop");
                if (reqNode != null) {
                    qi.addReward(new QuestPopReward(reqNode.getIntValue(), status));
                }

                // pet rewards: closeness and pet skills for the summoned pet
                int petTameness = ((WzProperty) statusNode.getChild("pettameness", 0)).getIntValue();
                int petSkill = ((WzProperty) statusNode.getChild("petskill", 0)).getIntValue();
                if (petTameness > 0 || petSkill > 0) {
                    qi.addReward(new QuestPetReward(status, petTameness, petSkill));
                }

                // other quests' states that change with this one
                WzObject<?, ?> actQuestNode = statusNode.getChild("quest");
                if (actQuestNode != null) {
                    for (WzObject<?, ?> qNode : actQuestNode) {
                        int otherId = ((WzProperty) qNode.getChild("id", 0)).getIntValue();
                        int otherState = ((WzProperty) qNode.getChild("state", 0)).getIntValue();
                        if (otherId != 0 && otherId != id && (otherState == 1 || otherState == 2)) {
                            qi.addReward(new QuestStateReward(status, otherId, otherState));
                        }
                    }
                }

                reqNode = (WzProperty) statusNode.getChild("buffItemID");
                if (reqNode != null) {
                    qi.addReward(new QuestBuffItemReward(reqNode.getIntValue(), status));
                }

                WzObject<?, ?> itemTopNode = statusNode.getChild("item");
                if (itemTopNode != null) {
                    for (WzObject<?, ?> itemNode : itemTopNode) {
                        QuestItemReward qir = new QuestItemReward();
                        qir.setStatus(status);
                        qir.setId(((WzProperty) itemNode.getChild("id", 0)).getIntValue());
                        qir.setProp(((WzProperty) itemNode.getChild("prop", 0)).getIntValue());
                        qir.setQuantity((short) ((WzProperty) itemNode.getChild("count", 0)).getIntValue());
                        qir.setPotentialGrade(((WzProperty<String>) itemNode.getChild("potentialGrade", "")).getValue());
                        // absent gender = both genders
                        qir.setGender(((WzProperty) itemNode.getChild("gender", QuestItemReward.GENDER_BOTH)).getIntValue());
                        qir.setJob(((WzProperty) itemNode.getChild("job", 0)).getIntValue());
                        qir.setJobEx(((WzProperty) itemNode.getChild(0, "jobEx", "jobEx_GL")).getIntValue());
                        qir.setPeriod(((WzProperty) itemNode.getChild("period", 0)).getIntValue());
                        WzObject<?, ?> dateExpire = itemNode.getChild("dateExpire");
                        qir.setDateExpire(dateExpire == null ? "" : dateExpire.toString());
                        qir.setResignRemove(((WzProperty) itemNode.getChild("resignRemove", 0)).getIntValue() != 0);
                        if (qir.getId() != 0) {
                            qi.addReward(qir);
                        }
                    }
                }

                // Skill rewards
                WzObject<?, ?> skillTopNode = statusNode.getChild("skill");
                if (skillTopNode != null) {
                    for (WzObject<?, ?> skillNode : skillTopNode) {
                        int skillId = ((WzProperty) skillNode.getChild("id", 0)).getIntValue();
                        if (skillId == 0) {
                            continue;
                        }
                        QuestSkillReward qsr = new QuestSkillReward(status, skillId,
                                ((WzProperty) skillNode.getChild("skillLevel", 0)).getIntValue(),
                                ((WzProperty) skillNode.getChild("masterLevel", 0)).getIntValue(),
                                ((WzProperty) skillNode.getChild("onlyMasterLevel", 0)).getIntValue() != 0);
                        WzObject<?, ?> jobNode = skillNode.getChild("job");
                        if (jobNode != null) {
                            for (WzObject<?, ?> jn : jobNode) {
                                qsr.addJob((short) ((WzProperty) jn).getIntValue());
                            }
                        }
                        qi.addReward(qsr);
                    }
                }

                // SP rewards
                WzObject<?, ?> spTopNode = statusNode.getChild("sp");
                if (spTopNode != null) {
                    for (WzObject<?, ?> spNode : spTopNode) {
                        QuestSpReward qsr = new QuestSpReward(status,
                                ((WzProperty) spNode.getChild("sp_value", 0)).getIntValue());
                        WzObject<?, ?> jobNode = spNode.getChild("job");
                        if (jobNode != null) {
                            for (WzObject<?, ?> jn : jobNode) {
                                qsr.addJob((short) ((WzProperty) jn).getIntValue());
                            }
                        }
                        qi.addReward(qsr);
                    }
                }

                // Trait EXP rewards
                for (String traitKey : new String[]{"charismaEXP", "insightEXP", "willEXP", "craftEXP", "senseEXP", "charmEXP"}) {
                    reqNode = (WzProperty) statusNode.getChild(traitKey);
                    if (reqNode != null && reqNode.getIntValue() > 0) {
                        qi.addReward(new QuestTraitExpReward(status, QuestTraitExpReward.getTraitByWzName(traitKey),
                                reqNode.getIntValue()));
                    }
                }

                // Quest record changes
                WzObject<?, ?> infoNode = statusNode.getChild("info");
                if (infoNode instanceof WzProperty && ((WzProperty<?>) infoNode).getPropertyType() == WzProperty.Type.STRING) {
                    qi.addReward(new QuestRecordReward(status, id, "", infoNode.toString()));
                }
                WzObject<?, ?> setExNode = statusNode.getChild("setEx");
                if (setExNode != null) {
                    for (WzObject<?, ?> sn : setExNode) {
                        int qrId = ((WzProperty) sn.getChild("qrID", id)).getIntValue();
                        WzObject<?, ?> key = sn.getChild("key");
                        WzObject<?, ?> value = sn.getChild("value");
                        if (key != null && !key.toString().isEmpty()) {
                            qi.addReward(new QuestRecordReward(status, qrId, key.toString(),
                                    value == null ? "" : value.toString()));
                        } else if (value != null && sn.getChild("qrID") != null) {
                            // no key: the whole record of qrID is set to the value (e.g. 3562 sets 7081 to "1")
                            qi.addReward(new QuestRecordReward(status, qrId, "", value.toString()));
                        }
                    }
                }
            }
        }
        actNode.clear();

        WzObject<?, ?> questInfoNode = file.getChild("QuestInfo.img");
        for (WzObject<?, ?> qiNode : questInfoNode) {
            int id = Integer.parseInt(qiNode.getName());
            QuestInfo qi = getQuestInfo(id);
            if (qi == null) {
                qi = new QuestInfo();
                qi.setQuestID(id);
                getBaseQuests().put(id, qi);
            }
            qi.setAutoComplete(((WzProperty) qiNode.getChild("autoComplete", 0)).getIntValue() != 0);
            qi.setSelfStart(((WzProperty) qiNode.getChild("selfStart", 0)).getIntValue() != 0);
            qi.setSelfComplete(((WzProperty) qiNode.getChild("selfComplete", 0)).getIntValue() != 0);
            qi.setStartNavi(((WzProperty) qiNode.getChild("startNavi", 0)).getIntValue() != 0);
            boolean blocked = ((WzProperty) qiNode.getChild("blocked", 0)).getIntValue() != 0;
            qi.setBlocked(blocked);
            qi.setHiddenInQuestLog(((WzProperty) qiNode.getChild("disableAtPerformTab", 0)).getIntValue() != 0
                    || blocked);
            qi.setMedalItemId(((WzProperty) qiNode.getChild("viewMedalItem", 0)).getIntValue());
            qi.setArea(((WzProperty) qiNode.getChild("area", 0)).getIntValue());
            qi.setDailyAlarm(((WzProperty) qiNode.getChild("dailyAlarm", 0)).getIntValue() != 0);
            int timeLimit = ((WzProperty) qiNode.getChild("timeLimit", 0)).getIntValue();
            if (timeLimit == 0) {
                timeLimit = ((WzProperty) qiNode.getChild("timeLimit2", 0)).getIntValue();
            }
            qi.setTimeLimit(timeLimit);
            WzObject<?, ?> resignScript = qiNode.getChild("resignScript");
            if (resignScript != null) {
                qi.setResignationScript(resignScript.toString());
            }
        }
        questInfoNode.clear();
        file.clear();
    }

    /**
     * Quest.wz/ChangeableQExpTable.img: the level -> EXP tables that Act.img "expTable" refers to.
     */
    private static Map<String, Map<Integer, Long>> loadExpTables(WzFile file) {
        Map<String, Map<Integer, Long>> tables = new HashMap<>();
        WzObject<?, ?> tablesNode = file.getChild("ChangeableQExpTable.img");
        if (tablesNode == null) {
            return tables;
        }
        for (WzObject<?, ?> tableNode : tablesNode) {
            if (tableNode instanceof WzProperty
                    && ((WzProperty<?>) tableNode).getPropertyType() != WzProperty.Type.SUB_PROPERTY) {
                continue; // the "info" note (the tables themselves are sub properties)
            }
            Map<Integer, Long> table = new TreeMap<>();
            for (WzObject<?, ?> levelNode : tableNode) {
                if (Util.isNumber(levelNode.getName()) && levelNode instanceof WzProperty) {
                    table.put(Integer.parseInt(levelNode.getName()), ((WzProperty) levelNode).getLongValue());
                }
            }
            tables.put(tableNode.getName(), table);
        }
        return tables;
    }

    public static Map<Integer, QuestInfo> getBaseQuests() {
        return baseQuests;
    }

    public static void linkMobData() {
        if (getBaseQuests().size() == 0) {
            loadQuestsFromWZ();
        }
        if (MobData.getMobs().size() == 0) {
            MobData.loadMobsFromWz();
        }
        for (QuestInfo qi : getBaseQuests().values()) {
            for (QuestProgressMobRequirement qpmr :
                    qi.getQuestProgressRequirements()
                            .stream()
                            .filter(q -> q instanceof QuestProgressMobRequirement)
                            .map(q -> (QuestProgressMobRequirement) q)
                            .collect(Collectors.toSet())) { // readability is overrated
                var m = MobData.getMobs().get(qpmr.getMobID());
                if (m != null) {
                    m.addQuest(qi.getQuestID());
                    for (int childId : m.getMobSet()) {
                        var childMob = MobData.getMobInfoById(childId);
                        if (childMob != null) {
                            childMob.addQuest(qi.getQuestID());
                        }
                    }
                }
            }
        }
    }

    public static void linkItemData() {
        if (getBaseQuests().size() == 0) {
            loadQuestsFromWZ();
        }
        for (QuestInfo qi : getBaseQuests().values()) {
            for (QuestProgressItemRequirement qpmr :
                    qi.getQuestProgressRequirements()
                            .stream()
                            .filter(q -> q instanceof QuestProgressItemRequirement)
                            .map(q -> (QuestProgressItemRequirement) q)
                            .collect(Collectors.toSet())) { // readability is overrated
                int itemID = qpmr.getItemID();
                if (ItemConstants.isEquip(itemID)) {
                    // create new ItemInfos just for equips that are required for quests
                    // normally ItemInfo is just for non-equips.
                    ItemInfo ii = new ItemInfo();
                    ii.setItemId(itemID);
                    ii.setInvType(InvType.EQUIP);
                    ii.addQuest(qi.getQuestID());
                    ItemData.addItemInfo(ii);
                } else {
                    ItemInfo item = ItemData.getItems().get(qpmr.getItemID());
                    if (item != null) {
                        item.addQuest(qi.getQuestID());
                    }
                }
            }
        }
        Map<Integer, Integer> itemIdToQuestMap = new HashMap<>();
        itemIdToQuestMap.put(ItemConstants.ARCANE_SYMBOL_VANISHING_JOURNEY, 34120);
        itemIdToQuestMap.put(ARCANE_SYMBOL_CHU_CHU_ISLAND, 34216);
        itemIdToQuestMap.put(ARCANE_SYMBOL_LACHELEIN, 34330);
        itemIdToQuestMap.put(ARCANE_SYMBOL_ARCANA, 34478);
        itemIdToQuestMap.put(ARCANE_SYMBOL_MORASS, 34272);
        itemIdToQuestMap.put(ARCANE_SYMBOL_ESFERA, 37871);

        for (var entry : itemIdToQuestMap.entrySet()) {
            var itemId = entry.getKey();
            var questId = entry.getValue();

            ItemInfo item = ItemData.getItems().get(itemId);
            if (item != null) {
                item.addQuest(questId);
            }
        }
    }

    public static void generateDatFiles() {
        log.info("Started generating quest data.");
        long start = System.currentTimeMillis();
        if (getBaseQuests().size() == 0) {
            loadQuestsFromWZ();
        }
        saveAllQuestInfos(QUESTS_FILE);
        try {
            Files.write(new File(QUESTS_VERSION_FILE).toPath(),
                    String.valueOf(QUESTS_DAT_VERSION).getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            log.error("Could not write " + QUESTS_VERSION_FILE, e);
        }
        log.info(String.format("Completed generating quest data in %dms.", System.currentTimeMillis() - start));
    }

    /**
     * Regenerates quests.dat from Quest.wz when it was written by an older version of this loader (its layout would
     * otherwise be read incorrectly). Called at server startup, before any quest is loaded.
     */
    public static synchronized void ensureDatUpToDate() {
        if (questOffsets != null) {
            return;
        }
        int version = 0;
        File versionFile = new File(QUESTS_VERSION_FILE);
        if (versionFile.exists()) {
            try {
                version = Integer.parseInt(new String(Files.readAllBytes(versionFile.toPath()), StandardCharsets.UTF_8).trim());
            } catch (IOException | NumberFormatException e) {
                version = 0;
            }
        }
        if (version != QUESTS_DAT_VERSION || !new File(QUESTS_FILE).exists()) {
            log.warn(String.format("quests.dat is outdated (version %d, expected %d). Regenerating it from Quest.wz.",
                    version, QUESTS_DAT_VERSION));
            getBaseQuests().clear();
            generateDatFiles();
        }
    }

    private static void saveAllQuestInfos(String file) {
        int tableSize = getBaseQuests().size() * 8 + 4; // 4 for id, 4 for offset pos, single 4 for size
        questOffsets = new HashMap<>();
        try (RandomAccessFile raf = new RandomAccessFile(file, IOHelper.READ_WRITE_MODE)) {
            raf.seek(tableSize);
            for (QuestInfo qi : getBaseQuests().values()) {
                questOffsets.put(qi.getQuestID(), (int) raf.getFilePointer());
                raf.writeInt(qi.getQuestID());
                raf.writeInt(qi.getStartNpc());
                raf.writeBoolean(qi.isNormalAutoStart());
                raf.writeShort(qi.getQuestStartRequirements().size());
                for (QuestStartRequirement qsr : qi.getQuestStartRequirements()) {
                    raf.writeByte(QuestStartRequirementType.getQPRTByObj(qsr).getVal());
                    qsr.write(raf);
                }
                raf.writeShort(qi.getQuestProgressRequirements().size());
                for (QuestProgressRequirement qpr : qi.getQuestProgressRequirements()) {
                    raf.writeByte(QuestProgressRequirementType.getQPRTByObj(qpr).getVal());
                    qpr.write(raf);
                }
                raf.writeShort(qi.getQuestRewards().size());
                for (QuestReward qr : qi.getQuestRewards()) {
                    raf.writeByte(QuestRewardType.getQPRTByObj(qr).getVal());
                    qr.write(raf);
                }
                raf.writeShort(qi.getFieldEnters().size());
                for (int i : qi.getFieldEnters()) {
                    raf.writeInt(i);
                }
                raf.writeInt(qi.getInfoNumber());
                raf.writeLong(qi.getEnd());
                raf.writeLong(qi.getEndT());
                raf.writeUTF(qi.getStartScript());
                raf.writeUTF(qi.getEndScript());
                raf.writeLong(qi.getStart());
                raf.writeLong(qi.getStartT());
                raf.writeInt(qi.getEndNpc());
                raf.writeInt(qi.getSubJobFlags());
                raf.writeBoolean(qi.isCompleteNpcAutoGuide());
                raf.writeInt(qi.getSkill());
                raf.writeInt(qi.getFieldSetKeepTime());
                raf.writeUTF(qi.getFieldSet());
                raf.writeBoolean(qi.isAutoStart());
                raf.writeInt(qi.getDeathCount());
                raf.writeShort(qi.getScenarios().size());
                for (int i : qi.getScenarios()) {
                    raf.writeInt(i);
                }
                raf.writeShort(qi.getSpeech().size());
                for (String i : qi.getSpeech()) {
                    raf.writeUTF(i);
                }
                raf.writeShort(qi.getExProgressRequirements().size());
                for (var entry : qi.getExProgressRequirements().entrySet()) {
                    raf.writeUTF(entry.getKey());
                    raf.writeInt(entry.getValue());
                }
                raf.writeInt(qi.getMobDropMeso());
                raf.writeInt(qi.getMorph());
                raf.writeBoolean(qi.isSecret());
                if (qi.getTransferFields() == null){
                    qi.setTransferFields(new HashMap<>());
                }
                raf.writeShort(qi.getTransferFields().size());
                for (Map.Entry<Integer, Integer> entry : qi.getTransferFields().entrySet()) {
                    raf.writeInt(entry.getKey());
                    raf.writeInt(entry.getValue());
                }
                raf.writeInt(qi.getNextQuest());
                raf.writeBoolean(qi.isAutoComplete());
                raf.writeInt(qi.getMedalItemId());
                raf.writeInt(qi.getTimeLimit());
                raf.writeUTF(qi.getResignationScript());
                // version 2
                raf.writeShort(qi.getQuestCompleteRequirements().size());
                for (QuestStartRequirement qsr : qi.getQuestCompleteRequirements()) {
                    raf.writeByte(QuestStartRequirementType.getQPRTByObj(qsr).getVal());
                    qsr.write(raf);
                }
                raf.writeBoolean(qi.isStartQuestOrOption());
                raf.writeBoolean(qi.isCompleteQuestOrOption());
                raf.writeInt(qi.getRepeatInterval());
                raf.writeBoolean(qi.isDayByDay());
                raf.writeBoolean(qi.isWeeklyRepeat());
                raf.writeBoolean(qi.isScenarioQuest());
                // version 3
                raf.writeBoolean(qi.isStartNavi());
                // version 4
                raf.writeBoolean(qi.isHiddenInQuestLog());
                // version 5
                raf.writeBoolean(qi.isStartItemOrOption());
                raf.writeBoolean(qi.isCompleteItemOrOption());
                // version 6
                raf.writeInt(qi.getArea());
                raf.writeBoolean(qi.isSelfStart());
                raf.writeBoolean(qi.isSelfComplete());
                // version 9
                raf.writeBoolean(qi.isDailyAlarm());
                // version 10
                raf.writeBoolean(qi.isBlocked());
            }
            IOHelper.writeOffsetTable(questOffsets, raf);
        } catch (IOException e) {
            log.error("QuestData.saveAllQuestInfos", e);
        }
    }

    private static QuestInfo getQuestInfo(int id) {
        return getBaseQuests().get(id);
    }

    public static QuestInfo getQuestInfoById(int id) {
        QuestInfo qi = getQuestInfo(id);
        return qi == null ? loadQuestInfoById(id) : qi;
    }

    private static QuestInfo loadQuestInfoById(int id) {
        ensureDatUpToDate();
        if (questOffsets == null) {
            synchronized (QuestData.class) { // loaded once; the map is shown only when it's complete
                if (questOffsets == null) {
                    Map<Integer, Integer> loadedOffsets = new HashMap<>();
                    IOHelper.loadOffsets(loadedOffsets, QUESTS_FILE);
                    questOffsets = loadedOffsets;
                }
            }
        }
        if (!questOffsets.containsKey(id)) {
            return null;
        }
        QuestInfo qi = null;
        try (RandomAccessFile raf = new RandomAccessFile(QUESTS_FILE, IOHelper.READ_MODE)) {
            raf.seek(questOffsets.get(id));
            qi = new QuestInfo();
            qi.setQuestID(raf.readInt());
            qi.setStartNpc(raf.readInt());
            qi.setNormalAutoStart(raf.readBoolean());
            short size = raf.readShort();
            for (int i = 0; i < size; i++) {
                QuestStartRequirementType qsrt = QuestStartRequirementType.getQPRTByVal(raf.readByte());
                qi.addRequirement(qsrt.load(raf));
            }
            size = raf.readShort();
            for (int i = 0; i < size; i++) {
                QuestProgressRequirementType qprt = QuestProgressRequirementType.getQPRTByVal(raf.readByte());
                qi.addProgressRequirement(qprt.load(raf));
            }
            size = raf.readShort();
            for (int i = 0; i < size; i++) {
                QuestRewardType qr = QuestRewardType.getQPRTByVal(raf.readByte());
                qi.addReward(qr.load(raf));
            }
            size = raf.readShort();
            for (int i = 0; i < size; i++) {
                qi.addFieldEnter(raf.readInt());
            }
            qi.setInfoNumber(raf.readInt());
            qi.setEnd(raf.readLong());
            qi.setEndT(raf.readLong());
            qi.setStartScript(raf.readUTF());
            qi.setEndScript(raf.readUTF());
            qi.setStart(raf.readLong());
            qi.setStartT(raf.readLong());
            qi.setEndNpc(raf.readInt());
            qi.setSubJobFlags(raf.readInt());
            qi.setCompleteNpcAutoGuide(raf.readBoolean());
            qi.setSkill(raf.readInt());
            qi.setFieldSetKeepTime(raf.readInt());
            qi.setFieldSet(raf.readUTF());
            qi.setAutoStart(raf.readBoolean());
            qi.setDeathCount(raf.readInt());
            size = raf.readShort();
            for (int i = 0; i < size; i++) {
                qi.addScenario(raf.readInt());
            }
            size = raf.readShort();
            for (int i = 0; i < size; i++) {
                qi.addSpeech(raf.readUTF());
            }
            size = raf.readShort();
            for (int i = 0; i < size; i++) {
                qi.getExProgressRequirements().put(raf.readUTF(), raf.readInt());
            }
            qi.setMobDropMeso(raf.readInt());
            qi.setMorph(raf.readInt());
            qi.setSecret(raf.readBoolean());
            size = raf.readShort();
            for (int i = 0; i < size; i++) {
                int status = raf.readInt();
                int transferField = raf.readInt();
                qi.addTransferField(status, transferField);
            }
            qi.setNextQuest(raf.readInt());
            qi.setAutoComplete(raf.readBoolean());
            qi.setMedalItemId(raf.readInt());
            qi.setTimeLimit(raf.readInt());
            qi.setResignationScript(raf.readUTF());
            // version 2
            size = raf.readShort();
            for (int i = 0; i < size; i++) {
                QuestStartRequirementType qsrt = QuestStartRequirementType.getQPRTByVal(raf.readByte());
                qi.addCompleteRequirement(qsrt.load(raf));
            }
            qi.setStartQuestOrOption(raf.readBoolean());
            qi.setCompleteQuestOrOption(raf.readBoolean());
            qi.setRepeatInterval(raf.readInt());
            qi.setDayByDay(raf.readBoolean());
            qi.setWeeklyRepeat(raf.readBoolean());
            qi.setScenarioQuest(raf.readBoolean());
            // version 3
            qi.setStartNavi(raf.readBoolean());
            // version 4
            qi.setHiddenInQuestLog(raf.readBoolean());
            // version 5
            qi.setStartItemOrOption(raf.readBoolean());
            qi.setCompleteItemOrOption(raf.readBoolean());
            // version 6
            qi.setArea(raf.readInt());
            qi.setSelfStart(raf.readBoolean());
            qi.setSelfComplete(raf.readBoolean());
            // version 9
            qi.setDailyAlarm(raf.readBoolean());
            // version 10
            qi.setBlocked(raf.readBoolean());
            getBaseQuests().put(qi.getQuestID(), qi);
        } catch (IOException e) {
            log.error(String.format("IOException when loading %d", qi.getQuestID()));
            log.error("QuestData.loadQuestInfoById", e);
        }
        return qi;
    }

    public static Quest createQuestFromId(int questID) {
        QuestInfo qi = getQuestInfoById(questID);
        Quest quest = new Quest();
        quest.setQRKey(questID);
        if (qi != null) {
            if (qi.isAutoComplete()) {
                quest.setStatus(QuestStatus.Started);
//            quest.completeQuest(); // TODO check what autocomplete actually means
            } else {
                quest.setStatus(QuestStatus.Started);
            }
            for (QuestProgressRequirement qpr : qi.getQuestProgressRequirements()) {
                quest.addQuestProgressRequirement(qpr.deepCopy());
            }
        } else {
            quest.setStatus(QuestStatus.Started);
        }
        return quest;
    }

    public static void main(String[] args) {
        generateDatFiles();
    }

    public static void clear() {
        getBaseQuests().clear();
        if (questOffsets != null) {
            questOffsets = null;
        }
    }
}
