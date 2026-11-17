# 13550 - [New Years]Get the Glowing Ghost
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Cassandra (9201256)

sm.setSpeakerID(9201256)  # Cassandra
sm.sendNext("Yay, my shoes!\r\nDid that nasty ghost bail? I should've smacked it myself...")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("Thank you! That stupid Glowing Ghost dropped its bundle in front of my home. I don't need it, so you can have it. My shoes better still be shining...")
