# Prison (931060053), inLab00 -> the Laboratory (931060050), blocked by the Boom Barrier until it's destroyed
# (Breaking the Boom Barrier, Xenon 23692).
if sm.hasQuestCompleted(23692) or (sm.hasQuest(23692) and sm.isComplete(23692)):
    sm.warp(931060050, 1)
elif sm.hasQuest(23692):
    sm.chat("The Boom Barrier blocks the way. Destroy it with a long range attack.")
else:
    sm.chat("The way into the testing room is blocked.")
sm.dispose()
