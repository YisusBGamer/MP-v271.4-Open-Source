# 57148 - Mu Lung Mojo
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Do Gong (2091001)

sm.setSpeakerID(2091001)  # Do Gong
sm.sendNext("Did you find what you were looking for?")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("So they planned to use Mu Lung's energy to control the monsters. But why?")
