# Exit of an Erel Light fight (693010360): open once the fight is won (record 61721 archon2=1), or when nothing is left alive in
# the map. Each player fights in their own copy of the map, as on GMS.
RECORD = 61721
FLAG = "archon2=1"
DONE = "dir=1;archon1=1;archon2=1"
NEXT_SCENE = 693007164

won = FLAG in sm.getQRValue(RECORD)
if not won and field.getAliveMobCount() == 0:
    sm.setQRValue(RECORD, DONE, True)
    won = True
if won:
    sm.warpInstanceOut(NEXT_SCENE, 0)
else:
    sm.chatScript("Defeat all the enemies first.")
sm.dispose()
