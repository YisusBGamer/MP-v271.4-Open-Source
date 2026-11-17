# 910540600 Radiant Crystal Passageway - Explorer story, Hearing Voices (32293)
# Overhearing the Black Wings sets the quest's own record (32293 = "1"); the quest has no NPC to hand it in to, so
# it's finished here. Both exits (exit_adventure) lead back to the Holy Ground at the Snowfield.
if sm.hasQuest(32293) and sm.getQRValue(32293) != "1":
    sm.setQRValue(32293, "1")
    sm.chatScript("Black Wing peons up ahead are talking: they're collecting Seal Stones without knowing why.")
    sm.chatScript("One of the two Black Wings leaves. Now is the time for a surprise attack!")
    sm.completeQuest(32293)
sm.dispose()
