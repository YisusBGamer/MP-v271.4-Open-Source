# [Moonbridge] Unleashing the Alliance (35630): the Weakened Gloom is down. Into the Eye of the Void Interior to
# destroy its Core (Destroy the Core, 35631).
if sm.questEndDialogue(35630) and sm.getFieldID() == 450009500:
    if sm.questStartDialogue(35631):
        sm.warp(993060081, 0)
sm.dispose()
