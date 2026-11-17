# 39058 - [Daily Quest] Defeat 200 Seeker T-Drone Model B's
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Rona (3003104)

sm.setSpeakerID(3003104)  # Rona
sm.sendNext("I see you defeated all 200 Seeker T-Drone Model B's. Here, I'll give you 1 #i1712001:# #t1712001:#.")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("#b(You gave Rona the information you obtained from observing the Seeker T-Drone Model B's up close.)#k")
    sm.sendSay("Thank you, #h0#. We are a step closer to revealing this place for what it is, thanks to you.")
