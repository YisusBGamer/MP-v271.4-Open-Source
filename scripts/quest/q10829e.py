# 10829 - Cassandra's Package
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Cassandra (9201256)

sm.setSpeakerID(9201256)  # Cassandra
sm.sendNext("A package for me? Ahh, the item I requested from Gold Richie must have finally arrived. Thank you for delivering it to me... Now I'll be able to help the Resistance in Edelstein.")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("Thank you. Why don't you take a break before you leave?")
