# 910142200 (Hidden Street : Little Forest Edge) - Explorer story: Sentrobo guards Luminous's hideout
# (Strangebot in a Strange Land 32345)
if sm.hasQuest(32345) and not sm.isComplete(32345) and not sm.hasMobsInField():
    sm.spawnMob(9300805, 300, -32, False)
    sm.chatScript("A strange robot calls you an intruder!")
