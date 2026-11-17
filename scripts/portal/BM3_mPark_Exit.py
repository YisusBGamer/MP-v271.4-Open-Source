# Tenebris story stage rooms: leave.
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


maps, ret = find(sm.getFieldID())
if maps is not None and sm.sendAskYesNo("Do you want to leave?"):
    leave(ret)
sm.dispose()
