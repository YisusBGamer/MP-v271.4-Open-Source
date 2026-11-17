# Attacked Crossroads of Ereve (993201068): Revelatory Visit (36621) ends at Kidan.
execfile("scripts/quest/_mihile_story.py")

if sm.hasQuest(36621):
    show("74", "75")
    sm.chatScript("Ereve is under attack! Talk to Kidan.")
sm.dispose()
