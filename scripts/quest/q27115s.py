# [Epic Dungeon] Delver of the Deep (27115, Selazar 3006760): the medal once the Angler Company story is told
from net.swordie.ms.world.field.instance.instancehelper.epic import EpicDungeon as ED

sm.setSpeakerID(ED.SELAZAR_ODIUM)
if not sm.hasQuestCompleted(ED.ANGLER_STORY_QUEST):
    sm.sendSayOkay("The song isn't over yet. Bring Cosim out of the deep first.")
elif sm.hasQuestCompleted(parentID):
    sm.sendSayOkay("The song is told. Thank you for listening to the end.")
elif not sm.canHold(ED.ANGLER_MEDAL):
    sm.sendSayOkay("I've made something for you, but your Equip inventory is full. Make some room first.")
else:
    sm.sendNext("Cosim's tale is told, and you were there to the very end. Here, a little something to remember "
                "the deep by.")
    if not sm.hasQuest(parentID):
        sm.startQuestNoCheck(parentID)
    sm.completeQuestNoRewards(parentID)
    sm.giveItem(ED.ANGLER_MEDAL)
sm.dispose()
