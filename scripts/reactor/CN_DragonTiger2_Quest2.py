# Wu Tien mountain reactor (7492038): 1,900m Up
# [Beasts of Fury] 'Restful Herbs' (56738/56751): herbs close to the peak. The item drops only while a quest still needs
# it (quest items also only show for characters on the quest).

NEEDS = {
    None: [(56738, 4034354, 8), (56751, 4034355, 8)],
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
