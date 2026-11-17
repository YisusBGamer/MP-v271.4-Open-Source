# Mu Young's Storage (1058003, its Npc.wz script item2635), Hidden Street: Mu Young's Storage. Tristan's Keepsake
# (2635): "You were attacked by an unknown enemy! You got Tristan's Keepsake" (the quest's own summary; Mu Young's
# official line after: "The Storage Room was raided?").
KEEPSAKE = 4033188
if sm.hasQuest(2635) and not sm.hasItem(KEEPSAKE):
    if not sm.canHold(KEEPSAKE):
        sm.chatScript("Make some room in your Etc inventory first.")
    else:
        sm.giveItem(KEEPSAKE)
        sm.chatScript("Someone rushes at you from the shadows and flees! ...You got Tristan's Keepsake. Take it to Mu Young.")
else:
    sm.chatScript("(Dusty boxes. Whatever was valuable here was looted long ago.)")
sm.dispose()
