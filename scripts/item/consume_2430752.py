# Burke's Message (2430752), from Bart for "Suspicious Letter" (53249, Jett 4th job).
# Reading it is what the quest needs (record 53250); Bart (Nautilus) then completes it.
if sm.hasQuest(53249):
    if sm.getQRValue(53250) != "1":
        sm.setQRValue(53250, "1", False)
    sm.sendSayOkay("(It's a letter from #b#p9270084##k, with no return address. Tell #b#p1094000##k what it says.)")
else:
    sm.sendSayOkay("(A letter from #b#p9270084##k.)")
sm.dispose()
