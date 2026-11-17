# 1730 - Nestessary
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Spiegelmann (9074300)

sm.setSpeakerID(9074300)  # Spiegelmann
sm.sendNext("Did you bring them? I need the feathers!")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("This'll make a GREAT pillow for me to lay on while I watch the egg...")
