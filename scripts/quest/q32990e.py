# 32990 - [Grand Athenaeum] Episode V: Shadow Alchemist
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Thales the Librarian (2500002)

sm.setSpeakerID(2500002)  # Thales the Librarian
sm.sendNext("How was #eEpisode V: Shadow Alchemist#n?")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("Many stories have not been heard by the people of this world.\r\nNow, do you want to try any other books? I'll give you a gift for reading everything!")
