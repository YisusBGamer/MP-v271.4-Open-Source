# 25865 - Specter Invasion (Angelic Buster), from Cartalion (3000000). Battlefield quest: accepting moves you to East
# Border Battleground 1; winning it (portal/finish_warZone0.py) sets record 25797, which Cartalion's end check waits
# for. No Say.img start lines exist: the prompt is the quest's own summary.
sm.setSpeakerID(3000000)  # Cartalion
if sm.sendAskAccept("Magnus has launched a full-scale attack on the #m400010200#. Ward them off and protect the "
                    "#m400000000#!\r\n#r#e(If you accept, you will automatically move to the relevant field.)"):
    sm.startQuest(parentID)
    if sm.hasQuest(parentID):
        sm.warpInstanceIn(400050000)  # East Border Battleground 1
sm.dispose()
