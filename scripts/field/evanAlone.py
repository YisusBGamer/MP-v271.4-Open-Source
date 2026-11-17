# Evan Intro | Dream World: Dream Forest Entrance (900010000)
# Tutorial skipper snippet
from net.swordie.ms.client.character import PlayStyleModule


def skip_tutorial():
    MAPLE_ADMINISTARTOR = 2007

    quests_to_complete = [
        22000,  # Strange Dream
        22001,  # Feeding Bull Dog
        22002,  # Sandwich for Breakfast
        22003,  # Delivering the Lunch Box
        22004,  # Fixing the Fence
        22005,  # Rescuing the Piglet
        22006,  # Returning the Empty Lunch Box
        22007,  # Collecting Eggs
        22008,  # Chasing away the Foxes
        22009,  # Verifying the Farm Situation
        22010,  # Strange Farm
        22130,  # Dragon Master 1st Job Advancement (the job below)
    ]

    map_to_warp = 100030102  # Front Yard
    target_level = 10

    sm.setSpeakerID(MAPLE_ADMINISTARTOR)
    sm.removeEscapeButton()

    if sm.askTutorialSkip(map_to_warp):
        if sm.getChr().getLevel() < target_level:
            sm.addLevel(target_level - sm.getChr().getLevel())

        for quest in quests_to_complete:
            sm.completeQuestNoRewards(quest)

        sm.jobAdvance(2200)  # Evan 1st job (v232 id; 2210 was the old one)
        sm.giveItem(1372107)  # Beginner Magician's Wand
        sm.addSP(5)
        sm.setSTR(4)
        sm.setINT(4)
        sm.setDEX(4)
        sm.setLUK(4)
        sm.setAP(4 + chr.getLevel() * 5)
        sm.warp(map_to_warp)
        # sm.addSP(3, True)

    sm.createQuestWithQRValue(22011, "noskip")
    sm.dispose()


if sm.getQRValue(22011) != "noskip" or PlayStyleModule.isSkipRun(chr):
    skip_tutorial()
