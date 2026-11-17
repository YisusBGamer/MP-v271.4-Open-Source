# 914050007 Riena Strait igloos: Finding Aran 3 (Shade 38049) and Reunion with Aran (38050; Aran appears once 38049 is
# done). The map has no exit: anyone else goes back to Rien, and a player still guessing may leave too.
RIEN = 140020300

if sm.hasQuest(38049) or sm.hasQuest(38050) or (sm.hasQuestCompleted(38049) and not sm.hasQuestCompleted(38050)):
    if sm.hasQuest(38049) and sm.getQRValue(38049) != "clear":
        if sm.sendAskYesNo("Go back to Rien?"):
            sm.warp(RIEN, 0)
        else:
            sm.chatScript("Guess which igloo the penguins are in.")
else:
    sm.warp(RIEN, 0)
