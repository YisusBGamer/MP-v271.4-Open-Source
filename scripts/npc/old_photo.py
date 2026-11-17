# Old photo in the Empty House (2159389). Claudine's Friend (23637, Xenon): take the Old Photo back
# to Claudine.
PHOTO = 4033636
if sm.hasQuest(23637) and not sm.hasItem(PHOTO):
    if sm.canHold(PHOTO):
        sm.giveItem(PHOTO)
        sm.sendSayOkay("(A faded photo of two children smiling in old Edelstein. Take it to #bClaudine#k.)")
    else:
        sm.sendSayOkay("(Make room in your Etc inventory to take the photo.)")
else:
    sm.sendSayOkay("(A dusty frame on an empty shelf.)")
sm.dispose()
