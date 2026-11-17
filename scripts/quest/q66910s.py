# 66910 I Will Light Your Way - start (the Earth Guardian Pan, Lynn). The lamp in front of the hut's entrance (the
# Sparking Crystal Ball, 4037238) is knocked loose with the Delicate Branch; the map has nothing to hit for it here, so
# it's taken right after accepting. Handed in to Pan.
LAMP = 4037238

if sm.questStartDialogue(parentID, 9401822):
    if not sm.hasItem(LAMP) and sm.canHold(LAMP, 1):
        sm.setPlayerAsSpeaker()
        sm.sendNext("#b(You swing the Delicate Branch at the lamp in front of the entrance. It falls right into your "
                    "hands.)#k")
        sm.giveItem(LAMP)
sm.dispose()
