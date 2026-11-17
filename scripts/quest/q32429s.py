# 32429 [Weekly Quest] Kritias Weekly Mission (2230000, Kritias). This week's missions, one from each of the three
# Kritias quartermasters (2230000, 2230001, 2230002): their records get start=1, which is what opens them.
import random

TIERS = (range(32430, 32457), range(32458, 32470), range(32470, 32480))

sm.sendNext("Kritias needs every able fighter. The quartermasters have this week's orders ready.")
if not sm.sendAskAccept("Will you take on this week's Kritias missions?"):
    sm.dispose()
if not sm.hasQuest(parentID):
    sm.startQuest(parentID)
for tier in TIERS:
    open_now = [q for q in tier if sm.getQRValue(q, "start") == "1" and not sm.hasQuestCompleted(q)]
    if not open_now:
        sm.setQRValue(random.choice(list(tier)), "start", "1")
sm.completeQuestNoCheck(parentID)
sm.sendSayOkay("This week's missions are ready: one from each quartermaster. Talk to them to take them on.")
sm.dispose()
