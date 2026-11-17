# 5630 - [Attendance] December Fun!
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Cassandra (9201256)

sm.setSpeakerID(9201256)  # Cassandra
sm.sendNext("Between 12/4 and 1/31 a special gift to help you with your adventures this December will be given out starting today for total of 7 days so make sure to participate daily to receive yo")
if sm.sendAskAccept("#b#Gift List#!#k\r\n1st Day: One Permanent Pet Random Box + Meso Magnet + 1 REG Hair Coupon\r\n2nd Day: One Item Pouch (30 Day) + 1 REG Face Coupon \r\n3rd Day: 1 Item Radius Increase (30 Day) + Premium Style Random Box\r\n4th Day: 2x Drop Rate / 2x EXP Coupon 7 Day Coupon + Hyper Teleport Rock 7 Day Coupon \r\n5th Day: One Auto Fill All Cure Potion (30 Day) + One Perm Pet Equipment \r\n6th Day: One Perm Mount Random Box + 3 Monster Familiar Coupon \r\n7th Day: One auto HP fill (30 Day) + One auto MP fill (30 Day)"):
    sm.startQuest(parentID)
    sm.sendNext("You will be getting something special for your adventures in this December. Please make space in your inventory. \r\n#e[December Fun! Schedule]\r\n>2013/12/04 ~ 2014/01/31#k")
else:
    sm.sendNext("Too bad. I just wanted to help you enjoy Maplestory even more. Come talk to me again if you need help")
