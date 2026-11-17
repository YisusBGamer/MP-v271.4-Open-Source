#Arkarium Ban Map out | Sinister Inner Lot
from net.swordie.ms.constants import BossConstants
if field.hasProperty("killed"):
    if field.getProperty("killed") == True:
        # 272020500 is Normal's prison in GMS, 272020300 Easy's
        if sm.getFieldID() == 272020500:
            sm.warp(BossConstants.ARKARIUM_NORMAL_FIELD)
        else:
            sm.warp(BossConstants.ARKARIUM_EASY_FIELD)
