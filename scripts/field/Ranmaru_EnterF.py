from net.swordie.ms.constants import BossConstants

# first player into Ranmaru's normal (807300110) or hard (807300210) map spawns the boss, once
spawned = field.hasProperty(BossConstants.RANMARU_SPAWN_KEY) and field.getProperty(BossConstants.RANMARU_SPAWN_KEY)
if not spawned:
    field.setProperty(BossConstants.RANMARU_SPAWN_KEY, True)
    if chr.getFieldID() == BossConstants.RANMARU_HARD_MAP:
        sm.spawnMob(BossConstants.RANMARU_HARD_BODY_1, -375, 123, False, BossConstants.RANMARU_HARD_HP)
    else:
        sm.spawnMob(BossConstants.RANMARU_NORMAL_BODY, -375, 123, False, BossConstants.RANMARU_NORMAL_HP)
