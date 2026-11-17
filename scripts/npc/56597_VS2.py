# Beasts of Fury (Mu Lung dojos): the fight the quest asks for. Data: bofgen/gen_bof.py.
# Brother Sondal (56597): '56597_VS2' - the NPC's Npc.wz script. Talking to him while the duel quest is open takes you to the
# duel (your own copy of the map), where his copy waits (field/56597_VS2_SummonMob).
if sm.hasQuest(56597) and not sm.isComplete(56597):
    if sm.sendAskYesNo("We usually don't let the youngest duel, but it's a special occasion. Let's just keep this between you and me, eh? Let me know when you're ready!" + "\r\n\r\n#b(Duel Brother Sondal now?)#k"):
        sm.warpInstanceIn(706041650, 0)
else:
    line = sm.getRandomNpcDefaultLine(parentID)  # as the default NPC script does
    if line is not None:
        sm.setSpeakerID(parentID)
        sm.sendSayOkay(line)
sm.dispose()
