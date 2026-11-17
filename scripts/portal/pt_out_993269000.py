# Twilight Altar (993269000) exit. The altar is entered from the towns' pt_event portals (pt_in_993269000) and its own
# returnMap points at itself, so the player goes back to the town they came from (Henesys if unknown).
ENTRY_TOWNS = (100000000, 410000500, 410007000, 450001000)

prev = chr.getPreviousFieldID()
sm.warp(prev if prev in ENTRY_TOWNS else 100000000, 0)
