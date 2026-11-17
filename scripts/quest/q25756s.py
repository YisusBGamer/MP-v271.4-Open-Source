# 25756 - Start the Counterattack (Kaiser), from Cartalion (3000000). Battlefield quest: accepting moves you to the
# South Pantheon Border (940000160, field/event_25756.py). Beating the Specters there leads to Magnus's appearance
# (940000170, field/event_25756_end.py), which sets record 25801. No Say.img start lines exist: the prompt is the
# quest's own summary.
sm.setSpeakerID(3000000)  # Cartalion
if sm.sendAskAccept("The weak Specters are down, but the stronger ones are still kicking. #bDefeat all the Specters#k "
                    "inside the Protective Shield.\r\n#r#e(If you accept, you will automatically move to the relevant "
                    "field.)"):
    sm.startQuest(parentID)
    if sm.hasQuest(parentID):
        sm.warpInstanceIn(940000160)  # South Pantheon Border
sm.dispose()
