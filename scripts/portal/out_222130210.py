# Fantasy Theme World (Dylan & Delilah, 31363-31392). Official lines where the client has them; otherwise written from
# the quests' Quest.wz summaries / the NPCs' Npc.wz lines. Data: ftwgen/gen_ftw.py.
# Fantasy Crittertorium <Venue> (222130210), portal out_02: back to the entrance (out of your own copy).
if chr.getInstance() is not None:
    sm.warpInstanceOut(222130200, 0)
else:
    sm.warp(222130200, 0)
sm.dispose()
