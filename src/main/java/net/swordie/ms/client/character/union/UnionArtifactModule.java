package net.swordie.ms.client.character.union;

import net.swordie.ms.client.Account;
import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.quest.Quest;
import net.swordie.ms.client.character.quest.QuestManager;
import net.swordie.ms.client.character.skills.Skill;
import net.swordie.ms.connection.InPacket;
import net.swordie.ms.connection.OutPacket;
import net.swordie.ms.connection.packet.UnionArtifactPacket;
import net.swordie.ms.connection.packet.WvsContext;
import net.swordie.ms.connection.packet.model.MessagePacket;
import net.swordie.ms.constants.QuestConstants;
import net.swordie.ms.enums.ChatType;
import net.swordie.ms.life.mob.Mob;
import net.swordie.ms.loaders.ArtifactData;
import net.swordie.ms.loaders.SkillData;
import net.swordie.ms.util.FileTime;
import net.swordie.orm.dao.UnionArtifactDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.IsoFields;
import java.util.*;

/**
 * Union (Legion) Artifacts, once the Legion is founded: crystals (account-wide, union_artifacts) that each carry three
 * stat skills, levelled with artifact AP; artifact points (spent to extend, change or reset crystals), artifact EXP and
 * level (quest record 503392: point, level, exp) earned from the weekly common missions (hunting enemies near your
 * level, logging in) and from bosses: a boss hunt mission pays its rank's reward for up to maxCount boss clears a week,
 * and a boss's first clear sets the boss clear record (39392, key = the clear monster) and pays its special mission.
 * Crystals unlock at the artifact levels Artifact.img gives. Other special missions are not tracked. Ported from the reference tree's UnionArtifact / UnionHandler / Char.initUnionArtifact.
 */
public final class UnionArtifactModule {

    private static final Logger log = LogManager.getLogger(UnionArtifactModule.class);

    public static final UnionArtifactDao DAO = new UnionArtifactDao();

    public static final int ARTIFACT_QUEST = 503392; // point, start, level, exp
    public static final int DATA_QUEST = 503155; // attendance, mobCount, week, day
    public static final int COMMON_QUEST = 503144; // missionState
    private static final int MOB_COUNT_MAX = 20000;
    private static final int MOB_LEVEL_RANGE = 10;
    private static final int START_POINTS = 100;
    private static final int CRYSTAL_DAYS = 30;

    private UnionArtifactModule() {
    }

    /** Length of the common mission state (one digit per mission, 2 = done) */
    private static int commonMissions() {
        return Math.max(7, ArtifactData.getCommonMissions().size());
    }

    public static boolean isOpen(Char chr) {
        return chr != null && chr.getAccount() != null && chr.getQuestManager() != null
                && chr.getQuestManager().hasQuestCompleted(QuestConstants.UNION_START_QUEST);
    }

    // ------------------------------------------------------------------------------------------------- records

    private static Quest record(Char chr, int questId, String init) {
        QuestManager qm = chr.getQuestManager();
        Quest q = qm.getQuestById(questId);
        if (q == null || q.getQRValue() == null || q.getQRValue().isEmpty()) {
            qm.addQuestWithQr(chr, questId, init);
            q = qm.getQuestById(questId);
        }
        return q;
    }

    private static void send(Char chr, Quest q) {
        chr.write(WvsContext.message(MessagePacket.questRecordExMessage(q)));
    }

    private static Quest artifact(Char chr) {
        return record(chr, ARTIFACT_QUEST, "point=" + START_POINTS + ";start=1;level=1;exp=0");
    }

    private static String weekKey() {
        LocalDate now = net.swordie.ms.util.ResetClock.today();
        return now.get(IsoFields.WEEK_BASED_YEAR) + "w" + now.get(IsoFields.WEEK_OF_WEEK_BASED_YEAR);
    }

    public static int getLevel(Char chr) {
        return Math.max(1, artifact(chr).getIntProperty("level"));
    }

    /** @return the artifact AP not spent on crystal levels */
    public static int getFreeAp(Char chr) {
        int level = Math.min(getLevel(chr), ArtifactData.LEVELS.length);
        int ap = ArtifactData.LEVELS[level - 1][2];
        for (UnionArtifact ua : chr.getAccount().getUnionArtifacts().values()) {
            for (int l = 2; l <= ua.getLevel(); l++) {
                ap -= ArtifactData.getEnforcePoint(l);
            }
        }
        return ap;
    }

    // ------------------------------------------------------------------------------------------------- login

    public static void onLogin(Char chr) {
        if (!isOpen(chr)) {
            return;
        }
        try {
            Quest data = record(chr, DATA_QUEST, "attendance=0;mobCount=0;week=;day=");
            Quest common = record(chr, COMMON_QUEST, "missionState=" + "0".repeat(commonMissions()));
            String week = weekKey();
            if (!week.equals(data.getProperty("week"))) { // a new week: the common missions start over
                data.setProperty("week", week);
                data.setProperty("attendance", 0);
                data.setProperty("mobCount", 0);
                common.setProperty("missionState", "0".repeat(commonMissions()));
                send(chr, common);
            }
            String today = net.swordie.ms.util.ResetClock.today().toString();
            if (!today.equals(data.getProperty("day"))) {
                data.setProperty("day", today);
                data.setProperty("attendance", data.getIntProperty("attendance") + 1);
                checkMissions(chr, "weeklyAttendance", data.getIntProperty("attendance"));
            }
            send(chr, data);
            unlockCrystals(chr);
            applyStatSkills(chr);
            chr.write(UnionArtifactPacket.information());
            chr.write(UnionArtifactPacket.result(chr));
        } catch (Exception e) {
            log.error("Could not set up the Union Artifact of " + chr.getName(), e);
        }
    }

    /** Crystals the artifact level allows, with the first three stats and 30 days */
    private static void unlockCrystals(Char chr) {
        Account account = chr.getAccount();
        Map<Integer, UnionArtifact> artifacts = account.getUnionArtifacts();
        List<Integer> reqLevels = ArtifactData.getCrystalReqLevels();
        List<Integer> stats = ArtifactData.getStatSkills();
        if (stats.size() < 3) {
            return;
        }
        int level = getLevel(chr);
        boolean added = false;
        for (int i = 0; i < reqLevels.size(); i++) {
            if (level >= reqLevels.get(i) && !artifacts.containsKey(i)) {
                FileTime expiry = FileTime.fromDate(LocalDateTime.now().plusDays(CRYSTAL_DAYS));
                artifacts.put(i, new UnionArtifact(i, stats.get(0), stats.get(1), stats.get(2), expiry));
                added = true;
            }
        }
        if (added) {
            DAO.save(account.getId(), artifacts.values());
        }
    }

    /** Each stat skill's level is the sum of the levels of the unexpired crystals that carry it */
    public static void applyStatSkills(Char chr) {
        Map<Integer, Integer> levels = new HashMap<>();
        for (int skillId : ArtifactData.getStatSkills()) {
            levels.put(skillId, 0);
        }
        for (UnionArtifact ua : chr.getAccount().getUnionArtifacts().values()) {
            if (ua.isExpired()) {
                continue;
            }
            for (int i = 0; i < 3; i++) {
                levels.computeIfPresent(ua.getSkill(i), (k, v) -> v + ua.getLevel());
            }
        }
        List<Skill> changed = new ArrayList<>();
        for (var entry : levels.entrySet()) {
            int skillId = entry.getKey();
            Skill current = chr.getSkill(skillId);
            if (entry.getValue() <= 0) {
                if (current != null) {
                    chr.removeSkillAndSendPacket(skillId);
                }
                continue;
            }
            Skill skill = SkillData.getSkillDeepCopyById(skillId);
            if (skill == null) {
                continue;
            }
            int max = Math.max(1, skill.getMaxLevel());
            int level = Math.min(max, entry.getValue());
            if (current != null && current.getCurrentLevel() == level) {
                continue;
            }
            skill.setCurrentLevel(level);
            skill.setMasterLevel(max);
            changed.add(skill);
        }
        if (!changed.isEmpty()) {
            chr.addSkills(changed);
        }
    }

    // ------------------------------------------------------------------------------------------------- missions

    /** Kills of enemies within 10 levels count for the common hunting missions (the top damage dealer's) */
    public static void onMobKilled(Mob mob) {
        onBossMissions(mob);
        Char chr = mob.getMostDamageChar();
        if (!isOpen(chr) || Math.abs(mob.getLevel() - chr.getLevel()) > MOB_LEVEL_RANGE) {
            return;
        }
        Quest data = chr.getQuestManager().getQuestById(DATA_QUEST);
        if (data == null || !data.hasProperty("mobCount")) {
            return; // set up at login
        }
        int count = data.getIntProperty("mobCount");
        if (count >= MOB_COUNT_MAX) {
            return;
        }
        data.setProperty("mobCount", ++count);
        if (count % 100 == 0) {
            send(chr, data);
        }
        checkMissions(chr, "mobKill", count);
    }

    /** Boss hunt and first-clear missions for everyone who hit the clear monster and their party there. */
    private static void onBossMissions(Mob mob) {
        int mobId = mob.getTemplateId();
        int[] hunt = ArtifactData.getHuntMission(mobId);
        int[] first = ArtifactData.getFirstClearMission(mobId);
        if ((hunt == null && first == null) || mob.getField() == null) {
            return;
        }
        Set<Char> winners = new LinkedHashSet<>();
        for (var entry : mob.getDamageDone().entrySet()) {
            Char chr = entry.getKey();
            if (chr == null || entry.getValue() == null || entry.getValue() <= 0) {
                continue;
            }
            winners.add(chr);
            if (chr.getParty() != null) {
                winners.addAll(chr.getParty().getPartyMembersInField(chr));
            }
        }
        for (Char chr : winners) {
            if (chr.getField() != mob.getField() || !isOpen(chr)) {
                continue;
            }
            try {
                creditBoss(chr, mobId, hunt, first);
            } catch (Exception e) {
                log.error("Could not credit the artifact boss missions of " + chr.getName(), e);
            }
        }
    }

    private static void creditBoss(Char chr, int mobId, int[] hunt, int[] first) {
        String key = String.valueOf(mobId);
        Quest clears = chr.getQuestManager().getQuestById(ArtifactData.BOSS_CLEAR_QUEST);
        boolean firstClear = clears == null || !"1".equals(clears.getProperty(key));
        clears = record(chr, ArtifactData.BOSS_CLEAR_QUEST, key + "=1");
        clears.setProperty(key, 1);
        send(chr, clears);
        int exp = 0;
        int point = 0;
        if (first != null && firstClear) {
            exp += first[1];
            point += first[2];
            chr.chatMessage(ChatType.SystemNotice, "[Union Artifact] First clear mission done.");
        }
        if (hunt != null) {
            Quest data = record(chr, DATA_QUEST, "attendance=0;mobCount=0;week=;day=");
            String week = weekKey();
            if (!week.equals(data.getProperty("huntWeek"))) {
                data.setProperty("huntWeek", week);
                data.setProperty("huntCount", 0);
                data.setProperty("hunts", "");
            }
            String done = Objects.toString(data.getProperty("hunts"), "");
            String tag = "." + hunt[0] + ".";
            int count = data.getIntProperty("huntCount");
            if (count < ArtifactData.getHuntMaxCount() && !("." + done).contains(tag)) {
                data.setProperty("huntCount", count + 1);
                data.setProperty("hunts", done + hunt[0] + ".");
                send(chr, data);
                exp += hunt[1];
                point += hunt[2];
                chr.chatMessage(ChatType.SystemNotice, String.format("[Union Artifact] Boss hunt mission done (%d / %d "
                        + "this week).", count + 1, ArtifactData.getHuntMaxCount()));
            }
        }
        if (exp > 0 || point > 0) {
            gain(chr, exp, point);
        }
    }

    private static void checkMissions(Char chr, String type, int value) {
        Quest common = chr.getQuestManager().getQuestById(COMMON_QUEST);
        if (common == null) {
            return;
        }
        StringBuilder state = new StringBuilder(Objects.toString(common.getProperty("missionState"), ""));
        while (state.length() < commonMissions()) {
            state.append('0');
        }
        List<ArtifactData.Mission> missions = ArtifactData.getCommonMissions();
        int exp = 0;
        int point = 0;
        for (int i = 0; i < missions.size() && i < commonMissions(); i++) {
            ArtifactData.Mission m = missions.get(i);
            if (type.equals(m.type) && state.charAt(i) != '2' && value >= m.value) {
                state.setCharAt(i, '2');
                exp += m.exp;
                point += m.point;
                chr.write(UnionArtifactPacket.missionDone(0, i, m.exp, m.point));
                chr.chatMessage(ChatType.SystemNotice, "[Union Artifact] " + m.name + " done.");
            }
        }
        if (exp > 0 || point > 0) {
            common.setProperty("missionState", state.toString());
            send(chr, common);
            gain(chr, exp, point);
        }
    }

    /** Artifact EXP (levels up, unlocking crystals) and points (up to what the level can hold) */
    public static void gain(Char chr, int exp, int point) {
        Quest q = artifact(chr);
        int level = getLevel(chr);
        int curExp = q.getIntProperty("exp") + exp;
        boolean levelUp = false;
        while (level < ArtifactData.LEVELS.length && ArtifactData.LEVELS[level - 1][1] > 0
                && curExp >= ArtifactData.LEVELS[level - 1][1]) {
            curExp -= ArtifactData.LEVELS[level - 1][1];
            level++;
            levelUp = true;
        }
        if (level >= ArtifactData.LEVELS.length) {
            curExp = 0;
        }
        int maxPoints = ArtifactData.LEVELS[level - 1][0];
        q.setProperty("level", level);
        q.setProperty("exp", curExp);
        q.setProperty("point", Math.min(maxPoints, q.getIntProperty("point") + point));
        send(chr, q);
        chr.write(UnionArtifactPacket.gain(exp, point, levelUp ? level : 0));
        if (levelUp) {
            unlockCrystals(chr);
            applyStatSkills(chr);
            chr.write(UnionArtifactPacket.result(chr));
        }
    }

    // ------------------------------------------------------------------------------------------------- requests

    // UNION_ARTIFACT_UPDATE results (the client's SID_UNIONARTIFACT_MSG_REQUESTRESULT_* messages)
    private static final int RESULT_OK = 0;
    private static final int RESULT_INVALID_REQUEST = 2;
    private static final int RESULT_NOT_ENOUGH_POINTS = 5; // INSUFFICIENTARTIFACTPOINT
    private static final int RESULT_NOT_ENOUGH_AP = 6; // INSUFFICIENTSLOTPOINT
    private static final int RESULT_MAX_SLOT_LEVEL = 8; // ALREADYMAXSLOTLEVEL

    private static boolean spendPoints(Char chr, int cost) {
        Quest q = artifact(chr);
        int points = q.getIntProperty("point");
        if (cost < 0 || points < cost) {
            return false;
        }
        q.setProperty("point", points - cost);
        send(chr, q);
        return true;
    }

    private static FileTime extend(UnionArtifact ua) {
        LocalDateTime from = ua.isExpired() ? LocalDateTime.now() : ua.getExpiration().toLocalDateTime();
        ua.setExpiration(FileTime.fromDate(from.plusHours(ArtifactData.getExtendPeriodHours())));
        return ua.getExpiration();
    }

    public static void handleUpdateRequest(Char chr, InPacket inPacket) {
        int type = inPacket.decodeInt();
        if (!isOpen(chr)) {
            chr.dispose();
            return;
        }
        Account account = chr.getAccount();
        Map<Integer, UnionArtifact> artifacts = account.getUnionArtifacts();
        int index = 0;
        int result = RESULT_OK;
        boolean changed = false;
        switch (type) {
            case 0 -> { // extend one
                index = inPacket.decodeInt();
                UnionArtifact ua = artifacts.get(index);
                if (ua == null) {
                    result = RESULT_INVALID_REQUEST;
                } else if (!spendPoints(chr, ArtifactData.getExtendPoint())) {
                    result = RESULT_NOT_ENOUGH_POINTS;
                } else {
                    chr.write(UnionArtifactPacket.extended(index, extend(ua)));
                    changed = true;
                }
            }
            case 1 -> { // extend all
                if (artifacts.isEmpty()) {
                    result = RESULT_INVALID_REQUEST;
                } else if (!spendPoints(chr, ArtifactData.getExtendPoint() * artifacts.size())) {
                    result = RESULT_NOT_ENOUGH_POINTS;
                } else {
                    FileTime last = null;
                    for (UnionArtifact ua : artifacts.values()) {
                        last = extend(ua);
                    }
                    chr.write(UnionArtifactPacket.extended(-1, last));
                    changed = true;
                }
            }
            case 2 -> { // change a crystal's stats
                index = inPacket.decodeInt();
                int[] skills = {inPacket.decodeInt(), inPacket.decodeInt(), inPacket.decodeInt()};
                UnionArtifact ua = artifacts.get(index);
                List<Integer> valid = ArtifactData.getStatSkills();
                boolean ok = ua != null && skills[0] != skills[1] && skills[0] != skills[2] && skills[1] != skills[2];
                for (int skill : skills) {
                    ok &= valid.contains(skill);
                }
                if (!ok) {
                    result = RESULT_INVALID_REQUEST;
                } else if (!spendPoints(chr, ArtifactData.getChangePoint())) {
                    result = RESULT_NOT_ENOUGH_POINTS;
                } else {
                    for (int i = 0; i < 3; i++) {
                        ua.setSkill(i, skills[i]);
                    }
                    changed = true;
                }
            }
            case 3 -> { // all crystals back to level 1 (frees their AP)
                if (spendPoints(chr, ArtifactData.getResetPoint())) {
                    artifacts.values().forEach(ua -> ua.setLevel(1));
                    changed = true;
                } else {
                    result = RESULT_NOT_ENOUGH_POINTS;
                }
            }
            case 4 -> { // level a crystal up with AP
                index = inPacket.decodeInt();
                UnionArtifact ua = artifacts.get(index);
                if (ua == null) {
                    result = RESULT_INVALID_REQUEST;
                } else if (ua.getLevel() >= ArtifactData.getMaxSlotLevel()) {
                    result = RESULT_MAX_SLOT_LEVEL;
                } else if (getFreeAp(chr) < ArtifactData.getEnforcePoint(ua.getLevel() + 1)) {
                    result = RESULT_NOT_ENOUGH_AP;
                } else {
                    ua.setLevel(ua.getLevel() + 1);
                    changed = true;
                }
            }
            default -> {
                // 7: window opened; 8: special mission sync (not tracked)
            }
        }
        if (changed) {
            DAO.save(account.getId(), artifacts.values());
            applyStatSkills(chr);
            chr.write(UnionArtifactPacket.result(chr));
        }
        chr.write(UnionArtifactPacket.update(type, result, index));
        chr.dispose();
    }

    // ------------------------------------------------------------------------------------------------- encoding

    /** count, then (index, index, level, 3 skills, expiry) per crystal */
    public static void encode(Char chr, OutPacket outPacket) {
        Map<Integer, UnionArtifact> artifacts = chr.getAccount() != null && chr.getId() != 0
                ? chr.getAccount().getUnionArtifacts() : Map.of();
        outPacket.encodeInt(artifacts.size());
        for (UnionArtifact ua : artifacts.values()) {
            outPacket.encodeInt(ua.getIndex());
            outPacket.encodeInt(ua.getIndex());
            ua.encode(outPacket);
        }
    }
}
