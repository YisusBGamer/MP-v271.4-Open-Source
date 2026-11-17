# 2916 - Shulynch's Training 2
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Shulynch (1092008)

sm.setSpeakerID(1092008)  # Shulynch
if sm.sendAskAccept("Are you ready for something a bit harder?"):
    sm.startQuest(parentID)
    sm.sendNext("The next wave of monsters may look like OctoPirates, but don't underestimate them or you'll regret it! You're fighting for your life here.")
    sm.sendSay("Talk to me once you're ready to enter the Training Room, then eliminate #r30 OctoPirates#k.")
else:
    sm.sendNext("Don't waste my time. Piracy isn't for quitters!")
