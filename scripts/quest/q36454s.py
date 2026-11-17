# Empress's Gift (36454): Kinu gives the Feathered Noblesse Hat; the quest is handed in wearing it.
FEATHERED_NOBLESSE_HAT = 1003769
KINU = 1541105

if sm.questStartDialogue(parentID, KINU):
    if not sm.hasItem(FEATHERED_NOBLESSE_HAT) and not sm.isEquipped(FEATHERED_NOBLESSE_HAT):
        if sm.canHold(FEATHERED_NOBLESSE_HAT):
            sm.giveItem(FEATHERED_NOBLESSE_HAT)
        else:
            sm.sendSayOkay("Please free 1 equipment tab slot.")
sm.dispose()
