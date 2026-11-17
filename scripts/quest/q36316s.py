# 36316 Checking on the Temple of Time (Athena Pierce): the Gate of the Present stands open. Ends by itself.
sm.setSpeakerID(1012100)
sm.sendNext("The Black Wings won't give up on the Seal Stone so easily. And I fear they're after something bigger.")
if not sm.sendAskAccept("In the Temple of Time, the #bGate of the Present#k seals away the Black Mage. Please go and make sure it's still closed."):
    sm.dispose()
sm.startQuest(parentID)
sm.setPlayerAsSpeaker()
sm.sendNext("#b(You travel to the Temple of Time. The Gate of the Present, which should be firmly sealed, is standing open... and the Seal Stone of Time inside is cracked.)#k")
sm.completeQuest(parentID)
sm.chatScript("Report to Athena Pierce that the Gate of the Present is open.")
sm.dispose()
