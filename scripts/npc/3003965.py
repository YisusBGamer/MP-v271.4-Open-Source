# Grendel the Really Old (3003965), Limina End of the World 1-1.
# Jammed (35811) is handed in to him, Destiny Defined (35812) starts with him (see zgen/gen_bm.py), and for The Final
# Foe (35813) he sends you into the Giant's Heart (450012500), where the Black Mage waits (field/enter_450012500).
sm.setSpeakerID(3003965)
if sm.hasQuest(35811) and sm.isComplete(35811):
    sm.questEndDialogue(35811)
elif sm.hasQuestCompleted(35811) and not sm.hasQuest(35812) and not sm.hasQuestCompleted(35812):
    if sm.questStartDialogue(35812):
        sm.questEndDialogue(35812)
elif sm.hasQuest(35813) and not sm.isComplete(35813):
    if sm.sendAskYesNo("Fate is cruel.\r\n\r\n#b(Enter the Giant's Heart to face the Black Mage?)#k"):
        sm.warpInstanceIn(450012500, 0)
else:
    sm.sendSayOkay("Fate is cruel.")
sm.dispose()
