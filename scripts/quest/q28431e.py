# 28431 - Lucky Gift 
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Maple Administrator (9201257)

sm.setSpeakerID(9201257)  # Maple Administrator
sm.sendNext("Oh, you're here! I've been waiting for you. Now, let me give you your gift!")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("Do you like your gift? Please continue to enjoy your adventures in Maple World!")
