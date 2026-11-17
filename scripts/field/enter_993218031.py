# Nightmare Paradise story hub (993218031): the story maps return here (death, a dropped connection) and it has no way
# out, so it sends the character back to Selazar at the Operations Center
from net.swordie.ms.world.field.instance.instancehelper.epic import EpicDungeon as ED

if chr.getFieldID() == 993218031:
    if chr.getInstance() is not None:
        sm.warpInstanceOut(ED.NP_ENTRANCE, 0)
    else:
        sm.warp(ED.NP_ENTRANCE, 0)
