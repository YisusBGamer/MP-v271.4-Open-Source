# Resurrection of the Hoblin King: each area's instructions
from net.swordie.ms.enums import WeatherEffNoticeType
MESSAGES = {
    921120005: "Hoblins are coming! Hold them off to protect Shammos!",
    921120100: "More Hoblins! Defeat them all so Shammos can move on!",
    921120200: "The Ferocious Hoblins are here! Defeat them all!",
    921120300: "Rex, the Hoblin King, has awakened!",
}
msg = MESSAGES.get(sm.getFieldID())
if msg is not None:
    sm.showWeatherNoticeToField(msg, WeatherEffNoticeType.ShammosPQ)
sm.dispose()
