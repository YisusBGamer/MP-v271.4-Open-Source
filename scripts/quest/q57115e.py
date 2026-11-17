# 57115 - Samurai Skirmish 1
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Mouri Motonari (9130008)

sm.setSpeakerID(9130008)  # Mouri Motonari
sm.sendNext("How goes the attack? Do not tarry.")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("The enemy will only grow stronger. We cannot afford for any one man to under-perform.")
