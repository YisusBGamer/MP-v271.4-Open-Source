# 28960 - [Presidential Election] Campaigner (Stew)
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Stew (9201240)

sm.setSpeakerID(9201240)  # Stew
sm.sendNext("Did you get more badges? Are you feeling extra contributy now?")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("Keep 'em coming!")
