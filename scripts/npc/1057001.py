# Ryden (1057001), Kerning City Back Alley. A Dual Blade beginner who finished Sugar's The Adventure Begins (36307)
# gets Thief of the Shade (2604). v265 blocks the old Secret Garden start (2600-2603).
RYDEN = 1057001
LADY_SYL_MAP = 103050101
MAPLE_ISLAND = (36301, 36302, 36303, 36304, 36306, 36307)

sm.setSpeakerID(RYDEN)
dual_blade_beginner = sm.getChr().getJob() == 0 and sm.getChr().getSubJob() == 1
if dual_blade_beginner and not sm.hasQuestCompleted(36307):
    # reached Victoria Island before the Maple Island start existed: Sugar's part is done here
    for q in MAPLE_ISLAND:
        if not sm.hasQuestCompleted(q):
            sm.completeQuestNoRewards(q)

if dual_blade_beginner and not sm.hasQuest(2604) and not sm.hasQuestCompleted(2604):
    sm.sendNext("So you're the one Sugar brought to Victoria Island. You have a knack for staying unseen... I've been watching.")
    sm.sendSay("I recommend you for a #bspecial mission#k. What it is, only #bLady Syl#k can tell you, IF she finds you worthy.")
    if sm.sendAskAccept("When you accept, I'll send you to Lady Syl."):
        sm.startQuest(2604)
        sm.warp(LADY_SYL_MAP, 0)
    else:
        sm.sendNext("Come back when you've made up your mind.")
elif dual_blade_beginner and sm.hasQuest(2604):
    if sm.sendAskYesNo("Lady Syl is waiting for you. Shall I take you to her?"):
        sm.warp(LADY_SYL_MAP, 0)
else:
    sm.sendSayOkay("Stay in the shadows, recruit.")
sm.dispose()
