# 32665 - [Grand Athenaeum] Read The Black Witch
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Thales the Librarian (2500002)

sm.setSpeakerID(2500002)  # Thales the Librarian
sm.sendNext("How was #e<Episode III : The Black Witch>#n?")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("How was the story? Now remember, we all live in our own stories and it's being written as we go.")
