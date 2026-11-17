# Fantasy Theme World (Dylan & Delilah, 31363-31392). Official lines where the client has them; otherwise written from
# the quests' Quest.wz summaries / the NPCs' Npc.wz lines. Data: ftwgen/gen_ftw.py.
# Heart-Stopping Confession on the Swingboat (31378), end - Oliver, Fantasy Station <2>: "You arrived at the
# Swingboat in Fantasy Station <2>." (his Npc.wz line; his official lines in 'Swingboat Disaster' follow)
if sm.hasQuest(parentID):
    sm.setSpeakerID(2192023)
    sm.sendNext("Before you stands the world's most thrilling swingboat.")
    sm.completeQuest(parentID)
sm.dispose()
