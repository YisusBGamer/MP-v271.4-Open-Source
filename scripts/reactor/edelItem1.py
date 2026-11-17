# Dahlia (3102001), Edelstein Park 3 / Dahlia Garden
# 'A Gift Most Feminine' (23685) and 'How to Confess Your Crush 1' (23919): regular-attack the Dahlias. The item drops
# only while a quest still needs it (quest items also only show for characters on the quest).

NEEDS = {
    None: [(23685, 4033647, 5), (23919, 4032752, 1)],
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
