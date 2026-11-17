# 36608 What's Next? (Atan 1541202): the Stray Dogs came close; no training outside for a while.
execfile("scripts/quest/_mihile_story.py")

say(ATAN, "Mihile, where were you?")
me("Right outside. I heard a noise. I just went to check on it.")
say(ATAN, "Noise? Must have been the Stray Dogs. They're starving, if they've come this close.")
say(ATAN, "Unfortunately, that means no training outside for a while.")
me("Hmm? Why, Father?")
say(ATAN, "If those dogs are acting up, there's something else going on. We'll need to play it safe.")
me("Then...")
if finish(parentID):
    me("(This isn't the first time those dogs have put our training on hold.)")
    me("(Father doesn't think I can handle them.)")
    me("(I didn't think so either, but I learned more from facing that dog than I ever did from avoiding them.)")
    me("(What else can I learn? There are stronger enemies out there to face...)")
    me("(...I should tell him.)")
sm.dispose()
