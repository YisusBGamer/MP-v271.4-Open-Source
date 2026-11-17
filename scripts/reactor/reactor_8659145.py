# Optusa (8659145), Spring Wind Hollow
# [MONAD] 'Elva's Role' (64114) asks for Frozen Optusa Leaves. The item drops only while a quest still needs it (quest
# items also only show for characters on the quest).

NEEDS = {
    None: [(64114, 4036390, 10)],
}

def needed():
    for questId, itemId, count in NEEDS.get(parentID, NEEDS.get(None, [])):
        if sm.hasQuest(questId) and not sm.hasItem(itemId, count):
            return itemId
    return 0

reactor.incHitCount()
if reactor.getHitCount() >= 3:
    item = needed()
    if item:
        sm.dropItem(item, sm.getPosition(objectID).getX(), sm.getPosition(objectID).getY())
    sm.removeReactor()
sm.dispose()
