# Selazar (3006768) in the Angler Company's exit room (993253250): opens the clear reward window again (Out 212: EXP,
# Sol Erda, Sol Erda Fragments, EXP / Erda bonus tiers for Maple Points), otherwise back to Odium.
from net.swordie.ms.world.field.instance.instancehelper.epic import EpicDungeon as ED

sm.setSpeakerID(3006768)
angler = ED.Dungeon.ANGLER


def leave():
    if chr.getInstance() is not None:
        sm.warpInstanceOut(ED.ANGLER_ENTRANCE, 0)
    else:
        sm.warp(ED.ANGLER_ENTRANCE, 0)

if ED.hasReward(chr, angler):
    ED.openClearWindow(chr, angler)
elif sm.sendAskYesNo("Shall I take you back to Odium?"):
    leave()
sm.dispose()
