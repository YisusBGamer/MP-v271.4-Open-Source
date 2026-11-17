# 57148 - Mu Lung Mojo
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Do Gong (2091001)

sm.setSpeakerID(2091001)  # Do Gong
sm.sendNext("You would not know this, but Maple World's energy flows strongest in Mu Lung. One might even say that Mu Lung is the piece that holds the whole puzzle together. If evil is afoot in Mu Lung, you can be sure that Maple World's energy is the goal.")
if sm.sendAskAccept("The people you speak of are after Mu Lung's energy, no doubt. If they were sighted near the Peach Farm, they will soon arrive at the forest, where the energy flows most strongly. You must follow them."):
    sm.startQuest(parentID)
    sm.sendNext("There's a forest deep within the farm; a forest teeming with monsters warped by Mu Lung's energy. The Sage Cats are particularly nasty, but I would like you to hunt them.")
else:
    sm.sendNext("Success rarely follows apathy.")
