# 57146 - Tae Soo and You
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Tae Soo (2091003)

sm.setSpeakerID(2091003)  # Tae Soo
sm.sendNext("Suspicious people? Yes, I did see them. It was right around the time Tae Sang came to inspect the trees.")
sm.sendSay("The Peach Monkeys and Reindeer were in a tizzy when that group passed through. I've never seen them so worked up. I fear they're becoming a threat to the farm.")
if sm.sendAskAccept("If they don't settle down, this year's harvest will be ruined. They must learn discipline! Can you teach them?"):
    sm.startQuest(parentID)
    sm.sendNext("Give them a good wallop! I want to see some monkey fur on you when you return.")
else:
    sm.sendNext("I guess you're making the right decision. These majestic trees DO deserve to die at the hands of a feral monkey colony, don't they?")
