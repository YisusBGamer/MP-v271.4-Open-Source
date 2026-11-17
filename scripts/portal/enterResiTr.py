# Resistance Headquarters: Training Room Entrance (310010010), portal in00
# The Resistance training courses. Each course's monsters live in their own Training Room, and every room's exit leads
# back to this portal: A 310010100 (9300409), B 310010200 (9300410), C 310010300 (9300411), D 310010400 (9300412), and
# the Underground Base's Training Room A 931000400 (9300413) for the test subject's request.
ROOMS = (
    (23103, 310010100), (23104, 310010200), (23105, 310010300), (23106, 310010400),  # Battle Mage, Wild Hunter, Mechanic, Blaster
    (23118, 931000400),                                                              # Request of the Test Subject 3
    (23231, 310010100), (23232, 310010200), (23233, 310010300), (23234, 310010400),  # Demon Slayer, Demon Avenger
    (23235, 310010100), (23236, 310010200),                                          # Demon Fury Training 1-2
    (23632, 310010100), (23633, 310010400),                                          # Xenon: Not Quite Human 1-2
)

target = 0
for quest, field_id in ROOMS:
    if sm.hasQuest(quest):
        target = field_id
        break
if target:
    sm.warp(target, 1)  # out00, by the room's exit
else:
    sm.chat("The training rooms are only open while you're on a training course.")
sm.dispose()
