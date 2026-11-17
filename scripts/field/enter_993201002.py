# Vacant Lot (993201002): the Remote Forest Clearing for Lost Property (36607). The photo is by the fence, and so is a
# Stray Dog: one to defeat in 20 minutes. clear_993201002 ends the quest.
execfile("scripts/quest/_mihile_story.py")

if sm.hasQuest(36607):
    try:
        scene_start()
        sm.blind(1, 255, 0, 0)
        sm.sendDelay(300)
        unblack(1000)
        me("Oho, the light shines upon me! Found it.")
        me("...Wait, where are all the sparrows?")
        say(STRAY_DOG_SAY, "Grrr!")
        me("Well hey there, boy. You hungry?")
        me("All right. Come and get it.")
    finally:
        scene_end()
    sm.spawnMob(STRAY_DOG, 600, 65)
else:
    sm.warpInstanceOut(REMOTE_HOUSE, 0)
sm.dispose()
