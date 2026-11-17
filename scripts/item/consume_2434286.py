# Unidentified Traits Growth Box (2434286, Mu Lung Dojo shop): raises one random trait (Ambition, Empathy, Insight,
# Willpower, Diligence or Charm) by 30.
from net.swordie.ms.enums import Stat

TRAITS = [(Stat.charismaEXP, "Ambition"), (Stat.senseEXP, "Empathy"), (Stat.insightEXP, "Insight"),
          (Stat.willEXP, "Willpower"), (Stat.craftEXP, "Diligence"), (Stat.charmEXP, "Charm")]

stat, name = TRAITS[sm.getRandomIntBelow(len(TRAITS))]
sm.consumeItem()
chr.addTraitExp(stat, 30)
sm.chat("Your %s went up by 30." % name)
sm.dispose()
