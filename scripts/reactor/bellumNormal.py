from net.swordie.ms.enums import WeatherEffNoticeType

sm.removeReactor()
sm.invokeAfterDelay(1500, "spawnMobAppearing", 8930100, -192, 443, 12)
sm.showWeatherNotice("You ignore my warnings?! I will show you no mercy!", WeatherEffNoticeType.BossVellum, 10000)
sm.dispose()
