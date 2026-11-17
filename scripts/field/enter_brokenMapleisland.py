# 2100000 (Southperry, after the disaster) - Explorer story NPCs the story shows here (no map data places them):
# Rondo with the tools (32351-32352), the Black Mage's Shadow (32355-32356) and the Goddess (32356-32359)
if sm.hasQuest(32351) or sm.hasQuest(32352) or sm.hasQuestCompleted(32351) and not sm.hasQuestCompleted(32352):
    sm.spawnNpcIfAbsent(1520049, 600, 527)
if sm.hasQuest(32355) or sm.hasQuest(32356) or sm.hasQuestCompleted(32354) and not sm.hasQuestCompleted(32356):
    sm.spawnNpcIfAbsent(1520029, 1100, 527)
if sm.hasQuest(32356) or sm.hasQuestCompleted(32356) and not sm.hasQuestCompleted(32358):
    sm.spawnNpcIfAbsent(1520058, 1250, 527)
if sm.hasQuestCompleted(32357) and not sm.hasQuestCompleted(32359):
    sm.spawnNpcIfAbsent(1520059, 1400, 527)
