# Chosen Seren's maps, all three modes (BossConstants.SEREN_FIELD_BASE): base + 20 phase 1, + 60 phase 2 (starting at
# Midday; SerenInstanceHelper turns her through Midday, Dusk, Twilight and Dawn), + 80 the treasure chest.
from net.swordie.ms.constants import BossConstants

fieldId = sm.getFieldID()
mode = BossConstants.getSerenMode(fieldId)
if mode >= 0 and sm.getChr() is not None and sm.getChr().getInstance() is not None:
    if fieldId == BossConstants.serenP1Field(mode):
        sm.spawnMob(BossConstants.serenMob(mode, BossConstants.SEREN_P1), 200, 275, False, BossConstants.SEREN_HP[mode][0])
    elif fieldId == BossConstants.serenP2Field(mode):
        sm.spawnMob(BossConstants.serenMob(mode, BossConstants.SEREN_MIDDAY), 100, 305, False, BossConstants.SEREN_HP[mode][1])
    elif fieldId == BossConstants.serenRewardField(mode):
        sm.spawnMob(BossConstants.serenMob(mode, BossConstants.SEREN_TREASURE_CHEST), 200, 275, False)
