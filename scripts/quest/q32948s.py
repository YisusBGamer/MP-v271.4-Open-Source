# 32948 - [Daily] The Black Mushroom Gang
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Scholarly 'Shroom (9000292)

sm.setSpeakerID(9000292)  # Scholarly 'Shroom
if sm.sendAskAccept("Listen to me. Those Black Mushrooms we met last time... They keep interfering with my research. Could you go set them straight?"):
    sm.startQuest(parentID)
    sm.sendNext("I'll send you to where the Black Mushrooms are. Eliminate #b200 of them#k for me.")
else:
    sm.sendNext("O-okay. If you're really so busy... *Whimper*")
