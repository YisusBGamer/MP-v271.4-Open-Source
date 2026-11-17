# 10253 - Keeny's Spring Flower Research 3
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Keeny (2111005)

sm.setSpeakerID(2111005)  # Keeny
sm.sendNext("Oh...? Are these Clovers? Did you hear that Keeny is researching spring flowers?")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("Finally, you brought me some Clovers! I am glad that I can do my best on the research for spring flowers! Thank you so much!")
    sm.sendSay("Clovers mean 'luck'. I wish you have always good luck in Maple World. I will give you a #bClover Sticker#k!")
