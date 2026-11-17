# Jett tutorial | 552000060 Scene Change 1: the story so far, then the space station
def skip_tutorial():
    MAPLE_ADMINISTARTOR = 2007

    quests_to_complete = [
    ]

    map_to_warp = 552000050 # Nautilus bunkroom
    target_level = 10

    sm.setSpeakerID(MAPLE_ADMINISTARTOR)
    sm.removeEscapeButton()

    if sm.askTutorialSkip(map_to_warp):
        if sm.getChr().getLevel() < target_level:
            sm.addLevel(target_level - sm.getChr().getLevel())

        for quest in quests_to_complete:
            sm.completeQuestNoRewards(quest)
        sm.levelUntil(10)

        sm.jobAdvance(508)
        sm.setSTR(4)
        sm.setINT(4)
        sm.setDEX(4)
        sm.setLUK(4)
        sm.setAP(4 + chr.getLevel() * 5)
        sm.warp(map_to_warp)
        return True
    return False
# --------
if not skip_tutorial():
    # the tutorial's quests (53245-53247) are for Jetts
    if chr.getJob() != 508:
        sm.jobAdvance(508)
    sm.lockInGameUI(True, False)
    sm.setPlayerAsSpeaker()
    sm.removeEscapeButton()
    sm.sendNext("(The King of Cerberus is dead, and the royal guard thinks I did it. I'm a bounty hunter, not an "
                "assassin!)")
    sm.sendSay("(Burke and I have to get off this station before the royal guard finds us.)")
    sm.lockInGameUI(False)
    sm.warp(552000010)
