# Verus Hilla entrance (Altar of Desire Entrance, 450011990)
# Official (maplestorywiki.net, GMS): Normal and Hard, level 250, 30 minutes, death count 5. Both use the same map, so
# the Hard instance is marked for JinHillah_onFirstUserEnter.py.
from net.swordie.ms.constants import BossConstants
from net.swordie.ms.enums import BossCooldown

VERUS_HILLA_MAP = 450010500

sm.setPlayerAsSpeaker()
response = sm.sendSayOkay("I need to enter the Altar of Desire and face #rVerus Hilla#k.\r\n\r\n"
                          "#L0##rFight Verus Hilla (Normal)#l\r\n"
                          "#L1##rFight Verus Hilla (Hard)#l\r\n"
                          "#L2##bLeave#l")

if response in (0, 1):
    hard = response == 1
    cd = BossCooldown.VerusHillaHard if hard else BossCooldown.VerusHilla
    if sm.getParty() is None:
        sm.createSoloParty()

    if not sm.isPartyLeader():
        sm.sendSayOkay("Please have your party leader enter if you wish to face Verus Hilla.")
    elif sm.checkParty(cd, 250):
        if hard:
            sm.setNextInstanceProperty(BossConstants.HARD_MODE_KEY, True)
        sm.warpInstanceIn(VERUS_HILLA_MAP, 0, True)
        sm.setInstanceTime(30 * 60)
        sm.setDeathCount(5)
        sm.setBossCooldown(cd)
