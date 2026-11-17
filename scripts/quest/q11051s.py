# 11051 - Battle Mode Participation Prize
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Rosette (9070005)

sm.setSpeakerID(9070005)  # Rosette
sm.sendNext("Hello, #h0#! I am Rosette, the Battle Square guide. Right now, I am handing out gifts to those who complete challenges.")
if sm.sendAskAccept("This mission is very easy. Just participate in any Battle Mode 5 times, and I'll give you a gift. You can do that, right?"):
    sm.startQuest(parentID)
    sm.sendNext("Great! Okay, just one thing to remember. You only get credit for participating if you complete one whole round, so don't leave the round early. Also, you have one day to finish the challenge, so don't waste time!")
else:
    sm.sendNext("Really? It's not like it's hard...")
