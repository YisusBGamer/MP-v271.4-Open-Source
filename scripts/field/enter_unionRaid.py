#
#   Legion Raid: Dragon's Domain (921172000, 921172100), onUserEnter
#   The dragon, the whelp waves and the raid counters start here, whichever way the player came in.
#
raid = chr.getUnionRaid()
if raid is not None:
    raid.onFieldEnter(chr)
