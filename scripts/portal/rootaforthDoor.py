from net.swordie.ms.enums import BossCooldown

sm.setSpeakerID(1064015) # Fourth Seal Door
response = sm.sendNext("Would you like to fight Vellum?\r\n #b#L0#Normal Mode (Level 125+)#l \r\n #L1#Chaos Mode (Level 180+)#l")

if response == 0:
    if sm.getParty() is None:
        sm.createSoloParty()

    if not sm.isPartyLeader():
        sm.sendSayOkay("Please have your party leader enter if you wish to face Vellum.")
    elif sm.checkParty(BossCooldown.Vellum, 125):
        sm.warpInstanceIn(105200400, 0, True) # North Garden
        sm.setInstanceTime(15 * 60)
        sm.setDeathCount(5)
        sm.setBossCooldown(BossCooldown.Vellum)
elif response == 1:
    if sm.getParty() is None:
        sm.createSoloParty()

    if not sm.isPartyLeader():
        sm.sendSayOkay("Please have your party leader enter if you wish to face Vellum.")
    elif sm.checkParty(BossCooldown.VellumHard, 180):
        sm.warpInstanceIn(105201300, 0, True) # waiting room; ptBossIn -> 105200810
        sm.setInstanceTime(10 * 60)
        sm.setDeathCount(5)
        sm.setBossCooldown(BossCooldown.VellumHard)