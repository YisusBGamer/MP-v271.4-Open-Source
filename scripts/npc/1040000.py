# Luke (1040000), the guard at the Six Path Crossway. Explorer story, Hat Required (32248): Rondo's hat fell down here
# and Luke picked it up - he can't tell which one is Rondo's, so pick it.
HATS = (4033881, 4033882, 4033883)  # Red Starry Bandana, Green M-Forcer Helmet, Old Wisconsin
if sm.hasQuest(32248) and not any(sm.hasItem(h) for h in HATS):
    sm.sendNext("A hat? Funny you should ask. A few came tumbling down from up there today. Folks sure lose a lot of hats around here.")
    sel = sm.sendNext("Which one are you looking for?\r\n#L0##i4033881# #t4033881##l\r\n#L1##i4033882# #t4033882##l\r\n#L2##i4033883# #t4033883##l")
    if 0 <= sel < len(HATS):
        sm.giveItem(HATS[sel])
        sm.sendSayOkay("Here you go. Try not to lose it again, eh?")
else:
    sm.sendSayOkay("I'm keeping watch over the Six Path Crossway. Be careful out there.")
