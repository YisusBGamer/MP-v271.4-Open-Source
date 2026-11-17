# map id 2620MXDDD: M = mode (0 = attack), DDD%10 = difficulty
mode = (chr.getFieldID() / 1000) % 10

if mode == 0:
    sm.waitForMobDeath()
