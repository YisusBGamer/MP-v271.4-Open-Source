# Mark of the Squad(2083004) | Cave of Life, Entrance to Horntail's Cave
from net.swordie.ms.constants import BossConstants
from net.swordie.ms.enums import BossCooldown

NORMAL_PB_MAP = 270050100
CHAOS_PB_MAP = 270051100
PB_ENTRANCE = 270050000

info = {
    0: [140, BossCooldown.PinkBean, NORMAL_PB_MAP],
    1: [170, BossCooldown.PinkBeanHard, CHAOS_PB_MAP]
}

response = sm.sendNext("Would you like to fight #rPink Bean#k?\r\n"
                       "#b#L0#Normal Mode (Level 140+)#l\r\n"
                       "#L1#Chaos Mode (Level 170+)#l")

reqLv = info[response][0]
cd = info[response][1]
toFieldId = info[response][2]

if sm.getParty() is None:
    sm.createSoloParty()

if not sm.isPartyLeader():
    sm.sendSayOkay("Please have your party leader enter if you wish to face Pink Bean.")
elif sm.checkParty(cd, reqLv):
    if toFieldId == BossConstants.CHAOS_PINK_BEAN_FIELD:
        sm.warpInstanceIn(270051400, 0, True) # waiting room; ptBossIn starts the fight
        sm.setInstanceTime(10 * 60, PB_ENTRANCE)
    else:
        sm.warpInstanceIn(toFieldId, 0, True)
        sm.setInstanceTime(BossConstants.PINK_BEAN_TIME, PB_ENTRANCE)
    sm.setDeathCount(5)
    sm.setBossCooldown(cd)