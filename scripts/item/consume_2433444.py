# Legendary Cryptic Chest
NPC = 9010000

sm.setSpeakerID(NPC)
sm.flipDialogue()
if sm.getEmptyInventorySlots(2) >= 2:
    sm.consumeItem(parentID)
    chr.addItemToInventory(2433509,1,"day",90)
    chr.addItemToInventory(2433510,1,"day",90)
else:
    sm.sendNext("You don't have enough space in your Use inventory to receive the reward.")