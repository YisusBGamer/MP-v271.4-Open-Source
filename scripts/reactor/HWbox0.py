# Attic Dress Crate ([Halloween] Episode 2: Dress Up): a few hits and Ludmilla's Ball Dress turns up.
DRESS = 4034707

reactor.incHitCount()
if reactor.getHitCount() >= 3:
    reactor.setHitCount(0)
    if sm.hasQuest(15113) and not sm.hasItem(DRESS) and sm.canHold(DRESS):
        sm.giveItem(DRESS, 1)
        sm.chatScript("You found Ludmilla's Ball Dress!")
sm.dispose()
