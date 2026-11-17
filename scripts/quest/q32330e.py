# 32330 - In Search of a Piglet
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Utah (1013101)

sm.setSpeakerID(1013101)  # Utah
sm.sendNext("You got my piglet back! Thanks much.")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("Hey, little piglet, how many times must Evan bring his dragon home before you realize she's NOT going to eat you? Sigh.")
