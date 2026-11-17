from net.swordie.ms.enums import BossCooldown

sm.setSpeakerID(1064012) # First Seal Door
response = sm.sendNext("Would you like to fight Pierre?\r\n #b#L0#Normal Mode (Level 125+)#l \r\n #L1#Chaos Mode (Level 180+)#l")


if response == 0:
    if sm.getParty() is None:
        sm.createSoloParty()

    if not sm.isPartyLeader():
        sm.sendSayOkay("Please have your party leader enter if you wish to face Pierre.")
    elif sm.checkParty(BossCooldown.Pierre, 125):
        sm.warpInstanceIn(105200200, 0, True) # West Garden
        sm.setInstanceTime(15 * 60)
        sm.setDeathCount(5)
        sm.setBossCooldown(BossCooldown.Pierre)
elif response == 1:
    if sm.getParty() is None:
        sm.createSoloParty()

    if not sm.isPartyLeader():
        sm.sendSayOkay("Please have your party leader enter if you wish to face Pierre.")
    elif sm.checkParty(BossCooldown.PierreHard, 180):
        sm.warpInstanceIn(105200600, 0, True) # West Garden
        sm.setInstanceTime(20 * 60)
        sm.setDeathCount(5)
        sm.setBossCooldown(BossCooldown.PierreHard)