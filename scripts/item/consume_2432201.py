# Folded Diary Page (2432201): one of the ten Diary pages (4001064-4001073) for
# [Cross World Party Quest] The Diary of the Goddess (32416), one the character doesn't have yet.
FIRST_PAGE = 4001064

missing = [FIRST_PAGE + i for i in range(10) if not sm.hasItem(FIRST_PAGE + i)]
if sm.hasQuestCompleted(32416):
    sm.chat("You already gave the diary back to Minerva.")
elif not missing:
    sm.chat("You already have every page of the diary.")
else:
    page = missing[sm.getRandomIntBelow(len(missing))]
    if not sm.canHold(page):
        sm.chat("Please make room in your Etc inventory.")
    else:
        sm.consumeItem()
        sm.giveItem(page)
sm.dispose()
