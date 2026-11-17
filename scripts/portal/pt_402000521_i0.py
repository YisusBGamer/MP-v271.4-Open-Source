# Portal script pt_402000521_i0. Illium's Sanctuary and Crystal Academy (402000500-402000535), walked through in Illium's
# Classic story. The game data gives no targets for these portals, so the links come from the region's world map
# (where each map sits), each portal's side and height, and the quest descriptions.
DEST = {
    (402000521, 2): (402000532, 1),  # Crystal Academy / Academy Lobby pt_i0 -> Crystal Academy / Empty Lecture Room pt_l
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
