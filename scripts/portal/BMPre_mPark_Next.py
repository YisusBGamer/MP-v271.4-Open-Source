# Tenebris story stage rooms: next room once the monsters are gone; the last room leads back out.
GROUPS = (
    ((993060100, 993060200, 993060300), 993060010),
    ((993060500, 993060600, 993060700), 993060013),
    ((993060800, 993060900, 993061000), 993060001),
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
