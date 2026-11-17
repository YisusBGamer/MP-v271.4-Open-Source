# 927030060 Black Mage's Antechamber, through the Gate of the Present (Shade).
# A Foreigner At Home (38066, record 38066 = clear) is done on arrival; Traces of the Black Mage (38067, needs its
# record = impossible to start, clear to finish) starts here: defeat the Omens (clear_927030060).
OMEN = 9300874
OMEN_X = (-400, -200, 0, 200, 400, 600)

if sm.hasQuest(38066):
    sm.setQRValue(38066, "clear")
    if sm.completeQuest(38066):
        sm.chatScript("As you suspected, the Gate of the Present is open to you.")
if sm.hasQuestCompleted(38066) and not sm.hasQuest(38067) and not sm.hasQuestCompleted(38067):
    sm.startQuestNoCheck(38067)
    sm.setQRValue(38067, "impossible")
if sm.hasQuest(38067) and sm.getQRValue(38067) != "clear":
    if not sm.hasMobsInField():
        for x in OMEN_X:
            sm.spawnMob(OMEN, x, 71, False)
    sm.chatScript("The Omens are guarding the Black Mage's Antechamber. Eliminate all Omens to enter.")
