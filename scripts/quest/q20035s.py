# 20035 - The Final Test (start). Neinheart summons Assailants in the General Store Yard (913070050).

NEINHEART = 1106003
ASSAILANT = 9001050

sm.setSpeakerID(NEINHEART)
sm.sendNext("The Empress believes you're the one we came for. I'll believe it when I see it.")
if sm.sendAskAccept("Let's call this a test. Defeat what I send at you, and I'll listen to whatever she has to say."):
    sm.startQuest(parentID)
    for x in (-200, -50, 100, 250, 400):
        sm.spawnMob(ASSAILANT, x, 65, False)
        sm.spawnMob(ASSAILANT, x + 60, 65, False)
    sm.sendNext("Don't let them touch you. Go!")
else:
    sm.sendNext("Afraid already? Talk to me when you're ready.")
