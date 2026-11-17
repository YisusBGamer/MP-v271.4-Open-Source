# 38201 [Karote] A New Mission - end (Neinheart)
NEINHEART = 1101002

if sm.hasQuest(parentID):
    sm.setSpeakerID(NEINHEART)
    sm.setBoxChat()
    sm.sendNext("Thank you for coming so quickly, #h0#.")
    sm.sendNext("The High Flora now threaten both worlds. I have called the Chief Knights together to share what they "
                "have seen. Join the meeting.")
    sm.completeQuest(parentID)
sm.dispose()
