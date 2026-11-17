# Lord Pirate party quest: the stage's instructions when entering it
from net.swordie.ms.connection.packet import WvsContext
from net.swordie.ms.enums import WeatherEffNoticeType

MESSAGES = {
    925100000: "Eliminate all monsters and break all the chests!",
    925100100: "Collect the Pirate Marks and bring them to Guon!",
    925100400: "Close the four doors with Old Metal Keys and defeat every pirate!",
    925100500: "Defeat the Lord Pirate!",
}
msg = MESSAGES.get(sm.getFieldID())
if msg is not None:
    sm.showWeatherNoticeToField(msg, WeatherEffNoticeType.LordPiratePQ)
sm.dispose()
