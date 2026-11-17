# 20610 - Training Still Never Ends 
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Neinheart (1101002)

sm.setSpeakerID(1101002)  # Neinheart
if sm.sendAskAccept("Have you been mastering your skills? I am sure you've mastered all your skills, which means... it's time for you to learn a #bnew skill#k, right?"):
    sm.startQuest(parentID)
else:
    sm.sendNext("Well, what you're doing right now doesn't make you appear humble. You just look complacent, and that's never a good thing.")
