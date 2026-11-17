# Robed Lady (9390251), San Commerci. Pack Up, and Set Sail 2 (17639): the store sent the supplies to the harbor and
# you run into her; meeting her is the quest's task (its record value=clear), then it is handed in to her.
if sm.hasQuest(17639):
    if sm.getQRValue(17639, "value") != "clear":
        sm.setQRValue(17639, "value", "clear")
    sm.questEndDialogue(17639)
else:
    sm.setSpeakerID(9390251)
    sm.sendSayOkay("Leave me be, will you?")
sm.dispose()
