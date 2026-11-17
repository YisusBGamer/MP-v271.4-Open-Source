# Portal script enterBackStreet.
# Mu Lung Dojo entrance, back door: leads to the dojo's back street where Mu Gong waits, for The Shadow Knight's
# Letter (21744, once How to Meet Mu Gong 21745 is done) and Catch the Shadow Knight! (21747) (Aran).
if sm.hasQuest(21747) or (sm.hasQuest(21744) and sm.hasQuestCompleted(21745)):
    sm.warp(925040000, 1)
else:
    chr.chatMessage("You don't have permission to go back there.")
sm.dispose()
