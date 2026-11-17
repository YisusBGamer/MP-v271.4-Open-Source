# 10247 - Lisa's Spring Flower Research 3
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Lisa (2012012)

sm.setSpeakerID(2012012)  # Lisa
sm.sendNext("Spring flowers make me feel warm and fragrant. Do you have some Clovers for me?")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("Clovers mean 'luck'. I wish you have always good luck in Maple World. I will give you a #bClover Sticker#k!")
