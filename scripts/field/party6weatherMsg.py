# Forest of Poison Haze: each area's instructions
from net.swordie.ms.enums import WeatherEffNoticeType
MESSAGES = {
    930000010: "Go through the portal into the poisoned forest.",
    930000100: "Defeat all 50 Poisoned Lord Trees!",
    930000200: "Dilute the poison in the water hole, then drop 4 Diluted Poison on the Thorny Vines!",
    930000300: "Find your way through the haze to Ellin at the bottom right!",
    930000400: "Defeat the Poisoned Sprights and bring Ellin 20 Monster Marbles!",
    930000500: "Find the Purple Stone of Magic in one of the boxes up top and show it to the Sprite!",
    930000600: "Drop the Purple Stone of Magic on the altar and defeat the Poison Golem!",
}
msg = MESSAGES.get(sm.getFieldID())
if msg is not None:
    sm.showWeatherNoticeToField(msg, WeatherEffNoticeType.EllinPQ)
sm.dispose()
