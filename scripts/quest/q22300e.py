# 22300 - Hero's Succession
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Mir (1013000)

sm.setSpeakerID(1013000)  # Mir
sm.sendNext("The Dragon Master's strength clearly shows the relationship between Humans and Onyx Dragons.")
sm.sendSay("When the relationship between the two are not in harmony, the Onyx Dragon is only at half power. But when the two are in perfect harmony, the Onyx Dragon's power explodes.")
sm.sendSay("You are both young, yet you have great powers. Freud and I were not as strong at that age... That is because the two of you are bonded so tightly.")
sm.sendSay("Onyx Dragons are a race drawn to strong spirits. Just looking at you two, it is obvious how strong your spirits must be... There is no doubt that you are qualified to receive this skill...")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("#bHero's Echo#k... This is a skill that was used by Freud, my former master. I trust that you will use the skill well... Please take good care of Maple World...and of #p1013000#.")
