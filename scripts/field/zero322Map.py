# 322000000 [Mirror World] Ariant (Swordie Plus): the NPCs of the Sand Bandits' side story, which Map.wz doesn't place.
# A Letter from the Sand Bandits (41063) and Phantom's Letter (41064) start at letters left in town, and Phantom's
# Letter is handed in to Phantom himself. They only appear while a character on the field needs them.
LETTER_SAND_BANDITS = 2420021
LETTER_PHANTOM = 2420024
PHANTOM = 2420015


def can_start(qid, prev):
    return sm.hasQuestCompleted(prev) and not sm.hasQuest(qid) and not sm.hasQuestCompleted(qid) \
        and chr.getLevel() >= 115


if can_start(41063, 41062):
    sm.spawnNpcIfAbsent(LETTER_SAND_BANDITS, 1600, 271)  # by the house with the blue and pink canopy
if can_start(41064, 41063):
    sm.spawnNpcIfAbsent(LETTER_PHANTOM, 1750, 271)
if sm.hasQuest(41064):
    sm.spawnNpcIfAbsent(PHANTOM, 2300, 276)  # by the path to the Sand Bandits' hideout
