from net.swordie.ms.enums import WeatherEffNoticeType
PIERRE = 8900100

sm.spawnMobAppearing(PIERRE, 1000, 551, 1)
sm.showFieldEffect("Map/Effect.img/rootabyss/firework") # "Welcome" effect
sm.showWeatherNotice("From the bottom of my heart, welcome to the tea party!", WeatherEffNoticeType.BossPierre, 10000)
