# Bridge the year-round agent event to its original quest journal. Loaded with execfile, so script
# reloads also pick up changes to this helper.
from net.swordie.ms.loaders import QuestData


def _agent_start(quest_id):
    qm = chr.getQuestManager()
    if qm.getQuestById(quest_id) is None:
        quest = QuestData.createQuestFromId(quest_id)
        if quest is not None:
            # The event owns its rewards; never run the expired Act.img rewards.
            qm.addCustomQuest(quest)


def _agent_complete(quest_id):
    if not chr.getQuestManager().hasQuestCompleted(quest_id):
        _agent_start(quest_id)
        sm.completeQuestNoRewards(quest_id)


def _agent_sync():
    if not Agent.isAgent(chr):
        return
    rec = Agent.record(chr)
    if int(rec.getOrDefault("badges", "0")) > 0:
        _agent_complete(10001)
        _agent_complete(10002)
    if int(rec.getOrDefault("docs", "0")) >= 5:
        _agent_complete(10007)
        _agent_complete(10008)
    if int(rec.getOrDefault("melonsAll", "0")) >= 5:
        _agent_complete(10005)
    if rec.get("true") is not None:
        _agent_complete(10021)
    mission_name = rec.get("mission")
    if mission_name is not None:
        mission = Agent.Mission.valueOf(mission_name)
        quest_id = mission.quest
        state = rec.getOrDefault("mstate", "ing")
        # Reset only the journal of a new day's task; preserve event totals/rewards.
        day_key = "journalDay%d" % quest_id
        if rec.get(day_key) != rec.get("day"):
            qm = chr.getQuestManager()
            if qm.getQuestById(quest_id) is not None:
                qm.removeQuest(quest_id)
            rec.put(day_key, rec.get("day"))
            Agent.save(chr, rec)
        _agent_start(quest_id)
        _agent_complete(10014)
        # Original daily quests read these infoNumber records for readiness.
        info_id = {10015: 10024, 10017: 10026, 10018: 10027,
                   10019: 10028, 10020: 10029}[quest_id]
        info = chr.getQuestManager().getQuestById(info_id)
        # Agent E pays the reward and completes the journal in one flow. Do not
        # enable the native reward-bearing completion before that payout.
        qr = "clear" if state == "done" else "ing"
        if info is None or info.getQRValue() != qr:
            _agent_start(info_id)
            sm.setQRValue(info_id, qr, False)
        if state == "done":
            _agent_complete(quest_id)
    if Agent.isOnSpecialOp(chr):
        # Keep the accepted tier after levelling; the event accepts its old scrolls.
        qm = chr.getQuestManager()
        if not any(qm.hasQuestInProgress(q) for q in (10010, 10011, 10012)):
            quest_id = 10010 + list(Agent.SCROLLS).index(Agent.scrollFor(chr))
            if qm.hasQuestCompleted(quest_id):
                qm.removeQuest(quest_id)
            _agent_start(quest_id)


def _agent_open(npc_id):
    if not Agent.isAgent(chr):
        return
    _agent_sync()
    if npc_id == Agent.AGENT_O:
        _agent_complete(10001)
        _agent_start(10002)
    elif npc_id == Agent.AGENT_C:
        _agent_start(10007)
    elif npc_id == Agent.AGENT_W:
        # The native quest consumes five melons with no selected tube reward.
        # Keep it out of the active journal; _agent_sync completes it only after
        # the custom hand-in has paid. Retire entries created earlier as well.
        if chr.getQuestManager().hasQuestInProgress(10005):
            chr.getQuestManager().removeQuest(10005)


def _agent_action(name, *args):
    # Capture the actual consumed tier before the event changes the inventory.
    scrolls = []
    if name == "turnInScrolls":
        scrolls = [(item, sm.getQuantityOfItem(item)) for item in Agent.SCROLLS]
    result = getattr(Agent, name)(*args)
    if name == "turnInScrolls" and result:
        for item, count in scrolls:
            if sm.getQuantityOfItem(item) < count:
                _agent_complete(10010 + list(Agent.SCROLLS).index(item))
        # One operation was handed in even if the player outlevelled its tier.
        for quest_id in (10010, 10011, 10012):
            if chr.getQuestManager().hasQuestInProgress(quest_id):
                _agent_complete(quest_id)
    elif name == "replaceShoes" and result:
        _agent_complete(10023)
    _agent_sync()
    return result
