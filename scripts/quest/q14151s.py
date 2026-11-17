# 14151 - [Attendance]  Daily Attendance Mission!
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Cassandra (9201256)

sm.setSpeakerID(9201256)  # Cassandra
if sm.sendAskAccept("Hello, #b#h0##k!\r\nThe #e#bSeptember Attendance Check Event#k#n is #kscheduled to start.\r\n #n \r\nDo you want to try out the Attendance Check right now? #e#rAre you ready?#k#n"):
    sm.startQuest(parentID)
    sm.sendNext("Okay, just take out #b#e200 monsters around your level#k#n, and you get an Attendance Stamp! Let's keep it to monsters that are #r10 levels below you or 20 levels above you#k.")
else:
    sm.sendNext("There'll be prizes!")
