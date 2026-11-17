# Ninja Castle <Chamber of Traps>: caught by a trap, back to st01
p = sm.getField().getInfo().getPortalByName("st01")
sm.teleportToPortal(p.getId() if p is not None else 5)
sm.dispose()
