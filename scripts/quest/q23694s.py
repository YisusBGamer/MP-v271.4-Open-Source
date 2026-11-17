# Data Mining 2 (23694, Xenon story): turning on the device brings a horde of Black Wings Goons into the Forgotten
# Laboratory. If you fail, forfeit and start the quest again.
if sm.questStartDialogue(parentID) and sm.getFieldID() == 931060050 and not sm.hasMobsInField():
    for x in (-450, -250, -50, 150, 350):
        sm.spawnMob(9300650, x, 63, False)
