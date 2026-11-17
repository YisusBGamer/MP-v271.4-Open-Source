# [Moonbridge] Unleashing the Alliance (35630): the official start dialogue, then the Eye of the Void (the story
# version, 450009500) to defeat the Weakened Gloom (field/dusk_onFirstUserEnter).
if sm.questStartDialogue(parentID):
    sm.warpInstanceIn(450009500, 0)
sm.dispose()
