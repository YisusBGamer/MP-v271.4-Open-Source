# 865010011 / 865010012 The Scarlet Terror: [Commerci Republic] Red Tide (17679) - Captain Blood and his crew are back
# for a character still on the quest.
if sm.hasQuest(17679) and not sm.isComplete(17679) and not sm.hasMobsInField(sm.getFieldID(), 9390817):
    pos = chr.getPosition()
    sm.removeNpc(9390246)
    sm.spawnMob(9390817, pos.getX() + 300, pos.getY(), False)
    for i in range(10):
        sm.spawnMob(9390816, pos.getX() - 400 + i * 90, pos.getY(), False)
        sm.spawnMob(9390815, pos.getX() - 350 + i * 90, pos.getY(), False)
