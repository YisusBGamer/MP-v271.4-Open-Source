# 14263 - [Attendance] Present!
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Cassandra (9201256)

sm.setSpeakerID(9201256)  # Cassandra
sm.sendNext("All you have to earn your Attendance Stamp is... take out #b#e200 monsters around your level#k#n. Shocking, right? Keep it to monsters that are #r10 levels below you or 20 levels above you#k.")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("Click the #i3800423#Attendance Icon at the left side of the screen to open the Attendance Board, where you can see your attendance progress and collect stamps. \r\n#e[Attendance Event Schedule]\r\n#b> TKTKTK - TKTKTK#k")
