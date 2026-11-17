# 20420 - A Great Honor
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Cygnus (1101000)

sm.setSpeakerID(1101000)  # Cygnus
if sm.sendAskAccept("Knight of light, protector of virtue, your presence has been an inspiration for our people. I only hope I do not place too much weight on your shoulders, for I have another favor I must ask. \r\nP-please. Take on the training of the Noblesse who wish to follow you as Dawn Warriors. You're the only one who can!"):
    sm.startQuest(parentID)
else:
    sm.sendNext("I knew you would accept. But you seem troubled... is there something I can help with?")
