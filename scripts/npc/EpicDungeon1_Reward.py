# Selazar (3006722) at the summit of High Mountain (993241950): opens the clear reward window again (Out 212: EXP,
# Sol Erda, Sol Erda Fragments, EXP / Erda bonus tiers for Maple Points), otherwise back to Cernium.
from net.swordie.ms.world.field.instance.instancehelper.epic import EpicDungeon as ED

sm.setSpeakerID(3006722)


def leave():
    if chr.getInstance() is not None:
        sm.warpInstanceOut(ED.HM_ENTRANCE, 0)
    else:
        sm.warp(ED.HM_ENTRANCE, 0)

if ED.hasReward(chr):
    ED.openClearWindow(chr)
elif sm.sendAskYesNo("Shall I take you back to Cernium?"):
    leave()
sm.dispose()
