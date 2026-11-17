# 36308 Stolen Treasure: a note from the Bowman Instructional School. Handed in to Athena Pierce.
sm.setSpeakerID(1012100)
sm.sendNext("#b(A note has arrived from the Bowman Instructional School in Henesys.)#k\r\n\r\n#e- WANTED -#n\r\nOlive, for the theft of a treasure entrusted to the Bowman Instructional School. Anyone with information, please report to Athena Pierce.")
if not sm.sendAskAccept("#b(Olive? The same Olive you trained with? Something isn't right. Visit the Bowman Instructional School and find out what's going on.)#k"):
    sm.dispose()
sm.startQuest(parentID)
sm.chatScript("Go to the Bowman Instructional School in Henesys and talk to Athena Pierce.")
sm.dispose()
