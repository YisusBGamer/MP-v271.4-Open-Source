# id 401040000 (Heliseum Downtown : Downtown Black Market), field 401040000
# The Third Person (31662, Angelic Buster): Eurenth sends you back to the central region, where you watch Kaiser and
# Velderoth fight; Edea's end check waits for record 31663.
if sm.hasQuest(31662) and sm.getQRValue(31663) != "1":
    sm.startQuest(31663)
    sm.setQRValue(31663, "1")
    sm.chat("You returned to the central region and watched the battle between Kaiser and Velderoth. Go see Edea.")
# Edea Rage! (31673): record 31684 is set when Edea gives the order (quest/q31673s.py); this covers players who
# accepted it before that script existed.
if sm.hasQuest(31673) and sm.getQRValue(31684) != "1":
    sm.startQuest(31684)
    sm.setQRValue(31684, "1")
sm.dispose()
