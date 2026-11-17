# 10244 - Betty's Spring Flower Research 3
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Betty (1032104)

sm.setSpeakerID(1032104)  # Betty
sm.sendNext("I want to have much more spring flowers bloom so I need flowers for my research. Do you have some Clovers for me?")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("Clovers mean 'luck'. I wish you have always good luck in Maple World. I will give you a #bClover Sticker#k!")
