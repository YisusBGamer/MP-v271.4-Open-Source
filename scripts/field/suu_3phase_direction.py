# Lotus's scene before phase 3 (GMS v271 timings)
PHASE, BLACK_FRAME, HIDE_USER = 3, False, False
STEPS = [(1300, ["ani", "Suu3PhaseEnterEff"]), (1500, ["Suu3PhaseEnter0"]), (6690, ["Suu3PhaseEnter1_1"]),
         (1200, ["Suu3PhaseEnter1_2"]), (1200, ["Suu3PhaseEnter1_3"]), (1200, ["Suu3PhaseEnter1_4"]),
         (3600, ["Suu3PhaseEnter2_0"]), (3600, [])]
execfile("scripts/field/_suu_direction.py")
