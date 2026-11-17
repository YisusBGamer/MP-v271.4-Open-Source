# Medina (2183003) | Azwan Refuge Zone : Azwan
from net.swordie.ms.enums import BossCooldown

sm.setSpeakerID(2183001)
if sm.getFieldID() >= 262030100:
    if sm.getFieldID() <= 262031300:
        if sm.sendAskYesNo("Would you like to leave the boss fight?"):
            sm.warpInstanceOut(262000000, 0)

else:
    sm.sendNext("#e<Boss:Hilla>#n \r\n \r\n Are you ready to defeat Hilla and liberate Azwan? Make sure everyone in "
                "your party is here!\r\n\r\n \r\n#L0##bRequest to enter <Boss: Hilla>#l#n")
    response = sm.sendNext(
        "#e<Boss:Hilla>#n\r\n\r\nChoose a mode.\r\n\r\n\r\n#L0#Normal (Level 85+)#l\r\n#L1#Hard (Level 170+)#l")
    if response == 0:
        if sm.getParty() is None:
            sm.createSoloParty()

        if not sm.isPartyLeader():
            sm.sendSayOkay("Please have your party leader enter if you wish to face Hilla.")
        elif sm.checkParty(BossCooldown.Hilla, 85):
            # GMS: straight to Hilla's chamber, 30 min, no death count
            sm.warpInstanceIn(262030300, 0, True)
            sm.setInstanceTime(30 * 60)
            sm.setBossCooldown(BossCooldown.Hilla)
    if response == 1:
        if sm.getParty() is None:
            sm.createSoloParty()

        if not sm.isPartyLeader():
            sm.sendSayOkay("Please have your party leader enter if you wish to face Hilla.")
        elif sm.checkParty(BossCooldown.HillaHard, 170):
            # GMS: chamber with 15 deaths, 30 min (GMS goes through the 262031400 waiting room first)
            sm.warpInstanceIn(262031400, 0, True) # waiting room; ptBossIn starts the fight
            sm.setInstanceTime(10 * 60)
            sm.setDeathCount(15)
            sm.setBossCooldown(BossCooldown.HillaHard)