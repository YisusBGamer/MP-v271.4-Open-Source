# Beasts of Fury (Mu Lung dojos): the fight the quest asks for. Data: bofgen/gen_bof.py.
# Red Tiger Dojo 3F (706041450), first one in: Apprentice Revenge (56581) - the Evil Snake Brothers (9601015), whom the quest
# counts as 'The Poisoner' (its quest-count group).
if sm.hasQuest(56581) and not sm.isComplete(56581) and not sm.hasMobsInField(706041450, 9601015):
    sm.spawnMob(9601015, 0, 127, False)
