# id 811000033 (Hieizan : Inside Small Shrine), field 811000033
# Princess No fights you for The End of Princess No (58927 / 58940) - the fight was missing
# (The recorded script started 58940 for everyone; each quest line starts its own quest.)
if (sm.hasQuest(58927) or sm.hasQuest(58940)) and not sm.hasMobsInField():
    sm.spawnMob(9450040, 200, -195, False)
sm.createQuestWithQRValue(58973, "m040=clear;m035=clear;dr1=clear;dr2=clear;m037=clear;m038=clear;m039=clear")
