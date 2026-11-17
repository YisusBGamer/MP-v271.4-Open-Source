# Sinkhole Sunk (22860, Kinesis story): descend to the bottom of the Giant Sinkhole in a copy of your own and defeat
# King Omen (enter_331004000 spawns him). If you fail, forfeit and start the quest again.
if sm.questStartDialogue(parentID):
    sm.warpInstanceIn(331004000, 0)
