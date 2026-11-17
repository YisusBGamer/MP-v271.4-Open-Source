# 38207 [Karote] A Hidden Mural - end (Neinheart, Bottom Floor)
NEINHEART = 3005232

if sm.hasQuest(parentID):
    sm.setSpeakerID(NEINHEART)
    sm.setBoxChat()
    sm.sendNext("A man thrown into the sea after a failed experiment... and his face matched the statue's?")
    sm.sendNext("Hm. I need time to think on this. For now, the tower has given us a way up. Let us climb it.")
    sm.completeQuest(parentID)
sm.dispose()
