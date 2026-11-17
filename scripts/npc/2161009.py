# Queen Ifia (2161009) before the Lion King, past the Secret Corridor (921140001) - her Npc.wz script, "talk2161009"
# She gives Ifia's Pendant, which [Lion King's Castle] Is There Any Hope? (3178) is handed in with. No official lines
# exist for the meeting; written from the quest's own summary ("Ifia said there still is good in the Lion King's
# heart, and that she will do everything it takes to turn him back").
IFIA = 2161009
PENDANT = 4032839  # Ifia's Pendant

sm.setSpeakerID(IFIA)
if sm.hasQuest(3178) and not sm.hasItem(PENDANT):
    if not sm.canHold(PENDANT):
        sm.sendSayOkay("Please make some room in your Etc inventory first.")
    else:
        sm.sendNext("Leon didn't say he would stop helping the Black Mage... But I saw it in his eyes. "
                    "There is still good in his heart.")
        sm.sendSay("Please keep this pendant for me. Whatever it takes, I will bring him back to the way he was. "
                   "Let's go back now.")
        sm.giveItem(PENDANT)
        sm.warpInstanceOut(211061001, 0)
else:
    line = sm.getRandomNpcDefaultLine(IFIA)
    if line is not None:
        sm.sendSayOkay(line)
    else:
        sm.sendSayOkay("He'll return to his old self. I know it.")
sm.dispose()
