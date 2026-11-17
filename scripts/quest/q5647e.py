# 5647 - Supplies for T-1337 Step 1
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: T-1337 (9201101)

sm.setSpeakerID(9201101)  # T-1337
sm.sendNext("Thank you for your assistance, new human friend. This completes my needs at this moment - take this item as a reward./rYour support may be needed again after 24 hours have elapsed.")
sm.tryCompleteQuest(parentID)
