# will_phase1, fieldId 450008150 (Diffraction Hall)
from net.swordie.ms.connection.packet.field import WillFieldPacket
from net.swordie.ms.constants import BossConstants
from net.swordie.ms.constants import FieldConstants
from net.swordie.ms.life.mob.skill import MobSkillID

mode = max(0, BossConstants.getWillMode(sm.getFieldID()))  # 0 Easy, 1 Normal, 2 Hard
HP = BossConstants.willHp(mode, 1)

REAL_WILL = BossConstants.willP1(mode)
BLUE_WILL = BossConstants.willBlueP1(mode)
PURPLE_WILL = BossConstants.willPurpleP1(mode)

WILL_IDS = [REAL_WILL, BLUE_WILL, PURPLE_WILL]
TRIGGER_BLOCK_PERCS = [50, 1] if mode < 2 else [67, 34, 1]  # GMS Easy/Normal: 50% and 0.3%

SECRET_WILL = BossConstants.willDummy(mode, 1, 1) # For the attacks from the sky
BULLETS_WILL = BossConstants.willDummy(mode, 1, 2)

sm.spawnMob(REAL_WILL, 0, 0, HP) # Red (main) bar
sm.spawnMob(BLUE_WILL, 282, 159, HP) # Blue bar
sm.spawnMob(SECRET_WILL, 282, 159, 1)
sm.spawnMob(BULLETS_WILL, -253, 159, 1)
sm.spawnMob(BULLETS_WILL, 282, 159, 1)
sm.spawnMob(BULLETS_WILL, -253, -259, 1)
sm.spawnMob(BULLETS_WILL, 282, -259, 1)

sm.spawnMob(PURPLE_WILL, 282, -2020, HP) # Purple bar
sm.spawnMob(SECRET_WILL + 1, 282, -2020, 1)
sm.spawnMob(BULLETS_WILL + 1, -253, -2020, 1)
sm.spawnMob(BULLETS_WILL + 1, 282, -2020, 1)
sm.spawnMob(BULLETS_WILL + 1, -253, -2320, 1)
sm.spawnMob(BULLETS_WILL + 1, 282, -2320, 1)

for will in WILL_IDS:
    mob = field.getLifeByTemplateId(will)
    mob.addBlockedSkill(MobSkillID.BossWillAttack, 8)
    mob.addBlockedSkill(MobSkillID.BossWillAttack, 15)
    for blockPerc in TRIGGER_BLOCK_PERCS:
        mob.addHpTriggerBlockPerc(blockPerc)

sm.showHP(REAL_WILL)
sm.showHP(BLUE_WILL)
sm.showHP(PURPLE_WILL)

field.broadcastPacket(WillFieldPacket.willTriggerBlockP1(TRIGGER_BLOCK_PERCS, WILL_IDS))

sm.startWillTriggerTimer()