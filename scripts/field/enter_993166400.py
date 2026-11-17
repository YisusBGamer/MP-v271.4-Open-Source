# Forgotten Home (993166400): the Cygnus Knight's story begins (the v265 tutorial, 36451-36467). Express play skips it
# to Ereve at level 10, as the old tutorial did; otherwise on to the forest path where Kimu waits.
TUTORIAL_QUESTS = (36451, 36452, 36453, 36454, 36455, 36456, 36457, 36458, 36459, 36460, 36461, 36462, 36463,
                   36465, 36466, 36467)
NOBLESSE_SKILLS = (10001245, 10001244, 10001253, 10001254)
EREVE = 130000000
TARGET_LEVEL = 10

for skill_id in NOBLESSE_SKILLS:
    if not sm.hasSkill(skill_id):
        sm.giveSkill(skill_id)

sm.setSpeakerID(2007)
sm.removeEscapeButton()
if sm.askTutorialSkip(EREVE):
    if chr.getLevel() < TARGET_LEVEL:
        sm.addLevel(TARGET_LEVEL - chr.getLevel())
    for quest in TUTORIAL_QUESTS:
        if not sm.hasQuestCompleted(quest):
            sm.completeQuestNoRewards(quest)
    sm.warp(EREVE)
else:
    # the 2022 Cygnus story art (Effect/Direction29.img/cygnus_2022)
    sm.showEffect("Effect/Direction29.img/cygnus_2022/illust/0/0")
    sm.chatScript("You set out from your forgotten home to become a Knight of Cygnus.")
    sm.invokeAfterDelay(4000, "warp", 993166401, 0)
sm.dispose()
