# 57115 - Samurai Skirmish 1
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Mouri Motonari (9130008)

sm.setSpeakerID(9130008)  # Mouri Motonari
sm.sendNext("The falcon returns. You have no doubt realized that Oda knows where we are. His men are practically swarming the eastern reaches of our base.")
sm.sendSay("It is only a matter of time before he descends upon Momijigaoka, and we will not last the day if that happens.")
if sm.sendAskAccept("We must act first. To wait for luck is to wait for death. Will you grace us with your sword?"):
    sm.startQuest(parentID)
    sm.sendNext("I knew you would be willing to acquiesce. Go to Momijigahara 1, and take care of the enemy forces there.")
else:
    sm.sendNext("We cannot wait for the enemy to establish a base. This mission is critical to our success.")
