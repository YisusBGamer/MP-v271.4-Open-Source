# 13789 - [9th Anniversary]  Daily Attendance!
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Cassandra (9201256)

sm.setSpeakerID(9201256)  # Cassandra
if sm.sendAskAccept("I want a clear record of the wonderful players celebrating MapleStory's 9th Anniversary, so I put together the e#bMaple 9th Anniversary Attendance Check Event#k#n. Are you ready to begin right now?"):
    sm.startQuest(parentID)
    sm.sendNext("Take out #b#e#o9101025# x200#k#n, and you'll get an Attendance Stamp! Let's keep it to monsters that are #r10 levels below you or 20 levels above you#k.")
else:
    sm.sendNext("There'll be prizes!")
