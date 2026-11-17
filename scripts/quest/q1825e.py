# 1825 - [Training Course] System Correction
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: ESS (9075204)

sm.setSpeakerID(9075204)  # ESS
sm.sendNext("Did you eliminate all #o9306005# and #o9306100# monsters to fix the error in Link 5?")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("Link 5 error fixed and Correction program completed.")
