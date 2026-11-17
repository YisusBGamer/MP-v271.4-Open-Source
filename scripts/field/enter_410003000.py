#Hotel Arcus Saloon
# Characters who finished 'Til We Meet Again (38120) before quest/done_38120 gave its medal: the 'Slow and Steady'
# medal (1143244) and its title quest (38121), which Karote and Odium need.
if sm.hasQuestCompleted(38120) and not sm.hasQuestCompleted(38121):
    if not sm.hasItem(1143244) and sm.canHold(1143244):
        sm.giveItem(1143244)
    sm.completeQuestNoRewards(38121)
