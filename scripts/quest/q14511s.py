# 14511 - [Attendance] Always There Challenge!
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Cassandra (9201256)

sm.setSpeakerID(9201256)  # Cassandra
if sm.sendAskAccept("Hi, #b#h0##k! What does the winter season mean? Of course, events! Want to participate in this Attendance Check event?"):
    sm.startQuest(parentID)
    sm.sendNext("Hunt #b#e300 monsters around your level#k#n and open your Attendance Log! You'll see your stamp already there!\r\nLet's keep it to monsters that are #r10 Levels below you or 20 Levels above you#k. ")
else:
    sm.sendNext("Getting Attendance Stamps is now easier than ever! You won't be disappointed with the gifts either!")
