# 25561 - The Power Is Yours
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Maple Administrator (9010000), taken from the other quest phase

sm.setSpeakerID(9010000)  # Maple Administrator
if sm.sendAskAccept("This Dark power... I shall sharpen it into a deadly weapon to use against my enemies!"):
    sm.startQuest(parentID)
    sm.sendNext("I'll begin by testing my new powers against #b10 Bubblings#k.")
else:
    sm.sendNext("No... No! I must resist the temptation!")
