# Beasts of Fury (Mu Lung dojos): the fight the quest asks for. Data: bofgen/gen_bof.py.
# Duel map 706041650 (first one in): Brother Sondal's copy (9601008), for quest 56597.
if sm.hasQuest(56597) and not sm.isComplete(56597) and not sm.hasMobsInField(706041650, 9601008):
    sm.spawnMob(9601008, 0, 51, False)
