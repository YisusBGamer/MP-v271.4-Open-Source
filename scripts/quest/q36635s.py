# 36635 Mad Dragons Part 3 (from the quest list, after 36634). The purple scale (4036873) found on one of the
# frenzied Drakes goes back to Ereve; Neinheart takes it (q36635e) and Mihile makes the 3rd job.
SCALE = 4036873

sm.setPlayerAsSpeaker()
if not sm.sendAskAccept("#b(Everyone's accounted for. Take the suspicious purple scale from the Drake back to #m130000000#?)#k"):
    sm.dispose()
if not sm.hasQuest(parentID):
    sm.startQuest(parentID)
if not sm.hasItem(SCALE) and sm.canHold(SCALE, 1):
    sm.giveItem(SCALE)
sm.chatScript("Return to Ereve and give Neinheart the scale.")
sm.dispose()
