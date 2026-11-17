# Portal script inZephi.
# Giant Tree (North Forest): Jeppi's Hideout, for The Kerning City Book Store Owner 2 (2631). The Dark Lord's
# minions are there too (field/q2631M).
if sm.hasQuest(2631):
    sm.warp(910100200, 3)
else:
    chr.chatMessage("A hollow in the giant tree. Nobody seems to be home.")
sm.dispose()
