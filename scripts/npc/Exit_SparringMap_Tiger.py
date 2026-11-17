# Beasts of Fury (Mu Lung dojos): the fight the quest asks for. Data: bofgen/gen_bof.py.
# Red Tiger Dojo (in the duel / sparring maps): back to the Drill Hall.
if sm.sendAskYesNo("Do you want to go back to the Drill Hall?"):
    if chr.getInstance() is not None:
        sm.warpInstanceOut(706041008, 0)
    else:
        sm.warp(706041008, 0)
sm.dispose()
