# 31435 - Tonero and Romero
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Romero (3001005), taken from the other quest phase

sm.setSpeakerID(3001005)  # Romero
if sm.sendAskAccept("There's the transmitter, just like #p3001004# said. Looks like it's #b#p3001005##k."):
    sm.startQuest(parentID)
    sm.sendNext(u"\u201cHey, it's #p3001005#! I'm on my way. Wait for me so we can finish up.\u201d\r\n\r\n\r\nHe doesn't hold anything back, I guess. I might as well wait for #p3001005#.\r\n")
else:
    sm.sendNext("I've got a great idea. I'll ignore the call and make #b#p3001005##k wait. Heh.")
