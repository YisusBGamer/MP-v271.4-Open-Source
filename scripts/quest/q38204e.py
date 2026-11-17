# 38204 [Karote] A Gate to Distant Lands - end (Archelon, Hotel Arcus 2nd Floor). Archelon carries the Alliance to
# the tower, which falls just as they arrive (The Unending Tower, 410005001).
ARCHELON = 3005254
NICKY = 3005248
UNENDING_TOWER = 410005001

if sm.hasQuest(parentID):
    sm.setSpeakerID(ARCHELON)
    sm.setBoxChat()
    sm.sendNext("The tower in the desert? I know where it stands. Climb on, all of you.")
    sm.completeQuest(parentID)
    sm.setSpeakerID(NICKY)
    sm.sendNext("There it is! Karote! It's... wait... no, no, NO! It's falling!")
    sm.setPlayerAsSpeaker()
    sm.sendNext("(The tower crumbled before your eyes, just as you arrived.)")
    sm.warp(UNENDING_TOWER, 0)
sm.dispose()
