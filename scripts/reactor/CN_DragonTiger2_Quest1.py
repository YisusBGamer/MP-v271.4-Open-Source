# Wu Tien mountain reactor (7492037): 900m Up: Mountainside
# [Beasts of Fury] 'Healing Herbs' (56736/56749): herbs on the mountainside. The item drops only while a quest still
# needs it (quest items also only show for characters on the quest).

NEEDS = {
    None: [(56736, 4034362, 8), (56749, 4034363, 8)],
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
