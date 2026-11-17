# 13630 - [Attendance] Check in to Win Season 1
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Cassandra (9201256)

sm.setSpeakerID(9201256)  # Cassandra
if sm.sendAskAccept("Hi there! I'm guessing you're here for the #e#bDaily Attendance Event#k!#n \r\nWill you challenge yourself to perfect attendance? #e#rAre you ready?#k#n"):
    sm.startQuest(parentID)
    sm.sendNext("Take out 300 #b#e#monsters around your level##k#n, and you get an Attendance Stamp! Let's keep it to monsters that are #r10 levels below you or 20 levels above you#k.")
else:
    sm.sendNext("It's nothing difficult! You can get #e#bEXP#k#n as well as the #e#rHOTTEST#k#n participation awards just by checking in every day.")
