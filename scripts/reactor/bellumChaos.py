from net.swordie.ms.enums import WeatherEffNoticeType

sm.removeReactor()
sm.spawnMobAppearing(8930000, -190, 443, 200000000000, 12)
sm.showWeatherNotice("You ignore my warnings?! I will show you no mercy!", WeatherEffNoticeType.BossVellum, 10000)
sm.dispose()
