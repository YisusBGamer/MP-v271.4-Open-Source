# Black Wings beaten in the Cave of Silence (914100023): Dangerous Premonition (22589, Evan) is done here
# (quest record 22604, which the quest checks). On to the sleeping dragon.
if sm.hasQuest(22589) and sm.getQRValue(22604) != "1":
    sm.setQRValue(22604, "1", False)
    chr.chatMessage("Ibech's henchmen are down. Ibech was using you all along...")
    sm.warpInstanceOut(914100021, 2)
