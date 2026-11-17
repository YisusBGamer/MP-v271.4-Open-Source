# Neglected Garden Entrance (1064032) | Root Abyss: Neglected Garden mini dungeon (1 hour, party or solo)
MIN_LEVEL = 125
sm.setSpeakerID(1064032)
if chr.getLevel() < MIN_LEVEL:
    sm.sendSayOkay("A thick tangle of roots blocks the way. You must be at least Lv. " + str(MIN_LEVEL) + " to enter the #bNeglected Garden#k.")
elif sm.sendAskYesNo("Enter the #bNeglected Garden#k? Your party will have 1 hour to hunt inside before being sent back."):
    if sm.getParty() is None:
        sm.createSoloParty()
    if not sm.isPartyLeader():
        sm.sendSayOkay("Only the party leader can lead the way into the Neglected Garden.")
    else:
        sm.warpInstanceIn(105200900, 0, True)
        sm.setInstanceTime(60 * 60)
sm.dispose()
