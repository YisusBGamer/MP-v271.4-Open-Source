# Weekend Teleport Rock (2431571), from [Super Weekend] Teleport Rock (13697): to any town, whatever the
# continent. 20-minute cooldown; not used up (the item itself expires).
from java.lang import System

COOLDOWN_MS = 20 * 60 * 1000
TOWNS = [100000000, 101000000, 102000000, 103000000, 104000000, 105000000, 120000000, 130000000, 140000000,
         200000000, 211000000, 220000000, 221000000, 222000000, 230000000, 240000000, 250000000, 251000000,
         260000000, 261000000, 310000000, 400000000]

last = sm.getQRValueByKey(13697, "used")
now = System.currentTimeMillis()
if last is not None and now - long(last) < COOLDOWN_MS:
    sm.chat("The Weekend Teleport Rock can be used again in %d minutes." % ((COOLDOWN_MS - (now - long(last))) / 60000 + 1))
elif chr.getInstance() is not None:
    sm.chat("You can't use it here.")
else:
    towns = [t for t in TOWNS if t != sm.getFieldID()]
    text = "Where would you like to go?#b\r\n"
    for i in range(len(towns)):
        text += "#L%d##m%d##l\r\n" % (i, towns[i])
    sm.setSpeakerID(9010000)
    choice = sm.sendNext(text)
    if 0 <= choice < len(towns):
        sm.setQRValue(13697, "used", str(now))
        sm.warp(towns[choice])
sm.dispose()
