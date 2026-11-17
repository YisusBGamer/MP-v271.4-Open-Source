# west00 on the Heliseum battlegrounds (401070000-401073300): leave the battle to the map's return field. The
# battlefield quest stays unfinished (forfeit and restart it to fight again).
DOWNTOWN = (401071300, 401071400, 401071500, 401071600, 401071700, 401071800, 401071900, 401072000, 401072100,
            401072800, 401072900, 401073000, 401073100)
field = sm.getFieldID()
if field in DOWNTOWN:
    sm.warpInstanceOut(401040000)  # Downtown Black Market
elif field in (401073200, 401073300):
    sm.warpInstanceOut(401030600)  # Path to Downtown
else:
    sm.warpInstanceOut(401000000)  # To Heliseum Reclamation HQ
sm.dispose()
