# 2251 - Zombie Mushroom Signal 3
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: The Rememberer (1061011)

sm.setSpeakerID(1061011)  # The Rememberer
sm.sendNext("Ah, this is the sound! Just hold on a second!")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("#b#e'Mushking of the Mushroom Castle is in danger. Watch out for King Pepe! Request help from the courageous heroes of Maple World who are at least Level 30.' #n#k\r\n\r\nMushroom Castle? I've never heard of the place...")
