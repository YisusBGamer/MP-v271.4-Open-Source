# 10500 - A Sign of the Dragon Master's Return
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Cassandra (9201256)

sm.setSpeakerID(9201256)  # Cassandra
if sm.sendAskAccept("Have you heard about the #bDragon Master#k? No? Hehe, that's what I figured. If you're curious about #bDragon Master Evan#k, come talk to me."):
    sm.startQuest(parentID)
    sm.sendNext("You do know where I am right? I can be found in any of the major towns in Maple World.")
else:
    sm.sendNext("I called you so that I could give you info about the #bDragon Master#k and give you a gift as a bonus but you don't want it? You're going to regret it...")
