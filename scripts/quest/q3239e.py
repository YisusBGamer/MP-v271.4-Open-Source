# 3239 - The Missing Mechanical Parts
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Manager Karl (2040015)

sm.setSpeakerID(2040015)  # Manager Karl
sm.sendNext("Oh! You brought 10 #b#t4031092##k items just in time. Here, take this for your trouble. I need to get right on putting these back where they belong.")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("The factory can run smoothly again thanks to you. Thanks.")
