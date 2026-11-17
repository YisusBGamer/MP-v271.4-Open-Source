package net.swordie.ms.loaders;

import net.swordie.ms.ServerConstants;
import net.swordie.ms.client.character.keys.FuncKeyRules;
import net.swordie.ms.client.character.skills.Skill;
import net.swordie.ms.client.character.skills.SkillStat;
import net.swordie.ms.client.character.skills.info.SecondAtomInfo;
import net.swordie.ms.client.character.skills.info.SkillInfo;
import net.swordie.ms.constants.JobConstants;
import net.swordie.ms.constants.SkillConstants;
import net.swordie.ms.enums.WeaponType;
import net.swordie.ms.jwzlib.WzFile;
import net.swordie.ms.jwzlib.WzObject;
import net.swordie.ms.jwzlib.WzProperty;
import net.swordie.ms.life.mob.skill.MobSkillStat;
import net.swordie.ms.loaders.containerclasses.MakingSkillRecipe;
import net.swordie.ms.loaders.containerclasses.MobSkillInfo;
import net.swordie.ms.util.*;
import net.swordie.ms.util.container.Tuple;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.python.jline.internal.Log;

import java.awt.*;
import java.io.*;
import java.util.List;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Created on 12/20/2017.
 */
public class SkillData {
    private static final String SKILLS_FILE = ServerConstants.DAT_DIR + "/skills.dat";
    private static final String MOB_SKILLS_FILE = ServerConstants.DAT_DIR + "/mobSkills.dat";
    private static final String MAKING_SKILL_RECIPES_FILE = ServerConstants.DAT_DIR + "/recipes.dat";

    // Filled on demand from several threads (logins, job changes, the probes) while others read it
    private static Map<Integer, SkillInfo> skills = new java.util.concurrent.ConcurrentHashMap<>();
    private static volatile Map<Integer, Integer> skillOffsets;
    private static Map<Integer, Map<Integer, Integer>> eliteMobSkills = new HashMap<>();
    private static Map<Short, Map<Short, MobSkillInfo>> mobSkillInfos = new HashMap<>();
    private static volatile Map<Integer, Integer> mobSkillInfoOffsets;
    private static Map<Integer, MakingSkillRecipe> makingSkillRecipes = new HashMap<>();
    private static volatile Map<Integer, Integer> makingSkillRecipeOffsets;
    private static final Logger log = LogManager.getRootLogger();
    private static final boolean LOG_UNKS = false;
    private static final Set<Integer> missingLogged = ConcurrentHashMap.newKeySet();
    /** Jobs whose skills loadSkillsOfJob has read in. */
    private static final Set<Integer> loadedJobs = ConcurrentHashMap.newKeySet();
    /** getSkillsByJob's skill ids per job, kept while no other skill is loaded (loaded = the skill count then). */
    private static final Map<Short, JobSkillIds> jobSkillIds = new ConcurrentHashMap<>();

    private record JobSkillIds(int loaded, List<Integer> ids) {
    }

    public static void saveSkills(String file) {
        int tableSize = skills.size() * 8 + 4; // 4 for id, 4 for offset pos, single 4 for size
        skillOffsets = new HashMap<>();
        try (RandomAccessFile raf = new RandomAccessFile(file, IOHelper.READ_WRITE_MODE)) {
            raf.seek(tableSize);
            for (Map.Entry<Integer, SkillInfo> entry : skills.entrySet()) {
                SkillInfo si = entry.getValue();
                skillOffsets.put(si.getSkillId(), (int) raf.getFilePointer());
                raf.writeInt(si.getSkillId());
                raf.writeInt(si.getRootId());
                raf.writeInt(si.getMaxLevel());
                raf.writeInt(si.getMasterLevel());
                raf.writeInt(si.getFixLevel());
                raf.writeBoolean(si.isInvisible());
                raf.writeBoolean(si.isMassSpell());
                raf.writeInt(si.getInfoType());
                raf.writeInt(si.getType());
                raf.writeUTF(si.getElemAttr());
                raf.writeInt(si.getHyper());
                raf.writeInt(si.getHyperStat());
                raf.writeInt(si.getVehicleId());
                raf.writeInt(si.getReqTierPoint());
                raf.writeBoolean(si.isNotCooltimeReset());
                raf.writeBoolean(si.isNotIncBuffDuration());
                raf.writeBoolean(si.isNotCooltimeReduce()); // dat part version 2
                raf.writeByte(si.getvSkill());
                raf.writeInt(si.getNormalGauge());
                raf.writeBoolean(si.isBallSkill());
                raf.writeBoolean(si.isSummon());
                raf.writeBoolean(si.isDragonAction());
                raf.writeBoolean(si.isAffectedArea());
                raf.writeBoolean(si.isFinalAttack());
                raf.writeBoolean(si.isShootObject());
                raf.writeBoolean(si.isPsd());
                raf.writeInt(si.getAssistSkillLink());
                raf.writeInt(si.getKeyDownConsumptionInterval());
                raf.writeBoolean(si.isPetAutoBuff());
                raf.writeUTF(si.getMes()); // dat part version 5
                raf.writeByte(si.getKeyFlags()); // dat part version 6

                raf.writeShort(si.getSkillStatInfo().size());
                for (Map.Entry<SkillStat, String> ssEntry : si.getSkillStatInfo().entrySet()) {
                    raf.writeUTF(ssEntry.getKey().toString());
                    if (ssEntry.getValue() == null) {
                        raf.writeUTF("");
                    } else {
                        raf.writeUTF(ssEntry.getValue());
                    }
                }
                raf.writeShort(si.getRects().size());
                for (Rect r : si.getRects()) {
                    raf.writeInt(r.getLeft());
                    raf.writeInt(r.getTop());
                    raf.writeInt(r.getRight());
                    raf.writeInt(r.getBottom());
                }
                raf.writeShort(si.getPsdSkills().size());
                for (int i : si.getPsdSkills()) {
                    raf.writeInt(i);
                }
                raf.writeShort(si.getReqSkills().size());
                for (Map.Entry<Integer, Integer> reqSkill : si.getReqSkills().entrySet()) {
                    raf.writeInt(reqSkill.getKey());
                    raf.writeInt(reqSkill.getValue());
                }
                raf.writeShort(si.getAddAttackSkills().size());
                for (int i : si.getAddAttackSkills()) {
                    raf.writeInt(i);
                }
                raf.writeShort(si.getExtraSkillInfo().size());
                for (HashMap<Integer, Integer> extraSkillInfoHashMap : si.getExtraSkillInfo()) {
                    raf.writeShort(extraSkillInfoHashMap.size());
                    for (Map.Entry<Integer, Integer> extraSkillInfoHashMapEntry : extraSkillInfoHashMap.entrySet()) {
                        raf.writeInt(extraSkillInfoHashMapEntry.getKey());
                        raf.writeInt(extraSkillInfoHashMapEntry.getValue());
                    }
                }
                raf.writeShort(si.getRandomSkills().size());
                for (Map<Integer, Integer> randomSkillListMap : si.getRandomSkills()) {
                    raf.writeShort(randomSkillListMap.size());
                    for (Map.Entry<Integer, Integer> randomSkillMapEntry : randomSkillListMap.entrySet()) {
                        raf.writeInt(randomSkillMapEntry.getKey());
                        raf.writeInt(randomSkillMapEntry.getValue());
                    }
                }
                raf.writeShort(si.getPsdWT().size());
                for (Map.Entry<WeaponType, Map<SkillStat, Double>> psdWTEntry : si.getPsdWT().entrySet()) {
                    raf.writeShort(psdWTEntry.getKey().getVal()); // 3-digit types (Martial Brace 403...) don't fit a byte

                    raf.writeShort(psdWTEntry.getValue().size());
                    for (Map.Entry<SkillStat, Double> ssEntry : psdWTEntry.getValue().entrySet()) {
                        raf.writeInt(ssEntry.getKey().ordinal());
                        raf.writeDouble(ssEntry.getValue());
                    }
                }

                raf.writeShort(si.getOldSkillStats().size());
                for (Map.Entry<Integer, Map<SkillStat, String>> oldSlvSSEntry : si.getOldSkillStats().entrySet()) {
                    raf.writeInt(oldSlvSSEntry.getKey()); // slv

                    Map<SkillStat, String> oldSSMap = oldSlvSSEntry.getValue();
                    raf.writeShort(oldSSMap.size());
                    for (Map.Entry<SkillStat, String> oldSSEntry : oldSSMap.entrySet()) {
                        raf.writeInt(oldSSEntry.getKey().ordinal());
                        raf.writeUTF(oldSSEntry.getValue());
                    }
                }

                raf.writeShort(si.getPsdSkillsOrigin().size());
                for (Integer skillId : si.getPsdSkillsOrigin()) {
                    raf.writeInt(skillId);
                }

                raf.writeShort(si.getSecondAtomInfos().size());
                for (SecondAtomInfo sai : si.getSecondAtomInfos()) {
                    sai.write(raf);
                }
            }
            IOHelper.writeOffsetTable(skillOffsets, raf);
        } catch (IOException e) {
            log.error("SkillData.saveSkills", e);
        }
    }

    private static void loadSkillsOfJob(int job) {
        if (skillOffsets == null) {
            synchronized (SkillData.class) { // loaded once; the map is shown only when it's complete
                if (skillOffsets == null) {
                    Map<Integer, Integer> loadedOffsets = new HashMap<>();
                    IOHelper.loadOffsets(loadedOffsets, SKILLS_FILE);
                    skillOffsets = loadedOffsets;
                }
            }
        }
        if (loadedJobs.contains(job)) {
            return;
        }
        int jobLevel = JobConstants.getJobLevel((short) job);

        Set<Integer> matchingSkills = skillOffsets.keySet().stream()
                .filter(s -> SkillConstants.isValidSkillForJobAndJobLevel(s, job, jobLevel)) // doesn't include 5th job/80000 skills
                .collect(Collectors.toSet());
        matchingSkills.forEach(SkillData::loadSkill);
        loadedJobs.add(job);
    }

    private static void loadSkill(int id) {
        if (getSkillInfos().containsKey(id)) {
            return;
        }
        try (RandomAccessFile raf = new RandomAccessFile(SKILLS_FILE, IOHelper.READ_MODE)) {
            raf.seek(skillOffsets.get(id)); // skillOffsets should never be null here
            SkillInfo skillInfo = new SkillInfo();
            skillInfo.setSkillId(raf.readInt());
            skillInfo.setRootId(raf.readInt());
            skillInfo.setMaxLevel(raf.readInt());
            skillInfo.setMasterLevel(raf.readInt());
            skillInfo.setFixLevel(raf.readInt());
            skillInfo.setInvisible(raf.readBoolean());
            skillInfo.setMassSpell(raf.readBoolean());
            skillInfo.setInfoType(raf.readInt());
            skillInfo.setType(raf.readInt());
            skillInfo.setElemAttr(raf.readUTF());
            skillInfo.setHyper(raf.readInt());
            skillInfo.setHyperStat(raf.readInt());
            skillInfo.setVehicleId(raf.readInt());
            skillInfo.setReqTierPoint(raf.readInt());
            skillInfo.setNotCooltimeReset(raf.readBoolean());
            skillInfo.setNotIncBuffDuration(raf.readBoolean());
            skillInfo.setNotCooltimeReduce(raf.readBoolean());
            skillInfo.setvSkill(raf.readByte());
            skillInfo.setNormalGauge(raf.readInt());
            skillInfo.setBallSkill(raf.readBoolean());
            skillInfo.setSummon(raf.readBoolean());
            skillInfo.setDragonAction(raf.readBoolean());
            skillInfo.setAffectedArea(raf.readBoolean());
            skillInfo.setFinalAttack(raf.readBoolean());
            skillInfo.setShootObject(raf.readBoolean());
            skillInfo.setPsd(raf.readBoolean());
            skillInfo.setAssistSkillLink(raf.readInt());
            skillInfo.setKeyDownConsumptionInterval(raf.readInt());
            skillInfo.setPetAutoBuff(raf.readBoolean());
            skillInfo.setMes(raf.readUTF());
            skillInfo.setKeyFlags(raf.readByte());

            short ssSize = raf.readShort();
            for (int j = 0; j < ssSize; j++) {
                skillInfo.addSkillStatInfo(SkillStat.getSkillStatByString(
                        raf.readUTF()), raf.readUTF());
            }
            short rectSize = raf.readShort();
            for (int j = 0; j < rectSize; j++) {
                int left = raf.readInt();
                int top = raf.readInt();
                int right = raf.readInt();
                int bottom = raf.readInt();
                skillInfo.addRect(new Rect(left, top, right, bottom));
            }
            short psdSize = raf.readShort();
            for (int j = 0; j < psdSize; j++) {
                skillInfo.addPsdSkill(raf.readInt());
            }
            short reqSkillSize = raf.readShort();
            for (int j = 0; j < reqSkillSize; j++) {
                skillInfo.addReqSkill(raf.readInt(), raf.readInt());
            }
            short addAttackSize = raf.readShort();
            for (int j = 0; j < addAttackSize; j++) {
                skillInfo.addAddAttackSkills(raf.readInt());
            }

            short extraSkillInfoSize = raf.readShort();
            for (int j = 0; j < extraSkillInfoSize; j++) {
                HashMap<Integer, Integer> extraSkillMap = new HashMap<>();
                short extraSkillMapSize = raf.readShort();
                for (int k = 0; k < extraSkillMapSize; k++) {
                    int skillId = raf.readInt();
                    int delay = raf.readInt();
                    extraSkillMap.put(skillId, delay);
                }
                skillInfo.addExtraSkillInfo(extraSkillMap);
            }

            short randomSkillsSize = raf.readShort();
            for (int j = 0; j < randomSkillsSize; j++) {
                Map<Integer, Integer> randomSkillsMap = new HashMap<>();
                short randomSkillMapSize = raf.readShort();
                for (int k = 0; k < randomSkillMapSize; k++) {
                    int randomSkillId = raf.readInt();
                    int delay = raf.readInt();
                    randomSkillsMap.put(randomSkillId, delay);
                }
                skillInfo.addRandomSkill(randomSkillsMap);
            }

            short psdWTMapSize = raf.readShort();
            for (int i = 0; i < psdWTMapSize; i++) {
                WeaponType wt = WeaponType.getByVal(raf.readShort());
                short skillStatMapSize = raf.readShort();
                Map<SkillStat, Double> skillStatMap = new HashMap<>();
                for (int j = 0; j < skillStatMapSize; j++) {
                    SkillStat ss = SkillStat.values()[raf.readInt()];
                    double value = raf.readDouble();
                    skillStatMap.put(ss, value);
                }
                skillInfo.addPsdWT(wt, skillStatMap);
            }

            short oldSSMapSize = raf.readShort();
            for (int i = 0; i < oldSSMapSize; i++) {
                int slv = raf.readInt();

                int SSMapSize = raf.readShort();
                Map<SkillStat, String> oldSSMap = new HashMap<>();
                for (int j = 0; j < SSMapSize; j++) {
                    SkillStat ss = SkillStat.values()[raf.readInt()];
                    String val = raf.readUTF();
                    oldSSMap.put(ss, val);
                }

                skillInfo.addOldSkillStats(slv, oldSSMap);
            }

            short fromPsdSkillsSize = raf.readShort();
            for (int i = 0; i < fromPsdSkillsSize; i++) {
                int skillId = raf.readInt();

                skillInfo.addPsdSkillsOrigin(skillId);
            }

            short secondAtomSize = raf.readShort();
            for (int i = 0; i < secondAtomSize; i++) {
                SecondAtomInfo sai = new SecondAtomInfo();
                sai.read(raf);
                skillInfo.addSecondAtomInfo(sai);
            }

            getSkillInfos().put(skillInfo.getSkillId(), skillInfo);
        } catch (IOException e) {
            log.error("SkillData.loadSkill", e);
        }
    }

    private static void loadSkillsFromWz() {
        String[] wzs = new String[]{"/Skill.wz", "/Skill001.wz", "/Skill002.wz", "/Skill003.wz"};
        Set<java.nio.file.Path> sources = new HashSet<>();
        Set<Integer> quickSlotSkills = new HashSet<>();
        for (String wz : wzs) {
            WzFile file = new WzFile(ServerConstants.WZ_DIR + wz);
            // an XML export has all skills in one Skill.wz folder that every split name opens
            if (!sources.add(file.getSource())) {
                continue;
            }
            for (WzObject<?, ?> jobNode : file) {
                String name = jobNode.getName().replace(".img", "");
                if (!Util.isNumber(name)) {
                    continue;
                }
                int rootId = Integer.parseInt(name);
                WzObject<?, ?> skillTopNode = jobNode.getChild("skill");
                if (skillTopNode == null) {
                    continue; // an image without job skills
                }
                for (WzObject<?, ?> skillNode : skillTopNode) {
                    int skillId = Integer.parseInt(skillNode.getName());
                    SkillInfo si = new SkillInfo();
                    si.setRootId(rootId);
                    si.setSkillId(skillId);
                    si.setMasterLevel(((WzProperty) skillNode.getChild("masterLevel", 1)).getIntValue());
                    si.setFixLevel(((WzProperty) skillNode.getChild("fixLevel", 0)).getIntValue());
                    si.setInvisible(((WzProperty) skillNode.getChild("invisible", 0)).getIntValue() != 0);
                    si.setMassSpell(((WzProperty) skillNode.getChild("massSpell", 0)).getIntValue() != 0);
                    si.setType(((WzProperty) skillNode.getChild("type", 0)).getIntValue());
                    si.setPsd(((WzProperty) skillNode.getChild("psd", 0)).getIntValue() != 0);

                    WzObject<?, ?> node = skillNode.getChild("psdSkill");
                    if (node != null) {
                        for (WzObject<?, ?> subNode : node) {
                            if (Util.isNumber(subNode.getName())) { // v271 400004156 has an icon here
                                si.addPsdSkill(Integer.parseInt(subNode.getName()));
                            }
                        }
                    }

                    WzObject<?, ?> keydownNode = skillNode.getChild("keydown");
                    if (keydownNode != null) {
                        WzProperty keydownTime = (WzProperty) keydownNode.getChild("time");
                        si.setKeyDownConsumptionInterval(keydownTime == null ? 0 : keydownTime.getIntValue());
                    }

                    WzObject<?, ?> assistSkillLinkNode = skillNode.getChild("assistSkillLink");
                    if (assistSkillLinkNode != null) {
                        si.setAssistSkillLink(((WzProperty) assistSkillLinkNode.getChild("skill")).getIntValue());
                    }

                    si.setElemAttr(((WzProperty<String>) skillNode.getChild("elemAttr", "")).getValue());
                    si.setHyper(((WzProperty) skillNode.getChild("hyper", 0)).getIntValue());
                    si.setHyperStat(((WzProperty) skillNode.getChild("hyperStat", 0)).getIntValue());
                    si.setVehicleId(((WzProperty) skillNode.getChild("vehicleID", 0)).getIntValue());
                    si.setNotCooltimeReset(((WzProperty) skillNode.getChild("notCooltimeReset", 0)).getIntValue() != 0);
                    si.setNotIncBuffDuration(((WzProperty) skillNode.getChild("notIncBuffDuration", 0)).getIntValue() != 0);
                    si.setNotCooltimeReduce(((WzProperty) skillNode.getChild("notCooltimeReduce", 0)).getIntValue() != 0);
                    si.setPetAutoBuff(((WzProperty) skillNode.getChild("isPetAutoBuff", 0)).getIntValue() != 0);
                    si.setvSkill((byte) ((WzProperty) skillNode.getChild("vSkill", 0)).getIntValue());
                    si.setNormalGauge(((WzProperty) skillNode.getChild("normalGauge", 0)).getIntValue());
                    readKeyData(skillNode, si, quickSlotSkills);


                    if (skillNode.getChild("ball") != null) {
                        si.setBallSkill(true);
                    }
                    if (skillNode.getChild("summon") != null) {
                        si.setSummon(true);
                    }
                    if (skillNode.getChild("tile") != null) {
                        si.setAffectedArea(true);
                    }
                    if (skillNode.getChild("dragonAction") != null) {
                        si.setDragonAction(true);
                    }
                    if (skillNode.getChild("shootobj") != null) {
                        si.setShootObject(true);
                    }

                    node = skillNode.getChild("req");
                    if (node != null) {
                        for (WzObject<?, ?> subNode : node) {
                            if (Util.isNumber(subNode.getName())) {
                                si.addReqSkill(Integer.parseInt(subNode.getName()), ((WzProperty) subNode).getIntValue());
                            } else if ("reqTierPoint".equalsIgnoreCase(subNode.getName())) {
                                si.setReqTierPoint(((WzProperty) subNode).getIntValue());
                            }
                        }
                    }

                    WzObject<?, ?> saNode = skillNode.getChild("SecondAtom");
                    if (saNode != null) {
                        WzObject<?, ?> atomsNode = saNode.getChild("atom");


                        List<SecondAtomInfo> sais = new ArrayList<>();
                        if (atomsNode != null) {
                            // Multiple Atoms

                            var dataIndex = ((WzProperty) saNode.getChild("dataIndex", -1)).getIntValue();

                            for (WzObject<?, ?> atomNode : atomsNode) {
                                SecondAtomInfo sai = new SecondAtomInfo();
                                sai.readSecondAtomInfo(atomNode);

                                if (sai.getDataIndex() == 0 && dataIndex >= 0) {
                                    sai.setDataIndex(dataIndex);
                                }

                                sais.add(sai);
                            }
                        } else {
                            // 1 Atom
                            SecondAtomInfo sai = new SecondAtomInfo();
                            sai.readSecondAtomInfo(saNode);
                            sais.add(sai);
                        }

                        si.setSecondAtomInfos(sais);
                    }

                    String[] infoNames = new String[]{"common", "info", "info2"};
                    for (String infoName : infoNames) {
                        WzObject<?, ?> infoNode = skillNode.getChild(infoName);
                        if (infoNode != null) {
                            for (WzObject<?, ?> commonNode : infoNode) {
                                String commonName = commonNode.getName();
                                switch (commonName) {
                                    case "maxLevel":
                                        si.setMaxLevel(((WzProperty) commonNode).getIntValue());
                                        break;
                                    case "massSpell":
                                        si.setMassSpell(((WzProperty) commonNode).getIntValue() != 0);
                                        break;
                                    case "type":
                                        if (infoName.startsWith("info")) {
                                            si.setInfoType(((WzProperty) commonNode).getIntValue());
                                        }
                                        break;
                                    case "mes":
                                        if (infoName.equals("info")) {
                                            si.setMes(commonNode.toString());
                                        }
                                        break;
                                    case "finalAttack":
                                        if (infoName.equals("info")) {
                                            si.setFinalAttack(true);
                                        }
                                        break;
                                    default:
                                        SkillStat skillStat = SkillStat.getSkillStatByString(commonName);
                                        if (skillStat != null) {
                                            if (skillStat != SkillStat.dot || infoName.equals("common")) { // There's also a 'dot' nodeName in info/info2 which acts only as a boolean
                                                si.addSkillStatInfo(skillStat, commonNode.toString());
                                            }
                                        }
                                }
                                if (commonName.startsWith("rb")) {
                                    WzProperty<Point> rbProp = (WzProperty<Point>) commonNode;
                                    WzProperty<Point> ltProp = (WzProperty<Point>) infoNode.getChild(commonName.replace("rb", "lt"));
                                    si.addRect(new Rect(ltProp.getValue(), rbProp.getValue()));
                                }
                            }
                        }
                    }

                    WzObject<?, ?> oldSkillStatNode = skillNode.getChild("level");
                    if (oldSkillStatNode != null) {
                        for (WzObject<?, ?> levelNode : oldSkillStatNode) {
                            var nameLvNode = levelNode.getName();
                            if (!Util.isNumber(nameLvNode)) {
                                continue;
                            }
                            int slv = Integer.parseInt(nameLvNode);
                            for (WzObject<?, ?> skillStatsNode : levelNode) {
                                SkillStat skillStat = SkillStat.getSkillStatByString(skillStatsNode.getName());
                                if (skillStat != null) {
                                    si.addOldSkillStats(slv, skillStat, skillStatsNode.toString());
                                }
                            }
                        }
                    }

                    WzObject<?, ?> addAttackNode = skillNode.getChild("addAttack");
                    if (addAttackNode != null) {
                        WzObject<?, ?> skillPlusNode = addAttackNode.getChild("skillPlus");
                        if (skillPlusNode != null) {
                            for (WzObject<?, ?> skillPlus : skillPlusNode) {
                                si.addAddAttackSkills(((WzProperty) skillPlus).getIntValue());
                            }
                        }
                    }

                    WzObject<?, ?> extraSkillInfoNode = skillNode.getChild("extraSkillInfo");
                    if (extraSkillInfoNode != null) {
                        for (WzObject<?, ?> extraSkill : extraSkillInfoNode) {
                            int skill = ((WzProperty) (extraSkill.getChild("skill", 0))).getIntValue();
                            int delay = ((WzProperty) (extraSkill.getChild("delay", 0))).getIntValue();
                            if (skill > 0) {
                                si.addExtraSkillInfo(skill, delay);
                            }
                        }
                    }

                    WzObject<?, ?> randomSkillNode = skillNode.getChild("randomSkill");
                    if (randomSkillNode != null) {
                        for (WzObject<?, ?> randomSkill : randomSkillNode) {
                            Map<Integer, Integer> randomSkillMap = new HashMap<>();
                            if (randomSkill.getChild("skillID") != null) {
                                int skill = ((WzProperty) (randomSkill.getChild("skillID", 0))).getIntValue();
                                randomSkillMap.put(skill, 0);

                            } else if (randomSkill.getChild("skillList") != null) {
                                WzObject<?, ?> skillListNode = randomSkill.getChild("skillList");
                                if (skillListNode != null) {
                                    for (WzObject<?, ?> sln : skillListNode) {
                                        randomSkillMap.put(Integer.parseInt(sln.getName()), ((WzProperty) sln).getIntValue());
                                    }
                                }
                            }


                            if (randomSkillMap.size() > 0) {
                                si.addRandomSkill(randomSkillMap);
                            }
                        }
                    }

                    WzObject<?, ?> psdWtTopNode = skillNode.getChild("psdWT");
                    if (psdWtTopNode != null) {
                        for (WzObject<?, ?> psdWtNode : psdWtTopNode) {
                            WeaponType wt = WeaponType.getByVal(Integer.parseInt(psdWtNode.getName()));
                            if (wt == null) {
                                Log.error(String.format("Unknown WeaponType %s, in SkillID: %d", wt, skillId));
                                continue;
                            }
                            Map<SkillStat, Double> skillStatMap = new HashMap<>();
                            for (WzObject<?, ?> skillStatNode : psdWtNode) {
                                SkillStat skillStat = SkillStat.getSkillStatByString(skillStatNode.getName());
                                String value = skillStatNode.toString().trim();
                                if (skillStat == null) {
                                    log.error(String.format("Unknown psdWT SkillStat: %s, in SkillID: %d", skillStatNode.getName(), skillId));
                                    continue;
                                }
                                if (!Util.isNumber(value)) {
                                    log.error(String.format("Not a numbered psdWT SkillStat: %s, in SkillID: %d", skillStatNode.getName(), skillId));
                                    continue;
                                }
                                skillStatMap.put(skillStat, Double.parseDouble(value));
                            }
                            si.addPsdWT(wt, skillStatMap);
                        }
                    }

                    skills.put(skillId, si);
                }
                jobNode.clear();
            }
        }
        for (Map.Entry<Integer, SkillInfo> entry : skills.entrySet()) {
            int skillId = entry.getKey();
            SkillInfo si = entry.getValue();
            if (si == null) {
                continue;
            }

            for (int psdSkillID : si.getPsdSkills()) {
                if (getSkillInfos().containsKey(psdSkillID)) {
                    getSkillInfos().get(psdSkillID).addPsdSkillsOrigin(skillId);
                }
            }
        }
        markKeyFlag(quickSlotSkills, FuncKeyRules.KEY_QUICKSLOT_CHANGE);
        markKeyFlag(loadLiveEventBaseSkills(), FuncKeyRules.KEY_EVENT_BASE);
    }

    // what the client's not-keyable test (FuncKeyRules) reads from a skill node
    private static void readKeyData(WzObject<?, ?> skillNode, SkillInfo si, Set<Integer> quickSlotSkills) {
        boolean process90 = ((WzProperty) skillNode.getChild("processtype", 0)).getIntValue() == 90;
        WzObject<?, ?> node = skillNode.getChild("additional_process");
        if (node != null) {
            for (WzObject<?, ?> sub : node) {
                if (sub instanceof WzProperty && ((WzProperty) sub).getIntValue() == 90) {
                    process90 = true;
                }
            }
        }
        if (process90) {
            si.addKeyFlag(FuncKeyRules.KEY_PROCESS_90);
        }
        if (((WzProperty) skillNode.getChild("showSummonedBuffIcon", 0)).getIntValue() != 0) {
            si.addKeyFlag(FuncKeyRules.SUMMON_BUFF_ICON);
        }
        // forms another skill turns into on the quick slot
        addQuickSlotChanges(skillNode.getChild("changeSkill"), quickSlotSkills);
        node = skillNode.getChild("changeSkillJob");
        if (node != null) {
            for (WzObject<?, ?> jobNode : node) {
                addQuickSlotChanges(jobNode, quickSlotSkills);
            }
        }
    }

    private static void addQuickSlotChanges(WzObject<?, ?> node, Set<Integer> quickSlotSkills) {
        if (node == null) {
            return;
        }
        for (WzObject<?, ?> change : node) {
            int skill = ((WzProperty) change.getChild("skill", 0)).getIntValue();
            boolean quickSlot = ((WzProperty) change.getChild("quickslot", 0)).getIntValue() != 0;
            boolean onCooltime = ((WzProperty) change.getChild("onCooltime", 0)).getIntValue() != 0;
            if (skill != 0 && quickSlot && !onCooltime) {
                quickSlotSkills.add(skill);
            }
        }
    }

    private static void markKeyFlag(Set<Integer> skillIds, int flag) {
        for (int id : skillIds) {
            SkillInfo si = skills.get(id);
            if (si != null) {
                si.addKeyFlag(flag);
            }
        }
    }

    // Etc.wz UIEvent.img: baseSkill of every event in liveEventSkill
    private static Set<Integer> loadLiveEventBaseSkills() {
        Set<Integer> res = new HashSet<>();
        try {
            WzObject<?, ?> root = new WzFile(ServerConstants.WZ_DIR + "/Etc.wz").getChild("UIEvent.img");
            WzObject<?, ?> list = root == null ? null : root.getChild("liveEventSkill");
            WzObject<?, ?> data = root == null ? null : root.getChild("skillData");
            if (list == null || data == null) {
                return res;
            }
            for (WzObject<?, ?> entry : list) {
                Object name = entry instanceof WzProperty ? ((WzProperty<?>) entry).getValue() : null;
                WzObject<?, ?> event = name == null ? null : data.getChild(name.toString());
                if (event != null) {
                    int base = ((WzProperty) event.getChild("baseSkill", 0)).getIntValue();
                    if (base != 0) {
                        res.add(base);
                    }
                }
            }
        } catch (Exception e) {
            log.warn("Couldn't read Etc.wz UIEvent.img liveEventSkill", e);
        }
        return res;
    }

    public static Map<Integer, SkillInfo> getSkillInfos() {
        return skills;
    }

    public static SkillInfo getSkillInfoById(int skillId) {
        if (skillOffsets == null) {
            synchronized (SkillData.class) { // loaded once; the map is shown only when it's complete
                if (skillOffsets == null) {
                    Map<Integer, Integer> loadedOffsets = new HashMap<>();
                    IOHelper.loadOffsets(loadedOffsets, SKILLS_FILE);
                    skillOffsets = loadedOffsets;
                }
            }
        }
        if (!skillOffsets.containsKey(skillId)) {
            return null;
        }
        loadSkill(skillId);
        return getSkillInfos().get(skillId);
    }

    /**
     * For a skill the caller expects to have data (a learned skill, a potential): null when the data is missing,
     * logged once per skill id with where it was asked for, so the caller can skip it.
     */
    public static SkillInfo getSkillInfoOrLog(int skillId, String where) {
        SkillInfo si = getSkillInfoById(skillId);
        if (si == null && missingLogged.add(skillId)) {
            log.warn("[SkillData] no data for skill " + skillId + " (" + where + "), skipped");
        }
        return si;
    }

    public static Skill getSkillDeepCopyById(int skillId) {
        SkillInfo si = getSkillInfoById(skillId);
        if (si == null) {
            return null;
        }
        Skill skill = new Skill();
        skill.setSkillId(si.getSkillId());
        skill.setRootId(si.getRootId());
        skill.setMasterLevel(si.getMaxLevel());
        skill.setMaxLevel(si.getMaxLevel());
        if (si.getMasterLevel() <= 0) {
            skill.setMasterLevel(skill.getMaxLevel());
        }
        if (si.getFixLevel() > 0) {
            skill.setCurrentLevel(si.getFixLevel());
        } else {
            skill.setCurrentLevel(0);
        }
        return skill;
    }

    public static Skill getSkillDeepCopy(Skill oldSkill) {
        if (oldSkill == null) {
            return null;
        }
        Skill skill = new Skill();
        skill.setSkillId(oldSkill.getSkillId());
        skill.setRootId(oldSkill.getRootId());
        skill.setMasterLevel(oldSkill.getMaxLevel());
        skill.setMaxLevel(oldSkill.getMaxLevel());
        if (oldSkill.getMasterLevel() <= 0) {
            skill.setMasterLevel(skill.getMaxLevel());
        }
        if (oldSkill.getCurrentLevel() > 0) {
            skill.setCurrentLevel(oldSkill.getCurrentLevel());
        } else {
            skill.setCurrentLevel(0);
        }
        return skill;
    }

    public static List<Skill> getSkillsByJob(short id) {
        return getSkillsByJob(id, false);
    }

    private static WzFile skillWz;
    private static final Map<Integer, Set<Integer>> spLockedByRoot = new HashMap<>();

    /**
     * Skills marked disable=1 in Skill.wz take no SP: the skill window has no + for them and their level follows
     * another skill (Adrenaline Rush Activate, Serpent Assault, Evan's Return skills, Kain's Possess skills).
     */
    public static synchronized boolean isSpLocked(int skillId) {
        int root = SkillConstants.getSkillRootFromSkill(skillId);
        Set<Integer> locked = spLockedByRoot.get(root);
        if (locked == null) {
            locked = new HashSet<>();
            try {
                if (skillWz == null) {
                    skillWz = new WzFile(ServerConstants.WZ_DIR + "/Skill.wz");
                }
                WzObject<?, ?> top = skillWz.getChildByPath(String.format("%03d", root) + ".img/skill");
                if (top != null) {
                    for (WzObject<?, ?> node : top) {
                        WzObject<?, ?> flag = node.getChild("disable");
                        if (flag instanceof WzProperty<?> p && p.getValue() instanceof Number n && n.intValue() != 0) {
                            locked.add(Integer.parseInt(node.getName()));
                        }
                    }
                }
            } catch (Exception e) {
                log.error("Could not read the disable flags of Skill.wz " + root + ".img", e);
            }
            spLockedByRoot.put(root, locked);
        }
        return locked.contains(skillId);
    }

    private static final Map<Integer, Integer> sixthCounts = new HashMap<>();
    private static final String SIXTH_COUNT_NODE = "common/6thCount";

    /**
     * Skill.wz common/6thCount: how many times a 6th job Ascent skill can be used in one boss run (0 for any other
     * skill). The loader keeps no stat for it (the name starts with a digit), so it is read here once per skill.
     */
    public static synchronized int getSixthCount(int skillId) {
        Integer count = sixthCounts.get(skillId);
        if (count == null) {
            count = 0;
            try {
                if (skillWz == null) {
                    skillWz = new WzFile(ServerConstants.WZ_DIR + "/Skill.wz");
                }
                int root = SkillConstants.getSkillRootFromSkill(skillId);
                WzObject<?, ?> node = skillWz.getChildByPath(String.format("%03d", root) + ".img/skill/" + skillId
                        + "/" + SIXTH_COUNT_NODE);
                if (node instanceof WzProperty<?> p && p.getValue() != null) {
                    String value = p.getValue().toString().trim();
                    count = Util.isNumber(value) ? (int) Double.parseDouble(value) : 0;
                }
            } catch (Exception e) {
                log.error("Could not read the 6thCount of skill " + skillId, e);
            }
            sixthCounts.put(skillId, count);
        }
        return count;
    }

    private static final Map<Integer, Set<String>> summonActions = new HashMap<>();

    /**
     * True when the skill's summon node exists but has no such action (the client terminates on a summon action it
     * can't load). False when the skill has no summon node or the data can't be read.
     */
    public static synchronized boolean summonLacksAction(int skillId, String action) {
        Set<String> actions = summonActions.get(skillId);
        if (actions == null) {
            actions = Collections.emptySet();
            try {
                if (skillWz == null) {
                    skillWz = new WzFile(ServerConstants.WZ_DIR + "/Skill.wz");
                }
                int root = SkillConstants.getSkillRootFromSkill(skillId);
                WzObject<?, ?> node = skillWz.getChildByPath(String.format("%03d", root) + ".img/skill/" + skillId + "/summon");
                if (node != null) {
                    actions = new HashSet<>();
                    for (WzObject<?, ?> child : node) {
                        actions.add(child.getName());
                    }
                }
            } catch (Exception e) {
                log.error("Could not read the summon node of skill " + skillId, e);
            }
            summonActions.put(skillId, actions);
        }
        return !actions.isEmpty() && !actions.contains(action);
    }

    /** The loaded skills of a job (its own skills read in first), as copies; ids cached until another skill loads. */
    private static List<Skill> getSkillsByJob(short id, boolean rec) {
        if (!rec) {
            loadSkillsOfJob(id);
        }
        JobSkillIds cached = jobSkillIds.get(id);
        int loaded = getSkillInfos().size();
        if (cached == null || cached.loaded() != loaded) {
            List<Integer> ids = new ArrayList<>();
            getSkillInfos().forEach((key, si) -> {
                if (si.getRootId() == id && !SkillConstants.isInvisible(si)) {
                    ids.add(key);
                }
            });
            cached = new JobSkillIds(loaded, List.copyOf(ids));
            jobSkillIds.put(id, cached);
        }
        List<Skill> res = new ArrayList<>(cached.ids().size());
        for (int skillId : cached.ids()) {
            Skill skill = getSkillDeepCopyById(skillId);
            if (skill != null) {
                res.add(skill);
            }
        }
        return res;
    }

    public static Map<Short, Map<Short, MobSkillInfo>> getMobSkillInfos() {
        return mobSkillInfos;
    }

    public static Map<Integer, MakingSkillRecipe> getMakingSkillRecipes() {
        return makingSkillRecipes;
    }

    public static MakingSkillRecipe getRecipeById(int recipeID) {
        if (makingSkillRecipeOffsets == null) {
            synchronized (SkillData.class) { // loaded once; the map is shown only when it's complete
                if (makingSkillRecipeOffsets == null) {
                    Map<Integer, Integer> loadedOffsets = new HashMap<>();
                    IOHelper.loadOffsets(loadedOffsets, MAKING_SKILL_RECIPES_FILE);
                    makingSkillRecipeOffsets = loadedOffsets;
                }
            }
        }
        if (!makingSkillRecipeOffsets.containsKey(recipeID)) {
            return null;
        }
        loadRecipe(recipeID);
        return getMakingSkillRecipes().get(recipeID);
    }

    public static void addMobSkillInfo(MobSkillInfo msi) {
        getMobSkillInfos().computeIfAbsent(msi.getSkillId(), k -> new HashMap<>());
        Map<Short, MobSkillInfo> msiLevelMap = getMobSkillInfos().get(msi.getSkillId());
        msiLevelMap.put(msi.getLevel(), msi);
        getMobSkillInfos().put(msi.getSkillId(), msiLevelMap);
    }

    private static void loadEliteMobSkillsFromWZ() {
        WzFile file = new WzFile(ServerConstants.WZ_DIR + "/Skill.wz");
        WzObject<?, ?> eliteMobSkillNode = file.getChild("EliteMobSkill.img");
        for (WzObject<?, ?> gradeNode : eliteMobSkillNode) {
            int grade = Integer.parseInt(gradeNode.getName());
            for (WzObject<?, ?> ems : gradeNode) {
                int skillId = ((WzProperty) ems.getChild("skill", 0)).getIntValue();
                int slv = ((WzProperty) ems.getChild("level", 0)).getIntValue();
                addEliteMobSkill(grade, skillId, slv);
            }
        }
        file.clear();
    }

    private static void addEliteMobSkill(int grade, int skillID, int skillLevel) {
        if (!eliteMobSkills.containsKey(grade)) {
            eliteMobSkills.put(grade, new HashMap<>());
        }
        eliteMobSkills.get(grade).put(skillID, skillLevel);
    }

    @Saver(varName = "eliteMobSkills")
    private static void saveEliteMobSkills(File file) {
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(file))) {
            dos.writeInt(eliteMobSkills.size());
            for (Map.Entry<Integer, Map<Integer, Integer>> entry : eliteMobSkills.entrySet()) {
                dos.writeInt(entry.getKey()); // grade
                dos.writeInt(entry.getValue().size());
                for (Map.Entry<Integer, Integer> innerEntry : eliteMobSkills.get(entry.getKey()).entrySet()) {
                    dos.writeInt(innerEntry.getKey()); // skillID
                    dos.writeInt(innerEntry.getValue()); // skillLevel
                }
            }
        } catch (IOException e) {
            log.error("SkillData.saveEliteMobSkills", e);
        }
    }

    @Loader(varName = "eliteMobSkills")
    public static void loadEliteMobSkills(File file, boolean exists) {
        if (!exists) {
            loadEliteMobSkillsFromWZ();
            saveEliteMobSkills(file);
        } else {
            try (DataInputStream dis = new DataInputStream(new FileInputStream(file))) {
                int gradeSize = dis.readInt();
                for (int i = 0; i < gradeSize; i++) {
                    int grade = dis.readInt();
                    int gradeSkillSize = dis.readInt();
                    for (int j = 0; j < gradeSkillSize; j++) {
                        int skillID = dis.readInt();
                        int skillLevel = dis.readInt();
                        addEliteMobSkill(grade, skillID, skillLevel);
                    }
                }
            } catch (IOException e) {
                log.error("SkillData.loadEliteMobSkills", e);
            }
        }
    }

    public static Map<Integer, Integer> getEliteMobSkillsByGrade(int grade) {
        return eliteMobSkills.getOrDefault(grade, null);
    }

    public static void loadMobSkillsFromWz() {
        WzFile file = new WzFile(ServerConstants.WZ_DIR + "/Skill001.wz");
        List<WzObject<?, ?>> skillNodes = new ArrayList<>();
        WzObject<?, ?> topNode = file.getChild("MobSkill");
        if (topNode != null) {
            for (WzObject<?, ?> skillNode : topNode) {
                if (Util.isNumber(skillNode.getName().replace(".img", ""))) {
                    skillNodes.add(skillNode);
                }
            }
        }
        if (skillNodes.isEmpty()) {
            log.warn("No mob skills found in Skill.wz/MobSkill");
        }
        for (WzObject<?, ?> skillNode : skillNodes) {
            int skillId = Integer.parseInt(skillNode.getName().replace(".img", ""));
            WzObject<?, ?> levelTopNode = skillNode.getChild("level");
            for (WzObject<?, ?> levelNode : levelTopNode) {
                int level = Integer.parseInt(levelNode.getName());
                MobSkillInfo msi = new MobSkillInfo();
                msi.setSkillId((short) skillId);
                msi.setLevel((short) level);

                for (WzObject<?, ?> info : levelNode) {
                    String name = info.getName();
                    if (Util.isNumber(name)) {
                        msi.addIntToList(((WzProperty) info).getIntValue());
                        continue;
                    }
                    String value = info.toString();
                    try {
                        MobSkillStat mss = MobSkillStat.valueOf(name);
                        msi.putMobSkillStat(mss, value);
                        continue;
                    } catch (IllegalArgumentException e) {
                        // ignore: no mss found
                    }

                    if (info.getName().equals("otherSkill")) {
                        msi.setOtherSkillID(((WzProperty) info.getChild("otherSkillID", 0)).getIntValue());
                        msi.setOtherSkillLev(((WzProperty) info.getChild("otherSkillLev", 0)).getIntValue());
                    }
                    if (info.getName().equals("affectedOtherSkill")) {
                        int single = ((WzProperty) info.getChild("affectedOtherSkillID", 0)).getIntValue();
                        if (single > 0) {
                            msi.addAffectedOtherSkill(single, ((WzProperty) info.getChild("affectedOtherSkillLev", 0)).getIntValue());
                        } else {
                            // list form: 0/, 1/, ... each with its own id and level
                            for (WzObject<?, ?> entry : info) {
                                if (entry instanceof WzProperty<?> ep && ep.getPropertyType().isInteger()) {
                                    continue;
                                }
                                msi.addAffectedOtherSkill(((WzProperty) entry.getChild("affectedOtherSkillID", 0)).getIntValue(),
                                        ((WzProperty) entry.getChild("affectedOtherSkillLev", 0)).getIntValue());
                            }
                        }
                    }
                    if (info.getName().equals("mobGroup")) {
                        for (WzObject<?, ?> group : info) {
                            List<Integer> mobGroup = new ArrayList<>();
                            for (WzObject<?, ?> element : group) {
                                mobGroup.add(((WzProperty) element).getIntValue());
                            }
                            msi.addMobGroup(mobGroup);
                        }
                    }

                    switch (info.getName()) {
                        case "lt":
                            msi.setLt(new Position(((WzProperty<Point>) info).getValue()));
                            break;
                        case "lt2":
                            msi.setLt2(new Position(((WzProperty<Point>) info).getValue()));
                            break;
                        case "lt3":
                            msi.setLt3(new Position(((WzProperty<Point>) info).getValue()));
                            break;
                        case "rb":
                            msi.setRb(new Position(((WzProperty<Point>) info).getValue()));
                            break;
                        case "rb2":
                            msi.setRb2(new Position(((WzProperty<Point>) info).getValue()));
                            break;
                        case "rb3":
                            msi.setRb3(new Position(((WzProperty<Point>) info).getValue()));
                            break;
                        case "interval":
                            msi.putMobSkillStat(MobSkillStat.interval, value);
                            break;
                    }
                }
                addMobSkillInfo(msi);
            }
        }

    }

    public static void saveMobSkillsToDat(String file) {
        int tableSize = 0;
        for (Map.Entry<Short, Map<Short, MobSkillInfo>> entry : getMobSkillInfos().entrySet()) {
            tableSize += entry.getValue().size() * 8;
        }
        mobSkillInfoOffsets = new HashMap<>();
        try (RandomAccessFile raf = new RandomAccessFile(file, IOHelper.READ_WRITE_MODE)) {
            raf.seek(tableSize);
            for (Map.Entry<Short, Map<Short, MobSkillInfo>> entry : getMobSkillInfos().entrySet()) {
                short id = entry.getKey();
                for (Map.Entry<Short, MobSkillInfo> entry2 : entry.getValue().entrySet()) {
                    short level = entry2.getKey();
                    mobSkillInfoOffsets.put(id * 1000 + level, (int) raf.getFilePointer());
                    MobSkillInfo msi = entry2.getValue();
                    raf.writeShort(msi.getSkillId());
                    raf.writeShort(msi.getLevel());
                    // wow this is ugly
                    // but it's just a de/encoder, so who cares
                    boolean hasLt = msi.getLt() != null;
                    raf.writeBoolean(hasLt);
                    if (hasLt) {
                        raf.writeInt(msi.getLt().getX());
                        raf.writeInt(msi.getLt().getY());
                    }
                    boolean hasRb = msi.getRb() != null;
                    raf.writeBoolean(hasRb);
                    if (hasRb) {
                        raf.writeInt(msi.getRb().getX());
                        raf.writeInt(msi.getRb().getY());
                    }
                    boolean hasLt2 = msi.getLt2() != null;
                    raf.writeBoolean(hasLt2);
                    if (hasLt2) {
                        raf.writeInt(msi.getLt2().getX());
                        raf.writeInt(msi.getLt2().getY());
                    }
                    boolean hasRb2 = msi.getRb2() != null;
                    raf.writeBoolean(hasRb2);
                    if (hasRb2) {
                        raf.writeInt(msi.getRb2().getX());
                        raf.writeInt(msi.getRb2().getY());
                    }
                    boolean hasLt3 = msi.getLt3() != null;
                    raf.writeBoolean(hasLt3);
                    if (hasLt3) {
                        raf.writeInt(msi.getLt3().getX());
                        raf.writeInt(msi.getLt3().getY());
                    }
                    boolean hasRb3 = msi.getRb3() != null;
                    raf.writeBoolean(hasRb3);
                    if (hasRb3) {
                        raf.writeInt(msi.getRb3().getX());
                        raf.writeInt(msi.getRb3().getY());
                    }
                    raf.writeShort(msi.getMobSkillStats().size());
                    for (Map.Entry<MobSkillStat, String> msiString : msi.getMobSkillStats().entrySet()) {
                        raf.writeByte(msiString.getKey().ordinal());
                        raf.writeUTF(msiString.getValue());
                    }
                    raf.writeShort(msi.getInts().size());
                    for (int i : msi.getInts()) {
                        raf.writeInt(i);
                    }
                    raf.writeShort(msi.getMobGroups().size());
                    for (List<Integer> mobGroup : msi.getMobGroups()) {
                        raf.writeShort(mobGroup.size());
                        for (int mob : mobGroup) {
                            raf.writeInt(mob);
                        }
                    }
                    raf.writeInt(msi.getOtherSkillID());
                    raf.writeInt(msi.getOtherSkillLev());
                    raf.writeInt(msi.getAffectedOtherSkillID());
                    raf.writeInt(msi.getAffectedOtherSkillLev());
                    raf.writeShort(msi.getAffectedOtherSkills().size());
                    for (int[] other : msi.getAffectedOtherSkills()) {
                        raf.writeInt(other[0]);
                        raf.writeInt(other[1]);
                    }
                }
            }
            IOHelper.writeOffsetTable(mobSkillInfoOffsets, raf);
        } catch (IOException e) {
            log.error("SkillData.saveMobSkillsToDat", e);
        }
    }

    private static MobSkillInfo getMobSkillInfoByIdAndLevel(short id, short level) {
        int key = id * 1000 + level;
        if (mobSkillInfoOffsets == null) {
            synchronized (SkillData.class) { // loaded once; the map is shown only when it's complete
                if (mobSkillInfoOffsets == null) {
                    Map<Integer, Integer> loadedOffsets = new HashMap<>();
                    IOHelper.loadOffsets(loadedOffsets, MOB_SKILLS_FILE);
                    mobSkillInfoOffsets = loadedOffsets;
                }
            }
        }
        if (!mobSkillInfoOffsets.containsKey(key)) {
            return null;
        }
        MobSkillInfo msi = null;
        try (RandomAccessFile raf = new RandomAccessFile(MOB_SKILLS_FILE, IOHelper.READ_MODE)) {
            raf.seek(mobSkillInfoOffsets.get(key));
            msi = new MobSkillInfo();
            msi.setSkillId(raf.readShort());
            msi.setLevel(raf.readShort());
            boolean hasPos = raf.readBoolean();
            if (hasPos) {
                msi.setLt(new Position(raf.readInt(), raf.readInt()));
            }
            hasPos = raf.readBoolean();
            if (hasPos) {
                msi.setRb(new Position(raf.readInt(), raf.readInt()));
            }
            hasPos = raf.readBoolean();
            if (hasPos) {
                msi.setLt2(new Position(raf.readInt(), raf.readInt()));
            }
            hasPos = raf.readBoolean();
            if (hasPos) {
                msi.setRb2(new Position(raf.readInt(), raf.readInt()));
            }
            hasPos = raf.readBoolean();
            if (hasPos) {
                msi.setLt3(new Position(raf.readInt(), raf.readInt()));
            }
            hasPos = raf.readBoolean();
            if (hasPos) {
                msi.setRb3(new Position(raf.readInt(), raf.readInt()));
            }
            short size = raf.readShort();
            for (int i = 0; i < size; i++) {
                MobSkillStat mss = MobSkillStat.values()[raf.readByte()];
                String value = raf.readUTF();
                msi.putMobSkillStat(mss, value);
            }
            size = raf.readShort();
            for (int i = 0; i < size; i++) {
                msi.addIntToList(raf.readInt());
            }
            size = raf.readShort();
            for (int i = 0; i < size; i++) {
                List<Integer> mobGroup = new ArrayList<>();
                int mobGroupSize = raf.readShort();
                for (int j = 0; j < mobGroupSize; j++) {
                    mobGroup.add(raf.readInt());
                }
                msi.addMobGroup(mobGroup);
            }
            msi.setOtherSkillID(raf.readInt());
            msi.setOtherSkillLev(raf.readInt());
            msi.setAffectedOtherSkillID(raf.readInt());
            msi.setAffectedOtherSkillLev(raf.readInt());
            short otherCount = raf.readShort();
            for (int i = 0; i < otherCount; i++) {
                msi.addAffectedOtherSkill(raf.readInt(), raf.readInt());
            }
            addMobSkillInfo(msi);
        } catch (IOException e) {
            log.error("SkillData.getMobSkillInfoByIdAndLevel", e);
        }
        return msi;
    }

    public static MobSkillInfo getMobSkillInfoByIdAndLevel(int id, int level) {
        return getMobSkillInfoByIdAndLevel((short) id, (short) level);
    }

    public static void loadMakingRecipeSkillsFromWz() {
        WzFile file = new WzFile(ServerConstants.WZ_DIR + "/Skill.wz");
        int[] recipes = {9200, 9201, 9202, 9203, 9204};
        for (int recipeCategory : recipes) {
            WzObject<?, ?> recipeTopNode = file.getChild(String.format("Recipe_%d.img", recipeCategory));
            if (recipeTopNode == null) {
                // v271 only ships Recipe_9202..9204
                continue;
            }
            for (WzObject<?, ?> rin : recipeTopNode) {
                MakingSkillRecipe msr = new MakingSkillRecipe();
                int recipeId = Integer.parseInt(rin.getName());
                msr.setRecipeID(recipeId);
                msr.setReqSkillID(10000 * (recipeId / 10000));

                msr.setWeatherItemID(((WzProperty) rin.getChild("weatherItem", 0)).getIntValue());
                msr.setIncSkillProficiency(((WzProperty) rin.getChild("incSkillProficiency", 0)).getIntValue());
                msr.setIncSkillProficiencyOnFailure(((WzProperty) rin.getChild("incSkillProficiencyOnFailure", 0)).getIntValue());
                msr.setIncSkillMasterProficiency(((WzProperty) rin.getChild("incSkillMasterProficiency", 0)).getIntValue());
                msr.setIncSkillMasterProficiencyOnFailure(((WzProperty) rin.getChild("incSkillMasterProficiencyOnFailure", 0)).getIntValue());
                msr.setIncFatigability(((WzProperty) rin.getChild("incFatigability", 0)).getIntValue());
                msr.setAddedCoolProb(((WzProperty) rin.getChild("addedCoolProb", 0)).getIntValue());
                msr.setCoolTimeSec(((WzProperty) rin.getChild("coolTimeSec", 0)).getIntValue());
                msr.setAddedSecForMaxGauge(((WzProperty) rin.getChild("addedTimeTaken", 0)).getIntValue());
                msr.setExpiredPeriod(((WzProperty) rin.getChild("period", 0)).getIntValue());
                msr.setPremiumItem(((WzProperty) rin.getChild("premium", 0)).getIntValue() != 0);
                msr.setNeedOpenItem(((WzProperty) rin.getChild("needOpenItem", 0)).getIntValue() != 0);
                msr.setRecommendedSkillLevel(((WzProperty) rin.getChild("reqSkillLevel", 0)).getIntValue());
                msr.setReqSkillProficiency(((WzProperty) rin.getChild("reqSkillProficiency", 0)).getIntValue());
                msr.setReqMeso(((WzProperty) rin.getChild("reqMeso", 0)).getIntValue());
                msr.setReqMapObjectTag(rin.getChild("reqMapObjectTag", "").toString());

                WzObject<?, ?> targets = rin.getChild("target");
                if (targets != null) {
                    for (WzObject<?, ?> target : targets) {
                        MakingSkillRecipe.TargetElem tar = new MakingSkillRecipe.TargetElem();
                        tar.setItemID(((WzProperty) target.getChild("item", 0)).getIntValue());
                        tar.setCount(((WzProperty) target.getChild("count", 0)).getIntValue());
                        tar.setProbWeight(((WzProperty) target.getChild("probWeight", 0)).getIntValue());
                        msr.addTarget(tar);
                    }
                }

                WzObject<?, ?> recipeNode = rin.getChild("recipe");
                if (recipeNode != null) {
                    for (WzObject<?, ?> ingredient : recipeNode) {
                        int itemId = ((WzProperty) ingredient.getChild("item", -1)).getIntValue();
                        int count = ((WzProperty) ingredient.getChild("count", -1)).getIntValue();
                        if (itemId != -1 && count != -1) {
                            msr.addIngredient(itemId, count);
                        }
                    }
                }

                var skill = new SkillInfo();
                skill.setSkillId(recipeId);
                skill.setFixLevel(1);
                skill.setMasterLevel(1);

                makingSkillRecipes.put(recipeId, msr);
                skills.put(recipeId, skill);
            }
        }
    }

    public static void saveMakingRecipeSkillsToDat(String file) {
        int tableSize = getMakingSkillRecipes().size() * 8 + 4; // 4 for id, 4 for offset pos, single 4 for size
        makingSkillRecipeOffsets = new HashMap<>();
        try (RandomAccessFile raf = new RandomAccessFile(file, IOHelper.READ_WRITE_MODE)) {
            raf.seek(tableSize);
            for (MakingSkillRecipe msr : getMakingSkillRecipes().values()) {
                int recipeId = msr.getRecipeID();
                makingSkillRecipeOffsets.put(recipeId, (int) raf.getFilePointer());
                raf.writeInt(recipeId);
                raf.writeShort(msr.getTarget().size());
                for (MakingSkillRecipe.TargetElem target : msr.getTarget()) {
                    raf.writeInt(target.getItemID());
                    raf.writeInt(target.getCount());
                    raf.writeInt(target.getProbWeight());
                }
                raf.writeInt(msr.getWeatherItemID());
                raf.writeInt(msr.getIncSkillProficiency());
                raf.writeInt(msr.getIncSkillProficiencyOnFailure());
                raf.writeInt(msr.getIncFatigability());
                raf.writeInt(msr.getIncSkillMasterProficiency());
                raf.writeInt(msr.getIncSkillMasterProficiencyOnFailure());
                raf.writeBoolean(msr.isNeedOpenItem());
                raf.writeInt(msr.getReqSkillID());
                raf.writeInt(msr.getRecommendedSkillLevel());
                raf.writeInt(msr.getReqSkillProficiency());
                raf.writeInt(msr.getReqMeso());
                raf.writeUTF(msr.getReqMapObjectTag());
                raf.writeShort(msr.getIngredient().size());
                for (Tuple<Integer, Integer> ingredient : msr.getIngredient()) {
                    raf.writeInt(ingredient.getLeft());
                    raf.writeInt(ingredient.getRight());
                }
                raf.writeInt(msr.getAddedCoolProb());
                raf.writeInt(msr.getCoolTimeSec());
                raf.writeInt(msr.getAddedSecForMaxGauge());
                raf.writeInt(msr.getExpiredPeriod());
                raf.writeBoolean(msr.isPremiumItem());
            }
            IOHelper.writeOffsetTable(makingSkillRecipeOffsets, raf);
        } catch (IOException e) {
            log.error("SkillData.saveMakingRecipeSkillsToDat", e);
        }
    }

    public static void loadRecipe(int recipeId) {
        try (RandomAccessFile raf = new RandomAccessFile(MAKING_SKILL_RECIPES_FILE, IOHelper.READ_MODE)) {
            raf.seek(makingSkillRecipeOffsets.get(recipeId));
            MakingSkillRecipe msr = new MakingSkillRecipe();
            msr.setRecipeID(raf.readInt());
            short targets = raf.readShort();
            for (int j = 0; j < targets; j++) {
                MakingSkillRecipe.TargetElem target = new MakingSkillRecipe.TargetElem();
                target.setItemID(raf.readInt());
                target.setCount(raf.readInt());
                target.setProbWeight(raf.readInt());
                msr.addTarget(target);
            }
            msr.setWeatherItemID(raf.readInt());
            msr.setIncSkillProficiency(raf.readInt());
            msr.setIncSkillProficiencyOnFailure(raf.readInt());
            msr.setIncFatigability(raf.readInt());
            msr.setIncSkillMasterProficiency(raf.readInt());
            msr.setIncSkillMasterProficiencyOnFailure(raf.readInt());
            msr.setNeedOpenItem(raf.readBoolean());
            msr.setReqSkillID(raf.readInt());
            msr.setRecommendedSkillLevel(raf.readInt());
            msr.setReqSkillProficiency(raf.readInt());
            msr.setReqMeso(raf.readInt());
            msr.setReqMapObjectTag(raf.readUTF());
            short ingredients = raf.readShort();
            for (int j = 0; j < ingredients; j++) {
                msr.addIngredient(raf.readInt(), raf.readInt());
            }
            msr.setAddedCoolProb(raf.readInt());
            msr.setCoolTimeSec(raf.readInt());
            msr.setAddedSecForMaxGauge(raf.readInt());
            msr.setExpiredPeriod(raf.readInt());
            msr.setPremiumItem(raf.readBoolean());
            getMakingSkillRecipes().put(msr.getRecipeID(), msr);
        } catch (IOException e) {
            log.error("SkillData.loadRecipe", e);
        }
    }

    public static void generateDatFiles() {
        log.info("Started generating skill and recipe data.");
        long start = System.currentTimeMillis();
        loadSkillsFromWz();
        loadEliteMobSkillsFromWZ();
        loadMakingRecipeSkillsFromWz();
        saveSkills(SKILLS_FILE);
        saveMakingRecipeSkillsToDat(MAKING_SKILL_RECIPES_FILE);
        log.info(String.format("Completed generating skill and recipe data in %dms.", System.currentTimeMillis() - start));
        log.info("Started generating mob skill data.");
        start = System.currentTimeMillis();
        loadMobSkillsFromWz();
        saveMobSkillsToDat(MOB_SKILLS_FILE);
        log.info(String.format("Completed generating mob skill data in %dms.", System.currentTimeMillis() - start));
        log.info("Started generating recipe skill data.");
    }

    public static void main(String[] args) {
        generateDatFiles();
    }

    public static void clear() {
        getSkillInfos().clear();
        loadedJobs.clear();
        jobSkillIds.clear();
        if (skillOffsets != null) {
            skillOffsets = null;
        }
        getMobSkillInfos().clear();
        if (mobSkillInfoOffsets != null) {
            mobSkillInfoOffsets = null;
        }
    }
}
