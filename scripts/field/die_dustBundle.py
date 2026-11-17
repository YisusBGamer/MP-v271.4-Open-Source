# Dust Bundle (3501104) dies | Ruenna's Home, 2nd Floor Attic (Elodin)
# "Spring Cleaning" (37164): "Sweep away those evil Dust Bundles with the Makeshift Broomsticks" until its record
# reaches count 50 (Quest.wz). The quest hands out 3 broomsticks and more are made from Hay (item/consume_2630159);
# the player who did the most damage sweeps the Dust Bundle, which uses up one of their broomsticks.
SPRING_CLEANING = 37164
BROOMSTICK = 4036502
GOAL = 50
sweeper = mob.getMostDamageChar() if mob is not None else None
if sweeper is not None and sweeper.getQuestManager().hasQuestInProgress(SPRING_CLEANING):
    ssm = sweeper.getScriptManager()
    try:
        swept = int(ssm.getQRValue(SPRING_CLEANING, "count") or "0")
    except ValueError:
        swept = 0
    if swept < GOAL and ssm.hasItem(BROOMSTICK):
        ssm.consumeItem(BROOMSTICK, 1)
        ssm.setQRValue(SPRING_CLEANING, "count", str(swept + 1))
