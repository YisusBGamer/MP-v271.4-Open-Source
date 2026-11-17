# 927020220 Black Mage's Temple, at the end of the Gate of the Present (Explorer story).
# The Black Mage (32309) is handed in once all five Seals of Time are checked (record 32309 = 11111, one digit per
# seal, set by seal_Adventure1-5).
SEALS = ((1520022, -600), (1520023, -300), (1520024, 300), (1520025, 600), (1520026, 900))

if sm.hasQuest(32308):
    sm.completeQuest(32308)  # "The corridor lead to a temple shrouded in darkness!"
if sm.hasQuest(32309) or (sm.hasQuestCompleted(32308) and not sm.hasQuestCompleted(32309)):
    sm.spawnNpcIfAbsent(1520029, 0, -308)
    for npc, x in SEALS:
        sm.spawnNpcIfAbsent(npc, x, -80)
    if sm.hasQuest(32309):
        sm.chatScript("Check on the seals, but take care not to wake him.")
    else:
        sm.chatScript("Start 'The Black Mage' from the quest window, then check on the 5 seals.")
