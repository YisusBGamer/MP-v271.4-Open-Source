# Tenebris story stage rooms: stage banner on entering.
from net.swordie.ms.constants import WzConstants

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


maps, ret = find(sm.getFieldID())
if maps is not None:
    stage = list(maps).index(sm.getFieldID()) + 1
    if stage == len(maps):
        sm.showEffect(WzConstants.EFFECT_MONSTER_PARK_FINAL_STAGE)
    else:
        sm.showEffect(WzConstants.EFFECT_MONSTER_PARK_STAGE_NUMBER + str(stage))
        sm.showEffect(WzConstants.EFFECT_MONSTER_PARK_STAGE)
    sm.chatScript("Defeat all the monsters to move on.")
