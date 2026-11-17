# Beasts of Fury (Mu Lung dojos): the fight the quest asks for. Data: bofgen/gen_bof.py.
# Duel map 706041600 (first one in): Brother Wukong's copy (9601007), for quest 56570.
if sm.hasQuest(56570) and not sm.isComplete(56570) and not sm.hasMobsInField(706041600, 9601007):
    sm.spawnMob(9601007, 0, 51, False)
