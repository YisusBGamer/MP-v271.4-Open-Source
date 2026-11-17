# Zero's story (Classic Zeros; Swordie Plus version - see zgen/gen_story.py). Friends and Enemies (40308): Milo shows his true face - an ambush. If you fail, forfeit and start again.
if sm.questStartDialogue(parentID) and sm.getFieldID() == 324090230 and not sm.hasMobsInField():
    sm.removeNpc(2440003)
    sm.spawnMob(9300768, 150, 543, False)
    for x in (-500, -250, 450):
        sm.spawnMob(9300767, x, 543, False)
sm.dispose()
