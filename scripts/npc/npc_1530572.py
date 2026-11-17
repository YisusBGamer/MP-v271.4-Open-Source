# Secret Couple (1530572), shown only while Hidden Story: NO DATING (33048) is in progress.
# Each of the three couples counts once towards the quest's count=3.
QUEST = 33048
KEY = "c1530572"

if sm.hasQuest(QUEST) and sm.getQRValue(QUEST, KEY) != "1":
    count = sm.getQRValue(QUEST, "count")
    count = int(count) + 1 if count and count.isdigit() else 1
    sm.setQRValue(QUEST, KEY, "1")
    sm.setQRValue(QUEST, "count", str(min(count, 3)))
    if count >= 3:
        sm.chatScript("The power of love is great and terrible. I don't know who you are but I will find you. I will find you and report you.")
    else:
        sm.chatScript("You caught a secret couple. (%d/3)" % count)
