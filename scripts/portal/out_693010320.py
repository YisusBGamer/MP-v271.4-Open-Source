# Exit of an Erel Light fight (693010320): open once the fight is won (record 61713 boss=1), or when nothing is left alive in
# the map. Each player fights in their own copy of the map, as on GMS.
RECORD = 61713
FLAG = "boss=1"
DONE = "start=1;boss=1"
NEXT_SCENE = 693007141

won = FLAG in sm.getQRValue(RECORD)
if not won and field.getAliveMobCount() == 0:
    sm.setQRValue(RECORD, DONE, True)
    won = True
if won:
    sm.warpInstanceOut(NEXT_SCENE, 0)
else:
    sm.chatScript("Defeat all the enemies first.")
sm.dispose()
