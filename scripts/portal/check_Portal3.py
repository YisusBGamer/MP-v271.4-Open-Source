# 272000310 Ruins in Flames -> Leafre in Flames 4. [Arkarium] Traces of Arkarium (31176): Bastille waits in a copy of
# the map of its own (field/q31176continue).
if sm.hasQuest(31176) or (sm.hasQuestCompleted(31175) and not sm.hasHadQuest(31176)):
    sm.warpInstanceIn(272000410, 2)
else:
    sm.warp(272000400, 1)
sm.dispose()
