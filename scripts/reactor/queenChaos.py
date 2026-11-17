from net.swordie.ms.enums import WeatherEffNoticeType

reactor.incHitCount()
sm.showWeatherNotice("Please allow me to mourn over your imminent demise.", WeatherEffNoticeType.BossCrimsonQueenCrownPink, 10000)
if reactor.getHitCount() == 5:
    sm.spawnMobAppearing(8920002, 41, 135, 140000000000, 12)
    sm.removeReactor()
