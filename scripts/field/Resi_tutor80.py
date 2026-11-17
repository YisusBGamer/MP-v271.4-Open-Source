# 931000030: v271's map has lost J (2159010) and Vita (2159007); they stand where the v265 map had them, and J's talk
# ends Dangerous Hide-and-Seek (23007)
J, VITA = 2159010, 2159007

sm.lockInGameUI(False)
if not sm.hasQuestCompleted(23007):
    sm.spawnNpcIfAbsent(VITA, 171, 28)
    sm.spawnNpcIfAbsent(J, 426, 28)
