from net.swordie.ms.enums import WeatherEffNoticeType

sm.showWeatherNotice("Dodge the traps and navigate the obstacles to reach the exit.", WeatherEffNoticeType.Oz_Alicia, 5000)

if chr.getInstance() is not None:
    chr.getInstance().warp(chr.getFieldID() + 1)