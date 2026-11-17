# [Weekly Quest] Erda's Request. Daily setting: 3 Sol Erda (the quest has no Act reward). Official: 9 Sol Erda and
# 90 Sol Erda Fragments (Etc.wz MapleScheduler weekly_GLSolErda: items 2636656 / 4009547), once a week per account.
from net.swordie.ms.client.character.hexa import HexaModule
from net.swordie.ms.constants import ServerFeatures
from net.swordie.ms.client.character import AccountWeekly

FRAGMENT = 4009547

daily = ServerFeatures.on(ServerFeatures.Feature.ERDA_REQUEST_DAILY)
sm.setSpeakerID(1540940)
taken = None if daily else AccountWeekly.erdaRequestTakenBy(chr)
if taken is not None:
    sm.sendSayOkay("The Erda has already answered " + taken + " this week. Come back after the weekly reset (Thursday).")
elif not daily and not sm.canHold(FRAGMENT, 90):
    sm.sendSayOkay("Make room in your ETC inventory for 90 Sol Erda Fragments first.")
elif sm.completeQuest(parentID):
    if daily:
        HexaModule.addSolErda(chr, 3)
        sm.sendSayOkay("The Erda has calmed down. You received #b3 Sol Erda#k.")
    else:
        AccountWeekly.recordErdaRequest(chr)
        HexaModule.addSolErda(chr, 9)
        sm.giveItem(FRAGMENT, 90)
        sm.sendSayOkay("The Erda has calmed down. You received #b9 Sol Erda#k and #b90 #t" + str(FRAGMENT) + "##k.")
else:
    sm.sendSayOkay("The Erda is still restless. Defeat more Sacred Power enemies in Grandis.")
sm.dispose()
