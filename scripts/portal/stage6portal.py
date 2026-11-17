# Tower of Oz 6F: each teleporter leads to the next one in the chain (t001 -> t002 -> ... -> t013 -> t001).
info = sm.getField().getInfo()
here = info.getPortalByID(parentID)
if here is not None:
    there = info.getPortalByName(here.getTargetPortalName())
    if there is not None:
        sm.teleportToPortal(there.getId())
sm.dispose()
