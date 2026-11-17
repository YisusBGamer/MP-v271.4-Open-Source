# Laboratory System (3006480, Inside the Lab 410007606): Laboratory Investigation (38707) logs in with Gaon's
# Keycard (record 38707 clear=clear).
QUEST = 38707
KEYCARD = 4037197

if sm.hasQuest(QUEST) and sm.getQRValue(QUEST, "clear") != "clear":
    if sm.hasItem(KEYCARD):
        sm.sendNext("#b(You swiped Gaon's keycard. The system flickered to life.)#k")
        sm.sendNext("ACCESS GRANTED.\r\n...\r\nERROR: RECORDS 0001-4417 DELETED.\r\nERROR: RECORDS 4418-9020 DELETED.")
        sm.setQRValue(QUEST, "clear", "clear")
        sm.sendNext("#b(Almost everything has been erased. Report back to Neinheart.)#k")
    else:
        sm.sendNext("#b(The system needs a keycard to log in.)#k")
else:
    sm.sendNext("#b(The screen is dark. Nothing left to read.)#k")
sm.dispose()
