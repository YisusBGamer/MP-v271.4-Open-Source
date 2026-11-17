# 11447 - [Legends] The Legendary Hero
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Cassandra (9201256)

sm.setSpeakerID(9201256)  # Cassandra
sm.sendNext("HI, #h0#! Good job reaching Level 50. Wow, you look so strong! Here, let me make you look a little stronger.")
if sm.sendAskAccept("Tada! It's a #i1112668# #t1112668#, made especially for you. You can't trade it, but #ryou can share it with other characters under same account using the Storage Room#k. Also, if you're wearing it when you level up, #rthe ring will level up with you#k and its abilities will get better. Isn't that neat?"):
    sm.startQuest(parentID)
    sm.sendNext("Fine. This gift is available only until #b1/31/2012#k, so come back soon if you change your mind.")
else:
    sm.sendNext("What? You don't want it? Why?")
