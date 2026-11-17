# escape00, 401050200 Tyrant's Castle Foyer (the Specter-held one): Tiron's secret path behind the golden ornament,
# out of the castle to the Downtown Black Market. Escape the Tyrant's Castle (31495) waits for it (record 31564).
if sm.hasQuest(31495):
    sm.startQuest(31564)
    sm.setQRValue(31564, "1")
sm.warp(401040000, 0)
sm.dispose()
