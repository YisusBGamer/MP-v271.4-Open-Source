# 931000010 Suspicious Laboratory: v271's map has lost Vita (2159006); she is placed where the v265 map had her, so
# her two talks (npc 2159006) lead on to 931000011
VITA = 2159006

sm.avatarOriented("Effect/OnUserEff.img/guideEffect/resistanceTutorial/userTalk")
if "vel00=2" not in sm.getQRValue(23007):
    sm.spawnNpcIfAbsent(VITA, 903, -182)
