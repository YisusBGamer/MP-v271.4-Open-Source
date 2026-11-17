# Portal script DT_TA_NextMove.
# Destinations come from the game's map data (the portal's own target, the portal on the other side that
# leads here, or the room's exit to its town).
DEST = {
    (706051300, 2): (706050400, 1),  # Wu Tien Time Attack / 1,100m Up -> Wu Tien Mountain / 1,300m Up (own target)
    (706051300, 3): (706050400, 1),  # Wu Tien Time Attack / 1,100m Up -> Wu Tien Mountain / 1,300m Up (own target)
    (706051300, 4): (706050400, 1),  # Wu Tien Time Attack / 1,100m Up -> Wu Tien Mountain / 1,300m Up (own target)
    (706051300, 5): (706050400, 1),  # Wu Tien Time Attack / 1,100m Up -> Wu Tien Mountain / 1,300m Up (own target)
    (706051300, 6): (706050400, 1),  # Wu Tien Time Attack / 1,100m Up -> Wu Tien Mountain / 1,300m Up (own target)
    (706051300, 7): (706050400, 1),  # Wu Tien Time Attack / 1,100m Up -> Wu Tien Mountain / 1,300m Up (own target)
    (706051300, 8): (706050400, 1),  # Wu Tien Time Attack / 1,100m Up -> Wu Tien Mountain / 1,300m Up (own target)
    (706051300, 9): (706050400, 1),  # Wu Tien Time Attack / 1,100m Up -> Wu Tien Mountain / 1,300m Up (own target)
    (706051300, 10): (706050400, 1),  # Wu Tien Time Attack / 1,100m Up -> Wu Tien Mountain / 1,300m Up (own target)
    (706051300, 11): (706050400, 1),  # Wu Tien Time Attack / 1,100m Up -> Wu Tien Mountain / 1,300m Up (own target)
    (706051600, 2): (706050400, 1),  # Wu Tien Time Attack / 1,700m Up -> Wu Tien Mountain / 1,300m Up (own target)
    (706051600, 3): (706050400, 1),  # Wu Tien Time Attack / 1,700m Up -> Wu Tien Mountain / 1,300m Up (own target)
    (706051600, 4): (706050400, 1),  # Wu Tien Time Attack / 1,700m Up -> Wu Tien Mountain / 1,300m Up (own target)
    (706051600, 5): (706050400, 1),  # Wu Tien Time Attack / 1,700m Up -> Wu Tien Mountain / 1,300m Up (own target)
    (706051600, 6): (706050400, 1),  # Wu Tien Time Attack / 1,700m Up -> Wu Tien Mountain / 1,300m Up (own target)
    (706051600, 7): (706050400, 1),  # Wu Tien Time Attack / 1,700m Up -> Wu Tien Mountain / 1,300m Up (own target)
    (706051600, 8): (706050400, 1),  # Wu Tien Time Attack / 1,700m Up -> Wu Tien Mountain / 1,300m Up (own target)
    (706051600, 9): (706050400, 1),  # Wu Tien Time Attack / 1,700m Up -> Wu Tien Mountain / 1,300m Up (own target)
    (706051600, 10): (706050400, 1),  # Wu Tien Time Attack / 1,700m Up -> Wu Tien Mountain / 1,300m Up (own target)
    (706051600, 11): (706050400, 1),  # Wu Tien Time Attack / 1,700m Up -> Wu Tien Mountain / 1,300m Up (own target)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
