# Tenebris story stage rooms: leave.
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


maps, ret = find(sm.getFieldID())
if maps is not None and sm.sendAskYesNo("Do you want to leave?"):
    leave(ret)
sm.dispose()
