# 2322 - Over the Castle Walls (2)
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Secretary of Domestic Affairs (1300003)

sm.setSpeakerID(1300003)  # Secretary of Domestic Affairs
if sm.sendAskAccept("Like I said, just breaking the barrier is not enough cause for celebration. The Mushroom Castle is extremely secure and does not allow anyone outside the Mushking Empire to enter, so it may not be so easy to infiltrate the castle. Hmmm... To figure out a way to enter, can you investigate the outer walls of the castle first?"):
    sm.startQuest(parentID)
    sm.sendNext("Once you pass the Mushroom Forest and near the Castle Walls from #b#m106020400##k, you will see #b#m106020500##k. Investigate the area for any weaknesses and let me know what you find.")
else:
    sm.sendNext("Oh, really? You think you have a better idea?! Come talk to me when you get stuck outside the Castle Walls.")
