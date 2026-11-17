# 240010102 -> 915020200 Leafre Treasure Vault Entrance (Phantom 4th job)
if chr.getJob() == 2411 and chr.getLevel() >= 100:
    sm.warpInstanceIn(915020200, 1)
elif 2500 <= chr.getJob() <= 2512 and (sm.hasQuest(38053) or sm.hasQuest(38054)
                                       or sm.hasQuestCompleted(38053) and not sm.hasQuestCompleted(38054)):
    # Shade: Phantom's Last Hideaway / Phantom Reunion
    sm.warpInstanceIn(915020200, 1)
else:
    sm.chat("Only Phantoms looking to grow can enter.")
sm.dispose()
