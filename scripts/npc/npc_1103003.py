# Hersha (1103003), Ellinia: the Knights' informant. During [Alliance] The Knights' Path (36517) she hands over her
# report for the Empress (the quest asks for it).
from net.swordie.ms.scripts import ScriptManagerImpl
QUEST = 36517
REPORT = 4036868

if sm.hasQuest(QUEST) and not sm.hasItem(REPORT):
    sm.sendNext("The Empress sent you? Then take this. Everything I've seen in Ellinia is in here.")
    if sm.canHold(REPORT):
        sm.giveItem(REPORT)
    else:
        sm.sendSayOkay("Make room in your Etc inventory first.")
else:
    lines = ScriptManagerImpl.getNpcDefaultLines(parentID)
    sm.sendSayOkay(lines[0] if lines else "...")
sm.dispose()
