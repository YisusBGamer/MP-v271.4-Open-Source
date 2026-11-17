# 12886 - Fighting Heat with Heat
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Arturo (9000114)

sm.setSpeakerID(9000114)  # Arturo
sm.sendNext("People are too spoiled! It's summer. It's SUPPOSED to be hot! Why are all my customers asking for medicine to cool them off?!")
if sm.sendAskAccept("Don't they know the best way to beat the heat is to...eliminate monsters?! Try it and you'll see. "):
    sm.startQuest(parentID)
    sm.sendNext("Eliminate #e#b200 monsters#k#n and you won't even notice the heat. It'll only work if you battle monsters that are between 11 levels below and 21 levels above you! ")
else:
    sm.sendNext("You're just like the rest of my customers. You disappoint me.")
