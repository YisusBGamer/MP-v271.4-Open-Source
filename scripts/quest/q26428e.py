# 26428 - [Maple Rewards] 
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Matilda (9000018)

sm.setSpeakerID(9000018)  # Matilda
sm.sendNext("Did the Reward Points Bag open up to you?\r\nI wonder how many #i2431872##b#t2431872##k goodies are inside this time!")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("You've earned #b#i2431872# #t2431872# x300!#k \r\n\r\nHow much mileage is that? Looks like too much to count....\r\n\r\nWHAT?! 300?! You really got 300?")
    sm.sendSay("You and the #i3994853##b#t3994853##k are like best friends now. \r\nHow strange...the bag is usually very fickle about people. \r\n\r\nAnyway, I'm glad that you and the Reward Points Bag are getting along. You've got one more shot today.")
