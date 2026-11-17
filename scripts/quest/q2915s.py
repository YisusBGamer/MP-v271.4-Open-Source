# 2915 - Shulynch's Training 1
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Shulynch (1092008)

sm.setSpeakerID(1092008)  # Shulynch
if sm.sendAskAccept("Combat is the best form of training, I always say. Are you ready to begin?"):
    sm.startQuest(parentID)
    sm.sendNext("The monsters inside won't hold back, so you shouldn't, either. When you're ready, go into the Training Room and eliminate #r30 OctoPirates#k. I'll be watching.")
else:
    sm.sendNext("You aren't scared, are you? That's probably smart.")
