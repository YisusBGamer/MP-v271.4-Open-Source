# 25593 - The Second Augury
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Vieren (1032209)

sm.setSpeakerID(1032209)  # Vieren
if sm.sendNext("What happened?\r\n#L0##bThe Augury's Light twisted to become a force for evil.#l\r\n#k") != 0:
    sm.dispose()
sm.sendSay("A darn shame, that. If Aurora weren't a ghost town, we could've stopped it from happening. Good thing you were there to put an end to it.")
sm.tryCompleteQuest(parentID)
