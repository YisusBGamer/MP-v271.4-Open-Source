# 13350 - [Unleashed Coin] Cold Hearted Mapler!
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Cassandra (9201256)

sm.setSpeakerID(9201256)  # Cassandra
sm.sendNext("Is the popsicle ready? I think my crystal ball is mad at me, and this'll help thaw it out. Get it?")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("(You hand the #i3994686##b#t3994686##k to Cassandra and she smushes it into her crystal ball.)\r\n\r\nMmm, isn't that yummy, my lovely little crystal? You like that, don't you? Don't you?")
    sm.sendSay("Oh! #b#h0##k, you're still here? Hehe, take this little gift and #rcome back tomorrow#k. My crystal ball and I would like some time alone. ")
