# Staff test of BlackMageInstanceHelper (InstanceHelperFactory, Tester+ in the party): it spawns and moves the
# party itself, so this script stays out of the way.
from net.swordie.ms.world.field.instance.instancehelper.boss import BlackMageInstanceHelper
_inst = field.getInstance() if field is not None else None
BM_HELPER = _inst is not None and isinstance(_inst.getInstanceHelper(), BlackMageInstanceHelper)

if not BM_HELPER:
    # 450,013,100 - Limina: Temple of Darkness
    TIME = 60 * 60  # Hard: 1 hour (maplestorywiki.net, GMS)

    MAP_P1 = 450013100
    MAP_P2 = 450013300
    MAP_P3 = 450013500
    MAP_P4 = 450013700
    REWARD_MAP = 450013750

    AEONIAN_RISE = 8880500
    TANADIAN_RUIN = 8880501
    BLACK_MAGE_P2 = 8880502
    BLACK_MAGE_P3 = 8880503
    BLACK_MAGE_P4 = 8880504
    GENESIS_CRUX = 8880518  # reward box; [Genesis Weapon] After the Destined Battle (35651) asks for it

    fieldId = field.getId()

    if fieldId == MAP_P1:
        sm.setInstanceTime(TIME)
        sm.spawnMob(AEONIAN_RISE, -942, 85, 31500000000000) # 31.5t: the two knights together have the official 63t
        sm.spawnMob(TANADIAN_RUIN, 942, 85, 31500000000000) # 31.5t

    elif fieldId == MAP_P2:
        sm.spawnMob(BLACK_MAGE_P2, 0, 88, 115500000000000) # 115.5t (wiki, hard)

    elif fieldId == MAP_P3:
        sm.spawnMob(BLACK_MAGE_P3, 375, 88, 157500000000000) # 157.5t (wiki, hard)

    elif fieldId == MAP_P4:
        sm.spawnMob(BLACK_MAGE_P4, 375, 88, 136500000000000) # 136.5t (wiki, hard)

    elif fieldId == REWARD_MAP:
        sm.spawnMob(GENESIS_CRUX, 0, 218)

