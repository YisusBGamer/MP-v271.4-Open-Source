# Tenebris story stage rooms: next room once the monsters are gone; the last room leads back out.
GROUPS = (
    ((993063800, 993063900), 450011320),
    ((993068600, 993068700), 450011220),
    ((993069000, 993069100), 450011220),
    ((993069300, 993069400), 450011220),
    ((993069600, 993069700), 450011220),
    ((993069900,), 450011320),
)


def find(field_id):
    for maps, ret in GROUPS:
        if field_id in maps:
            return maps, ret
    return None, 0


def leave(ret):
    if chr.getInstance() is not None:
        sm.warpInstanceOut(ret, 0)
    else:
        sm.warp(ret, 0)


field_id = sm.getFieldID()
maps, ret = find(field_id)
if sm.hasMobsInField():
    sm.chat("Defeat all the monsters first.")
elif maps is None:
    sm.chat("This portal doesn't lead anywhere.")
else:
    i = list(maps).index(field_id)
    if i + 1 < len(maps):
        sm.warp(maps[i + 1], 0)
    else:
        leave(ret)
sm.dispose()
