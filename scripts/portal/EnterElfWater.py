# Portal script EnterElfWater.
# Flowery Forest 1 (Ellinel/Fairy Forest), up: the Fairy Fountain. During Dirty Tactics (24077, Mercedes) the
# fountainhead is occupied by the Black Wings (fight them there).
if sm.hasQuest(24077):
    sm.warp(910150230, 1)
else:
    sm.warp(101050020, 1)
sm.dispose()
