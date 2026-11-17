# 57413 - Commanding Aura
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Mouri Motonari (9130008)

sm.setSpeakerID(9130008)  # Mouri Motonari
sm.sendNext("Kanna, our base has been exposed to the enemy. It seems your paltry efforts weren't enough.")
sm.sendSay("Nobunaga's full army has yet to arrive, but he has plenty of strong soldiers stationed just outside Momijigaoka . Once his main force arrives, he will sweep over Momijigaoka like a tempest.")
if sm.sendAskAccept("We will not wait for the enemy to attack. We will deal the first strike and turn the tides of battle before Nobunaga has the chance. This is your task, Kanna."):
    sm.startQuest(parentID)
    sm.sendNext("Good. Prepare yourself.")
else:
    sm.sendNext("We cannot wait for the enemy to establish a base. This mission is critical to our success.")
