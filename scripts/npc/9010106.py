# Dame Appropriation (9010106), Legion Manager in the towns
from net.swordie.ms.constants import QuestConstants
from net.swordie.ms.enums import UIType

if not sm.hasQuestCompleted(16013) and not sm.hasQuest(16014) and not sm.hasQuestCompleted(16014):
    sm.sendSayOkay("Hey there! Legions are for battle-hardened warriors: a #bCumulative Level of 500#k over at least "
                   "#b3 characters#k (Lv. 60+, 2nd job). Come back once you're ready!")
else:
    choice = sm.sendNext("Hey there! Ready to power up your Legion?\r\n"
                         "#b#L0#Open my Legion#l\r\n"
                         "#L1#Enter the Legion Raid#l\r\n"
                         "#L2#Legion Coin Shop#l")
    if choice == 0:
        sm.openUI(UIType.UNION_BOARD)
    elif choice == 1:
        sm.createQuestWithQRValue(QuestConstants.UNION_MAP, "map=" + str(sm.getFieldID()))
        if chr.getUnionRaid() is not None:
            chr.getUnionRaid().enter(chr)
        else:
            sm.warpInstanceIn(921172000, 0)
    elif choice == 2:
        sm.openShop(9010111)
sm.dispose()
