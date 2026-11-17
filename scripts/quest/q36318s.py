# 36318 Disaster on Maple Island (Olive): a strange fire on Maple Island. Ends by itself, then offers Sugar in Danger
# (36319) right away.
sm.setSpeakerID(1541003)
sm.sendNext("There you are! Something terrible is happening. A strange black fire has broken out on #bMaple Island#k!")
if not sm.sendAskAccept("It started right after we messed with the seal... Sugar is still there! We have to go!"):
    sm.dispose()
sm.startQuest(parentID)
sm.completeQuest(parentID)
if sm.sendAskYesNo("#b(Hurry to Maple Island and look for Sugar now?)#k"):
    sm.startQuest(36319)
    if sm.hasQuest(36319):
        sm.warpInstanceIn(993166200, 0)
else:
    sm.chatScript("Accept 'Sugar in Danger' from your quest list when you're ready.")
sm.dispose()
