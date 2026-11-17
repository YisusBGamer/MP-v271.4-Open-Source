# 930030001 Ulrika Gang Hideout - To the Hideout (20939): the Black Wing Henchmen ambush you ([Mission] 20946)
if sm.hasQuest(20939) and not sm.hasQuestCompleted(20946):
    if not sm.hasQuest(20946):
        sm.startQuestNoCheck(20946)
    if not sm.hasMobsInField():
        sm.chatScript("It's an ambush! Defeat the Black Wing Henchmen and save the kids.")
        for x in (1300, 1650, 2000):
            sm.spawnMob(9300733, x, -14, False)
