# Portal script pt_402000524_l. Illium's Sanctuary and Crystal Academy (402000500-402000535), walked through in Illium's
# Classic story. The game data gives no targets for these portals, so the links come from the region's world map
# (where each map sits), each portal's side and height, and the quest descriptions.
DEST = {
    (402000524, 2): (402000522, 3),  # Crystal Academy / Academy Corridor 2-1 pt_l -> Crystal Academy / Academy Corridor 1-2 pt_r
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
