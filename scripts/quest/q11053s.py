# 11053 - Battle Mode MVP
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Rosette (9070005)

sm.setSpeakerID(9070005)  # Rosette
sm.sendNext("Hello, #h0#! I am Rosette, the Battle Square guide. Right now, I am handing out gifts to those who complete challenges.")
if sm.sendAskAccept("You want a mission? Okay, I've got a mission for you. You have to win 3 Team matches throughout the day. How does that sound? You can do that, right?"):
    sm.startQuest(parentID)
    sm.sendNext("All right! Now, remember, you must win 3 Team matches throughout the day. And you only get one day...after that, you lose your progress. Got it? Good! Now get to it!")
else:
    sm.sendNext("Where's all your confidence? This stuff is easy!")
