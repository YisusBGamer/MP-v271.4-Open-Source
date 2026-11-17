# Tenebris story stage rooms: leave.
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


maps, ret = find(sm.getFieldID())
if maps is not None and sm.sendAskYesNo("Do you want to leave?"):
    leave(ret)
sm.dispose()
