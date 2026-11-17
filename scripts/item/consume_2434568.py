# Demon B Gone (2434568), [Shaolin Temple] Demon B Gone (62005): sprinkled on the demons at the Path of Stone.
# Opens the same scene as the path's portal (enter_701210161 uses it up).
PATH_OF_STONE = 701210160

if sm.hasQuest(62005) and sm.getFieldID() == PATH_OF_STONE:
    sm.warpInstanceIn(701210161)
elif sm.hasQuest(62005):
    sm.chat("Use it on the demons at the Path of Stone.")
else:
    sm.chat("A demon eradication potion. Will this really work?")
sm.dispose()
