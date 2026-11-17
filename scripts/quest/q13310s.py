# 13310 - Lucky Lucky Monstory
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Lucky Lucky Monstory (9000156)

sm.setSpeakerID(9000156)  # Lucky Lucky Monstory
sm.sendNext("answer = self.askAccept(\"Are you here to get into some Lucky Lucky Monstory?\");")
sm.startQuest(parentID)
