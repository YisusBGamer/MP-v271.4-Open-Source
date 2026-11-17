# Cornering the Master of Disguise (32294) - Holy Stone
# Accepted in the Holy Ground at the Snowfield itself, the Master of Disguise shows up right away (enter_211040401
# only spawns him when someone enters).
if sm.questStartDialogue(parentID) and sm.getFieldID() == 211040401 and not sm.isComplete(parentID) \
        and not sm.hasMobsInField():
    sm.spawnMob(9300804, 200, 454, False)
    sm.chatScript("The Master of Disguise drops his act!")
sm.dispose()
