# 26461 - [Style Festival] King of the Festival
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Big Headger (9000172)

sm.setSpeakerID(9000172)  # Big Headger
sm.sendNext("Ready to be royal? \r\nDid you bring the Fairy set?\r\n#bHat: #i1003955##t1003955#\r\nOutfit:#i1050299##t1050299#,#i1051366##t1051366#\r\nShoes: #i1072860##t1072860#\r\nShoes: #i1082555##t1082555#\r\nWeapon: #i1702456##t1702456##k")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("Whoa... Amazing! Your royal style exceeds that of a king! \r\nThis is it! You're truly the king of the festival!\r\nThis #i3010680:##b#t3010680:##k I prepared... it's all yours, #b#h0##k.\r\n\r\nPlease accept this, your highness!\r\nAnd do enjoy the rest of the festival.\r\n\r\n\r\n")
