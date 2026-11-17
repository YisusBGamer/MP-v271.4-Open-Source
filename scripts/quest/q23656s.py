# Invasion (Xenon story) (23656): the fight happens in a copy of the quest's map of your own (enter_23656 / its map script spawns the foe).
# If you fail, forfeit and start the quest again.
if sm.questStartDialogue(parentID):
    sm.warpInstanceIn(940030001, 0)
