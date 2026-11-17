# Ninja Castle <Chamber of Traps>: caught by a trap, back to st03
p = sm.getField().getInfo().getPortalByName("st03")
sm.teleportToPortal(p.getId() if p is not None else 1)
sm.dispose()
