# 1535 - [Battle Mode] Ereve in Danger
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Neinheart (1101002)

sm.setSpeakerID(1101002)  # Neinheart
if sm.sendAskAccept("Surprised to hear from me? Don't be. Surprise is a weakness. I have heard that you received an Elite Guardian Training Permit from Haktorius. Come to Ereve so that I may give you orders."):
    sm.startQuest(parentID)
    sm.sendNext("I sent you a Teleport Scroll. Use it.")
else:
    sm.sendNext("When I say be here, I expect you to be here.")
