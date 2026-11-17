# 11052 - Free-for-all Professional
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Rosette (9070005)

sm.setSpeakerID(9070005)  # Rosette
sm.sendNext("Hello, #h0#! I am Rosette, the Battle Square guide. Right now, I am handing out gifts to those who complete challenges.")
if sm.sendAskAccept("This mission is to score 500 points in Free-for-all mode throughout the day! How does that sound? It's not that hard, is it?"):
    sm.startQuest(parentID)
    sm.sendNext("Okay, score 1000 points throughout the day! And remember, you gotta do it in one day! Otherwise, you lose your progress. Now, get out there and fight like you mean it!")
else:
    sm.sendNext("It's not really a difficult mission... You can do it, can't you?")
