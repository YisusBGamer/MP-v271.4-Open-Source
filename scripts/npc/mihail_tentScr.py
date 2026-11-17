# NPC 1106008's Npc.wz script name is "mihail_tentScr", so this file runs when it is clicked instead of 1106008.py.
# Keep the two the same.
# Worn Tent (1106008), Chromile's Tent 913070410: Suspicious Movement on the Path (Mihile 20784) - investigating it
# brings out the Unidentified Assailants (9001056, 16 to defeat).
ASSAILANT = 9001056
if sm.hasQuest(20784):
    if not sm.hasMobsInField(sm.getFieldID(), ASSAILANT):
        pos = chr.getPosition()
        for i in range(8):
            sm.spawnMob(ASSAILANT, pos.getX() - 350 + i * 100, pos.getY(), False)
            sm.spawnMob(ASSAILANT, pos.getX() - 300 + i * 100, pos.getY(), False)
        sm.chat("You were being watched! Defeat the Unidentified Assailants.")
    else:
        sm.chat("Defeat the Unidentified Assailants!")
else:
    sm.sendSayOkay("(An old, worn tent.)")
sm.dispose()
