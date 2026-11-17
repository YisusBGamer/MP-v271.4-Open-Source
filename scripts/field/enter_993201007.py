# Somewhere in a Field (993201007): the Remote Forest Field on Going Out 2 (36612). A voice calls for help.
execfile("scripts/quest/_mihile_story.py")

if sm.hasQuest(36612):
    try:
        scene_start()
        sm.blind(1, 255, 0, 0)
        sm.sendDelay(300)
        unblack(1000)
        me("That ought to be enough.")
        me("Hmm?")
        say(VOICE, "Please... me.")
        sm.showEffect("Effect/OnUserEff.img/emotion/oh", 0, 0, 0, 0, 0, 0, 0)
        me("What is that sound?")
        say(VOICE, "Eeyaah!")
        black(500)
        finish(36612)
    finally:
        scene_end()
    sm.warpInstanceOut(FOREST_FIELD, 0)
elif sm.hasQuest(36607):
    # Lost Property begun before it moved to 993201002 (clear_993201007 ends it)
    sm.spawnMob(STRAY_DOG, 78, 65)
else:
    sm.warpInstanceOut(FOREST_FIELD, 0)
sm.dispose()
