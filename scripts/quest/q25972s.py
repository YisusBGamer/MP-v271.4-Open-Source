# [Star Force] Equipment Star Power: the explanation offered after attacking in a starstruck area.
if not sm.hasQuestCompleted(parentID) and chr.getQuestManager().canStartQuestFromScript(parentID):
    sm.setSpeakerID(2300001)
    sm.sendNext("The fallen stars have changed the monsters in that area. Their power can neutralize your attacks.")
    sm.sendNext("Enhance your equipment with Star Force. Your equipped items' combined Star Force must meet the requirement shown for the area to overcome that protection.")
    sm.sendNext("Once your equipment has enough Star Force, return and defeat the monsters. You can check your Star Force in your character's stats.")
    sm.startQuest(parentID)
    sm.completeQuest(parentID)
