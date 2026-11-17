# Beast Tamer tutorial. The client has only a few of its lines (Say.img, marked 'official'); the rest follow the
# quests' Quest.wz summaries. The Express skip (field/q59000_tuto.py) still works.
# Save the Cat 1 (59003): the trap is gone; the cat is free (and looks worried).
if sm.hasQuest(parentID):
    sm.setSpeakerID(9390467)
    sm.sendNext("I'm free! Thank you, thank you!")
    sm.setPlayerAsSpeaker()
    sm.sendNext("No problem! Saving cats is all in a day's work for a future hero. ...Hey, why the long face?")
    if sm.completeQuest(parentID):
        sm.warp(866104000, 0)
sm.dispose()
