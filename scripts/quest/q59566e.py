# 59566 - [Returning Player] We Meet Again! (Attendance Check)
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Maple Administrator (9201257)

if sm.tryCompleteQuest(parentID):
    sm.setSpeakerID(9201257)  # Maple Administrator
    sm.sendNext("The Attendance Log will open when you click on the #i3800423# Attendance Log icon on the left side of your screen!")
