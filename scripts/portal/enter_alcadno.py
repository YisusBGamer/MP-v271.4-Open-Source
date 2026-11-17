# Magatia (261000000) in02: the Alcadno Society.
# Demon Slayer / Avenger story: on The Forbidden Book of Alchemy (23270) you find Maed still hypnotized (926150000);
# afterwards Maed (2159321) waits with Carson in 926150020, where The Chase and the Conspiracy (23271) starts and ends.

FORBIDDEN_BOOK = 23270
CHASE = 23271
SEEN_MAED = 23276  # 23270's end check: 23276 = 1

if sm.hasQuest(FORBIDDEN_BOOK) and sm.getQRValue(SEEN_MAED) != "1":
    sm.warp(926150000, 0)
elif sm.hasQuestCompleted(FORBIDDEN_BOOK) and not sm.hasQuestCompleted(CHASE) \
        and (not sm.hasQuest(CHASE) or sm.hasItem(4032975)) and sm.sendAskYesNo("Go to Maed and Carson about the #bForbidden Book of Alchemy#k?"):
    sm.warp(926150020, 0)
else:
    sm.warp(261000020, 1)
