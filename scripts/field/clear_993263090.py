# Phantasmal Night Sanctum (993263090) cleared: the bout with Noctis is over; ends [Tallahart] Our Destined Roles
# (36995), then back outside to your allies
QUEST = 36995
OUTSIDE = 410013608

if chr.getInstance() is not None and sm.hasQuest(QUEST):
    sm.completeQuestNoCheck(QUEST)
    sm.chatScript("The misunderstanding is cleared up: Noctis gives you her power and the other gods' in exchange for Luce's.")
    sm.invokeAfterDelay(3000, "warpInstanceOut", OUTSIDE, 0)
sm.dispose()
