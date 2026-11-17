# 25591 - The First Augury
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Vieren (1032209)

sm.setSpeakerID(1032209)  # Vieren
if sm.sendNext("Luminous! How'd it go? Did you find the Augury?\r\n#L0##bIndeed. It was hidden in the royal seal.#l\r\n#k") != 0:
    sm.dispose()
sm.sendSay("In the royal seal, eh? What a crafty hiding place.")
sm.tryCompleteQuest(parentID)
