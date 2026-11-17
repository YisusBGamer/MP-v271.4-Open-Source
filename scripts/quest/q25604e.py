# 25604 - The Fourth Augury
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Vieren (1032209)

sm.setSpeakerID(1032209)  # Vieren
if sm.sendNext("You were gone a long time. Couldn't you find Kriel?\r\n#L0##bFinding her was no difficulty. Her requests, on the other hand...#l\r\n#k") != 0:
    sm.dispose()
sm.sendSay("Either way, you got the Augury. Good job.")
sm.tryCompleteQuest(parentID)
