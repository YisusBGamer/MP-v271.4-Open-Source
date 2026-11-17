from net.swordie.ms.constants import BossConstants
from net.swordie.ms.enums import BossCooldown

sm.setPlayerAsSpeaker()
response = sm.sendSayOkay("We should start looking at #rGloom#k.\r\n\r\n"
                          "#L0##rFight Gloom#l\r\n"
                          "#L1##bFight Gloom (Chaos)#l\r\n"
                          "#L2##kLeave#l")

# fight map, cooldown, GMS v271 waiting room (ptBossIn starts the fight)
info = [
    [BossConstants.GLOOM_FIELD, BossCooldown.Gloom, 450020600],
    [BossConstants.GLOOM_HARD_FIELD, BossCooldown.GloomHard, 450020700],
]

if response != 2:
    actInfo = info[response]
    if sm.getParty() is None:
        sm.createSoloParty()

    if not sm.isPartyLeader():
        sm.sendSayOkay("Please have your party leader enter if you wish to face Gloom.")
    elif sm.checkParty(actInfo[1], 245):
        sm.warpInstanceIn(actInfo[2], 0, True)
        sm.setInstanceTime(10 * 60)
        sm.setDeathCount(5)
        sm.setBossCooldown(actInfo[1])
