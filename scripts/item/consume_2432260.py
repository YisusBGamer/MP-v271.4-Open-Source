# Minah's Magnifying Glass (2432260), Beast Tamer, End of the Line (59142): used around El Nath it follows the
# footprints to their end, where the Triple Dummies (Nonvon Leon, 9390360) are. Every 2 minutes; not used up.
from java.lang import System

QUEST = 59142
DUMMIES = 9390360
COOLDOWN_MS = 2 * 60 * 1000

last = sm.getQRValueByKey(QUEST, "glass")
now = System.currentTimeMillis()
if last is not None and now - long(last) < COOLDOWN_MS:
    sm.chat("The magnifying glass can be used again in %d seconds." % ((COOLDOWN_MS - (now - long(last))) / 1000 + 1))
elif not sm.hasQuest(QUEST):
    sm.chat("A magnifying glass for tracking footprints. Use it in El Nath first.")
elif sm.getFieldID() / 1000000 != 211:
    sm.chat("There are no footprints here. Use it near El Nath.")
else:
    sm.setQRValue(QUEST, "glass", str(now))
    if field.getLifeByTemplateId(DUMMIES) is None:
        pos = chr.getPosition()
        sm.spawnNpc(DUMMIES, pos.getX() + 80, pos.getY())
    sm.chat("You found the end of the footprint trail!")
sm.dispose()
