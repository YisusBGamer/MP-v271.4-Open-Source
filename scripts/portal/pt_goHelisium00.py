# 400000000
# On Angelic Buster's 'Can I hold the Cannon?' (31614) the portal at the top right of the Pantheon leads to the Path
# to the Reclamation HQ, where Kaiser is (the quest's own text; your own copy of the map, see enter_401100000).
if sm.hasQuest(31614) or sm.hasQuestCompleted(31614) and not sm.hasQuestCompleted(31615) and not sm.hasItem(4033388):
    sm.warpInstanceIn(401100000, 0)
elif not sm.hasQuest(31807) and not sm.hasQuestCompleted(31807):
    sm.warp(401000002)
sm.dispose()
