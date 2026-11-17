# 25597 - The Third Augury
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Vieren (1032209)

sm.setSpeakerID(1032209)  # Vieren
if sm.sendNext("That didn't take you nearly as long as I expected.\r\n#L0##bFortunately, the warrior who had this had a strong sense of family values.#l\r\n#k") != 0:
    sm.dispose()
sm.sendSay("I suppose he'd have to. Our old teacher left the Augury with them for a reason.")
sm.tryCompleteQuest(parentID)
