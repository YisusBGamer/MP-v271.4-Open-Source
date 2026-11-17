# Tenebris story stage rooms: next room once the monsters are gone; the last room leads back out.
GROUPS = (
    ((993061400, 993061500, 993061600), 993060038),
    ((993061700, 993061800, 993061900), 993060041),
    ((993065300, 993065400, 993065500), 993060050),
    ((993065900, 993066000, 993066100), 993060058),
    ((993066500, 993066600, 993066700), 993060066),
    ((993066800, 993066900, 993067000), 993060068),
    ((993067400,), 993060046),
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
