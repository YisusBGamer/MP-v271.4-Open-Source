# 36314 Retrieving the Seal Stone - end (Athena Pierce): the file is handed over; the Seal Stone stays with the player
# (the Gate of the Present and the Maple Tree need it later).
DOCUMENT = 4033889
sm.setSpeakerID(1012100)
sm.sendNext("The Seal Stone! And these files... the Black Wings have been spying on the Resistance.")
sm.sendSay("Olive's name is cleared. I owe her an apology, and you my thanks.")
if sm.completeQuest(parentID):
    if sm.hasItem(DOCUMENT):
        sm.consumeItem(DOCUMENT, 1)
    sm.sendSayOkay("Keep the Seal Stone safe for now. You've proven you can be trusted with it.")
sm.dispose()
