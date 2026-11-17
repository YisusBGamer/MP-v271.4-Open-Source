# Tenebris story stage rooms: stage banner on entering.
from net.swordie.ms.constants import WzConstants

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


maps, ret = find(sm.getFieldID())
if maps is not None:
    stage = list(maps).index(sm.getFieldID()) + 1
    if stage == len(maps):
        sm.showEffect(WzConstants.EFFECT_MONSTER_PARK_FINAL_STAGE)
    else:
        sm.showEffect(WzConstants.EFFECT_MONSTER_PARK_STAGE_NUMBER + str(stage))
        sm.showEffect(WzConstants.EFFECT_MONSTER_PARK_STAGE)
    sm.chatScript("Defeat all the monsters to move on.")
