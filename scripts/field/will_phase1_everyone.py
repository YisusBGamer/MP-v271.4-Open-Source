# will_phase1, fieldId 450008150 (Diffraction Hall)
from net.swordie.ms.constants import BossConstants
from net.swordie.ms.enums import WeatherEffNoticeType

mode = max(0, BossConstants.getWillMode(sm.getFieldID()))
REAL_WILL = BossConstants.willP1(mode)
BLUE_WILL = BossConstants.willBlueP1(mode)
PURPLE_WILL = BossConstants.willPurpleP1(mode)

chr.getBossInfo().addMoonlight(50)
sm.invokeAtFixedRate(0, 1000, 0, "incrementMoonlight")

sm.showHP(REAL_WILL)
sm.showHP(BLUE_WILL)
sm.showHP(PURPLE_WILL)

sm.showWeatherNotice("You have to attack Will simultaneously in 2 separate dimensions. If you collect the moonlight and use it, you can shift to the other side.",
                     WeatherEffNoticeType.BossWill, 7000)
