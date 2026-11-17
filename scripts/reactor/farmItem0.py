# Haystack (1002008), Farm Center (Evan) / Nautilus stable
# Evan's 'A Bite of Hay' (22502): 'Use regular attacks on the haystacks nearby to get 1 Bundle of Hay'. The item drops
# only while a quest still needs it (quest items also only show for characters on the quest).

NEEDS = {
    None: [(22502, 4032452, 1)],
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
