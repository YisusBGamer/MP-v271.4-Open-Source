# 14151 - [Attendance]  Daily Attendance Mission!
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Cassandra (9201256)

sm.setSpeakerID(9201256)  # Cassandra
sm.sendNext("Okay, just take out #b#e200 monsters around your level#k#n, and you get an Attendance Stamp! Let's keep it to monsters that are #r10 levels below you or 20 levels above you#k.")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("Click on the #i3800423#Attendance Icon that appears on the left of the screen to open the Attendance Log. There, you can see the Attendance Check quests, get stamps, and collect prizes. \r\n#e[Attendance Event Schedule]\r\n#b> 9/1/2014 - 9/27/2014#k")
