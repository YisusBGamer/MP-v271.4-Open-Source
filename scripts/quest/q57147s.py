# 57147 - Forest Happenings
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Tae Soo (2091003)

sm.setSpeakerID(2091003)  # Tae Soo
sm.sendNext("Perhaps they were just envious of the peachy aroma wafting through the air. Not everyone can whip up a killer harvest like this, you know.")
if sm.sendAskAccept("Why not ask Do Gong in Mu Lung Temple? He keeps an eye on everything in Mu Lung."):
    sm.startQuest(parentID)
    sm.sendNext("Mu Lung Temple is to the right of Mu Lung. Do Gong is the wisest of the masters. I'd be surprised if he doesn't have something worthwhile to say.")
else:
    sm.sendNext("You're better off asking Do Gong for more info. I'm just a simple peach man.")
