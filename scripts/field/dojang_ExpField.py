# Mu Lung Dojo Unity Training Center (925080000): EXP every 5 seconds for as long as the Entrance Charm's time lasts
# (So Gong set the instance's time when you went in).
from net.swordie.ms.enums import WeatherEffNoticeType

if hasattr(sm, "giveUnityTrainingExp"):
    sm.showWeatherNotice("Train with your fellow disciples! You gain EXP every 5 seconds.", WeatherEffNoticeType.MuLungDojo, 5000)
    sm.invokeAtFixedRate(5000, 5000, 0, "giveUnityTrainingExp")
