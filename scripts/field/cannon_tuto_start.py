# Coco Island : Somewhere in Coco Island (3000600) - where Cannoneers start; the map data has no enter script
# (CustomFieldScripts). The play style question, like every class tutorial's start (PlayStyleModule): Express skips
# the intro the same way cannon_tuto_01's skip does; Classic plays it (talk to Skipper).

# v265 wants Maple Island (36301-36307) done before Skipper's 2573; Cannoneers start here instead
MAPLE_ISLAND = (36301, 36302, 36303, 36304, 36306, 36307)

if sm.getChr().getJob() == 0:
    for q in MAPLE_ISLAND:
        if not sm.hasQuestCompleted(q):
            sm.completeQuestNoRewards(q)

if sm.getChr().getLevel() < 10 and not sm.hasQuestCompleted(2568):
    sm.setSpeakerID(2007)
    sm.removeEscapeButton()
    if sm.askTutorialSkip(912060500):
        sm.completeQuestNoRewards(2568)
        if sm.getChr().getLevel() < 10:
            sm.addLevel(10 - sm.getChr().getLevel())
        sm.warp(912060500)
sm.dispose()
