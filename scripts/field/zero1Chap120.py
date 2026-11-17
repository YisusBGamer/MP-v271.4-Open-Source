# Zero's story (Classic Zeros; Swordie Plus version - see zgen/gen_story.py). 322090120 Master Thief Phantom's Hideout - To Catch a Phantom (40104): Thief Crows guard Ardin
if sm.hasQuest(40104) and not sm.isComplete(40104) and not sm.hasMobsInField():
    for x in (-200, 100, 400, 650, 900):
        sm.spawnMob(9300760, x, 55, False)
