# 36629 Daybreak (Chromile 1541206, Ereve's End): Mihile's real father, the previous Knight of Light.
execfile("scripts/quest/_mihile_story.py")

say(CHROMILE, "It's been a long time, my son.")
me("You're... Chromile? My... father?")
if ask(CHROMILE, "Listen well. I will tell you what it means to be the Knight of Light."):
    begin(parentID)
    say(CHROMILE, "The Knight of Light is a shield. He protects the Empress, Ereve, and the world, whatever it costs him.")
    say(CHROMILE, "There is work yet to be done. I leave it to you, my son.")
    finish(parentID)
sm.dispose()
