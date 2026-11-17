# Rare Fire Flower (1022004) on the Perion cliffs: Big Rocky Road, Rugged Rocky Zone, Gusty Peak
# 'Slower than the Wind' (20796): 'A single Rare Fire Flower blooms along each of the cliffs of Big Rocky Road, Rugged
# Rocky Zone, and Gusty Peak'. The item drops only while a quest still needs it (quest items also only show for
# characters on the quest).

NEEDS = {
    None: [(20796, 4033227, 3)],
}

def needed():
    for questId, itemId, count in NEEDS.get(parentID, NEEDS.get(None, [])):
        if sm.hasQuest(questId) and not sm.hasItem(itemId, count):
            return itemId
    return 0

item = needed()
if item:
    sm.dropItem(item, sm.getPosition(objectID).getX(), sm.getPosition(objectID).getY())
sm.removeReactor()
sm.dispose()
