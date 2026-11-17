# The Chief Knights' path quests (20861-20865) need the newer tutorial's last quest (36467) done; this tutorial
# doesn't have it.
if chr.getJob() == 1000 and not sm.hasQuestCompleted(36467):
    sm.completeQuestNoRewards(36467)

# Tutorial skipper snippet (the play style question: Express skips, see PlayStyleModule)
def skip_tutorial():
    MAPLE_ADMINISTARTOR = 2007

    quests_to_complete = [
        20820, # The City of Ereve
        20821, # Knight's Orientation
        20822, # The Path of Bravery
        20823, # Question and Answer
        20824, # Knight's Cavalier
        20825, # Well-Behaved Student
        20826, # Lesson 1 - Ereve History
        20827, # What's Next?
        20828, # Lesson 2 - Physical Training
        20829, # Lesson 3 - Battle Basics 1
        20830, # A Much-Needed Break
        20831, # Lesson 3 - Battle Basics 2
        20832, # Lesson, Interrupted
        20833, # Tiny Bird
        20834, # The Tranquil Garden
        20835, # The Chief Knights
        20836, # Lesson, Resumed
        20837, # Lesson 5 - Skills
        20838, # Certified Knight
        20839, # Meeting with the Empress
    ]
    skills_to_add = [
        10001245,
        10001244,
        10001253,
        10001254,
    ]

    map_to_warp = 130000000 # Ereve
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
        sm.warp(map_to_warp)
        sm.lockInGameUI(False)
        sm.dispose()
    sm.lockInGameUI(False)

skip_tutorial()
sm.showEffect("Effect/OnUserEff.img/guideEffect/cygnusTutorial/0", 0, 0)
sm.invokeAfterDelay(5000, "showEffect", "Effect/OnUserEff.img/guideEffect/cygnusTutorial/1", 0, 0)