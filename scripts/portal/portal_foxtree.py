if sm.hasQuest(38004) or sm.hasQuest(38011):
    sm.warp(940200011, 0)
else:
    # Nothing Remains (Shade 38062, record 38062 = clear): go to the Fox Tree, where Moonbeam sits
    if sm.hasQuest(38062) and sm.getQRValue(38062) != "clear":
        sm.setQRValue(38062, "clear")
    sm.warp(410000002, 0)
