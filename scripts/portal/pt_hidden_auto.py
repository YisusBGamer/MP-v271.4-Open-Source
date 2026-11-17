# Hidden lifts of Illium's Sanctuary and Crystal Academy Corridor 1-2 (see pt_hidden_auto2): each hidden spot takes the
# player to the next one of its chain, the last one back to the first. The chains come from the spots' positions in
# the map data (no rope or other way between those floors).
CHAINS = {
    402000500: [4, 3],  # Town of Sanctuary: pt_h1 (297, 81) <-> pt_h2 (3552, 80), the two ends of town
    402000522: [[8, 7], [6, 5]],  # Corridor 1-2: right pt_h4 (248, -663) -> pt_h3 (476, -1006, by pt_r);
                                  # left pt_h2 (-247, -661) -> pt_h1 (-499, -1335, by pt_l)
}

chains = CHAINS.get(sm.getFieldID())
if chains is not None:
    if not isinstance(chains[0], list):
        chains = [chains]
    for chain in chains:
        if parentID in chain:
            i = chain.index(parentID)
            sm.teleportToPortal(chain[(i + 1) % len(chain)])
            break
sm.dispose()
