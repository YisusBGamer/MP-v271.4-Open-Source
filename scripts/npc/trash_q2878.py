# Knocked Trash Can (1052108), Hut in the Swamp (103030101), for 'Examine the Hut in the Swamp' (2878): "Go to the Hut
# in the Swamp and look inside the Knocked Trash Can." JM From tha Streetz waits for the quest's record 2878 = "check"
# (Check.img). The line is the trash can's own (Npc.wz); anything else the official script did isn't in the game data.
QUEST = 2878

sm.sendSayOkay("(A trash can that has been knocked down. Contains a piece of paper or two mixed with dirt.)")
if sm.hasQuest(QUEST) and sm.getQRValue(QUEST) != "check":
    sm.setQRValue(QUEST, "check", False)
sm.dispose()
