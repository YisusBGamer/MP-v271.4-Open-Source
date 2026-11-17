# 11873 - [Homecoming] Maple Sports Celebration
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Cassandra (9201256)

sm.setSpeakerID(9201256)  # Cassandra
if sm.sendAskAccept("Hello #h0##k! I hope you're ready to sweat! The Maple Homecoming is running from #b09/25/12 to 10/24/12#k. You can earn a #rHomecoming Coins#k just for logging in each day. Do you want your coin now?"):
    sm.startQuest(parentID)
else:
    sm.sendNext("I only give this out once a day, so get it before today's over!")
