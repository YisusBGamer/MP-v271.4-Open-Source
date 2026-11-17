# 6700 - The Bowman's Road
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Athena Pierce (1012100)

sm.setSpeakerID(1012100)  # Athena Pierce
sm.sendNext("These are for you. Choose anything you need.")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("Please be a excellent bowman. Take care.")
