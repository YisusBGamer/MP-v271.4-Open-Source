# Wu Tien dojo reactor (7492031): Boulder Gorge Training Ground
# [Beasts of Fury] 'The Field' (weeds), 'The Cleanup' (old arrows on the logs), 'Tired Pan Zhuan' (Moonlight Posie at
# the training grounds). The item drops only while a quest still needs it (quest items also only show for characters on
# the quest).

NEEDS = {
    None: [(56565, 4034207, 12), (56592, 4034210, 12), (56567, 4034208, 8), (56594, 4034211, 8), (56575, 4009294, 1), (56602, 4009294, 1)],
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
