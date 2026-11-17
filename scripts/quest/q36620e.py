# 36620 The Keepsake (10 Boogies 1103004, Perion)
execfile("scripts/quest/_mihile_story.py")

sm.setSpeakerID(1103004)
sm.sendNext("Empress Aria's Keepsake! You got it back. It's safe in our hands now.")
finish(parentID, ((KEEPSAKE, 1),))
sm.dispose()
