# Unidentified Assailant (1106007), Chromile's Tent 913070400: A Truth So Cruel (Mihile 20785) - they attack;
# their Cloth Scraps (10) are the clues.
ASSAILANT = 9001056
if not sm.hasHadQuest(20785) and sm.hasQuestCompleted(20784):
    sm.questStartDialogue(20785, 1106007)
if sm.hasQuest(20785) and not sm.hasMobsInField(sm.getFieldID(), ASSAILANT):
    pos = chr.getPosition()
    for i in range(8):
        sm.spawnMob(ASSAILANT, pos.getX() - 350 + i * 100, pos.getY(), False)
    sm.chat("Defeat the Unidentified Assailants and gather 10 Cloth Scraps.")
sm.dispose()
