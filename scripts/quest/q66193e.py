# 66193 - [Goo Island] A Favor for Mayor McGoo
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Mayor McGoo (9401406)

if sm.tryCompleteQuest(parentID):
    sm.setSpeakerID(9401406)  # Mayor McGoo
    sm.sendNext("You've collected all the Goo-Pons! Hurry and claim your reward!")
