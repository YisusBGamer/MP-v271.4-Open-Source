# will_phase3, fieldId 450008350 (The Deep Mirror)
from net.swordie.ms.constants import BossConstants
from net.swordie.ms.enums import WeatherEffNoticeType
from net.swordie.ms.constants import FieldConstants
from net.swordie.ms.life.mob.boss.will import WillModule

mode = max(0, BossConstants.getWillMode(sm.getFieldID()))  # 0 Easy, 1 Normal, 2 Hard
WILL = BossConstants.willP3(mode)

SECRET_WILL = BossConstants.willDummy(mode, 3, 1)
BULLETS_WILL = BossConstants.willDummy(mode, 3, 2)

sm.spawnMob(WILL, 282, 159, BossConstants.willHp(mode, 3))
sm.spawnMob(SECRET_WILL, 0, 0, 1)
sm.spawnMob(BULLETS_WILL, 0, 0, 1)

sm.startWillWebTimer()

for i in range(0, 36):
    WillModule.spawnNarrowWeb(field)

sm.showWeatherNoticeToField("Looks like Will is serious now. Collected moonlight can burn the spider webs.", WeatherEffNoticeType.BossWill, 7000)
