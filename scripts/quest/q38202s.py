# 38202 [Karote] Rumblings of the Tower - start (Neinheart). The Chief Knights' meeting; the quest has no end NPC and
# is done once the meeting ends, which sends you to Hotel Arcus.
NEINHEART = 1101002
SALOON = 410003000

sm.setSpeakerID(NEINHEART)
sm.setBoxChat()
sm.sendNext("The Chief Knights have reported in from across Maple World. Most of it is what we feared: High Flora "
            "scouts, and Angler Company ships where they have no business being.")
sm.sendNext("But one report stands out. Travelers in the desert speak of a tower that rises and falls on its own... "
            "a legendary tower called #bKarote#k.")
if sm.sendAskYesNo("The Seekers of #bHotel Arcus#k know the desert better than anyone. Will you go and find out "
                   "what they know about Karote?"):
    sm.startQuest(parentID)
    sm.completeQuestNoCheck(parentID)
    if sm.getFieldID() != SALOON and sm.sendAskYesNo("Shall I send you to #b#m410003000##k now?"):
        sm.warp(SALOON)
sm.dispose()
