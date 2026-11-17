# Xuanshan School (875000000), Mo Xuan's home. Shang Xuan (9401282, Return to Xuanshan 65920) and Feng (9401343, In
# the Shadows of Jianghu 65970) are quest NPCs that no v265 map places: they stand here for those quests.
SHANG_XUAN = 9401282
FENG = 9401343

present = [n.getTemplateId() for n in field.getNpcs()]
if (sm.hasQuest(65920) or not sm.hasQuestCompleted(65920)) and SHANG_XUAN not in present:
    sm.spawnNpc(SHANG_XUAN, -260, 333)
if (sm.hasQuest(65970) or (sm.hasQuestCompleted(65969) and not sm.hasQuestCompleted(65970))) and FENG not in present:
    sm.spawnNpc(FENG, 140, 331)
