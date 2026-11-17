# Phantasmal Night Sanctum (993263080) cleared: Baldrix is beaten and slips away; ends [Tallahart] The Decisive Battle
# (36994), then on to Noctis's seal
QUEST = 36994
NOCTIS_SEAL = 410013608

if chr.getInstance() is not None and sm.hasQuest(QUEST):
    sm.completeQuestNoCheck(QUEST)
    sm.chatScript("You defeated Baldrix, but he escaped while you looked at Noctis's seal.")
    sm.invokeAfterDelay(3000, "warpInstanceOut", NOCTIS_SEAL, 0)
sm.dispose()
