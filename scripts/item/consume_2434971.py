# Poison Flower Monsterbloom

MONSTER_COLLECTOR = 7269  # autoComplete; done once record 18822 has q7269=register (Quest.wz Check/1 infoex)

chr.getAccount().getMonsterCollection().addMobAndUpdateClient(9300174, chr)
sm.consumeItem()
if sm.hasQuest(MONSTER_COLLECTOR):
    sm.setQRValue(18822, "q7269", "register")
    sm.completeQuest(MONSTER_COLLECTOR)
sm.dispose()
