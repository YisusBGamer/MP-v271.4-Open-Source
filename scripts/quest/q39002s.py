# 39002 [Weekly Quest] Dark World Tree Weekly Mission (Alchetto, Dark World Tree 105300000). Alchetto hands out this
# week's three missions, one per tier of the World Tree: their records get start=1, which is what opens them
# (39003-39012 need it). The mission quest itself has nothing to hand in.
import random

TIERS = ((39003, 39004), (39005, 39006, 39007, 39008), (39009, 39010, 39011, 39012))

sm.setSpeakerID(1540895)
sm.sendNext("The corruption of the World Tree spreads every week. I need someone strong enough to push it back.")
if not sm.sendAskAccept("Will you take on this week's missions in the World Tree?"):
    sm.dispose()
if not sm.hasQuest(parentID):
    sm.startQuest(parentID)
picked = []
for tier in TIERS:
    open_now = [q for q in tier if sm.getQRValue(q, "start") == "1" and not sm.hasQuestCompleted(q)]
    q = open_now[0] if open_now else random.choice(tier)
    sm.setQRValue(q, "start", "1")
    picked.append(q)
sm.completeQuestNoCheck(parentID)
sm.sendSayOkay("This week's missions are ready: one for each tier of the World Tree. Talk to me again to "
               "take them on.")
sm.dispose()
