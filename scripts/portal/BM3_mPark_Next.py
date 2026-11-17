# Tenebris story stage rooms: next room once the monsters are gone; the last room leads back out.
GROUPS = (
    ((993070100, 993070200, 993070300), 450012010),
    ((993070700,), 450012100),
    ((993071000,), 450012110),
    ((993071300, 993071400, 993071500), 450012120),
    ((993072000, 993072100, 993072200, 993072300, 993072400, 993072500), 450012410),
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
