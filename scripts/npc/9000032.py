# Agent W: Chumji's watermelon field and Pink Flower Tubes (the Secret Agent event, see SecretAgentEvent)
from net.swordie.ms.world import SecretAgentEvent as Agent
sm.setSpeakerType(3)
sm.setSpeakerID(Agent.AGENT_W)

execfile("scripts/npc/_secret_agent_journal.py")
_agent_open(Agent.AGENT_W)

HQ = Agent.HQ


def recruited():
    if Agent.isAgent(chr):
        return True
    sm.sendSayOkay("Who are you? This is a restricted area. Talk to #bAgent E#k in any major town if you want to "
                   "become an agent.")
    return False


def missed_native_reward():
    rec = Agent.record(chr)
    return (chr.getQuestManager().hasQuestCompleted(10005)
            and int(rec.getOrDefault("melonsAll", "0")) == 0
            and int(rec.getOrDefault("melons", "0")) >= 5
            and sm.getQuantityOfItem(Agent.WATERMELON) == 0
            and rec.get("nativeMelonRepair") is None)


def recover_native_reward():
    if not missed_native_reward():
        sm.sendSayOkay("There is no missed five-watermelon reward to claim.")
        return
    from net.swordie.ms.world import SeasonalEvents
    from net.swordie.ms.loaders import ItemData
    weapon = Agent.agentWeapon(chr)
    if weapon != 0:
        if ItemData.getItemDeepCopy(weapon) is None:
            sm.sendSayOkay("Your Pink Flower Tube could not be loaded. Your reward is still waiting.")
            return
        if not sm.canHold(weapon):
            sm.sendSayOkay("Make one free slot in your Equip inventory, then claim your missed reward again.")
            return
        sm.giveItemWithExpiry(weapon, 7 * 24)
    else:
        SeasonalEvents.giveCoins(chr, 5, "Agent W missed watermelon reward")
    rec = Agent.record(chr)
    rec.put("nativeMelonRepair", "1")
    rec.put("melonsAll", "5")
    Agent.save(chr, rec)
    Agent.clearMission(chr, Agent.Mission.MELON)
    Agent.addPoints(chr, 50, "five watermelons (missed turn-in)")
    _agent_sync()
    reward = "#i%d# #t%d# (7 days)" % (weapon, weapon) if weapon != 0 else "the Agent Points (there's no Pink Flower Tube for your weapon type)"
    sm.sendSayOkay("The old quest took your five watermelons before my turn-in could record them. "
                   "Here is your missing reward: %s. Your five deliveries and 50 Agent Points are now "
                   "recorded. Report today's mission to Agent E if you haven't already." % reward)


if recruited():
    n = sm.getQuantityOfItem(Agent.WATERMELON)
    total = int(Agent.record(chr).getOrDefault("melonsAll", "0"))
    text = "I'm #bAgent W#k, demolitions expert. Intelligence says #e#rMaster M#k#n has planted bombs in Chumji's " \
           "watermelons.\r\n#b"
    rec = Agent.record(chr)
    if rec.get("mission") == "MELON" and rec.get("mstate") == "ing":
        text += ("\r\n#k#eToday's Mission#n: enter the field below, climb past the Hoguls, "
                 "and attack the watermelon at the top. You'll return to the HQ with one watermelon. "
                 "Hand it to me, then report to Agent E. #bOne watermelon#k completes today's "
                 "mission; the five-watermelon Pink Flower Tube reward is separate.\r\n\r\n#b")
    text += "#L0#Go to the watermelon field (%d left today)#l\r\n" % Agent.melonsLeftToday(chr)
    if n > 0:
        text += "#L1#Hand in %d watermelon%s#l\r\n" % (n, "" if n == 1 else "s")
    if missed_native_reward():
        text += "#L3#Claim the missing reward for my five watermelons#l\r\n"
    text += "#L2#About the reward#l"
    sel = sm.sendNext(text)
    if sel == 0:
        if Agent.melonsLeftToday(chr) <= 0:
            sm.sendSayOkay("That's enough watermelons for one day. Chumji is getting suspicious. Come back tomorrow.")
        elif sm.sendAskYesNo("Avoid the #rHoguls#k, climb to the top of the field and pick a watermelon - "
                             "carefully. I'll pull you back here once you have it. (Type @stuck if you want out.) "
                             "Ready?"):
            sm.warp(Agent.WATERMELON_FIELD)
    elif sel == 1:
        taken = _agent_action("turnInWatermelons", chr)
        if taken > 0:
            sm.sendSayOkay("Oh! Be careful. Any sudden movement can trigger the bomb... Great work. Every 5 "
                           "watermelons earn you a Pink Flower Tube. (%d delivered so far.)" % (total + taken))
        else:
            sm.sendSayOkay("Make some room in your Equip inventory first.")
    elif sel == 3:
        recover_native_reward()
    else:
        sm.sendSayOkay("Every #b5 watermelons#k you bring me, I give you a #bPink Flower Tube#k for the weapon "
                       "you're holding (7 days; the Lv. 50 one from Lv. 50) and some EXP. No tube for your weapon "
                       "type? Then it's just the EXP. Each watermelon is also worth 10 Agent Points. (%d "
                       "delivered so far.)" % total)
