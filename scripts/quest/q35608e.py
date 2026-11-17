# [Moonbridge] Resolution (35608), handing it in
sm.onLayer(800, "bme0", 0, -80, 1, "Effect/Direction20.img/effect/BMPre_Cygnus/0", 4, False, -1, False)
done = sm.questEndDialogue(35608)
sm.offLayer(500, "bme0", False)
if done:
    sm.warp(993060030, 0)
sm.dispose()
