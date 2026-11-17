# Beasts of Fury (Mu Lung dojos): the fight the quest asks for. Data: bofgen/gen_bof.py.
# Brother Wukong (56570): '56570_VS1' - the NPC's Npc.wz script. Talking to him while the duel quest is open takes you to the
# duel (your own copy of the map), where his copy waits (field/56570_VS1_SummonMob).
if sm.hasQuest(56570) and not sm.isComplete(56570):
    if sm.sendAskYesNo("I like your spirit! Talk to me when you're ready to duel!" + "\r\n\r\n#b(Duel Brother Wukong now?)#k"):
        sm.warpInstanceIn(706041600, 0)
else:
    line = sm.getRandomNpcDefaultLine(parentID)  # as the default NPC script does
    if line is not None:
        sm.setSpeakerID(parentID)
        sm.sendSayOkay(line)
sm.dispose()
