# 926150000 Alcadno Society 1: The Forbidden Book of Alchemy (23270, end check 23276 = 1).
# The magicians have already left with the book; Maed is still under their spell. He comes to with Carson in 926150020,
# where he hands in the quest.
if sm.hasQuest(23270) and sm.getQRValue(23276) != "1":
    sm.startQuest(23276)
    sm.setQRValue(23276, "1")
    sm.chatScript("Maed: I will do all that is asked...")
    sm.chatScript("You were too late. Maed has handed over the other half of the book to the magicians who hypnotized him.")
    sm.invokeAfterDelay(4000, "warp", 926150020)
else:
    sm.invokeAfterDelay(500, "warp", 261000000)
