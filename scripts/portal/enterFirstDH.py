# Entrance to the Drill Hall (130020000), portal in00
# In v232 it leads to Mihile's Recruit Training Grounds for Training Instructor Kiku's sessions (each ground's exit
# comes back to this portal): 20771 Green Mushrooms in Recruit Training Grounds 1, 20772 Trained Attack Stirges in 2,
# 20773 Strange Pigs in 3. (The old Noblesse acclimation training in the 1st Drill Hall, quests 20701-20703, is
# blocked in v232.)
GROUNDS = ((20771, 913070300), (20772, 913070310), (20773, 913070320))

target = 0
for quest, field_id in GROUNDS:
    if sm.hasQuest(quest):
        target = field_id
        break
if target:
    sm.warp(target, 0)
else:
    sm.chat("There's no training session for you in there right now. Talk to Training Instructor Kiku.")
sm.dispose()
