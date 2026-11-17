# 34603 - Seeking Treatment
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Gen (3001202), taken from the other quest phase

sm.setSpeakerID(3001202)  # Gen
sm.sendNext("#b(Ugh... This feeling of guilt. Who has time for distractions like that?)#k")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("#b(The boss said he'd teach you a useful skill. You can probably find him back at the hideout.)#k")
