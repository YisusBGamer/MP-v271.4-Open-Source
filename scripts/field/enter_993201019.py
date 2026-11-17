# Somewhere in Ereve (993201019): A Young Man's Loss (36627), Mihile against Atan (mob 3645305); clear_993201019 ends
# it.
execfile("scripts/quest/_mihile_story.py")

ATAN_MOB = 3645305

if sm.hasQuest(36627):
    say(ATAN, "Then show me, Mihile. Show me what I raised you to be.")
    sm.spawnMob(ATAN_MOB, 1100, 88)
else:
    sm.warpInstanceOut(ATTACKED_EREVE, 0)
sm.dispose()
