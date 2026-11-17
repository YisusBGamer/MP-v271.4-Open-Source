# Tyrant's Castle: the door to Treglow's Laboratory (401052200), where Magnus's guardian Treglow waits - the Guardians
# of Magnus daily (31732) needs him. Party (or solo) instance, like Velderoth's Sitting Room next door.
# Kaiser's story: after Underground Passage - B2 (31506) the door is sealed (Can't Break Through! 31507 starts with
# record 31568) until the reagent of Goodbye Seal (31510) burns the seal off (record 31569).
SEALED = sm.hasQuestCompleted(31506) and not sm.hasQuestCompleted(31510) and not sm.hasQuest(31510)
if sm.hasQuest(31510) and sm.getQRValue(31569) != "1":
    sm.setPlayerAsSpeaker()
    sm.sendNext("The seal on the door just melted... and so did the doorknob... and some of the door. I should tell #b#p3001100##k.")
    sm.startQuest(31569)
    sm.setQRValue(31569, "1")
elif SEALED:
    if sm.getQRValue(31568) != "1":
        sm.startQuest(31568)
        sm.setQRValue(31568, "1")
    sm.chatScript("Treglow's Laboratory is sealed with some kind of power.")
else:
    if sm.getParty() is None:
        sm.createSoloParty()
    if not sm.isPartyLeader():
        sm.sendSayOkay("Please have your party leader enter if you wish to face Treglow.")
    elif sm.checkParty():
        sm.warpInstanceIn(401052200, 0, True)
sm.dispose()
