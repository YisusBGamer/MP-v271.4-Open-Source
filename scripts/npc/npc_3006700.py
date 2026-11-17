# Selazar (3006700), Cernium Square (410000500): the Epic Dungeon window. High Mountain: Lv. 260, after
# [Cernium (After)] Defensive Tactics (39921); story mode until [Epic Dungeon] Sun God Mitra (26216), then the weekly
# climb (10 stages, resets Thursday). The run itself is HighMountainInstanceHelper.
from net.swordie.ms.world.field.instance.instancehelper.epic import EpicDungeon as ED

sm.setSpeakerID(ED.SELAZAR)
if chr.getLevel() < ED.HM_LEVEL:
    sm.sendSayOkay("The tale of #bHigh Mountain#k is for those of #bLv. 260#k and above. Come back when you're stronger.")
elif not sm.hasQuestCompleted(39921):
    sm.sendSayOkay("Cernium still needs you. Finish #b[Cernium (After)] Defensive Tactics#k first, then I'll tell "
                   "you the tale of the Sun God.")
else:
    ED.openWindow(chr, ED.REGION_HIGH_MOUNTAIN)
sm.dispose()
