# 14586 - [Attendance] Always There Challenge!
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Cassandra (9201256)

sm.setSpeakerID(9201256)  # Cassandra
if sm.sendAskAccept("Hello, #b#h0#!#k Are you ready for a new season of events? Defeat #b300 #e#o9101025##k#n every day and earn #b#e30 Star Points#k#n on Star Planet to complete the attendance check! We've got really awesome rewards for you!"):
    sm.startQuest(parentID)
    sm.sendNext("Hunt #b#e300 #o9101025##k monsters, earn #b#e30 Star Points#k#n in Star Planet, and open your Attendance Log! There will be awesome gifts waiting for you. What is #e#o9101025#, anyway? It's any #nmonster between #r10 levels below and 20 levels above#k your own.#n")
else:
    sm.sendNext("Lot's of awesome gifts are available. You're really not interested? Let me know if you change your mind!")
