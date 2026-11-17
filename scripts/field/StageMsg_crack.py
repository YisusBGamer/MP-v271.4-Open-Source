# Dimensional Crack: the stage's instructions when entering it (the stage logic is in DimensionalCrackInstanceHelper)
from net.swordie.ms.connection.packet import WvsContext
from net.swordie.ms.enums import WeatherEffNoticeType

MESSAGES = {
    922010100: "Defeat the monsters and collect 20 Dimensional Passes!",
    922010400: "Defeat all the Dark Eyes and Shadow Eyes hiding in the five dark rooms!",
    922010600: "Find the right box in each row and get to the top!",
    922010700: "Defeat the 4 Rombots, then talk to the Sky-Blue Balloon!",
    922010800: "Ask the Blue Balloon for the question, then stand on the boxes of the answer!",
    922010900: "Defeat Alishar!",
}
msg = MESSAGES.get(sm.getFieldID())
if msg is not None:
    chr.write(WvsContext.weatherEffectNotice(WeatherEffNoticeType.LudibriumPQ, msg, 5000))
