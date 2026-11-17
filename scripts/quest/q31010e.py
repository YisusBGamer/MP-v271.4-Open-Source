# 31010 - Know Your Enemy
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Michael (2170000)

sm.setSpeakerID(2170000)  # Michael
sm.sendNext("We should probably know something about the things we're going to beat up.")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("Never punched a mammoth before. Well, I'll try anything once!")
