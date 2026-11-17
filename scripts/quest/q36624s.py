# 36624 Shinsoo's Teardrop Part 2 (self start, Lv 28): Mihile gives the Teardrop to Father.
execfile("scripts/quest/_mihile_story.py")

me("(Shinsoo's Teardrop... Father, here.)")
begin(parentID)
say(ATAN, "At last. After all these years, your brother will come back to us.")
finish(parentID)
me("(It's time to meet my brother, after all these years.)")
sm.dispose()
