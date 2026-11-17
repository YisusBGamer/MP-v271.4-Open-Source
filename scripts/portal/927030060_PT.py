# 927030060 -> 927030070 Black Mage's Temple, for Shade's The Broken Seal (38068) once the Omens are gone.
if sm.hasQuest(38067) and sm.getQRValue(38067) == "clear":
    if not sm.questEndDialogue(38067):
        sm.chatScript("Make room in your inventory to finish Traces of the Black Mage.")
if sm.hasQuestCompleted(38067) and not sm.hasQuestCompleted(38068):
    sm.warp(927030070, 0)
elif sm.hasQuest(38067) and sm.getQRValue(38067) != "clear":
    sm.chatScript("The Omens are guarding the Black Mage's Antechamber.")
sm.dispose()
