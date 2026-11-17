# Mirror World side quest (Zero; Swordie Plus). Mother Knows Best (41260): Han the Broker's book on alchemy, for Keeny
sm.setSpeakerID(2460013)
if not sm.hasItem(4033856):
    sm.sendNext("<Essentials of Alchemy>? For Phyllia's girl? Hmph. Fine - every genius has to start somewhere.")
    sm.giveItem(4033856)
else:
    sm.sendSayOkay("Take the book to Keeny.")
sm.dispose()
