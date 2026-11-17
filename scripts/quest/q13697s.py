# 13697 - [Super Weekend] Teleport Rock
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Maple Administrator (9201257)

sm.setSpeakerID(9201257)  # Maple Administrator
if sm.sendAskAccept("#b#h0##k, this weekend is so cool. I've got a #e#b#t2431312##k#n with YOUR name on it. This thing will zap you to any town on any continent all ZIP ZAP POW! Ya want it?\r\n#i2431571# #t2431571# x1"):
    sm.startQuest(parentID)
else:
    sm.sendNext("Are you crazy? What do you have against teleporting?! I'm going to just keep yours here for the rest of the weekend in case you change your mind.")
