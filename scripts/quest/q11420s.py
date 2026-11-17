# 11420 - The Legendary Fruit Juice Master!
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Gaga (9000021)

sm.setSpeakerID(9000021)  # Gaga
if sm.sendAskAccept("Hello, #b#h0##k. The #rLegends Awards#k has hit Maple World in a big way, and everyone is going crazy for Legendary stuff. I was thinking of how I could get in on the fun, and then it hit me! My mother's uncle's step-brother's babysitter's grandfather was a certified fruit juice master, and he passed his\r\n\r\n#i04162006# #rSecret Legendary Fruit Juice Recipe#k down to me. With this recipe, we can become legendary fruit juice masters! Will you join me, #b#h0##k, on my journey into legend?"):
    sm.startQuest(parentID)
else:
    sm.sendNext("Really? Perhaps you don't understand how honored and respected fruit juice masters are...")
