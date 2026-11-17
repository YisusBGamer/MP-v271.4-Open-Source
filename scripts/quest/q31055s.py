# 31055 - [Hilla] The Mystery Potion
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Mystery Potion (2184008)

sm.setPlayerAsSpeaker()
if sm.sendAskAccept("This potion from the soldiers... It's marked with #bancient Azwan writings#k. I should head back to Medina with this. "):
    sm.startQuest(parentID)
else:
    sm.sendNext("Maybe I should take another look around.")
