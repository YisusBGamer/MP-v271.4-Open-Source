# Wild Boar Land (102030000) and Initial Excavation Area (102040000), the first maps past Perion whose monsters are well
# above the town's: a character 10+ levels under the weakest monster here is warned on entering.
from net.swordie.ms.client.character import QuickMoveModule

levels = QuickMoveModule.getMobLevels(sm.getFieldID())
if levels is not None and len(levels) == 2 and chr.getLevel() + 10 <= levels[0]:
    if levels[0] == levels[1]:
        lv = "Lv. %d" % levels[0]
    else:
        lv = "Lv. %d-%d" % (levels[0], levels[1])
    sm.chatScript("Warning: the monsters in this area are %s, much stronger than you. Be careful." % lv)
sm.dispose()
