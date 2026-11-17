# 51199 - Melted Chocolate Exchange
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Maple Administrator (9010000)

sm.setSpeakerID(9010000)  # Maple Administrator
sm.sendNext("Would you like to exchange 9 #b#t3994199#s#k for a random chocolate?")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("Well done.\r\nYou have received a random chocolate.\r\nFeel free to bring me more Melted Chocolates.")
