# Fishing timer: the time the QR record holds
total = sm.getQRValue(518, "totalIncCount")
if total:
    sm.createClock(int(total))
