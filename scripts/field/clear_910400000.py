# 910400000 Tru's Info Shop: the disguised Puppeteer is beaten (Gathering Some Strange Information, Aran 21733,
# checks record 21762 = 2). Back to the real Tru.
if sm.hasQuest(21733) and sm.getQRValue(21762) != "2":
    sm.startQuest(21762)
    sm.setQRValue(21762, "2")
    sm.chatScript("It was a trap by the Puppeteer! He got away... Go see the real Tru.")
    sm.invokeAfterDelay(2000, "warpInstanceOut", 104000004, 0)
