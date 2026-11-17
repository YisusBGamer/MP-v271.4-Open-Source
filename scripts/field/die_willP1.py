# 8880300
import time
from net.swordie.ms.enums import WeatherEffNoticeType

sm.showWeatherNoticeToField("Will is serious now. Perhaps his true intentions will be reflected within the mirror.", WeatherEffNoticeType.BossWill, 7000)
if hasattr(sm, "warpFieldLaterIfStillIn"):
    sm.warpFieldLaterIfStillIn(11000, field.getId() + 100)  # backup if this script is stopped while it waits
sm.createClock(5)  # GMS: a 5 s countdown before the next phase
time.sleep(5)
sm.warpField(field.getId() + 100)