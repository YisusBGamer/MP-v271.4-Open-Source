# 36606 The Third Training Session (Atan 1541202): the Stray Dogs came closer than usual.
execfile("scripts/quest/_mihile_story.py")

me("I'm back.")
me("Though, oddly enough, the Stray Dogs came closer than usual.")
me("Not close enough to bite, but I did hear them barking.")
say(ATAN, "Stray Dogs, hmm?")
say(ATAN, "They must be getting hungry, if they're on the move. We should stay indoors a while.")
say(ATAN, "Do you hear me, Mihile? Those dogs are dangerous. Don't ever let your guard down.")
me("Of course.")
finish(parentID, ((EGG, 15),))
sm.dispose()
