# [Limina] Light of Annihilation (35846), handing it in; the outer-deck fight (rescued 1) is told if it was skipped
SOLDIER = 3003990

if sm.hasQuest(parentID):
    if sm.getQRValue(parentID, "rescued") != "1":
        sm.setPlayerAsSpeaker()
        sm.sendNext("(You reach the outer decks, pull the allied soldiers out of the wreckage and drive the monsters "
                    "off.)")
    sm.setSpeakerID(SOLDIER)
    if sm.completeQuest(parentID):
        sm.sendNext("Communications have been restored. Talk to Grendel the Really Old in #bEnd of the World 1-1#k.")
sm.dispose()
