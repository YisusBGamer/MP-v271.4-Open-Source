# 3020 - The Ring's Secret
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Sergeant Bravo (2030001)

sm.setSpeakerID(2030001)  # Sergeant Bravo
sm.sendNext("Oh, this ring is truly beautiful... I mean, it's old and worn and looks terrible, but it holds a special power. Let me take a closer look at it... Hmm, fine, very fine. You know, I can tell you quite a bit about this ring if you can help me out. Are you up for it?")
if sm.sendAskAccept("Alright, you want my help, you gotta help me. Wolf skin prices are through the roof and they're a lot tougher nowadays. You bring me #b50 #t4000051# items and 50 #t4000052# items#k, I'll give you the ring and tell you whatever you want. But speed it up, I ain't got all day."):
    sm.startQuest(parentID)
    sm.sendNext("Great, we have a deal! The wolves are a lot tougher nowadays. You bring me #b100 #t4000051##k items, I'll give you the ring and tell you whatever you want. But hustle up, I ain't got all day. ")
else:
    sm.sendNext("Don't you want to know this ring's secret? It's a pretty great secret.")
