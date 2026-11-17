# 59503 - [Spring Valley] Chase Them Away - Butterflies
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Navy Tail (9400003)

sm.setSpeakerID(9400003)  # Navy Tail
sm.sendNext("Oh, you did a wonderful job!")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("This idyllic spot will feel so lonely without those butterflies, but better they live their lives elsewhere than end up on some grubby collector's wall.")
