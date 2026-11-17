# Tenebris story stage rooms: stage banner on entering.
from net.swordie.ms.constants import WzConstants

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


maps, ret = find(sm.getFieldID())
if maps is not None:
    stage = list(maps).index(sm.getFieldID()) + 1
    if stage == len(maps):
        sm.showEffect(WzConstants.EFFECT_MONSTER_PARK_FINAL_STAGE)
    else:
        sm.showEffect(WzConstants.EFFECT_MONSTER_PARK_STAGE_NUMBER + str(stage))
        sm.showEffect(WzConstants.EFFECT_MONSTER_PARK_STAGE)
    sm.chatScript("Defeat all the monsters to move on.")
