# Fantasy Theme World (Dylan & Delilah, 31363-31392). Official lines where the client has them; otherwise written from
# the quests' Quest.wz summaries / the NPCs' Npc.wz lines. Data: ftwgen/gen_ftw.py.
# The Fluctuating Heartbeat Sensor (31372), start - Dylan: "Find the cardiotachometer (ribbon brooch) that Delilah
# lost and go to Extreme Station <2>." (His official line there: "I'll bet Delilah's ribbon brooch is at Extreme Station <2>.")
DYLAN = 2192004
if not sm.hasQuest(parentID) and not sm.hasQuestCompleted(parentID):
    sm.setSpeakerID(DYLAN)
    sm.sendNext("#h0#, look at the monitor! The heartbeat is all over the place... Wait, that's not Delilah's heartbeat. The signal is moving away from her!")
    if sm.sendAskAccept("She must have dropped the ribbon brooch! The signal's coming from #bExtreme Station <2>#k. Come on, we have to find it!"):
        sm.startQuest(parentID)
sm.dispose()
