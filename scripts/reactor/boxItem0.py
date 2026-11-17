# boxItem0: Abandoned Farms near Shanghai (7492039 rice, 7492040 red beans); also the Dead Mine boxes
# 'Jiangshi Versus Porridge' (62142/62143) asks for Glutinous Rice and Red Beans from the Abandoned Farms. The item
# drops only while a quest still needs it (quest items also only show for characters on the quest).

NEEDS = {
    7492039: [(62142, 4034642, 10), (62143, 4034642, 10)],
    7492040: [(62142, 4034641, 10), (62143, 4034641, 10)],
}

def needed():
    for questId, itemId, count in NEEDS.get(parentID, NEEDS.get(None, [])):
        if sm.hasQuest(questId) and not sm.hasItem(itemId, count):
            return itemId
    return 0

reactor.incHitCount()
if reactor.getHitCount() >= 4:
    item = needed()
    if item:
        sm.dropItem(item, sm.getPosition(objectID).getX(), sm.getPosition(objectID).getY())
    sm.removeReactor()
sm.dispose()
