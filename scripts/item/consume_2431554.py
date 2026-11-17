# Piece of Memory (2431554), [Twilight Perion] Piece of Memory (31913): a peek at the creator of the dream world -
# the Wightmare (9100043) appears and has to be defeated.
if sm.hasQuest(31913) and not sm.isComplete(31913):
    if chr.getField().getInfo().isTown():
        sm.chat("Use the Piece of Memory outside of town.")
    elif not sm.hasMobsInField(sm.getFieldID(), 9100043):
        pos = chr.getPosition()
        sm.spawnMob(9100043, pos.getX() + 200, pos.getY(), False)
        sm.chat("The Piece of Memory shows you the creator of the dream world... a Wightmare appears!")
else:
    sm.chat("The Piece of Memory is quiet.")
sm.dispose()
