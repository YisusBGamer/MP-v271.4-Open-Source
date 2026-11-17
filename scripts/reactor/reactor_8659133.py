# Burning Kaptafel debris (8659133)
# [MONAD: The First Omen] Burning Kaptafel: the missing daughter's hat and photo (64018), sturdy wood (64021, 64025) and
# burnt bread (64029) are searched for in the debris. The item drops only while a quest still needs it (quest items also
# only show for characters on the quest).

NEEDS = {
    None: [(64018, 4036368, 1), (64018, 4036369, 1), (64021, 4036370, 50), (64025, 4036395, 50), (64029, 4036371, 3)],
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
