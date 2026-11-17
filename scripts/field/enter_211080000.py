# Gardener's Spot (211080000), Rose Garden: Cecilia hands out the dailies here. On a new day, yesterday's score and
# Stage 2/3 unlock (record 31035 "score" / "x") are cleared, unless a daily is still in progress.
SCORE_RECORD = 31035
DAILIES = (31044, 31045, 31046)

if sm.hasQuestCompleted(31043) and not any(sm.hasQuest(q) for q in DAILIES):
    today = sm.getCurrentDateAsString()
    day = sm.getQRValue(SCORE_RECORD, "d")
    if day != today and (sm.getQRValue(SCORE_RECORD, "score") or sm.getQRValue(SCORE_RECORD, "x")):
        sm.setQRValue(SCORE_RECORD, "score", "0")
        sm.setQRValue(SCORE_RECORD, "x", "0")
        sm.setQRValue(SCORE_RECORD, "d", today)
sm.dispose()
