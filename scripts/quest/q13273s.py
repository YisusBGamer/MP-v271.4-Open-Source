# 13273 - [PC?] 2?? PC? ?? ??
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Matilda (9000018)

sm.setSpeakerID(9000018)  # Matilda
if sm.sendAskAccept("Thanks for participating in the PC Cafe participation check!\r\nYou can get the #bday 2#k gift right now. Do you want it now?"):
    sm.startQuest(parentID)
else:
    sm.sendNext("I am a tiny human in comparison to your great graciousness. I will literally fall over dead if you do not accept this gift! Please tell me when you are ready to take it!")
