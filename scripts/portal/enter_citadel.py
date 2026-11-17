# 401050000 Tyrant's Castle Entryway. While Kaiser is inside the castle in disguise (Wounded Victory 31492 to Escape the
# Tyrant's Castle 31495) the gate leads into the Specter-held foyer (401050200); otherwise into the castle.
INFILTRATION = (31492, 31493, 31494, 31495)
if any(sm.hasQuest(q) for q in INFILTRATION):
    sm.warp(401050200, 0)
else:
    sm.warp(401050001, 0)
sm.dispose()
