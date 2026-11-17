# Finding Aran 1 (Shade 38047): Lilin's boat takes you to Riena Strait, where Aran is. The boat ride itself isn't
# played: arriving finishes the quest (record 38047 clear=clear; it has no NPC to hand it in to).
RIENA_STRAIT = 914050001

if sm.hasQuest(parentID):
    sm.setQRValue(parentID, "clear", "clear")
    if sm.completeQuest(parentID):
        sm.chatScript("There were some close calls, but you arrived at Riena Strait safe and sound.")
        sm.warp(RIENA_STRAIT, 0)
sm.dispose()
