# Tenebris story stage rooms: stage banner on entering.
from net.swordie.ms.constants import WzConstants

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


maps, ret = find(sm.getFieldID())
if maps is not None:
    stage = list(maps).index(sm.getFieldID()) + 1
    if stage == len(maps):
        sm.showEffect(WzConstants.EFFECT_MONSTER_PARK_FINAL_STAGE)
    else:
        sm.showEffect(WzConstants.EFFECT_MONSTER_PARK_STAGE_NUMBER + str(stage))
        sm.showEffect(WzConstants.EFFECT_MONSTER_PARK_STAGE)
    sm.chatScript("Defeat all the monsters to move on.")
