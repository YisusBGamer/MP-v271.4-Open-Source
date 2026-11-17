# Jett: Enhancing the Broken Core (53238), after the 2nd job advancement (q53237e)
# The quest is started before it is completed ("Your core is brimming with power!"; the 3rd job comes from q53239e).
# The quest needs the 30 Blue Ore the blacksmith asked for (Quest.wz); he takes them, and the quest is done.
BLUE_ORE = 4033251
sm.setSpeakerID(9270091)
sm.startQuest(parentID)
if sm.hasQuest(parentID):
    sm.consumeItem(BLUE_ORE, 30)
    sm.completeQuest(parentID)
