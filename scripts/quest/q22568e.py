# 22568 - Grow Faster!
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Mir (1013000)

sm.setSpeakerID(1013000)  # Mir
sm.sendNext("An item covered in mud appeared from the #o2230114#. I better clean it.")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("If consumed by an animal, it will have unimaginable side effects. It should only be used on plants.")
