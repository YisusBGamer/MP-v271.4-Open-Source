# Catch the Shadow Knight! (21747, Aran): the lantern's password opens the way to the Sealed Temple, a copy of your
# own (aran_sealed_temple spawns the Shadow Knight). If you fail, forfeit and start the quest again.
if sm.questStartDialogue(parentID):
    sm.warpInstanceIn(925040100, 0)
