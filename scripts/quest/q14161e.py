# 14161 - [Golden Goose Egg] Catch the Goose Egg Thief!
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Frank Burton (9000206)

sm.setSpeakerID(9000206)  # Frank Burton
sm.sendNext("Did you get them all? Geez, I hope none of them cracked. Goodness knows monsters aren't the most nurturing types.")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("The golden goose eggs are valuable, but these regular ones are valuable, too. After all they're both alive. You really saved some lives today.")
