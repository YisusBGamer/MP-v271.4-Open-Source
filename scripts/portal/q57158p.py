# 807050500 Honnou-ji Southern Gates, in00 -> 807050501 Southern Grounds (The Demon's Lair 1: Hayato 57158, Kanna 57454)
# Kanna's copy of the quest (57454) is accepted too.
if sm.hasHadQuest(57158) or sm.hasHadQuest(57454):
    sm.warp(807050501, 1)
else:
    sm.chat("The gates are shut. Talk to Takeda Shingen first.")
sm.dispose()
