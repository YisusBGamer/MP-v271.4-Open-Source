# 36621 Revelatory Visit (10 Boogies 1103004, Perion): back to Ereve, which is under attack.
execfile("scripts/quest/_mihile_story.py")

sm.setSpeakerID(1103004)
if sm.sendAskAccept("Your mission is done, Knight. Shall we take you back to Ereve?"):
    begin(parentID)
    show("74", "75")
    sm.warp(CROSSROADS, 0)
sm.dispose()
