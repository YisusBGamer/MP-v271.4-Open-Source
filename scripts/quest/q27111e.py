# [Epic Dungeon] Angler Company (27111, Selazar 3006760): ends the song; the Angler Company opens in the window
from net.swordie.ms.world.field.instance.instancehelper.epic import EpicDungeon as ED

sm.setSpeakerID(ED.SELAZAR_ODIUM)
if not sm.hasQuestCompleted(parentID):
    sm.completeQuestNoRewards(parentID)
sm.sendSayOkay("That's how the song begins. Talk to me whenever you'd like to see how it ends.")
sm.dispose()
