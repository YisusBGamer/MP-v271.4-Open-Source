# 36966 Taming the Polearm - end (Maha). Aran 4th job advancement 2111 -> 2112.
sm.setSpeakerID(1201002)
sm.sendNext("The rage is gone... Thank you, Aran. You really are my master.")
if sm.questJobAdvance(parentID, 2111, 2112):
    sm.sendSayOkay("Now that I'm calm, I can awaken more of your past powers. Let's show the Black Mage what we can do!")
sm.dispose()
