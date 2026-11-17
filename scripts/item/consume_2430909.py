# Trait Boost Potion (2430909): 3,000 EXP to a random trait that isn't maxed out yet (an ending reward of
# Masque's Secret Stories).
import random
from net.swordie.ms.enums import Stat
from net.swordie.ms.constants import GameConstants
TRAITS = [(Stat.charismaEXP, "Ambition"), (Stat.insightEXP, "Insight"), (Stat.willEXP, "Willpower"),
          (Stat.craftEXP, "Diligence"), (Stat.senseEXP, "Empathy"), (Stat.charmEXP, "Charm")]
left = [t for t in TRAITS if chr.getStat(t[0]) < GameConstants.MAX_TRAIT_EXP]
if not left:
    sm.chatScript("All of your traits are already maxed out.")
else:
    stat, name = random.choice(left)
    sm.consumeItem(2430909, 1)
    chr.addTraitExp(stat, 3000)
    sm.chatScript("Your %s rose sharply!" % name)
sm.dispose()
