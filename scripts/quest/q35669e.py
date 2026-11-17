# [Genesis Weapon] Essence Reissue (35669): hand-in, with the Essence if it is still missing.
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
if sm.hasQuest(parentID) and give_essence():
    sm.completeQuestNoCheck(parentID)
sm.dispose()
