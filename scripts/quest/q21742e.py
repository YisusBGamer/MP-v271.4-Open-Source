# The Black Scroll (21742), end - Mr. Do (2090004). The scroll is restored with Jin Jin's Special Ink (21743: "open
# the Hanging Scroll and pour the Special Ink over it"); the quest checks its record 21763 = 800. The scroll can't be
# opened from the inventory on this server (no script existed for it), so the ink is poured when you bring the scroll
# back, then Mr. Do's official end lines play.
SCROLL, INK, REC = 4220151, 4032342, 21763
if sm.hasQuest(parentID):
    if sm.getQRValue(REC) != "800" and sm.hasItem(INK) and sm.hasItem(SCROLL):
        sm.setPlayerAsSpeaker()
        sm.sendNext("#b(You open the #t4220151# and pour the #t4032342# all over it. The erased writing slowly "
                    "comes back.)#k")
        sm.consumeItem(INK, sm.getQuantityOfItem(INK))
        sm.setQRValue(REC, "800")
    sm.questEndDialogue(parentID, 2090004)
sm.dispose()
