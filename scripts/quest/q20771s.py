# 20771 - The First Training Session
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Kiku (1102000)

sm.setSpeakerID(1102000)  # Kiku
sm.sendNext("I need to get a bead on your fitness level. Run to the Drill Hall and hustle into the Elf Elementary.")
if sm.sendAskAccept("Run straight to the left to the #bEntrance to the Drill Hall#k and hustle into the Elf Elementary. I want you to take down #b#e20 Green Mushrooms#k#n on the double and then meet me back at the Entrance to the Drill Hall. You got all that?"):
    sm.startQuest(parentID)
else:
    sm.sendNext("Don't get your pretty locks in a tussle. I won't get you killed.")
