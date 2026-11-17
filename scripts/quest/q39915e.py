# 39915 - [Cernium (After)] Retreat Is Not an Option
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Nova Soldier (3004513)

sm.setSpeakerID(3004513)  # Nova Soldier
sm.sendNext("Support the #rEastern Rampart#k for now. ")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("Status report!")
