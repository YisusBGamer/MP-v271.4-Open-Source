# Portal script check_23637.
# Edelstein: the blue door to the right of the mansion leads to the Empty House, for Claudine's Friend (23637, Xenon).
if sm.hasQuest(23637):
    sm.warp(931060000, 1)
else:
    chr.chatMessage("The door is locked.")
sm.dispose()
