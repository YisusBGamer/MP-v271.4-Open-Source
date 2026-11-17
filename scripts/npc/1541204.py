# Cygnus (1541204), Remote Forest Wildland: back into the Stray Dog fight of A Girl in Danger (36613) if it was left.
execfile("scripts/quest/_mihile_story.py")

if sm.hasQuest(36613) and sm.getQRValueByKey(36613, "kill") != "1":
    if ask(MIHILE, "(The Stray Dogs are still out there. Go back?)"):
        sm.warpInstanceIn(WILDLAND_SCENE, 0)
else:
    say(CYGNUS, "I am in your debt, good knight.")
sm.dispose()
