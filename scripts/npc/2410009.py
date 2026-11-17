# Peculiar Puzzle (2410009) | Shadowvale Clinic, Treatment Room (321190140)
# Mirror World Map (40051) is handed in here once its record 40069 says clear=clear: the puzzle is the map.
sm.setSpeakerID(parentID)
if sm.hasQuest(40051) and sm.getQRValue(40069, "clear") != "clear":
    sm.sendSayOkay("(This puzzle looks like a map, but it's very peculiar looking.)\r\n\r\n"
                   "#b(Ariant, Ludibrium... These are all areas of Maple World. This must be the Mirror World Map!)#k")
    sm.setQRValue(40069, "clear", "clear")
    sm.chatScript("You found the Mirror World Map. Talk to the Peculiar Puzzle again to finish the quest.")
else:
    sm.sendSayOkay("(This puzzle looks like a map, but it's very peculiar looking.)")
