# First Time Together (Cross World): each stage's instructions
from net.swordie.ms.enums import WeatherEffNoticeType
MESSAGES = {
    933011000: "Everyone! Talk to Cloto, and defeat Ligators to find the coupons Cloto wants!",
    933012000: "Three of the four vines are right. Hang on different vines and have your leader talk to Cloto!",
    933013000: "Three of the five platforms are right. Stand on them and have your leader talk to Cloto!",
    933014000: "Reach Cloto's number! Drop the Curse Eyes' numbers on the signs.",
    933015000: "Defeat King Slime!",
}
msg = MESSAGES.get(sm.getFieldID())
if msg is not None:
    sm.showWeatherNotice(msg, WeatherEffNoticeType.KerningPQ, 5000)
sm.dispose()
