# 57108 - Adaptation 3
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Mouri Motonari (9130008)

sm.setSpeakerID(9130008)  # Mouri Motonari
if sm.sendAskAccept("I was hoping you would have higher standards for yourself. Weren't you named the Bladed Falcon? Such a moniker is undeserved, from what I have seen. Nonetheless, let us continue."):
    sm.startQuest(parentID)
    sm.sendNext("Take down 30 of the Conjureyes stationed in Training Dojo 3. They will be much stronger. Perhaps you can join them, if you prove yourself!")
