# Ellinel Lake Shore (101070000), portal pt_east
# Past the shore is Ellinel Lake: Fanzy's magic gets the player across once 32105 'Academia' starts ("You can get
# to Ellinel Fairy Academy if you walk straight east"), to Above the Lake 1 (the after-magic shore 101070010 has a
# plain portal to the same place). Before that the lake can't be crossed.
ACADEMIA = 32105
ABOVE_THE_LAKE_1 = 101071000

if sm.hasQuest(ACADEMIA) or sm.hasQuestCompleted(ACADEMIA):
    sm.warp(ABOVE_THE_LAKE_1)
else:
    sm.chat("Ellinel Lake is in the way. Fanzy might know how to get across.")
sm.dispose()
