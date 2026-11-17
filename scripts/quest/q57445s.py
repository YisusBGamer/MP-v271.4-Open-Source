# 57445 - Shadow of Chaos
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Do Gong (2091001)

sm.setSpeakerID(2091001)  # Do Gong
sm.sendNext("They must be headed for the Goblin Forest. It's a real energy sinkhole.")
if sm.sendAskAccept("Investigate the Goblin Forest to discover the true intentions of these menaces. I can assure you they're up to no good."):
    sm.startQuest(parentID)
    sm.sendNext("The goblins have dug in deep within the Virtuous Forest. The lord of the area is powerful, so you would do well to prepare yourself and move with caution.")
else:
    sm.sendNext("Success rarely follows apathy.")
