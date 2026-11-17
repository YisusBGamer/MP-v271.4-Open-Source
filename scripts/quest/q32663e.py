# 32663 - [Grand Athenaeum] Read The White Mage
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Thales the Librarian (2500002)

sm.setSpeakerID(2500002)  # Thales the Librarian
sm.sendNext("How was #e<Episode I: The White Mage>#n? It's pretty fun getting into the shoes of someone else who lived in Maple World. Now come back to reality.")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("Now, do you want to try any other books? I'll give you a gift for reading everything!")
