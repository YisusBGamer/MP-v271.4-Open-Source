# 1838 - [Selectable Program] Shadow Monster
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: ESS (9075208)

sm.setSpeakerID(9075208)  # ESS
sm.sendNext("Did you defeat the shadow monster?")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("You completed the selectable program.")
