# id 2430872 (Moth-Eaten Scroll), Luminous' story
# "Inside the Scroll" (25540) is finished by reading this scroll (Quest.wz: the record of Aurora's Legacy 25539,
# read=1). The game data has no lines for it; what the scroll says comes from the quest's own text. The quest starts
# and finishes itself (selfStart, selfComplete), so reading the scroll does both.
INSIDE_THE_SCROLL = 25540
AURORAS_LEGACY = 25539
if sm.hasQuestCompleted(AURORAS_LEGACY) and not sm.hasQuest(INSIDE_THE_SCROLL) and not sm.hasQuestCompleted(INSIDE_THE_SCROLL):
    sm.startQuest(INSIDE_THE_SCROLL)
if sm.hasQuest(INSIDE_THE_SCROLL):
    sm.setQRValue(AURORAS_LEGACY, "read", "1")
    sm.setPlayerAsSpeaker()
    sm.sendNext("(The scroll mentions the Auroran sanctum of #bHarmony#k. Apparently you can go there even without being holy, but it doesn't say how.)")
    sm.completeQuest(INSIDE_THE_SCROLL)
else:
    sm.chat("Something important seems to be written on this old scroll.")
sm.dispose()
