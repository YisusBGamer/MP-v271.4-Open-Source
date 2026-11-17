# Witchgrass
# 1072000
# The Swamp of Despair (103030000)
# Drops a Witchgrass Leaf.

witchgrassLeaf = 4033042

# another hit can remove the reactor before this one runs
if reactor is not None:
	reactor.incHitCount()
	reactor.increaseState()

	if reactor.getHitCount() >= 3:
		sm.dropItem(witchgrassLeaf, sm.getPosition(objectID).getX(), sm.getPosition(objectID).getY())
		sm.removeReactor()
