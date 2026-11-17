# Research Command: Veritas (230050000), portal fromMaple00
# Back to Maple World (the Aquarium portal that leads here), or, during the two Veritas errands that send you after
# a monster, into that errand's Veritas (both maps' exits lead back to this portal):
#   23661 An Alchemist's Desire 1 - the letter Nathania (2300004) lost: 923050000, where 9300640 roams
#   23681 A Gift Most Manly 1 - Professor Dreamboat's (2300001) errand: 923050005, where 9300644 roams
ERRANDS = ((23661, 923050000), (23681, 923050005))

target = 0
for quest, field_id in ERRANDS:
    if sm.hasQuest(quest):
        target = field_id
        break
if target:
    sm.warp(target, 1)  # by the map's exit, outVeritas
else:
    sm.warp(230000000, 15)  # Aquarium, portal fromVeritas00
sm.dispose()
