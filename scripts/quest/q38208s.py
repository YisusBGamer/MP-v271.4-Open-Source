# 38208 [Karote] Vessel of the Adversary - start (Neinheart, Halfway Up)
NEINHEART = 3005233

sm.setSpeakerID(NEINHEART)
sm.setBoxChat()
sm.sendNext("Another statue, the same face. And look at these markings on the walls... records of experiments.")
sm.sendNext("One phrase repeats again and again: #r'vessel of the Adversary'#k.")
if sm.sendAskAccept("Someone here was trying to make something. Ask #b#p3005245##k what she has found; I will keep "
                    "reading."):
    sm.startQuest(parentID)
sm.dispose()
