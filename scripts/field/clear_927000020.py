# 927000020 - Demon Slayer's tutorial: the guards are down, Arkarium's fight comes next (as quest 23205's end at him)
SKILLS = [30010166, 30011167, 30011168, 30011169, 30011170]
ARKARIUM = 2159309

if sm.hasQuest(23205):
    sm.completeQuestNoRewards(23205)
    sm.deleteQuest(23205)
    for skill in SKILLS:
        if sm.hasSkill(skill):
            sm.removeSkill(skill)
    sm.removeNpc(ARKARIUM)
    sm.warpInstanceIn(927000070, 0)
