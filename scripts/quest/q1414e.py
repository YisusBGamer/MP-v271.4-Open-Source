# 1414 - 2nd job advancement (Magician), v265: the branch is picked here

sm.setSpeakerID(1032001)
JOBS = [210, 220, 230]
NAMES = ['Wizard (Fire, Poison)', 'Wizard (Ice, Lightning)', 'Cleric']
if chr.getLevel() < 30:
    sm.sendSayOkay("Come back when you've reached #blevel 30#k.")
    sm.dispose()
pick = sm.sendNext("You've grown strong. It's time to choose your path as a Magician.\r\n#L0##bWizard (Fire, Poison)#k#l\r\n#L1##bWizard (Ice, Lightning)#k#l\r\n#L2##bCleric#k#l")
if pick < 0 or pick >= len(JOBS):
    sm.dispose()
if sm.sendAskYesNo("So you want to become a #b" + NAMES[pick] + "#k? Once you choose, there's no going back."):
    sm.jobAdvance(JOBS[pick])
    sm.completeQuest(parentID)
    sm.sendSayOkay("You are now a #b" + NAMES[pick] + "#k. Keep training.")
else:
    sm.sendSayOkay("Take your time. Talk to me again when you've decided.")
