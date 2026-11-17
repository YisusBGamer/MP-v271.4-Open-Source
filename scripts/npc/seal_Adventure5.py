# Seal of Time 5 in the Black Mage's Temple (927020220). Checking it counts for The Black Mage (32309): its record
# is five digits, one per seal, and the quest is done at 11111.
# This is the broken seal (Say 32309 / 32310).
QUEST = 32309
SEAL = 5

if sm.hasQuest(QUEST):
    record = sm.getQRValue(QUEST)
    if len(record) != 5 or record.strip("01") != "":
        record = "00000"
    record = record[:SEAL - 1] + "1" + record[SEAL:]
    sm.setQRValue(QUEST, record)
    sm.setPlayerAsSpeaker()
    sm.sendSayOkay("Hey, one of the seals IS broken!")
    if record == "11111":
        sm.chatScript("You've looked over the seals locking up the Black Mage.")
    else:
        sm.chatScript("There are other seals. I need to check them all and make sure I didn't miss anything.")
sm.dispose()
