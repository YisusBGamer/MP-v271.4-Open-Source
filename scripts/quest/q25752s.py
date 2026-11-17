# 25752 - Stop the Summoning Ritual (Kaiser), from Cartalion (3000000). Battlefield quest: accepting moves you to West
# Border Battleground 1; winning it (portal/finish_warZone0.py) sets record 25799, which Cartalion's end check waits
# for. No Say.img start lines exist: the prompt is the quest's own summary.
sm.setSpeakerID(3000000)  # Cartalion
if sm.sendAskAccept("It's all over if the western enemies summon the main force! Stop the ritual by #bdefeating "
                    "Weakened Magician Specters#k!\r\n#r#e(If you accept, you will automatically move to the relevant "
                    "field.)"):
    sm.startQuest(parentID)
    if sm.hasQuest(parentID):
        sm.warpInstanceIn(400050600)  # West Border Battleground 1
sm.dispose()
