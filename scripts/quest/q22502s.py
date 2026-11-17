# 22502 - A Bite of Hay
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Gustav (1013103)

sm.setSpeakerID(1013103)  # Gustav
if sm.sendAskAccept("Wouldn't a lizard enjoy a #b#t4032452##k, like a cow? There are a lot of #bHaystacks#k nearby, so try feeding it that."):
    sm.startQuest(parentID)
else:
    sm.sendNext("Hmm... Maybe that is a bit strange. Let me think about it again. ")
