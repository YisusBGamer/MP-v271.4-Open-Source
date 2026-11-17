# [Limina] Guard Captain Darknell (35844), completed from the quest notifier; out of the fight copy if you're in it
done = sm.completeQuest(parentID)
if done and sm.getFieldID() == 993070076:
    sm.warpInstanceOut(450012300, 0)
sm.dispose()
