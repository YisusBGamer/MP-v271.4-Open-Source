# 32638 - Omen Piece
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Mars (2510010)

sm.setSpeakerID(2510010)  # Mars
sm.sendNext("What? You're looking for the master? I told you, don't go bother him unless it's very important! VERY important!\r\n")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("Ahem... Please be more careful next time. The master is on the roof of the temple.\r\n#b(Jeez, how grumpy... Go to the rooftop.)#k")
