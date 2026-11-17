# Seal of Time 2 in the Black Mage's Temple (927020220). Checking it counts for The Black Mage (32309): its record
# is five digits, one per seal, and the quest is done at 11111.
QUEST = 32309
SEAL = 2

if sm.hasQuest(QUEST):
    record = sm.getQRValue(QUEST)
    if len(record) != 5 or record.strip("01") != "":
        record = "00000"
    record = record[:SEAL - 1] + "1" + record[SEAL:]
    sm.setQRValue(QUEST, record)
    sm.setPlayerAsSpeaker()
    sm.sendSayOkay("I don't want to touch something and accidentally awaken the Black Mage... I need to be careful.")
    if record == "11111":
        sm.chatScript("You've looked over the seals locking up the Black Mage.")
    else:
        sm.chatScript("There are other seals. I need to check them all and make sure I didn't miss anything.")
sm.dispose()
