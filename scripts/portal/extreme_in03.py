# Monster Park Extreme (951000000 portal in03): opens the Extreme window (Enter / Auto-Matching come back as
# USER_RUN_SCRIPT 97). Lv. 260+, party of 1-4, one clear per character and two per world each week.
try:
    from net.swordie.ms.client.character import MonsterParkExtreme
except ImportError:
    MonsterParkExtreme = None

if MonsterParkExtreme is None:
    sm.setSpeakerID(9071006)
    sm.sendSayOkay("Monster Park Extreme is off limits at the moment.")
elif hasattr(MonsterParkExtreme, "openWindow"):
    MonsterParkExtreme.openWindow(chr)
else:
    sm.setSpeakerID(9071006)
    block = MonsterParkExtreme.getEntryBlock(chr)
    if block is not None:
        sm.sendSayOkay(block)
    elif sm.sendAskYesNo("#e<Monster Park Extreme>#n\r\n\r\nLevel 260+, a party of 1 to 4. Enter now?"):
        MonsterParkExtreme.enter(chr)
sm.dispose()
