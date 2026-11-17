# Anti-magic Stone Piece: 600 anti-magic into the Anti-magic Container (Kritias; quest record 500784)
ANTI_MAGIC_QR = 500784
AMOUNT = 600
cur = sm.getQRValue(ANTI_MAGIC_QR)
total = (int(cur) if cur and cur.isdigit() else 0) + AMOUNT
sm.consumeItem(parentID)
sm.setQRValue(ANTI_MAGIC_QR, str(total))
sm.chat("You absorbed %d anti-magic (%d collected). Exchange it for coins in Kritias." % (AMOUNT, total))
sm.dispose()
