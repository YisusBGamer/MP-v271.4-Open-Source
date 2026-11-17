# 910100200 Jeppi's Hideout - The Kerning City Book Store Owner 2 (2631, Dual Blade story): the Dark Lord's minions
if sm.hasQuest(2631) and not sm.isComplete(2631) and not sm.hasMobsInField():
    for x in (-250, 0, 250):
        sm.spawnMob(9300526, x, 182, False)
