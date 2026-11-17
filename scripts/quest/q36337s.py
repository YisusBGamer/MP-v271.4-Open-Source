# 36337 Tess's Help: Tess offers to help with the mysterious maple leaf. Ends by itself.
sm.setSpeakerID(1541009)
if sm.hasQuestCompleted(parentID):
    sm.dispose()
sm.sendNext("Uh, why are you here? Oh! You want to know about that maple leaf, don't you?")
if not sm.sendAskAccept("I don't know much myself... but if you keep training, I bet you'll run into someone who does. Promise you'll tell me what you find out!"):
    sm.dispose()
if not sm.hasQuest(parentID):
    sm.startQuest(parentID)
sm.completeQuest(parentID)
sm.sendSayOkay("Good luck out there! Oh, and I heard the #bBowman Instructional School#k in Henesys has been in a fuss lately...")
sm.dispose()
