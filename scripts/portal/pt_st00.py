# Portal script pt_st00.
# Cygnus's Rest (Grand Athenaeum, Episode II): during Willpower Flower (32624) the Mysterious Herb Canyon, otherwise
# the battle to protect Ereve.
if sm.hasQuest(32624):
    sm.warp(303040000, 0)
else:
    sm.warp(303050000, 0)
sm.dispose()
