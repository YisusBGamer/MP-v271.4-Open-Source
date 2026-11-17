# 31000 - The Floating Island of Chryse
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Ericsson (2012018)

sm.setSpeakerID(2012018)  # Ericsson
sm.sendNext("Where did Chryse go? I'd go get my telescope, but...ehhh.")
if sm.sendAskAccept("Chryse? It's one of the floating islands up above Orbis. Hey, maybe you should come see me. I can tell you all about it. Sound good?"):
    sm.startQuest(parentID)
    sm.sendNext("I knew you'd be up for a challenge! You know where I am, right? Come to Orbis Park. Hurry up, I'm already here!")
else:
    sm.sendNext("Is this a bad time? I could really use your help...")
