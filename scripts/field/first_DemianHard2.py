from net.swordie.ms.constants import BossConstants
from net.swordie.ms.life.mob.boss.demian import Demian

# phase 1 cut short by 7 Crystals: the HP Damien had left goes on into phase 2 (DemianInstanceHelper)
carry = 0
inst = sm.getChr().getInstance() if sm.getChr() is not None else None
if inst is not None and inst.hasProperty("demianCarryHp"):
    carry = inst.getProperty("demianCarryHp")
sm.spawnMob(BossConstants.DEMIAN_HARD_TEMPLATE_ID_2, 1073, 16, False, BossConstants.DEMIAN_HP_HARD_2 + carry)
sm.addEvent(Demian.stigmaIncinerateObjectTimer(field))  # start Pillar
