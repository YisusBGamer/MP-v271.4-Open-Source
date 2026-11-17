# Jellyrash Rope (2439168), [MONAD: The First Omen] Local Villagers (64084): the rope for Alika in the
# Windsleep Forest Trap. Kept in the inventory (the quest takes it); using it in the trap ties it like the
# rope spot does.
TRAP = 867201760

if sm.hasQuest(64084) and sm.getFieldID() == TRAP:
    if sm.getQRValueByKey(64084, "chk1") != "1":
        sm.setQRValue(64084, "chk1", "1")
    sm.chat("(Maybe just use it for now.)")
elif sm.hasQuest(64084):
    sm.chat("Use it at the pit Alika fell into.")
else:
    sm.chat("The rope is a bit sticky and gross, but it looks strong.")
sm.dispose()
