from net.swordie.ms.enums import BossCooldown

sm.setSpeakerID(1064014) # Third Seal Foor
response = sm.sendNext("Would you like to fight Crimson Queen? \r\n #b#L0#Normal Mode (Level 125+)#l \r\n #L1#Chaos Mode (Level 180+)#l")

if response == 0:
    if sm.getParty() is None:
        sm.createSoloParty()

    if not sm.isPartyLeader():
        sm.sendSayOkay("Please have your party leader enter if you wish to face Crimson Queen.")
    elif sm.checkParty(BossCooldown.Queen, 125):
        sm.warpInstanceIn(105200300, 0, True) # South Garden
        sm.setInstanceTime(15 * 60)
        sm.setDeathCount(5)
        sm.setBossCooldown(BossCooldown.Queen)
elif response == 1:
    if sm.getParty() is None:
        sm.createSoloParty()

    if not sm.isPartyLeader():
        sm.sendSayOkay("Please have your party leader enter if you wish to face Crimson Queen.")
    elif sm.checkParty(BossCooldown.QueenHard, 180):
        sm.warpInstanceIn(105200700, 0, True) # South Garden
        sm.setInstanceTime(20 * 60)
        sm.setDeathCount(5)
        sm.setBossCooldown(BossCooldown.QueenHard)
