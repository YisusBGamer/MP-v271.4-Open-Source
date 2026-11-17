# 20035 - The Final Test (end). Neinheart, after the Assailants are beaten.

NEINHEART = 1106003

sm.setSpeakerID(NEINHEART)
sm.sendNext("Not bad, for someone who usually fights with a broom.")
if sm.tryCompleteQuest(parentID):
    sm.sendSay("Empress, I take it back. Go on, talk to her. She has something to tell you.")
