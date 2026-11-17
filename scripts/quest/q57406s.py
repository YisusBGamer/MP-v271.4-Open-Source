# 57406 - Adaptation 3
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Mouri Motonari (9130008)

sm.setSpeakerID(9130008)  # Mouri Motonari
if sm.sendAskAccept("What I mean to say is that you've improved. But not enough! Someone who trained with the great Spirit Walker, Haruaki, should have higher standards. Continue your training until your body is perfect like Master Kenshin's. Oh, that's not what I meant... Just get stronger."):
    sm.startQuest(parentID)
    sm.sendNext("Take down 30 of the Conjureyes stationed in Training Dojo 3. They will be much stronger. Perhaps you can join them, if you prove yourself!")
