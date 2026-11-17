# 25750 - Deterrence (Kaiser), from Cartalion (3000000). Battlefield quest: accepting moves you to North Border
# Battleground 1; winning it (portal/finish_warZone0.py) sets record 25797, which Cartalion's end check waits for.
# No Say.img start lines exist: the prompt is the quest's own summary.
sm.setSpeakerID(3000000)  # Cartalion
if sm.sendAskAccept("You need to buy some time while the others strengthen the defenses. Do this by #bdefeating all "
                    "Guerrilla Specters#k.\r\n#r#e(If you accept, you will automatically move to the relevant field.)"):
    sm.startQuest(parentID)
    if sm.hasQuest(parentID):
        sm.warpInstanceIn(400050400)  # North Border Battleground 1
sm.dispose()
