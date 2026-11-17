# Portal script pt_800010000.
# Entrance to the Spiritwilds -> Spiritwilds Clearing (Mushroom Shrine Tales). During Kino Konoko's Little Sister (58726)
# the path leads to the hill where the little sister is.
if sm.hasQuest(58726):
    sm.warp(800020008, 1)
else:
    sm.warp(800020000, 1)
sm.dispose()
