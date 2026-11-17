# 505003 [Legion] Enter the Legion Raid (Dame Appropriation 9010106): a step of the Weekly Dragon Extermination
# (503281). Into the Dragon's Domain like Dame Appropriation's "Enter the Legion Raid". Not recorded, so the
# shortcut stays on offer for every visit while the weekly quest runs.
from net.swordie.ms.constants import QuestConstants

sm.setSpeakerID(9010106)
sm.sendNext("The #bLegion Raid#k is ready. I'll send you to the #rDragon's Domain#k to carry out the "
            "#b[Legion] Weekly Dragon Extermination#k!")
sm.createQuestWithQRValue(QuestConstants.UNION_MAP, "map=" + str(sm.getFieldID()))
if chr.getUnionRaid() is not None:
    chr.getUnionRaid().enter(chr)
else:
    sm.warpInstanceIn(921172000, 0)
sm.dispose()
