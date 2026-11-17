# Tower of Oz 42F: the warning above the spike pit.
from net.swordie.ms.enums import WeatherEffNoticeType

sm.showWeatherNotice("Watch your step! Falling onto the spikes sends you back to the start.", WeatherEffNoticeType.Oz_Alicia, 3000)
sm.dispose()
