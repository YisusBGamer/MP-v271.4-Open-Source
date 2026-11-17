# Somewhere in a Vacant Land (993201009): Unfamiliar Guests Part 1 (36614). The girl is sure she has found the Knight
# of Light; Neinheart presents Empress Cygnus.
execfile("scripts/quest/_mihile_story.py")

if sm.hasQuest(36614):
    try:
        scene_start()
        sm.blind(1, 255, 0, 0)
        sm.sendDelay(300)
        unblack(1000)
        say(NEINHEART, "That youngster there? Are you sure?")
        say(GIRL, "Neinheart, you said I had a connection to the #bKnight of Light#k, did you not?")
        say(GIRL, "I believe you're right. In fact, I believe the Knight stands before us.")
        say(GIRL, "I felt a certain energy flowing from his hand. From his shield, as well.")
        me("(I wonder what they're talking about.)")
        say(GIRL, "We have found the Knight of Light. I have no doubt of it.")
        me("(And... did I just see the shield shine again?)")
        say(NEINHEART, "Well, apologies for the interruption, good Sir Knight.")
        say(NEINHEART, "May I formally present #bEmpress Cygnus#k of Maple World? And I am #bNeinheart#k, the Empress's "
                       "tactician.")
        me("The Empress... I am Mihile. ...It's an honor to meet you.")
        say(CYGNUS, "A fine name. My thanks again for your noble valor.")
        me("My pleasure, Your Majesty.")
        black(500)
        finish(36614)
    finally:
        scene_end()
sm.warpInstanceOut(WILDLAND, 0)
sm.dispose()
