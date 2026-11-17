# Anti-magic Container (I.M.S.): shows the anti-magic collected (quest record 500784)
cur = sm.getQRValue(500784)
sm.chat("Anti-magic collected: %s." % (cur if cur and cur.isdigit() else "0"))
sm.dispose()
