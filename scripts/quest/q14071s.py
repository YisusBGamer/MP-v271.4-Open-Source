# 14071 - [Attendance] Winter Attendance - Season 1
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Cassandra (9201256)

sm.setSpeakerID(9201256)  # Cassandra
if sm.sendAskAccept("You know you want to participate in the first season of the #e#bWinter Attendance Check#k#n, amirite?"):
    sm.startQuest(parentID)
    sm.sendNext("So, like, fans blow cold air on you in the summer, so the opposite should happen if you use them with heaters in the winter, right? Trust me, my crystal ball is never wrong. Just go get me #b30 #e#t3994854#s#k#n items so I can warm up my apartment. Remember to fight monsters near your level to get them!")
