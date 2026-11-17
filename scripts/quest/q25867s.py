# 25867 - Kaiser's Idol (Angelic Buster), from Cartalion (3000000). Battlefield quest: accepting moves you to North
# Border Battleground 2; winning it (portal/finish_warZone0.py) sets record 25918, which Cartalion's end check waits
# for. No Say.img start lines exist: the prompt is the quest's own summary.
sm.setSpeakerID(3000000)  # Cartalion
if sm.sendAskAccept(u"Kaiser and the knights are going to take out the Magician Specters. You’ve been asked to go "
                    "to the #m400040200#, to defeat any other enemies.\r\n#r#e(If you accept, you will automatically "
                    "move to the relevant field.)"):
    sm.startQuest(parentID)
    if sm.hasQuest(parentID):
        sm.warpInstanceIn(400051500)  # North Border Battleground 2
sm.dispose()
