# 271030410 (Knight Stronghold: Secret Grove), onFirstUserEnter
# [Knight Stronghold] 31149 ends when the grove is found (Quest.wz: its record "find"); nothing else sets it.
FIND = 31149
if sm.getChr() is not None and sm.hasQuest(FIND) and sm.getQRValue(FIND) != "find":
    sm.setQRValue(FIND, "find", False)
    sm.updateQRValue(FIND, True)
