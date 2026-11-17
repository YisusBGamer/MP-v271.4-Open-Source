# Edelstein Message Board (2152019) | Edelstein (310000000)
# Quest 23005 "Request from a Mascot": posting Checky's flier sets record 23006 to "1", which the turn-in checks.

FLIER = 4032783

if sm.hasQuest(23005) and sm.hasItem(FLIER):
    sm.consumeItem(FLIER, 1)
    # the whole record, not appended: a second flier (quest forfeited and taken again) would make it "1;1"
    sm.createQuestWithQRValue(23006, "1")
    sm.chatScript("You posted Checky's flier on the message board. Go tell Checky.")
elif sm.hasQuest(23005) and sm.getQRValue(23006) == "1":
    sm.chatScript("Checky's flier is already up on the board. Go tell Checky.")
else:
    sm.chatScript("Fliers and notices are pinned all over the Edelstein message board.")
sm.dispose()
