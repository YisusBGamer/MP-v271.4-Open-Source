# [Epic Dungeon] Angler Company (27111, auto start, Selazar 3006760): Selazar's song; it opens the Angler Company
from net.swordie.ms.world.field.instance.instancehelper.epic import EpicDungeon as ED

sm.setSpeakerID(ED.SELAZAR_ODIUM)
sm.sendNext("I've written a new song. It's about #bSenya Angler#k, and the day she joined the #bAngler Company#k...")
sm.sendNext("Deep under the sea, the company keeps its secrets. Robots guard every floor, and somewhere at the "
            "bottom, a machine called #bCosim#k waits for someone to come.")
if not sm.hasQuest(parentID) and not sm.hasQuestCompleted(parentID):
    sm.startQuestNoCheck(parentID)
sm.completeQuestNoRewards(parentID)
sm.sendSayOkay("Would you see how the song ends with your own eyes? Talk to me, and I'll show you the way in.")
sm.dispose()
