# Crystal Academy hidden lifts (Illium's Classic story): the floors of the Academy Lobby and Corridor 2-1 have no
# ropes between them. Each hidden spot takes the player to the next one up its chain, the top one back down to the
# first. The chains come from the spots' positions in the map data, lowest first.
CHAINS = {
    402000521: [8, 9, 10, 11],  # Lobby left side: pt_h1 (y 67), pt_h2 (-375), pt_h3 (-989), pt_h4 (-1696)
    402000524: [8, 7, 5, 6],    # Corridor 2-1: pt_h4 (y -661), pt_h3 (-1006), pt_h1 (-1335), pt_h2 (-1624)
}

chain = CHAINS.get(sm.getFieldID())
if chain is not None and parentID in chain:
    i = chain.index(parentID)
    sm.teleportToPortal(chain[(i + 1) % len(chain)])
sm.dispose()
