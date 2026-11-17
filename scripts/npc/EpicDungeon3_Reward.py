# Selazar (3007050) in Nightmare Paradise's exit room (993271450): opens the clear reward window again (Out 212: EXP,
# Sol Erda, Sol Erda Fragments, EXP / Erda bonus tiers for Maple Points), otherwise back to the Operations Center.
from net.swordie.ms.world.field.instance.instancehelper.epic import EpicDungeon as ED

sm.setSpeakerID(3007050)
nightmare = ED.Dungeon.NIGHTMARE


def leave():
    if chr.getInstance() is not None:
        sm.warpInstanceOut(ED.NP_ENTRANCE, 0)
    else:
        sm.warp(ED.NP_ENTRANCE, 0)

if ED.hasReward(chr, nightmare):
    ED.openClearWindow(chr, nightmare)
elif sm.sendAskYesNo("Shall I take you back to the Operations Center?"):
    leave()
sm.dispose()
