# [Weekly Quest] Erda's Request (Lv. 260+, after 1488): 5,000 Sacred Power enemies in Grandis.
# Setting erda_request_daily on: every character, every day. Off (official): the reward once a week per account.
from net.swordie.ms.constants import ServerFeatures
from net.swordie.ms.client.character import AccountWeekly

daily = ServerFeatures.on(ServerFeatures.Feature.ERDA_REQUEST_DAILY)
sm.setSpeakerID(1540940)
taken = None if daily else AccountWeekly.erdaRequestTakenBy(chr)
if taken is not None:
    sm.sendSayOkay("The Erda has already answered " + taken + " this week. Come back after the weekly reset (Thursday).")
else:
    sm.sendNext(("Today" if daily else "This week") + ", defeat #b5,000 Sacred Power enemies#k in the Grandis regions, "
                "and the Erda will gather into #bSol Erda#k for you.")
    sm.startQuest(parentID)
sm.dispose()
