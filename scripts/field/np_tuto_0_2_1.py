# Jett tutorial | 552000021 Key Keeper's Room: beat the Key Keeper, then take the master key from its container
# (53247 ends at the Master Key Container)
KEY_KEEPER = 9420567
KEY_KEEPER_HP = 150 # the data's 15,000,000 is beyond a level 1 character

quest = chr.getQuestManager().getQuestById(53247)
if sm.hasQuestCompleted(53247):
    sm.warp(552000022)
elif quest is not None and not quest.isComplete(chr) and not sm.hasMobsInField():
    sm.spawnMob(KEY_KEEPER, 0, -120, False, KEY_KEEPER_HP)
