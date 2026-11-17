# 36616 My Choice (Neinheart 1541203): a letter for Father first.
execfile("scripts/quest/_mihile_story.py")

me("One thing: my father is away. I don't want to leave him wondering where I've gone.")
me("Would you give me a moment to write him a letter?")
say(NEINHEART, "As you wish. Let us know when you're ready to leave.")
finish(parentID)
sm.dispose()
