# Crystal Academy Lobby hidden lifts, right side (see pt_hidden_auto2): pt_a1 ground (252,66) -> pt_a2 (479,-700) ->
# pt_a3 (478,-1360) -> pt_a4 (266,-1985, by the way up to Corridor 1-2); the top one goes back down to the first.
CHAINS = {
    402000521: [5, 3, 13, 12],
}

chain = CHAINS.get(sm.getFieldID())
if chain is not None and parentID in chain:
    i = chain.index(parentID)
    sm.teleportToPortal(chain[(i + 1) % len(chain)])
sm.dispose()
