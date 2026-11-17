# Tyrant's Castle: the door to Victor's Workshop (401051200), where Magnus's guardian Victor waits - the Guardians of
# Magnus daily (31732) needs him. Party (or solo) instance, like Velderoth's Sitting Room next door.
if sm.getParty() is None:
    sm.createSoloParty()
if not sm.isPartyLeader():
    sm.sendSayOkay("Please have your party leader enter if you wish to face Victor.")
elif sm.checkParty():
    sm.warpInstanceIn(401051200, 0, True)
sm.dispose()
