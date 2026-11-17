# Large Boss Medal of Honor
# 2633892
# Picked up, it turns into Honor EXP right away (Char.addDrop, like the other Medals of Honor); this script covers any
# still in an inventory and gives the same 5,000 Honor EXP.
from net.swordie.ms.constants import ItemConstants

sm.consumeItem()
chr.addHonorExp(ItemConstants.LARGE_BOSS_MEDAL_OF_HONOR_EXP)
sm.chatScript("You gained " + str(ItemConstants.LARGE_BOSS_MEDAL_OF_HONOR_EXP) + " Honor EXP.")
