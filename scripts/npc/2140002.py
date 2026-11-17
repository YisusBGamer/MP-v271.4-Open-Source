# The Sorcerer (2140002), Sorcerer's Room (270020211). [Twilight Perion] Magic Refinement 2 (31963): his refinement
# chamber (913051301, field/enter_913051301) where the Fog of Oblivion is refined.
QUEST = 31963
CHAMBER = 913051301

sm.setSpeakerID(parentID)
if sm.hasQuest(QUEST) and sm.getQRValue(31966, "yes") != "fog":
    if sm.sendAskYesNo("The Fog of Oblivion won't refine itself. Help me in my refinement chamber?"):
        sm.warpInstanceIn(CHAMBER, 0)
        sm.setInstanceTime(600, 270020211)
else:
    line = sm.getRandomNpcDefaultLine(parentID)
    sm.sendSayOkay(line if line is not None else "Oblivion... it takes everything in the end.")
sm.dispose()
