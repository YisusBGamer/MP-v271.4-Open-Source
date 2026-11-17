# 10283 - Portrait of a Popular Monster: Octopus
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Cassandra (9201256)

sm.setSpeakerID(9201256)  # Cassandra
if sm.sendAskAccept("I'm really worried. You see, I wanted to thank everyone who helped in celebrating MapleStory's 5th Anniversary, but I'm short-handed right now. Do you think you can help?"):
    sm.startQuest(parentID)
    sm.sendNext("Okay, #b#h0##k. All you have to do is to bring me a #t04220149:#. In order to draw the portrait, you have to collect an #t04032303:# and use it on a blank piece of drawing paper.\r\n")
else:
    sm.sendNext("Fine, suit yourself. You're going to regret not helping the beautiful #b#p9010010##k.")
