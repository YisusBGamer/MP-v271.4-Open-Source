# id 2630159 (Hay), field 101082200
# "Double-click to make a Makeshift Broomstick." (String.wz). The item is only usable during "Spring Cleaning" (Item.wz
# reqQuestOnProgress 37164) and isn't used up by the client (notConsume), so the script trades one Hay for one
# Makeshift Broomstick.
HAY = 2630159
BROOMSTICK = 4036502
if sm.hasItem(HAY):
    if sm.canHold(BROOMSTICK):
        sm.consumeItem(HAY, 1)
        sm.giveItem(BROOMSTICK, 1)
    else:
        sm.chat("Make room in your Etc inventory for the broomstick.")
sm.dispose()
