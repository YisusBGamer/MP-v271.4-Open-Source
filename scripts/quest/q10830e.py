# 10830 - Gaga's Package
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Gaga (9000021)

sm.setSpeakerID(9000021)  # Gaga
sm.sendNext("Oh, you're the one Gold Richie sent. I've been waiting for you. This is extremely important to me! Thank you.")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("I'm in a rush, so I have to leave, but thank you so much!")
