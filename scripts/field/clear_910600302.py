# 910600302 Puppeteer's Hidden Cave: Francis and Dargoth are beaten (The Secrets of the Puppeteer, Xenon 23677,
# record 23720 = "1"). Francis is waiting further in (910600303) to be handed the quest.
if sm.hasQuest(23677) and sm.getQRValue(23720) != "1":
    sm.startQuest(23720)
    sm.setQRValue(23720, "1")
    sm.chatScript("Dargoth fled and left Francis behind. Talk to Francis.")
    sm.invokeAfterDelay(2000, "warpInstanceOut", 910600303, 0)
