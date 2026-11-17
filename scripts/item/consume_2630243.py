# id 2630243 (Pure Water), Twilit Forest (101084100-101084300)
# [Elodin] Another Chance! (37176): "the Small Bottle will fill up automatically when you collect Pure Water"
# (Quest.wz). Item.wz runs this script on pickup (spec/runOnPickup) and only during 37176 (reqQuestOnProgress). Each one
# adds a droplet to Small Bottle 4220198, whose fill is the quest's record (end check: "21" = info/grade 3 x exp 7).
ANOTHER_CHANCE = 37176
BOTTLE = 4220198
FULL = 21
if sm.hasQuest(ANOTHER_CHANCE) and sm.hasItem(BOTTLE):
    try:
        filled = int(sm.getQRValue(ANOTHER_CHANCE) or "0")
    except ValueError:
        filled = 0
    if filled < FULL:
        filled += 1
        sm.setQRValue(ANOTHER_CHANCE, str(filled), False)
        sm.updateQRValue(ANOTHER_CHANCE, True)
        if filled >= FULL:
            sm.chat("The Small Bottle is full.")
        else:
            sm.chat("You added Pure Water to the Small Bottle. (" + str(filled) + "/" + str(FULL) + ")")
sm.dispose()
