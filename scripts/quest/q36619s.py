# 36619 The Story of Chromile (Cygnus 1101000, Ereve): Neinheart tells of Chromile, the previous Knight of Light, in
# the garden (993201064); meanwhile Atan comes home to Mihile's letter (993201066).
execfile("scripts/quest/_mihile_story.py")

say(CYGNUS, "Mihile, I heard you passed the test. Congratulations.")
me("Thank you, Empress.")
say(CYGNUS, "Soon, you'll be right by my side, shining shield in hand.")
say(NEINHEART, "Did your audience go well?")
say(NEINHEART, "Ah, I have something to tell you.")
if ask(NEINHEART, "We've learned a bit more about him, if you're interested to hear."):
    begin(parentID)
    sm.warpInstanceIn(GARDEN_SCENE, 0)
sm.dispose()
