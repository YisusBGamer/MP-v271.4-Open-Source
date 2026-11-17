# [Limina] The Final Foe (35813): the Black Mage has fallen. You and Orchid are swallowed by the Light of
# Annihilation... and wake in the Erda Flow, where the White Mage waits (As Decreed, 35814).
sm.onLayer(800, "bmf0", 0, -80, 1, "Effect/Direction20.img/effect/BM3_soul/1", 4, False, -1, False)
done = sm.questEndDialogue(35813)
sm.offLayer(500, "bmf0", False)
if done and sm.getFieldID() == 450012500:
    sm.warp(993070086, 0)
sm.dispose()
