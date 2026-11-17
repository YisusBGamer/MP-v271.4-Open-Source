from net.swordie.ms.enums import WeatherEffNoticeType

reactor.incHitCount()
sm.showWeatherNotice("Please allow me to mourn over your imminent demise.", WeatherEffNoticeType.BossCrimsonQueenCrownPink, 10000)
if reactor.getHitCount() == 5:
    sm.spawnMobAppearing(8920102, 37, 135, 315000000, 12)
    sm.removeReactor()
