# Mirror World side quest (Zero; Swordie Plus). Schegerazade's Third Riddle (41057): her brother Jiyur knows what she wants most - his letter
sm.setSpeakerID(2420006)
if not sm.hasItem(4033808):
    sm.sendNext("What my sister wants most? A word from her big brother, I suppose. We haven't spoken in years... Here, give her this letter.")
    sm.giveItem(4033808)
else:
    sm.sendSayOkay("Please, give Schegerazade my letter.")
sm.dispose()
