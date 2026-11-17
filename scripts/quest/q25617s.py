# 25617 - Unexpected Encounter (Luminous), started at Vieren (1032209) in Harmony.
# On the way to Lania, Black Mage minions block the Forest Edge (910142060, enter_q25617). Defeating them sets record
# 25616 fight=1 (clear_910142060), which the quest checks.
sm.setSpeakerID(1032209)  # Vieren
if sm.sendAskAccept("Now that you've conquered your darkness, it should be safe to visit Lania."):
    sm.startQuest(parentID)
    sm.warpInstanceIn(910142060, 0)
sm.dispose()
