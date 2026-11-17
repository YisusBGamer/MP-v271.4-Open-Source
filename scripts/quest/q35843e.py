# [Limina] The Dark Knights (35843), completed from the quest notifier; out of the fight copy if you're in it
done = sm.completeQuest(parentID)
if done and sm.getFieldID() == 993070074:
    sm.warpInstanceOut(450012200, 0)
sm.dispose()
