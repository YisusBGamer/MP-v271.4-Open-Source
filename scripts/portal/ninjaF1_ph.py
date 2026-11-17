# Ninja Castle corridors: hidden passages, h0N0 <-> h0N1
info = sm.getField().getInfo()
p = info.getPortalByID(parentID)
if p is not None:
    name = p.getName()
    other = info.getPortalByName(name[:-1] + ("1" if name[-1] == "0" else "0"))
    if other is not None:
        sm.teleportToPortal(other.getId())
sm.dispose()
