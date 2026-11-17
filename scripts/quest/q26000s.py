# 26000 - Deia's G-Potion
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Deia (2010010)

sm.setSpeakerID(2010010)  # Deia
if sm.sendAskAccept("The Guild supplies have arrived. \r\nHere, take them. Goodness, complaints every time...\r\nDon't complain about the amount. If your Guild Level goes up, you will receive more."):
    sm.startQuest(parentID)
    sm.sendNext("Then good luck this week.\r\nSupplies will arrive again next Monday, so see you then.")
else:
    sm.sendNext("I don't even get any thanks... If you need more, wait for Monday.")
