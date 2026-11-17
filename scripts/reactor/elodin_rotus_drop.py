# Elodin Lotus Reactor (1012002), Twilit Forest (101084100-101084300)
# Reactor.wz gives it 3 hit states and the action "elodin_rotus_drop", which had no script. Its flower is the quest
# Lotus: [Elodin] Singing Necessities 2 (37174) wants 5 Lotus 4036504 from here, [Elodin] Another Chance! (37176)
# 10 Lotus 4036507. Both only show up for characters on that quest (Item.wz reqQuestOnProgress).
SINGING_NECESSITIES_2 = 37174
ANOTHER_CHANCE = 37176
reactor.incHitCount()
reactor.increaseState()
if reactor.getHitCount() >= 3:
    lotus = 0
    if sm.hasQuest(SINGING_NECESSITIES_2):
        lotus = 4036504
    elif sm.hasQuest(ANOTHER_CHANCE):
        lotus = 4036507
    if lotus != 0:
        sm.dropItem(lotus, sm.getPosition(objectID).getX(), sm.getPosition(objectID).getY())
    sm.removeReactor()
else:
    reactor.broadcastChangeState()
sm.dispose()
