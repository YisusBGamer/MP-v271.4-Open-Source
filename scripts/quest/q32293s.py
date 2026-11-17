# Hearing Voices (32293) - Holy Stone
# Following the Master of Disguise into the Crystal Passageway (910540600, field/enter_cristalAdventure.py), where
# the quest's own record (32293 = "1") is set. The quest has no NPC to hand it in to: the scene finishes it.
if sm.questStartDialogue(parentID) and sm.getQRValue(parentID) != "1":
    sm.chatScript("You hear a loud noise from the Crystal Passageway...")
    sm.warpInstanceIn(910540600, 0)
sm.dispose()
