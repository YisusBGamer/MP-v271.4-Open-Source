# 50901 - Daily Sengoku Coin Grab
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Maple Administrator (9201257)

sm.setSpeakerID(9201257)  # Maple Administrator
if sm.sendAskAccept("Hello #h0#! If you want two free #rSengoku Coins#k, you can get them once a day just for #blogging in during the Segoku Era Festival#k. Are you here for today's Sengoku Coins?"):
    sm.startQuest(parentID)
else:
    sm.sendNext("Remember, you only get one a day, so don't forget to nab it!")
