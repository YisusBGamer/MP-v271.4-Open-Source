# Somewhere in Esfera (450007420): once the Light Executors are gone, on to Will's ambush (450007430)
if chr.getField().getMobs().size() == 0:
    if chr.getInstance() is not None:
        chr.getInstance().stopEvents()
    sm.warp(450007430)
else:
    sm.chat("This gate is locked.")
