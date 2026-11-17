# 5532 - Happy Little Paints-1
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Cassandra (9201256)

sm.setSpeakerID(9201256)  # Cassandra
sm.sendNext("Oh geez, I didn't think that was going to work. Now I have to work on this thing all night. THANKS A LOT!!")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("I was being sarcastic, but really thank you. I do NOT want to have to give back the deposit I got for this restoration.\r\nThose leg warmers really set me back on rent.")
