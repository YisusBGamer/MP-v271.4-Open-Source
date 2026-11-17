# [Genesis Weapon] Essence Reissue (35669): Zero only. Neinheart replaces a lost Genesis Essence.
GENESIS_ESSENCE = 4310260


def give_essence():
    if sm.hasItem(GENESIS_ESSENCE):
        return True
    if not sm.canHold(GENESIS_ESSENCE):
        sm.sendSayOkay("Make room in your Etc inventory first.")
        return False
    sm.giveItem(GENESIS_ESSENCE)
    return True


sm.setSpeakerID(9063150)
if chr.getJob() // 100 != 101:
    sm.sendSayOkay("Only Zero uses the Genesis Essence.")
elif not sm.hasQuestCompleted(35667):
    sm.sendSayOkay("Unleash the Genesis Weapon's true power first.")
elif sm.hasItem(GENESIS_ESSENCE):
    sm.sendSayOkay("You still have your Genesis Essence. Take good care of it.")
elif give_essence():
    if not sm.hasQuest(parentID):
        sm.startQuestNoCheck(parentID)
    sm.completeQuestNoCheck(parentID)
sm.dispose()
