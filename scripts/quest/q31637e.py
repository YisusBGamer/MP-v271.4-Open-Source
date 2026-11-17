# 31637 - Gold Gone Astray
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Romero (3001031)

sm.setSpeakerID(3001031)  # Romero
sm.sendNext("p3001005#, I heard you bought some bad stock. I was thinking I could take it off your hands...")
if sm.tryCompleteQuest(parentID):
    sm.sendNext(u"My monthly sales are going to be at the top of the charts!#b(I\u2019ve never seen #p3001005# so happy! Better get this junk back to #p3001006#.)#k")
