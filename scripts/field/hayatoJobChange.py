# Hayato Tutorial Clipsence | Momijigaoka : Unfamiliar Hillside (807040000)
# Author: Tiger

# the v271 Sengoku story to Momijigaoka, done as GMS has it at this point (68500-68517 / 68520-68537, 68545-68551)
STORY = {4001: list(range(68500, 68518)), 4002: list(range(68520, 68538))}
for quest in STORY.get(chr.getJob(), []) + (list(range(68545, 68552)) if chr.getJob() in STORY else []):
    if not sm.hasQuestCompleted(quest):
        sm.completeQuestNoRewards(quest)

if chr.getJob() == 4001:
    sm.setJob(4100)
    sm.addMaxHP(350)
    sm.addMaxMP(200)
    sm.setSTR(4)
    sm.setINT(4)
    sm.setDEX(4)
    sm.setLUK(4)
    sm.setAP(4 + chr.getLevel() * 5)
    sm.lockInGameUI(True)
    #sm.playVideoByScript("JPHayato.avi")
    sm.lockInGameUI(False)
elif chr.getJob() == 4002:
    # GMS's tutorial brings Kanna here at level 10, which 57400/57401 need (their NPC stands only in this map); the
    # short tutorial ends at level 1, as the Express skip would without its levels
    if chr.getLevel() < 10:
        sm.addLevel(10 - chr.getLevel())
    sm.addMaxHP(10)
    sm.lockInGameUI(False)
    sm.setJob(4200)
    sm.addMaxMP(95)
    sm.setSTR(4)
    sm.setINT(4)
    sm.setDEX(4)
    sm.setLUK(4)
    sm.setAP(4 + chr.getLevel() * 5)
    sm.startQuestNoCheck(57400)
