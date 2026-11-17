# [Tallahart] Our Destined Roles (36995): the bout with Noctis at her seal, in an own copy of the Phantasmal Night
# Sanctum where she stands (993263090, Map.wz life); its clear script ends the quest.
SPEAKER = 3006837
NOCTIS_FIGHT = 993263090

sm.setSpeakerID(SPEAKER)
if not sm.hasQuest(parentID) and not sm.hasQuestCompleted(parentID):
    if sm.sendAskAccept("A powerful energy is pouring out of #bNoctis's#k seal. Luce asked you to save her... is that "
                        "even possible?"):
        sm.startQuest(parentID)
if sm.hasQuest(parentID):
    sm.warpInstanceIn(NOCTIS_FIGHT, 0)
sm.dispose()
