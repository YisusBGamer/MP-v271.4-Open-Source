# Ninja Castle <Chamber of Traps>: caught by a trap, back to st02
p = sm.getField().getInfo().getPortalByName("st02")
sm.teleportToPortal(p.getId() if p is not None else 6)
sm.dispose()
