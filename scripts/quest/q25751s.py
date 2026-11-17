# 25751 - Specter Invasion (Kaiser), from Cartalion (3000000). Battlefield quest: accepting moves you to East Border
# Battleground 2; winning it (portal/finish_warZone0.py) sets record 25798, which Cartalion's end check waits for.
# No Say.img start lines exist: the prompt is the quest's own summary.
sm.setSpeakerID(3000000)  # Cartalion
if sm.sendAskAccept("The main Specter force has launched their attack! Stop their attack at the eastern boundary by "
                    "#bdefeating all weakened Specter Shield Soldiers and Weakened Warrior Specters#k. Can you do it?"
                    "\r\n#r#e(If you accept, you will automatically move to the relevant field.)"):
    sm.startQuest(parentID)
    if sm.hasQuest(parentID):
        sm.warpInstanceIn(400050100)  # East Border Battleground 2
sm.dispose()
