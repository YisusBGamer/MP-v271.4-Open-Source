# Ninja Castle <Chamber of Traps>: caught by a trap, back to st00
p = sm.getField().getInfo().getPortalByName("st00")
sm.teleportToPortal(p.getId() if p is not None else 4)
sm.dispose()
