# Legion Growth Potion (Lv. 130-159): 49% of the EXP to the next level and a 2x EXP buff for 30 minutes
from net.swordie.ms.constants import GameConstants
from net.swordie.ms.client.character.skills.temp import CharacterTemporaryStat

if 130 <= chr.getLevel() <= 159:
    sm.giveExpNoAffectedByExpRate(GameConstants.charExp[chr.getLevel()] * 49 / 100)
    # an item's ExpBuffRate is the total rate: 200 = 2x
    sm.giveCTS(CharacterTemporaryStat.ExpBuffRate, 200, -2436618, 1800)
    sm.consumeItem(2436618)
else:
    sm.chat("Unable to use this item on your level")
