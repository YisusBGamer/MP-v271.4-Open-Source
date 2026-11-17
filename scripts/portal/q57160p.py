# 807050502 Honnou-ji Temple Plaza, in00 -> 807050503 Honnou-ji Temple (The Demon's Lair 3: Takigawa Kazumasu;
# Hayato 57160, Kanna 57456). Kanna's copy of the quest (57456) is accepted too.
if sm.hasQuest(57160) or sm.hasQuest(57456):
    sm.warpInstanceIn(807050503, 0)
elif sm.hasQuestCompleted(57160) or sm.hasQuestCompleted(57456):
    sm.chat("Honnou-ji Temple is quiet now.")
else:
    sm.chat("Takigawa Kazumasu is hiding inside. Talk to Takeda Shingen first.")
sm.dispose()
