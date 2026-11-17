# Portal script in_cygnusAK.
# Foyer of Darkness: Arkarium's Altar, for Arkarium's End (20757, Mihile). Arkarium waits inside (your own copy of the altar; the exit asks before leaving).
if sm.hasQuest(20757):
    sm.warpInstanceIn(272030400, 1)
else:
    chr.chatMessage("A dark barrier blocks the way to the altar.")
sm.dispose()
