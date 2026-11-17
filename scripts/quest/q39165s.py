# 39165 [Weekly Quest] Haven Weekly Mission (One-Eye 2155000, Haven 310070000). This week's commissions, one from each
# part of Haven: their records get start=1, which is what opens them. Four of them earn the Request Reward (39160).
import random

AREAS = (
    (39101, 39102, 39103, 39104, 39105, 39106, 39107, 39108),  # Haven
    (39111, 39112, 39113, 39114, 39115, 39116, 39117, 39118, 39119),  # Scrapyard
    (39121, 39122, 39123, 39124, 39125, 39126, 39127),  # Skyline
    (39131, 39132, 39133, 39134, 39135, 39136),  # Black Heaven Deck
    (39141, 39142, 39143, 39144, 39145, 39146, 39147, 39148, 39149, 39150, 39151, 39152, 39153, 39154, 39155),  # Inside
)

sm.setSpeakerID(2155000)
sm.sendNext("Squishy! Just in time. The Resistance has a whole pile of commissions this week.")
if not sm.sendAskAccept("Will you take on this week's commissions?"):
    sm.dispose()
if not sm.hasQuest(parentID):
    sm.startQuest(parentID)
for area in AREAS:
    open_now = [q for q in area if sm.getQRValue(q, "start") == "1" and not sm.hasQuestCompleted(q)]
    if not open_now:
        sm.setQRValue(random.choice(area), "start", "1")
if sm.getQRValue(39160, "start") != "1":
    sm.setQRValue(39160, "start", "1")
sm.completeQuestNoCheck(parentID)
sm.sendSayOkay("There you go: one commission in each part of Haven. Finish four this week and come see me for the reward. Heehee.")
sm.dispose()
