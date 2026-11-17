# NPC 9000036's Npc.wz script name is "A_office", so this file runs when it is clicked instead of 9000036.py.
# Keep the two the same.
# Agent E: recruiter, Today's Mission, special operation, Agent Points (the Secret Agent event, see SecretAgentEvent)
# Lv. 10+ to be recruited; Today's Mission shows its progress.
from net.swordie.ms.world import SecretAgentEvent as Agent
sm.setSpeakerType(3)
sm.setSpeakerID(Agent.AGENT_E)

execfile("scripts/npc/_secret_agent_journal.py")
_agent_open(Agent.AGENT_E)

HQ = Agent.HQ


def recruited():
    if Agent.isAgent(chr):
        return True
    sm.sendSayOkay("Who are you? This is a restricted area. Talk to #bAgent E#k in any major town if you want to "
                   "become an agent.")
    return False

def recruit():
    if chr.getLevel() < Agent.MIN_LEVEL:
        sm.sendSayOkay("Psst... you're a little green to be a Special Agent. Come back at #bLv. %d#k, and I'll have "
                       "a proposition for you." % Agent.MIN_LEVEL)
        return
    sm.sendNext("Agent #b#h0##k! Come in, over.\r\n#L0##bSay what? Agent? I'm not an agent.#l")
    sm.sendNext("Not yet, you aren't. I'm #bAgent E#k. There are agents all over Maple World trying to find one of "
                "Maple's Most Wanted: #e#r'Master M'#k#n.\r\n#L0##bWho is Master M?#l")
    sm.sendNext("#e#rMaster M#k#n is not only deadly, but mysterious. Nobody knows if Master M is a man or a woman, "
                "an adult or a child. Master M appears and vanishes in an instant, which makes Master M #ra threat "
                "to Maple World#k!")
    if not sm.sendAskAccept("Maple World needs you, #b#h0##k. Will you become a Special Agent?"):
        sm.sendSayOkay("You're not agent material yet. Come see me again when you're ready.")
        return
    if not _agent_action("recruit", chr):
        sm.sendSayOkay("Make some room in your Equip inventory first.")
        return
    sm.sendNext("Congratulations! You are now an agent. Here are your #bParachute Agent Shoes#k #i1072366#.\r\n\r\n"
                "Earn the respect of the other agents and you'll be wearing the full agent gear in no time: the "
                "#bAgent Suits#k, #bAgent O's Gloves#k, #bAgent C's and Agent N's Receivers#k and the "
                "#bAgent Cape#k.")
    sm.sendSayOkay("Talk to me every day for #bToday's Mission#k. The other agents work from the #bM-Forcer "
                   "Operations HQ#k. I can take you there any time. In case of wiretaps, let's end this "
                   "conversation.")


def todays_mission():
    mission = _agent_action("todaysMission", chr)
    state = _agent_action("missionState", chr)
    if state == "clear":
        if _agent_action("reportMission", chr):
            sm.sendSayOkay("Good job. You have completed today's mission. Your mission performance file will be "
                           "reported to headquarters.\r\n\r\n#i2022336# #t2022336# and 20 Agent "
                           "Points.")
        else:
            sm.sendSayOkay("Make some room in your Use inventory first.")
    elif state == "done":
        sm.sendSayOkay("You've already completed today's mission. A new one arrives tomorrow (midnight, Pacific).")
    else:
        if mission.name() == "SPECIAL_OP" and not Agent.isOnSpecialOp(chr):
            _agent_action("startSpecialOp", chr)
        if mission.name() == "ENRICH":
            _agent_action("startEnrichment", chr)
        sm.sendSayOkay("#eToday's Mission: %s#n\r\n\r\n%s\r\n\r\n%s" % (mission.title, mission.task,
                                                                     Agent.missionProgress(chr)))
        if mission.name() == "MELON" and sm.getFieldID() == HQ:
            # Agent W has no native talk script in this client. Open his task
            # here so the player can enter and hand in through Agent E.
            execfile("scripts/npc/9000032.py")


def special_op():
    scroll = Agent.scrollFor(chr)
    if _agent_action("turnInScrolls", chr):
        sm.sendSayOkay("#b20 #t%d#s#k! Excellent. These will help us track Master M down. 30 Agent Points for your "
                       "file." % scroll)
    elif Agent.isOnSpecialOp(chr):
        sm.sendSayOkay("Bring back #b20#k #b#t%d#s#k #i%d#. The monsters near your level are secretly carrying "
                       "them. That is your mission. (You have %d.)" % (scroll, scroll, sm.getQuantityOfItem(scroll)))
    else:
        sm.sendNext("You must be Special Agent #b#h0##k. An urgent order has arrived for you.")
        sm.sendNext("There have been reports of mysterious circles appearing in town, and some have even said "
                    "they've witnessed a UFO kidnapping Master M.")
        if sm.sendAskAccept("We must find #b#t%d#s#k to protect Maple World. Bring back #b20#k of them. The "
                            "monsters near your level are secretly carrying them. Will you do it?" % scroll):
            _agent_action("startSpecialOp", chr)
            sm.sendSayOkay("Good luck, agent.")


def points():
    rec = Agent.record(chr)
    total = int(rec.getOrDefault("pts", "0"))
    today = int(rec.getOrDefault("dpts", "0"))
    Agent.retryPointRewards(chr)
    text = "#eAgent Points#n\r\nToday: #b%d#k   Total: #b%d#k\r\n\r\n" % (today, total)
    text += "%s 100 in one day: #t1052165# and #t1002797# (7 days)\r\n" % ("#g(done today)#k" if rec.get("suit") else "-")
    text += "%s 200 in all: #t1102174#\r\n" % ("#g(done)#k" if rec.get("cape") else "-")
    text += "%s 350 in all: #t1052166# and #t1002800#\r\n" % ("#g(done)#k" if rec.get("inv") else "-")
    text += "%s 700 in all: #t1052167#\r\n\r\n" % ("#g(done)#k" if rec.get("ult") else "-")
    text += ("Points come from Today's Mission, the special operation, the Speed Quiz, badges, documents and "
             "watermelons.")
    sm.sendSayOkay(text)


def true_agent():
    if Agent.isTrueAgent(chr):
        sm.sendSayOkay("You're a true Special Agent, #b#h0##k. Maple World is lucky to have you.")
        return
    missing = Agent.missingForTrueAgent(chr)
    if missing is not None:
        sm.sendSayOkay("A true Special Agent wears the full set: #t1002800#, #t1032058#, #t1102174#, Agent Shoes, "
                       "#t1082245#, and the Invincible or Ultimate Agent Suit.\r\n\r\nYou're still missing: "
                       "#b%s#k." % missing)
        return
    if _agent_action("claimTrueAgent", chr):
        sm.sendSayOkay("Hey! Agent #b#h0##k! Judging by your clothes, you've completed every special agent task. "
                       "The magic on your shoes is running out, so here: #b#t1072368##k #i1072368#, and the "
                       "#bSecret Agent#k medal #i1142576#. You are a true Special Agent!")
    else:
        sm.sendSayOkay("Make room for two items in your Equip inventory first.")


if not Agent.isAgent(chr):
    recruit()
elif (_agent_action("missionState", chr) == "clear"
      and sm.sendAskYesNo("#eToday's Mission is complete!#n\r\n\r\n"
                          "You have finished #b%s#k. Report it now and claim your "
                          "#bSecret Box and 20 Agent Points#k?"
                          % Agent.todaysMission(chr).title)):
    todays_mission()
else:
    in_hq = sm.getFieldID() == HQ
    state = _agent_action("missionState", chr)
    text = "What is it, agent?%s\r\n#b" % ("\r\n#g(Today's Mission is done: report it!)#k" if state == "clear"
                                           else "")
    text += ("#L0##gComplete today's mission and claim rewards#k#l\r\n"
             if state == "clear" else "#L0#Today's Mission#l\r\n")
    text += "#L1#Special operation (Master M's scrolls)#l\r\n"
    text += "#L2#Agent Points and rewards#l\r\n"
    text += "#L3#True Special Agent#l\r\n"
    text += "#L4#I lost my Parachute Agent Shoes#l\r\n"
    text += "#L5#Leave the HQ#l" if in_hq else "#L5#Take me to the Agent HQ#l"
    if in_hq:
        text += "\r\n#L6#Talk to another agent (missions and turn-ins)#l"
    if Agent.isStaff(chr):  # testing options, staff accounts only
        text += "\r\n#k#L8#(Staff) Pick today's mission#l\r\n#L9#(Staff) Start today over#l"
    sel = sm.sendNext(text)
    if sel == 0:
        todays_mission()
    elif sel == 1:
        special_op()
    elif sel == 2:
        points()
    elif sel == 3:
        true_agent()
    elif sel == 4:
        if _agent_action("replaceShoes", chr):
            sm.sendSayOkay("These are my last pair, so make sure you don't lose them. #i1072366#")
        else:
            sm.sendSayOkay("You still have a pair of agent shoes, or your Equip inventory is full.")
    elif sel == 6 and in_hq:
        agents = [(9000032, "Agent W: watermelon mission"),
                  (9000034, "Agent O: Special Agent Badges"),
                  (9000033, "Agent C: Intelligence Documents"),
                  (9000037, "Agent Meow: Speed Quiz"),
                  (9000038, "Agent Kitty: Enrichment Program"),
                  (9000035, "Agent P: briefing and progress")]
        choices = "Who do you need?\r\n"
        for i in range(len(agents)):
            choices += "#L%d#%s#l\r\n" % (i, agents[i][1])
        pick = sm.sendNext(choices)
        if 0 <= pick < len(agents):
            execfile("scripts/npc/%d.py" % agents[pick][0])
    elif sel == 8 and Agent.isStaff(chr):
        missions = list(Agent.Mission.values())
        pick = "Which mission?\r\n#b"
        for i in range(len(missions)):
            pick += "#L%d#%s#l\r\n" % (i, missions[i].title)
        i = sm.sendNext(pick)
        if 0 <= i < len(missions):
            _agent_action("setMission", chr, missions[i])
            if missions[i].name() == "SPECIAL_OP" and not Agent.isOnSpecialOp(chr):
                _agent_action("startSpecialOp", chr)
            if missions[i].name() == "ENRICH":
                _agent_action("startEnrichment", chr)
            sm.sendSayOkay("Today's mission is now: #b%s#k." % missions[i].title)
    elif sel == 9 and Agent.isStaff(chr):
        if sm.sendAskYesNo("Start today over? Today's mission, today's points, watermelons and the quiz timer reset. "
                           "(Totals, badges, documents and rewards already earned are kept.)"):
            _agent_action("resetToday", chr)
            sm.sendSayOkay("Done. Fresh day, agent.")
    elif sel == 5:
        if in_hq:
            sm.warp(Agent.returnTown(chr))
        else:
            Agent.rememberTown(chr)
            sm.warp(HQ)
