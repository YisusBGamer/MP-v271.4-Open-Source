# 807050501 Honnou-ji Southern Grounds, in00 -> 807050502 Temple Plaza (The Demon's Lair 2: Hayato 57159, Kanna 57455)
# Kanna's copy of the quest (57455) is accepted too.
if sm.hasHadQuest(57159) or sm.hasHadQuest(57455):
    sm.warp(807050502, 1)
else:
    sm.chat("Oda's soldiers still hold the grounds. Talk to Takeda Shingen first.")
sm.dispose()
