# Beasts of Fury (Mu Lung dojos): the fight the quest asks for. Data: bofgen/gen_bof.py.
# Red Tiger Gate (Npc.wz script SoloDungeonEnter_Dragon): stands in the dojo (706041004) and on the other dojo's last floor (706041450).
# Apprentice Revenge (56581): "Head to the Red Tiger portal I summoned up and get our revenge!" - the gate takes you there (your own copy); the Evil Snake Brothers wait
# (field/Enter_706041450). On that floor the gate takes you back.
if sm.getFieldID() == 706041450:
    if sm.sendAskYesNo("Do you want to go back to your dojo?"):
        if chr.getInstance() is not None:
            sm.warpInstanceOut(706041004, 0)
        else:
            sm.warp(706041004, 0)
elif sm.hasQuest(56581) and not sm.isComplete(56581):
    if sm.sendAskYesNo("(The Red Tiger Gate shimmers. Go through?)"):
        sm.warpInstanceIn(706041450, 0)
else:
    line = sm.getRandomNpcDefaultLine(parentID)  # as the default NPC script does
    if line is not None:
        sm.setSpeakerID(parentID)
        sm.sendSayOkay(line)
sm.dispose()
