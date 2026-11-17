# 57107 - Adaptation 2
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Mouri Motonari (9130008)

sm.setSpeakerID(9130008)  # Mouri Motonari
if sm.sendAskAccept("I don't care how tired you are right now. Move on to the next lesson."):
    sm.startQuest(parentID)
    sm.sendNext("Go to the next Training Dojo and battle the stronger familiars, Onieyes. And do try to look presentable while you do it.")
