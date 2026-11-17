# 38210 [Karote] The Adversary Experiment - start (Neinheart, Halfway Up)
NEINHEART = 3005233

sm.setSpeakerID(NEINHEART)
sm.setBoxChat()
sm.sendNext("He survived the sea and built his way back to the surface... and called himself the #rAdversary#k?")
sm.sendNext("Then this was a laboratory meant to create an Adversary, and the man in the memories built #bKarote#k "
            "itself. But why?")
if sm.sendAskAccept("Nicky has said nothing since you returned. Speak with #b#p3005239##k; she has been watching him."):
    sm.startQuest(parentID)
sm.dispose()
