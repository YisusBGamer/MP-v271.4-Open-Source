# 11274 - Indomitable Ice Knight
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Rosette (9070005)

sm.setSpeakerID(9070005)  # Rosette
sm.sendNext("Wow? Already? You completed the mission, Achieve 1 victory with the Ice Knight in Ice Knight Mode. How are you feeling? It wasn't that hard, right? Alright then, I will give you a gift as I promised.")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("There are other missions as well, so challenge them now!")
