# Power Plant Lobby (310050000), in00: Francis's Room.
# The Sneakiest Secretary (Phantom 25416): Le Tierre's "introduction" is a trap (field/phantom_secretary_trap).
# Stolen Child 1 (Mercedes 24088): Le Tierre says the kid is in the room to the left - this one. Looking inside counts
# (record 24096 = "1"); the kid is a human from Edelstein, not the Elf child.
if sm.hasQuest(24088) and sm.getQRValue(24096) != "1":
    sm.startQuest(24096)
    sm.setQRValue(24096, "1")
    sm.chatScript("The kid inside was a human from Edelstein, not an Elf. You sent the kid home. Go back to Le Tierre.")
# Commanders of the Stronghold (Demon 23268): Francis's Diary (NPC 2159327, script pransisDiary) lies in this room,
# but no map places it; looking inside finds it, and the photo in it (4350003 Someone's Photo) is what the quest needs.
if sm.hasQuest(23268) and not sm.hasItem(4350003):
    if sm.canHold(4350003):
        sm.giveItem(4350003)
        sm.chatScript("The cover says 'Francis's Diary'. It holds a full account of the formation of the Black Wings, and a picture of their commander.")
    else:
        sm.chatScript("You spot Francis's Diary, but your Etc inventory is full.")
if sm.hasQuest(25416):
    sm.warpInstanceIn(931050220, 0)
else:
    sm.warp(931020010, 3)
sm.dispose()
