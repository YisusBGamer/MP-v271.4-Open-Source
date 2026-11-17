# Wu Tien dojo reactor (7492030): Drill Hall, Dojo 1F
# [Beasts of Fury] 'The Chopping' (56569/56596): firewood for the dojo. The item drops only while a quest still needs it
# (quest items also only show for characters on the quest).

NEEDS = {
    None: [(56569, 4034209, 20), (56596, 4034212, 20)],
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
