# Fantasy Theme World (Dylan & Delilah, 31363-31392). Official lines where the client has them; otherwise written from
# the quests' Quest.wz summaries / the NPCs' Npc.wz lines. Data: ftwgen/gen_ftw.py.
# Pin the Brooch on Delilah (31376), end - Delilah, Funky Station <2>: "You put the heartbeat sensor back on Delilah
# who, for some reason, does not question Dylan's insistence that she wear it."
DELILAH, BROOCH = 2192012, 4036094
if sm.hasQuest(parentID) and sm.hasItem(BROOCH):
    sm.setSpeakerID(DELILAH)
    sm.sendNext("My brooch! I must have dropped it on one of the rides. Thank you!")
    if sm.completeQuest(parentID) and sm.hasItem(BROOCH):
        sm.consumeItem(BROOCH, 1)
sm.dispose()
