# q25712s - Kaiser 4th job advancement

if chr.getJob() == 6111:
    sm.startQuest(parentID) # start it first, or completing it does nothing
    sm.jobAdvance(6112)
    sm.completeQuest(25712)
else:
    sm.sendSayOkay("You're currently not a third job Kaiser.")