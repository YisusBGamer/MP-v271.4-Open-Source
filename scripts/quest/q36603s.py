# 36603 Equipment Check (self start, Lv 10): equip the Beginner Warrior's Sword.
execfile("scripts/quest/_mihile_story.py")

me("(Oh, I forgot to put on my new equipment.)")
me("(Equip the #bBeginner Warrior's Sword#k in the #bEquip tab#k.)")
begin(parentID)
if not sm.hasItem(SWORD) and not sm.isEquipped(SWORD):
    sm.chatScript("If you've lost your sword, go tell Father.")
sm.dispose()
