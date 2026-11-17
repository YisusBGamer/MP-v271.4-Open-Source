# 38214 [Karote] Kalos the Guardian - end (Neinheart, Summit)
NEINHEART = 3005235

if sm.hasQuest(parentID):
    sm.setSpeakerID(NEINHEART)
    sm.setBoxChat()
    sm.sendNext("It's over. Kalos is down, and T-Boy is beaten. Well fought, #h0#.")
    sm.sendNext("Look there, past the clouds where Kalos stood guard... Something has appeared. A castle?")
    if sm.completeQuest(parentID):
        sm.sendNext("We will learn what it is in time. For now, return and rest. I will send word.")
sm.dispose()
