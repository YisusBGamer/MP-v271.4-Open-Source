# 36324 Mysterious Power: tell Athena Pierce what happened on Maple Island. Handed in to Athena Pierce.
sm.setPlayerAsSpeaker()
if not sm.sendAskAccept("#b(Maple Island is safe again, but you can't stop thinking about the voice and the power it woke in you. Tell Athena Pierce everything?)#k"):
    sm.dispose()
sm.startQuest(parentID)
sm.chatScript("Report to Athena Pierce at the Bowman Instructional School in Henesys.")
sm.dispose()
