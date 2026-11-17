# Mirror World side quest (Zero; Swordie Plus). Humanoid A wants a Baby (41255): Han the Broker has just the thing
sm.setSpeakerID(2460013)
if not sm.hasItem(4033849):
    sm.sendNext("A robot that wants a baby? Ha! Then it'll have to build one. Take this Neo Huroid Construction Manual - consider it a favor.")
    sm.giveItem(4033849)
else:
    sm.sendSayOkay("Everything's in that manual. Good luck.")
sm.dispose()
