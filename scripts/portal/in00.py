# in00: a portal named in00 with no script of its own runs this one (the server falls back to the portal's name),
# so the destination is picked by map (Crimsonwood Keep's Courtyard, Fantasy Theme World's Main Gate, ...).
fid = sm.getFieldID()
if fid == 100030300:
    # Evan's farm (Farm Center): the dragon egg's room while "Rescuing the Piglet" is on (inDragonEgg's own logic)
    if sm.hasQuest(22005):
        sm.warp(900020100, 0)
    else:
        sm.warp(100030301, 0)
elif fid == 103020000:
    sm.warp(103020100, 0)  # Kerning City Subway: the Ticketing Booth -> Along the Railway (the subway is free now)
elif fid == 223000000:
    sm.warp(223010000, 0)  # Fantasy Theme World <Main Gate> -> Funky Station <1>
elif 610030000 <= fid < 610040000:
    sm.warp(610030000)  # Crimsonwood Keep: the castle entrance -> the Courtyard
else:
    sm.chat("This way is closed.")
sm.dispose()
