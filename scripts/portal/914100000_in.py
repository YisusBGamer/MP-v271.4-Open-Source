# Portal script 914100000_in.
# Slumbering Dragon Island harbor -> Snowy Forest (for Meeting Evan, 38038, the copy where Evan waits).
if sm.hasQuest(38038):
    sm.warp(914100011, 1)
else:
    sm.warp(914100010, 1)
sm.dispose()
