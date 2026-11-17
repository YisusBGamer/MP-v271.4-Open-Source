# id 2430963 (Catalion's D-02 Receiver): "Double-click to contact the downtown black market from Tyrant's Castle Foyer."
# Wounded Victory (31492) waits for the call from inside the castle (record 31563).
if sm.getFieldID() in (401050200, 401050001) and sm.hasQuest(31492):
    sm.setPlayerAsSpeaker()
    sm.sendNext(u"All right, I’m in. Better turn on that receiver and let HQ know.")
    sm.sendSay("(You turned on the D-02 Receiver and reported to the downtown black market.)")
    sm.startQuest(31563)
    sm.setQRValue(31563, "1")
elif sm.getFieldID() in (401050200, 401050001):
    sm.chat("There is only static on the D-02 Receiver.")
else:
    sm.chat("You can only contact the downtown black market from Tyrant's Castle Foyer.")
sm.dispose()
