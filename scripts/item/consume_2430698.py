# Spiegelmann's Memo (2430698), Lip-Warmer Confidential (1710): the Moustache Factory's phone number and the order
# number, for the phone booth. Not used up (the quest takes it).
sm.setSpeakerID(9074100)
if sm.hasQuest(1710):
    sm.sendSayOkay("#b(The memo has the Moustache Factory's phone number and Spiegelmann's order number. Find the phone booth to place the order.)")
else:
    sm.sendSayOkay("#b(The Moustache Factory's phone number and an order number are scribbled on the memo.)")
sm.dispose()
