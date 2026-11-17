# All monster park mobs
from net.swordie.ms.constants import GameConstants
from net.swordie.ms.enums import WeatherEffNoticeType

#Exp based on mobTemplateId
exp = sm.getMPExpByMobId(mob.getTemplateId())

#Stores Exp from killing mobs
try:
    total = long(sm.getQRValue(GameConstants.MONSTER_PARK_EXP_QUEST))
except:
    total = 0  # no record yet (or not a number): start counting from here
total += exp
sm.setQRValue(GameConstants.MONSTER_PARK_EXP_QUEST, str(total))

#displays the EXP message
sm.showWeatherNoticeToField("EXP reward " + sm.formatNumber(str(total)) + " earned!", WeatherEffNoticeType.MonsterPark_ExpMsg)

# Stage cleared: the "Clear!" splash for everyone in the stage. It's a screen (field) effect from
# Map/Effect.img/monsterPark (like the Tower of Oz clear); the old user effect from Map002 didn't show.
if not sm.hasMobsInField():
    sm.showFieldEffect(GameConstants.MONSTER_PARK_CLEAR_EFFECT, 0)
