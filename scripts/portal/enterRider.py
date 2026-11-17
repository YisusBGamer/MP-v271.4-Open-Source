# Portal script enterRider.
# Icy Cold Field, the Wolf-Shaped Rock: leads to the Rider's Field during Scadur's Advice (21610, Aran).
if sm.hasQuest(21610):
    sm.warp(921110000, 1)
else:
    chr.chatMessage("Only those taking their wolf out for a run (Scadur's Advice) can enter this field.")
sm.dispose()
