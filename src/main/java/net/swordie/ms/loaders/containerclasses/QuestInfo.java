package net.swordie.ms.loaders.containerclasses;

import net.swordie.ms.client.character.quest.progress.QuestProgressRequirement;
import net.swordie.ms.client.character.quest.requirement.QuestStartRequirement;
import net.swordie.ms.client.character.quest.reward.QuestReward;

import java.util.*;

/**
 * Created on 3/2/2018.
 */
public class QuestInfo {
    private int questID;
    private int startNpc;
    private boolean normalAutoStart;
    private Set<QuestStartRequirement> questStartRequirements = new HashSet<>();
    private Set<QuestProgressRequirement> questProgressRequirements = new HashSet<>();
    private Set<QuestReward> questRewards = new LinkedHashSet<>(); // keeps the WZ order (needed for selectable rewards)
    private Set<Integer> fieldEnters = new HashSet<>();
    private int infoNumber;
    private long end;
    private long endT;
    private String startScript;
    private String endScript;
    private String resignationScript;
    private long start;
    private long startT;
    private int endNpc;
    private int subJobFlags;
    private boolean completeNpcAutoGuide;
    private int skill;
    private int fieldSetKeepTime;
    private String fieldSet;
    private boolean autoStart;
    private int deathCount;
    private List<Integer> scenarios = new ArrayList<>();
    private List<String> speech = new ArrayList<>();
    private int mobDropMeso;
    private int morph;
    private boolean secret;
    private Map<Integer, Integer> transferFields;
    private int nextQuest;
    private boolean autoComplete;
    private boolean selfStart;
    private boolean selfComplete;

    public boolean isSelfStart() { return selfStart; }
    public void setSelfStart(boolean value) { selfStart = value; }
    public boolean isSelfComplete() { return selfComplete; }
    public void setSelfComplete(boolean value) { selfComplete = value; }
    private int medalItemId;
    private boolean scenarioQuest;
    private int timeLimit;
    private Map<String, Integer> exProgressRequirements = new HashMap<>();
    // Requirements from Check.img/[id]/1 that must hold when completing (besides the progress requirements)
    private Set<QuestStartRequirement> questCompleteRequirements = new HashSet<>();
    // Check.img QuestOrOption: other-quest requirements are OR'ed instead of AND'ed
    private boolean startQuestOrOption;
    private boolean startItemOrOption; // Check.img ItemOrOption: having any one of the listed items is enough
    private boolean completeItemOrOption;
    private boolean completeQuestOrOption;
    // Repeat information from Check.img/[id]/0: interval (minutes), dayByDay, weeklyRepeat
    private int repeatInterval = -1;
    private boolean dayByDay;
    private boolean weeklyRepeat;
    // QuestInfo.img dailyAlarm: recurring daily/weekly tasks whose Check.img data does not always say dayByDay.
    private boolean dailyAlarm;
    // QuestInfo.img startNavi: the client starts the navigation to the quest's target after accepting
    private boolean startNavi;
    // QuestInfo.img disableAtPerformTab / blocked: the client never lists the quest under "In Progress" (internal
    // markers, drop controls, event trackers). @quests hides them too.
    private boolean hiddenInQuestLog;
    // QuestInfo.img blocked
    private boolean blocked;

    public Set<QuestReward> getQuestRewards() {
        return questRewards;
    }

    public void setQuestID(int questID) {
        this.questID = questID;
    }

    public int getQuestID() {
        return questID;
    }

    public void setStartNpc(int startNpc) {
        this.startNpc = startNpc;
    }

    public int getStartNpc() {
        return startNpc;
    }

    public void setNormalAutoStart(boolean normalAutoStart) {
        this.normalAutoStart = normalAutoStart;
    }

    public boolean isNormalAutoStart() {
        return normalAutoStart;
    }

    public Set<QuestStartRequirement> getQuestStartRequirements() {
        return questStartRequirements;
    }

    public void addRequirement(QuestStartRequirement qr) {
        getQuestStartRequirements().add(qr);
    }

    public void setInfoNumber(int infoNumber) {
        this.infoNumber = infoNumber;
    }

    public int getInfoNumber() {
        return infoNumber;
    }

    public void setEnd(long end) {
        this.end = end;
    }

    public long getEnd() {
        return end;
    }

    public void setEndT(long endT) {
        this.endT = endT;
    }

    public long getEndT() {
        return endT;
    }

    public void setStartScript(String startScript) {
        this.startScript = startScript;
    }

    public String getStartScript() {
        return startScript == null ? "" : startScript;
    }

    public void setEndScript(String endScript) {
        this.endScript = endScript;
    }

    public String getEndScript() {
        return endScript == null ? "" : endScript;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public long getStart() {
        return start;
    }

    public void setStartT(long startT) {
        this.startT = startT;
    }

    public long getStartT() {
        return startT;
    }

    public void setEndNpc(int endNpc) {
        this.endNpc = endNpc;
    }

    public int getEndNpc() {
        return endNpc;
    }

    public Set<QuestProgressRequirement> getQuestProgressRequirements() {
        return questProgressRequirements;
    }

    public void addProgressRequirement(QuestProgressRequirement qpr) {
        getQuestProgressRequirements().add(qpr);
    }

    public void addReward(QuestReward qir) {
        getQuestRewards().add(qir);
    }

    public void setSubJobFlags(int subJobFlags) {
        this.subJobFlags = subJobFlags;
    }

    public int getSubJobFlags() {
        return subJobFlags;
    }

    public Set<Integer> getFieldEnters() {
        return fieldEnters;
    }

    public void addFieldEnter(int fieldID) {
        getFieldEnters().add(fieldID);
    }

    public void setCompleteNpcAutoGuide(boolean completeNpcAutoGuide) {
        this.completeNpcAutoGuide = completeNpcAutoGuide;
    }

    public boolean isCompleteNpcAutoGuide() {
        return completeNpcAutoGuide;
    }

    public void setSkill(int skill) {
        this.skill = skill;
    }

    public int getSkill() {
        return skill;
    }

    public void setFieldSetKeepTime(int fieldSetKeepTime) {
        this.fieldSetKeepTime = fieldSetKeepTime;
    }

    public int getFieldSetKeepTime() {
        return fieldSetKeepTime;
    }

    public void setFieldSet(String fieldSet) {
        this.fieldSet = fieldSet;
    }

    public String getFieldSet() {
        return fieldSet == null ? "" : fieldSet;
    }

    public void setAutoStart(boolean autoStart) {
        this.autoStart = autoStart;
    }

    public boolean isAutoStart() {
        return autoStart;
    }

    public void setDeathCount(int deathCount) {
        this.deathCount = deathCount;
    }

    public int getDeathCount() {
        return deathCount;
    }

    public void addSpeech(String script) {
        if (script != null && !"".equals(script)) {
            getSpeech().add(script);
        }
    }

    public List<String> getSpeech() {
        return speech;
    }

    public void setSpeech(List<String> speech) {
        this.speech = speech;
    }

    public void addScenario(int value) {
        getScenarios().add(value);
    }

    public List<Integer> getScenarios() {
        return scenarios;
    }

    public void setScenarios(List<Integer> scenarios) {
        this.scenarios = scenarios;
    }

    public void setMobDropMeso(int mobDropMeso) {
        this.mobDropMeso = mobDropMeso;
    }

    public int getMobDropMeso() {
        return mobDropMeso;
    }

    public void setMorph(int morph) {
        this.morph = morph;
    }

    public int getMorph() {
        return morph;
    }

    public void setSecret(boolean secret) {
        this.secret = secret;
    }

    public boolean isSecret() {
        return secret;
    }

    public void addTransferField(int status, int transferField) {
        if (this.transferFields == null){
            this.transferFields = new HashMap<>();
        }
        this.transferFields.put(status, transferField);
    }

    public void setTransferFields(Map<Integer, Integer> transferFields){ this.transferFields = transferFields;}

    public Map<Integer, Integer> getTransferFields() {return transferFields;}

    public void setNextQuest(int nextQuest) {
        this.nextQuest = nextQuest;
    }

    public int getNextQuest() {
        return nextQuest;
    }

    public void setAutoComplete(boolean autoComplete) {
        this.autoComplete = autoComplete;
    }

    public boolean isAutoComplete() {
        return autoComplete;
    }

    public int getMedalItemId() {
        return medalItemId;
    }

    public void setMedalItemId(int medalItem) {
        this.medalItemId = medalItem;
    }

    public void setScenarioQuest(boolean scenarioQuest) {
        this.scenarioQuest = scenarioQuest;
    }

    public boolean isScenarioQuest() {
        return scenarioQuest;
    }

    public int getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(int timeLimit) {
        this.timeLimit = timeLimit;
    }

    public Map<String, Integer> getExProgressRequirements() {
        return exProgressRequirements;
    }

    public void setExProgressRequirements(Map<String, Integer> exProgressRequirements) {
        this.exProgressRequirements = exProgressRequirements;
    }

    public String getResignationScript() {
        return resignationScript == null ? "" : resignationScript;
    }

    public void setResignationScript(String resignationScript) {
        this.resignationScript = resignationScript;
    }

    public Set<QuestStartRequirement> getQuestCompleteRequirements() {
        return questCompleteRequirements;
    }

    public void addCompleteRequirement(QuestStartRequirement qr) {
        getQuestCompleteRequirements().add(qr);
    }

    public boolean isStartQuestOrOption() {
        return startQuestOrOption;
    }

    public void setStartQuestOrOption(boolean startQuestOrOption) {
        this.startQuestOrOption = startQuestOrOption;
    }

    public boolean isCompleteQuestOrOption() {
        return completeQuestOrOption;
    }

    public void setCompleteQuestOrOption(boolean completeQuestOrOption) {
        this.completeQuestOrOption = completeQuestOrOption;
    }

    /**
     * @return the amount of minutes after completion before this quest can be started again, or -1 if the quest
     * isn't repeated by interval.
     */
    public int getRepeatInterval() {
        return repeatInterval;
    }

    public void setRepeatInterval(int repeatInterval) {
        this.repeatInterval = repeatInterval;
    }

    public boolean isDayByDay() {
        return dayByDay;
    }

    public void setDayByDay(boolean dayByDay) {
        this.dayByDay = dayByDay;
    }

    public boolean isWeeklyRepeat() {
        return weeklyRepeat;
    }

    public void setWeeklyRepeat(boolean weeklyRepeat) {
        this.weeklyRepeat = weeklyRepeat;
    }

    public boolean isDailyAlarm() {
        return dailyAlarm;
    }

    public void setDailyAlarm(boolean dailyAlarm) {
        this.dailyAlarm = dailyAlarm;
    }

    /**
     * @return whether or not this quest can be done again after completing it (interval, daily or weekly quests).
     */
    public boolean isRepeatable() {
        return getRepeatInterval() >= 0 || isDayByDay() || isWeeklyRepeat() || isDailyAlarm();
    }

    public boolean isStartNavi() {
        return startNavi;
    }

    public void setStartNavi(boolean startNavi) {
        this.startNavi = startNavi;
    }

    public boolean isHiddenInQuestLog() {
        return hiddenInQuestLog;
    }

    public void setHiddenInQuestLog(boolean hiddenInQuestLog) {
        this.hiddenInQuestLog = hiddenInQuestLog;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    /**
     * @return whether this quest can't be started any more ({@link net.swordie.ms.constants.RetiredQuests}): the data
     * blocks it and a newer questline replaces it, or the data can no longer run it ({@link #isUnreachable()})
     */
    public boolean isRetired() {
        return (blocked && net.swordie.ms.constants.RetiredQuests.contains(questID)) || isUnreachable();
    }

    /** @return whether a start or end NPC, map or monster this quest needs is gone from the data */
    public boolean isUnreachable() {
        return net.swordie.ms.constants.RetiredQuests.isUnreachable(questID);
    }

    public boolean isStartItemOrOption() {
        return startItemOrOption;
    }

    public void setStartItemOrOption(boolean startItemOrOption) {
        this.startItemOrOption = startItemOrOption;
    }

    public boolean isCompleteItemOrOption() {
        return completeItemOrOption;
    }

    public void setCompleteItemOrOption(boolean completeItemOrOption) {
        this.completeItemOrOption = completeItemOrOption;
    }

    private int area; // QuestInfo.img area: the quest log category (50 = Event)

    public int getArea() {
        return area;
    }

    public void setArea(int area) {
        this.area = area;
    }

    /**
     * @return whether this is an event quest (quest log category Event) whose end date (Check.img/0/end,
     * yyyyMMddHHmm) has passed: such quests can't be started any more
     */
    public boolean isExpiredEvent() {
        if (area != 50 || end <= 0) {
            return false;
        }
        if (net.swordie.ms.world.SeasonalEvents.overridesQuestDates(questID)) {
            return false; // a yearly event that's on again (the Haunted Mansion)
        }
        String s = String.valueOf(end);
        try {
            java.time.LocalDateTime endTime;
            if (s.length() == 12) {
                endTime = java.time.LocalDateTime.parse(s, java.time.format.DateTimeFormatter.ofPattern("yyyyMMddHHmm"));
            } else if (s.length() == 10) {
                endTime = java.time.LocalDateTime.parse(s, java.time.format.DateTimeFormatter.ofPattern("yyyyMMddHH"));
            } else if (s.length() == 8) {
                endTime = java.time.LocalDate.parse(s, java.time.format.DateTimeFormatter.ofPattern("yyyyMMdd")).atStartOfDay();
            } else {
                return false;
            }
            return net.swordie.ms.util.ResetClock.now().toLocalDateTime().isAfter(endTime); // the client's clock
        } catch (java.time.format.DateTimeParseException e) {
            return false;
        }
    }
}
