# 32955 - [Slime] Bobbly Bit
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Scholarly 'Shroom (9000292)

sm.setSpeakerID(9000292)  # Scholarly 'Shroom
sm.sendNext("None of it is really that complicated. This isn't a field of reasoning or wits, but a forbidden one of daring and bravery.")
sm.sendSay("Slimes seem to disappear when they get eliminated, but they actually reform after a few minutes. It has been established that they are not edible for that very reason.")
sm.sendSay("What part of them would you eat? They don't have hands or feet, just their body... But have you noticed their antenna? They tend to come off when you hunt Slimes, right?")
if sm.sendAskAccept("So I'd like you to hunt Slimes and get me 100 Slime Bits. Then I can begin my research on them...\r\n#r<< You will be moved to a battle map when you accept. >>"):
    sm.startQuest(parentID)
    sm.sendNext("Good, I'll send you to where the Slimes are.")
else:
    sm.sendNext("Boo, I was looking forward to discovering a new taste!")
