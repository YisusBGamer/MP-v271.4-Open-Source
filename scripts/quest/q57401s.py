# 57401 - Zipangu's World
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Naoe Kanetsugu (9130082)

sm.setSpeakerID(9130082)  # Naoe Kanetsugu
sm.sendNext("As you know, there was a raid at Honnou-ji. By the time the alliance arrived, it was a scene of utter chaos. Nobunaga was fighting someone, but amid the din of battle, we could not tell who.")
sm.sendSay("You stopped Nobunaga's ritual. I hope you remember that part because it was fine work on your part. Good job. But we were shocked to find that it was Akechi Mitsuhide's men fighting Oda's army. They revolted from within, turned on their own kind like animals! Akechi must have wanted all of Hinomaru for himself. Still, it takes some sort of warrior's spirit to stand up to Nobunaga like that. I'm not without respect for the man.")
sm.sendSay("The fierce battle raged for hours. Akechi leapt from soldier to soldier, felling enemies much larger than himself with ease. I think I saw him cut a man in half! But before he could reach Nobunaga, a sizzling beam of light erupted from Honnou-ji Temple and something extraordinary happened.")
sm.sendSay("The light enveloped us, seeping right into our minds, and everything went dark. When color returned to the world, we found ourselves in this unfamiliar place filled with strange creatures. Even the laws of physics do not hold entirely true with what we know.")
if sm.sendAskAccept("It's a lot to process. Try to let it sink in."):
    sm.startQuest(parentID)
else:
    sm.sendNext("The light scattered everyone to the four winds; most we know not where. For all I know, some may have been sent here, but to a different time entirely.")
