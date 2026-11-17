# Kenta in Danger: each area's instructions (the stages are run by KentaInstanceHelper)
from net.swordie.ms.enums import WeatherEffNoticeType
MESSAGES = {
    923040100: "Break the shipwreck to free Kenta, then bring him 10 Air Bubbles from the Sharks!",
    923040200: "Seal the caves with rocks: 3 per cave!",
    923040300: "Climb to the top of the cave and clear out the Cave Bats!",
    923040400: "Defeat both Pianus!",
}
msg = MESSAGES.get(sm.getFieldID())
if msg is not None:
    sm.showWeatherNoticeToField(msg, WeatherEffNoticeType.KentaPQ)
sm.dispose()
