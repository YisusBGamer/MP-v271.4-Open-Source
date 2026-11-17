# Romeo and Juliet party quest: each stage's instructions (the stages are run by RomeoJulietInstanceHelper)
from net.swordie.ms.enums import WeatherEffNoticeType
off = sm.getFieldID() % 10000
MESSAGES = {
    0: "Search the glowing spots to find the switch!",
    1: "Defeat all the Homuns and Saities!",
    100: "Fill the 3 cracked beakers with Suspicious Liquid from the Homunculi!",
    200: "Get Card Keys from the Neo Huroids, open the side doors and find both Experiment Files!",
    203: "Defeat Yulete's Mutae and find the switch!",
    300: "Pick a door. One platform per floor leads up!",
    401: "Defeat Frankenroid!",
}
msg = MESSAGES.get(off)
if msg is not None:
    sm.showWeatherNoticeToField(msg, WeatherEffNoticeType.JulietNPC)
sm.dispose()
