# To the Hideout (20939, Cygnus Knights in Edelstein): Ulrika leads you to the "hideout" - a trap. Save the kids from
# the Black Wing Henchmen (enter_930030001; the mission is 20946). If you fail, forfeit and start the quest again.
if sm.questStartDialogue(parentID):
    sm.warpInstanceIn(930030001, 0)
