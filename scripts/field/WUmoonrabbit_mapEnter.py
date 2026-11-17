# Primrose Hill (933001000), first entrant: stage 1 of Moon Bunny's Rice Cake. Only the pigs (9300900/9300901, they
# drop the Primrose Seeds) come back; the flyeyes, stirges and goblin fires wait for stage 2 (reactor/92500xxaction).
from net.swordie.ms.enums import WeatherEffNoticeType

STAGE_2_MOBS = [9300903, 9300904, 9300905]

if not field.hasProperty("stage"):
    field.setProperty("stage", 1)
    field.setProperty("Stage1FlowerData", 0)
    field.setProperty("Stage1Cleared", False)
    field.setNoRespawn(STAGE_2_MOBS)
    sm.killMobs()
    sm.setInstanceTime(1200)
    sm.showWeatherNotice("6 Primrose Seeds, stolen by the pigs, must be recovered...", WeatherEffNoticeType.RiceCakePQ)
sm.dispose()
