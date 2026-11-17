# 807020100 Momijigahara 2, north00: the road to Honnou-ji (Hayato/Kanna story).
# The Eastern Wall (807050200) for the infiltration and Hideyoshi quests, the Southern Gates (807050500) for the final
# assault (Alliance Assemble and later). Hayato's quests are 57140-57160, Kanna's copies 57436-57456.
# Kanna's copies are accepted for the Hideyoshi meeting and the final assault too.
def had(*qs):
    return any(sm.hasHadQuest(q) for q in qs)


if had(57157, 57156, 57453, 57452):
    sm.warp(807050500, 1)  # west00
elif any(sm.hasQuest(q) for q in (57153, 57154, 57449, 57450)) or \
        ((sm.hasQuestCompleted(57153) and not had(57155)) or (sm.hasQuestCompleted(57449) and not had(57451))):
    # Hideyoshi's meeting (field/honnouji_east_wall): a copy of the map of its own
    sm.warpInstanceIn(807050200, 1)
elif had(57140, 57436):
    sm.warp(807050200, 1)  # in00
else:
    sm.chat("Honnou-ji is crawling with Oda's soldiers. It's too dangerous to go there now.")
sm.dispose()
