# 14540 - [Event] Aurora Pioneers
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Cassandra (9201256)

sm.setSpeakerID(9201256)  # Cassandra
if sm.sendAskAccept("Hello, #b#h0##k. You have the look of a true hero. Heh, I bet you hear that all the time. Well, I'm not just saying that. If you, #b#h0##k, can reach #eLv. 140#n during the event period, I will give you a fantastic gift. Are you up to the challenge?"):
    sm.startQuest(parentID)
    sm.sendNext("I will be watching you all the way to #r#b140#n#k, then.")
else:
    sm.sendNext("Come see me again if you change your mind.")
