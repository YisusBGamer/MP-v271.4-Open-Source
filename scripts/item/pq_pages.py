# Pages Pub sells in the Cross World Party Quest shop: each one recovers a page (quest record key "0".."9")
# of its quest. Set before execfile: QUEST, WHAT (what the pages make up).
pages = [str(i) for i in range(10) if sm.getQRValueByKey(QUEST, str(i)) != "1"]
if sm.hasQuestCompleted(QUEST):
    sm.chat("You already returned the %s." % WHAT)
elif not sm.hasQuest(QUEST):
    sm.chat("This page goes with the %s quest. Start the quest first." % WHAT)
elif not pages:
    sm.chat("You've already recovered every page of the %s." % WHAT)
else:
    page = pages[sm.getRandomIntBelow(len(pages))]
    sm.consumeItem()
    sm.setQRValue(QUEST, page, "1")
    sm.chat("You recovered page %d of the %s (%d of 10)." % (int(page) + 1, WHAT, 11 - len(pages)))
sm.dispose()
