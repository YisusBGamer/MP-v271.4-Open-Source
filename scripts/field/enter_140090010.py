# Snow Island Ice Cave (140090010): Aran wakes up here (The Return of the Hero, 36901). v271's Aran starts here, so
# the tutorial skip (SKIP/PLAY, Express) is offered here as in aranTutorAlone.
from net.swordie.ms.client.character import PlayStyleModule

COMBAT_STEP = 20001295
MAPLE_ADMINISTRATOR = 2007
QUESTS_TO_COMPLETE = [21000, 21001, 21010, 21011, 21012, 21014, 21015, 21016, 21017, 21018, 21100, 36901]
MAP_TO_WARP = 140000000  # Rien
TARGET_LEVEL = 10

if not sm.hasQuestCompleted(36901) and sm.getQRValue(21019) != "noskip":
    sm.setSpeakerID(MAPLE_ADMINISTRATOR)
    sm.removeEscapeButton()
    sm.lockInGameUI(True)
    if sm.askTutorialSkip(MAP_TO_WARP):
        if sm.getChr().getLevel() < TARGET_LEVEL:
            sm.addLevel(TARGET_LEVEL - sm.getChr().getLevel())
        for quest in QUESTS_TO_COMPLETE:
            sm.completeQuestNoRewards(quest)
        sm.giveSkill(COMBAT_STEP, 1, 1)
        sm.lockInGameUI(False)
        sm.warp(MAP_TO_WARP)
    else:
        sm.createQuestWithQRValue(21019, "noskip")
        sm.lockInGameUI(False)
        if not sm.hasQuest(36901):
            sm.chatScript("You wake up in a cold cave. Someone is standing nearby...")
