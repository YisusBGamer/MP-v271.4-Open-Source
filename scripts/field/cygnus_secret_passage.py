# 930030005 Puppeteer's Secret Passage (see CustomFieldScripts) - Into the Secret Passage (20944): [Mission] 20947
if sm.hasQuest(20944) and not sm.hasQuestCompleted(20947):
    if not sm.hasQuest(20947):
        sm.startQuestNoCheck(20947)
    if not sm.hasMobsInField():
        sm.chatScript("The Puppeteer and the Master of Disguise! Defeat them both.")
        sm.spawnMob(9300734, 850, 598, False)
        sm.spawnMob(9300735, 950, 598, False)
