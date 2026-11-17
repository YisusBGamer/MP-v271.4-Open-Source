# Queen Ifia (2161001), Roof of the Fifth Tower - her Npc.wz script, "ipia_teleport"
# During [Lion King's Castle] Is There Any Hope? (3178) she takes you along the Secret Corridor to the Lion King
# again (if you left before reaching him); otherwise she says her own client lines.
IFIA = 2161001

sm.setSpeakerID(IFIA)
if sm.hasQuest(3178) and not sm.hasItem(4032839):
    if sm.sendAskYesNo("Will you come with me to see Leon? We must take the Secret Corridor - "
                       "the one only he and I ever used."):
        sm.warpInstanceIn(921140000, 0)
else:
    line = sm.getRandomNpcDefaultLine(IFIA)
    if line is not None:
        sm.sendSayOkay(line)
sm.dispose()
