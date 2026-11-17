# 2254 - Karcasa of the Desert
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: The Rememberer (1061011)

sm.setSpeakerID(1061011)  # The Rememberer
if sm.sendAskAccept("Okay, I think I know what to do now. I'll send you to #bKarcasa#k right now. The only problem is that he's...quite the salesman. You'll know what I mean. Do you want to go over to see him right now?"):
    sm.startQuest(parentID)
    sm.sendNext("Alright, now head over to see #bKarcasa#k and ask him about Mushroom Castle. Again, thank you for helping me out with my research!")
else:
    sm.sendNext("Hmmm... I don't know if I've told you this, but at your level, it might be easier to level-up around the desert. It's your choice, though...")
