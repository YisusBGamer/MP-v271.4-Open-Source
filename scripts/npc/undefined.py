# Default NPC script, used when an NPC has no script file. NPCs with default lines in the client data
# (String.wz/Npc.img d0, d1, ..., made into strings/npc_default_lines.txt in the dat folder) say one at random.
line = sm.getRandomNpcDefaultLine(parentID)
if line is not None:
    sm.setSpeakerID(parentID)
    sm.sendSayOkay(line)
else:
    sm.debugChat("(Npc) Not coded. ID: {}, Field: {}".format(parentID, sm.getFieldID()))
