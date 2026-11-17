# Escape party quest: each stage's instructions (the stages are run by EscapeInstanceHelper)
from net.swordie.ms.enums import WeatherEffNoticeType

fid = sm.getFieldID()
stage = fid % 1000 / 100
MESSAGES = {
    2: "Defeat all the guards before they call for more!",
    3: "A maze of Dark Towers! Find Dark Tower 6. One portal of each tower leads on.",
    4: "Defeat all the guards defending the secret door!",
    5: "The last trap! One of you must reach the top.",
    6: "Open the 4 prison gates with Prison Keys and defeat the guards!",
    7: "Defeat the Prison Guard and free the prisoners!",
}
msg = MESSAGES.get(stage)
if msg is not None:
    sm.showWeatherNoticeToField(msg, WeatherEffNoticeType.EscapePQ)
sm.dispose()
