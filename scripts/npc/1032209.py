# Vieren (1032209), Harmony (101000200)
# Unexpected Encounter (Luminous 25617): a player who left the fight at the Forest Edge before the minions were
# defeated can go back from here. Otherwise one of his default lines.
if sm.hasQuest(25617) and sm.getQRValue(25616, "fight") != "1":
    sm.setSpeakerID(parentID)
    if sm.sendAskYesNo("Now that you've conquered your darkness, it should be safe to visit Lania. Go now?"):
        sm.warpInstanceIn(910142060, 0)
else:
    line = sm.getRandomNpcDefaultLine(parentID)
    if line is not None:
        sm.setSpeakerID(parentID)
        sm.sendSayOkay(line)
sm.dispose()
