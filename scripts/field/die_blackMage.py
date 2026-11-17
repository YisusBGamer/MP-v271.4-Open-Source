# Staff test of BlackMageInstanceHelper (InstanceHelperFactory, Tester+ in the party): it spawns and moves the
# party itself, so this script stays out of the way.
from net.swordie.ms.world.field.instance.instancehelper.boss import BlackMageInstanceHelper
_inst = field.getInstance() if field is not None else None
BM_HELPER = _inst is not None and isinstance(_inst.getInstanceHelper(), BlackMageInstanceHelper)

if not BM_HELPER:
    MAP_DIFF = 200 # 100 if direction maps are included

    AEONIAN_RISE = 8880500
    TANADIAN_RUIN = 8880501
    BLACK_MAGE_FINAL = 8880504
    FINAL_PHASE_MAP = 450013700
    REWARD_MAP = 450013750 # its exit portal (bmbossfield_out) leads back to Limina

    if sm.getFieldID() == FINAL_PHASE_MAP:
        # the last phase down: on to the reward map, the fight's only way out
        if field.getLifeByTemplateId(BLACK_MAGE_FINAL) is None:
            sm.warp(REWARD_MAP)
    elif field.getLifeByTemplateId(AEONIAN_RISE) is None and field.getLifeByTemplateId(TANADIAN_RUIN) is None:
        sm.warp(sm.getFieldID() + MAP_DIFF)

