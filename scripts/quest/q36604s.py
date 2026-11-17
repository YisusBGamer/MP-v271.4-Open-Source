# 36604 The First Training Session (Atan 1541202): Field Mouse x10 in the Remote Forest Field.
execfile("scripts/quest/_mihile_story.py")

say(ATAN, "Your first task for the day is to hunt #rField Mice#k. Go to the #bRemote Forest Field#k and defeat #r10 of them#k.")
if ask(ATAN, "Are you ready?"):
    begin(parentID)
    say(ATAN, "Good luck.")
sm.dispose()
