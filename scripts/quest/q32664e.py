# 32664 - [Grand Athenaeum] Read Empress In Training
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Thales the Librarian (2500002)

sm.setSpeakerID(2500002)  # Thales the Librarian
sm.sendNext("How was #e<Episode II: Empress In Training>#n?\r\nNot everyone lives like the protagonist in novels. But surely there are always great assistants next to them.")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("Now, do you want to try any other books? I'll give you a gift for reading everything!")
