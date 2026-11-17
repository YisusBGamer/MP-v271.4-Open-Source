# Easy Balrog altar: spawn, wait for the damage sink to die, then everyone to the treasure room
from time import sleep

WIN_MAP = 105100401

sm.spawnBalrog(True)
sm.waitForMobDeath(8830010)
sm.showFieldEffect("Map/Effect.img/balog/clear/stone")
sleep(1)
win = chr.getOrCreateFieldByCurrentInstanceType(WIN_MAP)
for c in list(chr.getField().getChars()):
    c.warp(win)
