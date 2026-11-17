# 14635 - [Attendance] Always There Challenge!
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Cassandra (9201256)

sm.setSpeakerID(9201256)  # Cassandra
if sm.sendAskAccept("Defeat #b200 #e#o9101025##k#n monsters every day to complete the attendance! Really awesome rewards are ready to go! You want to give it a try?"):
    sm.startQuest(parentID)
    sm.sendNext("Hunt #b#e200 #o9101025##k monsters and open your Attendance Log! There will be awesome gifts waiting for you. What is #e#o9101025#, anyway? It's any #nmonster between #r10 levels below and 20 levels above#k your own.#n")
else:
    sm.sendNext("Lot's of awesome gifts are available. You're really not interested? Let me know if you change your mind!")
