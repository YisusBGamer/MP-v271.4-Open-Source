# Dimensional Crack stage 1, every character entering (the map's onUserEnter): the stage's instructions
from net.swordie.ms.connection.packet import WvsContext
from net.swordie.ms.enums import WeatherEffNoticeType

chr.write(WvsContext.weatherEffectNotice(WeatherEffNoticeType.LudibriumPQ,
                                         "Defeat the monsters and collect 20 Dimensional Passes!", 5000))
