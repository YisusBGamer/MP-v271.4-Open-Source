# Somewhere in a Vacant Lot (993201003): the Remote Forest Clearing on the third training session (36606). A Stray Dog
# scares the sparrows off the fence.
execfile("scripts/quest/_mihile_story.py")

if sm.hasQuest(36606):
    try:
        scene_start()
        sm.blind(1, 255, 0, 0)
        sm.sendDelay(300)
        unblack(1000)
        me("...Hmm? Something feels odd... Ahh!")
        say(STRAY_DOG_SAY, "Grrr!")
        say(SPARROW_SAY, "Chirp! Chirp!")
        me("Stray Dogs? This isn't their territory.")
        me("Father said I ought to be careful...")
        me("I should finish here before the dogs get any closer.")
        black(500)
    finally:
        scene_end()
sm.warpInstanceOut(FOREST_CLEARING, 1)
sm.dispose()
