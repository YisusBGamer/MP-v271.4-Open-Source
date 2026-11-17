# Tokugawa Ieyasu (9130088), Secret Altar Path (807300100): the map's two portals (RanmaruEnter and west00) do nothing
# for players (the client never asks the server to use them there). He takes you in to face Ranmaru (the same as the
# RanmaruEnter portal's script) or back to El Nath (west00's script).
from net.swordie.ms.constants import BossConstants
from net.swordie.ms.enums import BossCooldown

sm.setSpeakerID(9130088)
sel = sm.sendNext("Ranmaru waits beyond the altar.\r\n\r\n#b"
                  "#L0#Face Ranmaru#l\r\n"
                  "#L1#Return to El Nath#l")
if sel == 1:
    sm.warp(211041700, 0)
else:
    mode = sm.sendNext("#e<Boss: Ranmaru>#n\r\nSelect a mode.\r\n\r\n#b"
                       "#L0#Normal (Level 120+)#l\r\n"
                       "#L1#Hard (Level 180+)#l")
    if sm.getParty() is None:
        sm.createSoloParty()
    if not sm.isPartyLeader():
        sm.sendSayOkay("Please have your party leader enter if you wish to face Ranmaru.")
    elif mode == 0:
        if sm.checkParty(BossCooldown.Ranmaru, 120):
            sm.warpInstanceIn(BossConstants.RANMARU_NORMAL_MAP, 0, True)
            sm.setInstanceTime(60 * 60)
            sm.setDeathCount(BossConstants.RANMARU_DEATHCOUNT)
            sm.setBossCooldown(BossCooldown.Ranmaru)
    elif mode == 1:
        if sm.checkParty(BossCooldown.RanmaruHard, 180):
            sm.warpInstanceIn(BossConstants.RANMARU_HARD_MAP, 3, True)
            sm.setInstanceTime(60 * 60)
            sm.setDeathCount(5)
            sm.setBossCooldown(BossCooldown.RanmaruHard)
sm.dispose()
