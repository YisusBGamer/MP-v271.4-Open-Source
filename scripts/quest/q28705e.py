# 28705 - Inkwell's One-Sided Love
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Gaga (9000021)

sm.setSpeakerID(9000021)  # Gaga
sm.sendNext("Do you have the Heart Chocolate yet? Love like mine can't wait forever!\r\n")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("This is great, but to make the gift as big as I want, I'm going to need more chocolate. Bring me as much as you can, all right?\r\n")
