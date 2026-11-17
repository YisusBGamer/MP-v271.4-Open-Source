# Path behind the mine
# 310040210
# Portal to fight some black wing commander rabbits
# The Resistance story (Battle Mage, Wild Hunter, Mechanic, Blaster, Xenon) goes on behind this portal, in your own
# copies of the maps (from the quests' own text):
#   Gelimer's Trap (23144): Vita waits in Gelimer's Trap.
#   Their Purpose (23146): "Investigate the area where Gelimer installed his trap" - Research Diary A in the rubble.
#   Black Wing Master (23147): Gelimer and Orchid at the entrance to Gelimer's Lab (blackwingmarster).
DIARY_A = 4033095

if sm.hasQuest(23144):
    sm.warpInstanceIn(931000631, 0)
elif sm.hasQuest(23146) and not sm.hasItem(DIARY_A):
    if sm.canHold(DIARY_A):
        sm.warpInstanceIn(931000630, 0)
        sm.giveItem(DIARY_A)
        sm.chatScript("You search the rubble where Gelimer's trap went off... and find Research Diary A.")
    else:
        sm.chatScript("Make some room in your Etc inventory first.")
elif sm.hasQuest(23147) and sm.getQRValue(23150) != "1":
    sm.warpInstanceIn(931000650, 0)
else:
    sm.warp(931050720,2)
sm.dispose()
