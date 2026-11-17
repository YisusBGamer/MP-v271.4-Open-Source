# Zero's story (Classic Zeros; Swordie Plus version - see zgen/gen_story.py). The Puppet (40607): the Teardrops are activated once more - and Will appears (no NPC to report to)
if sm.getQRValue(40616) != "1":
    sm.setQRValue(40616, "1")
sm.questEndDialogue(parentID)
sm.dispose()
