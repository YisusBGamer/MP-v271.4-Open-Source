# 3476 [Omega Sector] Gather the M-Forcers - start (Dr. Kim, Command Center 221000300). The shrunken M-Forcers and
# Porter are in the Silo (221000201), which no portal leads to: the quest takes you there (npc/2052006.py brings you
# back while the M-Force quests 3476-3477 are under way).
SILO = 221000201

if sm.questStartDialogue(parentID, 2052006):
    if sm.sendAskYesNo("#b(Go to the #m221000201# to find the shrunken M-Forcers?)#k"):
        sm.warp(SILO, 0)
sm.dispose()
