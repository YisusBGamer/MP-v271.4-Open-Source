# 36308 Stolen Treasure - end (Athena Pierce)
sm.setSpeakerID(1012100)
sm.sendNext("You came because of the note? Then you've heard. Last night a treasure the school was guarding was stolen.")
sm.sendSay("The guards say they saw Olive leaving the vault. I can hardly believe it myself... but the treasure is gone, and so is she.")
if sm.completeQuest(parentID):
    sm.sendSayOkay("Talk to me again when you're ready to hear the rest.")
sm.dispose()
