# Portal script pt_402000512_r_u. Illium's Sanctuary and Crystal Academy (402000500-402000535), walked through in Illium's
# Classic story. The game data gives no targets for these portals, so the links come from the region's world map
# (where each map sits), each portal's side and height, and the quest descriptions.
DEST = {
    (402000512, 3): (402000514, 2),  # Sanctuary / Academy Side Road pt_r_u -> Sanctuary / Western Crystal Forest 1 pt_l_d
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
