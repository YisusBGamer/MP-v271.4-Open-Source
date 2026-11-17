# NPC 2450023's Npc.wz script name is "q41207_NPCscript", so this file runs when it is clicked instead of 2450023.py.
# Keep the two the same.
# Mirror World side quest (Zero; Swordie Plus). 2450023 Edelstein Message Board (Mirror World) - Aspirations of becoming number 1 (41207): post the petition
if sm.hasQuest(41207) and sm.getQRValue(41207) != "clear=clear":
    sm.setQRValue(41207, "clear", "clear")
    sm.chatScript("You posted Anthony's petition on the message board.")
else:
    sm.chatScript("Notices about the city cover the board.")
sm.dispose()
