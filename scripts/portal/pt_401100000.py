# Hidden Street: Path to the Reclamation HQ (401100000), portal right00.
# It goes on to the Heliseum Reclamation HQ (401000000), where the Angelic Buster story continues with Edea (the
# quest's own text).
if chr.getInstance() is not None:
    sm.warpInstanceOut(401000000, 0)
else:
    sm.warp(401000000, 0)
sm.dispose()
