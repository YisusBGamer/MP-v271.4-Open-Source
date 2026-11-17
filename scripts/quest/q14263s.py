# 14263 - [Attendance] Present!
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Cassandra (9201256)

sm.setSpeakerID(9201256)  # Cassandra
if sm.sendAskAccept("Guess what, #b#h0##k! It's time for another #e#bAttendance Event#k#n! Surprised? What rock have you been living under? Anyway, you ready to participate?"):
    sm.startQuest(parentID)
    sm.sendNext("All you have to earn your Attendance Stamp is... take out #b#e200 monsters around your level#k#n. Shocking, right? Keep it to monsters that are #r10 levels below you or 20 levels above you#k.")
else:
    sm.sendNext("But it's so easy! Come back to me when you're ready to earn some easy prizes.")
