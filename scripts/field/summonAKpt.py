# 920030100 (Hidden Street : Alcaster's Laboratory) - Phantom story: the Black Wing Henchmen (25429), then the diary
# (25430)
if sm.hasQuest(25429) and not sm.isComplete(25429) and not sm.hasMobsInField():
    for x in (-250, -170, -90, -10, 70, 150, 230, 310, 380, -200):
        sm.spawnMob(9300504, x, 91, False)
    sm.chatScript("Black Wings are ransacking the laboratory!")
if sm.hasQuest(25430) and not sm.hasItem(4033125):
    sm.giveItem(4033125)
    sm.chatScript("You find a dirty old diary under the rubble.")
