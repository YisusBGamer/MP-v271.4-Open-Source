# 28951 - [Presidential Election] Campaigner (Cole)
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Cole (9201239)

sm.setSpeakerID(9201239)  # Cole
sm.sendNext("Did you get more badges? Are you feeling extra contributy now?")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("Splendid! Wear these proud and loud my little friend.")
