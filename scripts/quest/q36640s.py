# 36640 Ensorcelled Knights Part 3 (from the quest list, after 36639). The Empress is saved on the Small Bridge and
# appoints the knights Chief Knights: Mihile 4th job advancement 5111 -> 5112.
sm.setPlayerAsSpeaker()
sm.sendNext("#b(The Empress is on the east side of the Small Bridge. You hurry to her side...)#k")
sm.setSpeakerID(1101000)
sm.sendSay("You came for me... and the knights have come to their senses. Thank you, all of you.")
if not sm.sendAskAccept("For your heroism, I appoint you a #bChief Knight#k of the Cygnus Knights. Will you accept?"):
    sm.dispose()
if sm.questJobAdvance(parentID, 5111, 5112):
    sm.sendSayOkay("You have advanced to #bMihile (4th job)#k.")
sm.dispose()
