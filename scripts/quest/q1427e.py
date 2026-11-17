# 1427 - 2nd job advancement (Cannoneer), v265: the branch is picked here

sm.setSpeakerID(1090000)
JOBS = [530]
NAMES = ['Cannoneer']
if chr.getLevel() < 30:
    sm.sendSayOkay("Come back when you've reached #blevel 30#k.")
    sm.dispose()
pick = 0
if sm.sendAskYesNo("So you want to become a #b" + NAMES[pick] + "#k? Once you choose, there's no going back."):
    sm.jobAdvance(JOBS[pick])
    sm.completeQuest(parentID)
    sm.sendSayOkay("You are now a #b" + NAMES[pick] + "#k. Keep training.")
else:
    sm.sendSayOkay("Take your time. Talk to me again when you've decided.")
