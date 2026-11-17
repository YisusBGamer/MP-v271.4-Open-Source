# 59134 - Tristan's Sword
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Mudry (9390361)

sm.setSpeakerID(9390361)  # Mudry
if sm.sendAskAccept("Hurry on in. I'm heading back to Sleepywood. It's too dark in these caves."):
    sm.startQuest(parentID)
    sm.sendNext("Come back to Sleepywood when you get #bTristan's Sword#k.")
else:
    sm.sendNext("It's right in front of you...")
