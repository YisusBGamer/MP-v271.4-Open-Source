# Portal script check_23705.
# Magatia: the hidden room on the 2nd floor next to the Zenumist Society (the Alchemy Lab), for the Xenon story
# (Troubling Reports 23705 through Encounter 23708). The quest gives the password, so it's entered for you.
if any(sm.hasQuest(q) for q in (23705, 23706, 23707, 23708)):
    chr.chatMessage("You enter the password... the hidden door opens.")
    sm.warp(926160000, 1)
else:
    chr.chatMessage("A locked door. It needs a password.")
sm.dispose()
