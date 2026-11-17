# Beasts of Fury (Mu Lung dojos): the fight the quest asks for. Data: bofgen/gen_bof.py.
# Gold Dragon Gate (Npc.wz script SoloDungeonEnter_Tiger): stands in the dojo (706041009) and on the other dojo's last floor (706041400).
# Apprentice Revenge (56608): "Head to the Gold Dragon portal I summoned up and get our revenge!" - the gate takes you there (your own copy); the Evil Snake Brothers wait
# (field/Enter_706041400). On that floor the gate takes you back.
if sm.getFieldID() == 706041400:
    if sm.sendAskYesNo("Do you want to go back to your dojo?"):
        if chr.getInstance() is not None:
            sm.warpInstanceOut(706041009, 0)
        else:
            sm.warp(706041009, 0)
elif sm.hasQuest(56608) and not sm.isComplete(56608):
    if sm.sendAskYesNo("(The Gold Dragon Gate shimmers. Go through?)"):
        sm.warpInstanceIn(706041400, 0)
else:
    line = sm.getRandomNpcDefaultLine(parentID)  # as the default NPC script does
    if line is not None:
        sm.setSpeakerID(parentID)
        sm.sendSayOkay(line)
sm.dispose()
