# Portal script clearRider.
# Rider's Field finish line, for Scadur's Advice (21610, Aran): marks the run as done (quest record 21619, which the
# quest checks) and takes you back to the Icy Cold Field. Report back to Scadur.
if sm.hasQuest(21610):
    sm.setQRValue(21619, "0", False)
    chr.chatMessage("Your wolf had a great run across the field. Go tell Scadur.")
sm.warp(211050000, 4)
sm.dispose()
