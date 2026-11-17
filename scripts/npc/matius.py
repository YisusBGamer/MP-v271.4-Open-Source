# Matthias
# During [Alliance] The Knights' Path (36517) he hands over his report for the Empress (the quest asks for it).
KNIGHTS_PATH = 36517
REPORT = 4036869

if sm.hasQuest(KNIGHTS_PATH) and not sm.hasItem(REPORT):
    sm.sendNext("The Empress wants my report? Here. Kerning City has been busy lately.")
    if sm.canHold(REPORT):
        sm.giveItem(REPORT)
    else:
        sm.sendSayOkay("Make room in your Etc inventory first.")
elif sm.hasQuest(20807):
    if sm.sendAskYesNo("Would you like to attempt the first test?"):
        sm.warpInstanceIn(913070800, 0)
        sm.setInstanceTime(300, 913070800)
elif sm.sendAskYesNo("Would you like to attempt the first test?"):
    sm.warpInstanceIn(913070800)
    sm.setInstanceTime(300, 913070800)
sm.dispose()
