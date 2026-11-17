# Vacant Lot (993201002): the Stray Dog is down; Lost Property (36607) ends and Mihile heads home.
execfile("scripts/quest/_mihile_story.py")

if sm.hasQuest(36607):
    try:
        scene_start()
        me("*pant* And Father said you were dangerous.")
        me("(My heart's still pounding like crazy. I feel...)")
        me("(I think I feel a little stronger.)")
        me("(All right, I've got the picture. Homeward bound.)")
        finish(36607)
    finally:
        scene_end()
sm.warpInstanceOut(REMOTE_HOUSE, 0)
sm.dispose()
