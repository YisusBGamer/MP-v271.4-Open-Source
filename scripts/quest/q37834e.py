# 37834 - [Grand Athenaeum] Sharenian Knights
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Thales the Librarian (2500002)

sm.setSpeakerID(2500002)  # Thales the Librarian
sm.sendNext("What did you think of #e<Episode VI: Sharenian Knights>#n?")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("There are many stories the people of this world have yet to hear.\r\nNow, would you like to try any other books? I'll give you a gift if you read them all!")
