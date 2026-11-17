# 1418 - 2nd job advancement (Bowman), v265: the branch is picked here

sm.setSpeakerID(1012100)
JOBS = [310, 320]
NAMES = ['Hunter', 'Crossbowman']
if chr.getLevel() < 30:
    sm.sendSayOkay("Come back when you've reached #blevel 30#k.")
    sm.dispose()
pick = sm.sendNext("You've grown strong. It's time to choose your path as a Bowman.\r\n#L0##bHunter#k#l\r\n#L1##bCrossbowman#k#l")
if pick < 0 or pick >= len(JOBS):
    sm.dispose()
if sm.sendAskYesNo("So you want to become a #b" + NAMES[pick] + "#k? Once you choose, there's no going back."):
    sm.jobAdvance(JOBS[pick])
    sm.completeQuest(parentID)
    sm.sendSayOkay("You are now a #b" + NAMES[pick] + "#k. Keep training.")
else:
    sm.sendSayOkay("Take your time. Talk to me again when you've decided.")
