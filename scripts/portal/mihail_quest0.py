# Entrance to the Drill Hall (130020000), portal mob00
# It leads to the Training Monster Pens (their exits come back to this portal), where Mihile gets back the Ereve
# Archives' documents for Training Instructor Kiku: 20774 Retrieving Documents, 20775 More Missing Documents, 20776
# Disappeared Documents 2. (The documents drop from the Horny Mushrooms in Pens 1 and the Blue Ribbon Pigs in Pens 2.)
PENS = ((20774, 913070330), (20775, 913070340), (20776, 913070340))

target = 0
for quest, field_id in PENS:
    if sm.hasQuest(quest):
        target = field_id
        break
if target:
    sm.warp(target, 0)
else:
    sm.chat("The Training Monster Pens are only open to recruits sent by Training Instructor Kiku.")
sm.dispose()
