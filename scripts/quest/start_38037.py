# A Friend Departed (Shade 38037): Athena sends you to see Afrien, and on the way, in the Snowy Forest of Slumbering
# Dragon Island, you run into Evan (Meeting Evan 38038 starts at him there). The quest has no NPC to hand it in to:
# arriving finishes it (record 38037 1=impossible).
SNOWY_FOREST = 914100011

if sm.hasQuest(parentID):
    sm.setQRValue(parentID, "1", "impossible")
    if sm.completeQuest(parentID):
        if sm.getFieldID() != SNOWY_FOREST:
            sm.warp(SNOWY_FOREST, 0)
    else:
        sm.chatScript("Make room in your inventory, then forfeit A Friend Departed and take it again from Athena Pierce.")
sm.dispose()
