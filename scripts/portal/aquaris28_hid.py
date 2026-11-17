# Tower of Oz 28F: each hidden portal links to its mirror on the other side (hid00 <-> hid07, hid01 <-> hid06, ...).
info = sm.getField().getInfo()
here = info.getPortalByID(parentID)
if here is not None and here.getName().startswith("hid"):
    there = info.getPortalByName("hid%02d" % (7 - int(here.getName()[3:])))
    if there is not None:
        sm.teleportToPortal(there.getId())
sm.dispose()
