# Tutorial skipper snippet
MAPLE_ADMINISTARTOR = 2007

quests_to_complete = [
        24000,
        24001,
        24002,
        24003,
        24004,
        24005
    ]
skills_to_add = [
	    20020112,
	    20020111,
	    20020109,
	]
map_to_warp = 101050000 # Elluel
target_level = 10

sm.setSpeakerID(MAPLE_ADMINISTARTOR)
sm.removeEscapeButton()
sm.lockInGameUI(True)

if sm.askTutorialSkip(map_to_warp):
    if sm.getChr().getLevel() < target_level:
        sm.addLevel(target_level - sm.getChr().getLevel())

    for quest in quests_to_complete:
        sm.completeQuestNoRewards(quest)

    for skillID in skills_to_add:
        sm.giveSkill(skillID)
    sm.lockInGameUI(True)
    sm.levelUntil(10)  # (Express skips the opening video - this one was Phantom's anyway)

    sm.setJob(2300)
    sm.setSTR(4)
    sm.setINT(4)
    sm.setDEX(4)
    sm.setLUK(4)
    sm.setAP(4 + chr.getLevel() * 5)
    sm.addMaxHP(150)
    sm.addMaxMP(50)
    sm.giveItem(2000019, 50)
    # the record leaving the King's Seat sets (EntereurelTW): Awakening (24040) needs it
    sm.createQuestWithQRValue(24009, "1", False)
    sm.lockInGameUI(False)
    sm.warp(map_to_warp)
else:
    sm.lockInGameUI(False)
