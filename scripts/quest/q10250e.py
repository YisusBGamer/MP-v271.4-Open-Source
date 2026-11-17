# 10250 - The Rememberer's Spring Flower Research 3
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: The Rememberer (1061011)

sm.setSpeakerID(1061011)  # The Rememberer
sm.sendNext("I have a lot of work to do... so I can't go find spring flowers now...  Oh! Did you bring some Clovers for me?")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("Thank you for bringing me Clovers! I couldn't collect them by myself because I have a lot of work to do... anyway thank you so much!")
    sm.sendSay("Clovers mean 'luck'. I wish you have always good luck in Maple World. I will give you a #bClover Sticker#k!")
