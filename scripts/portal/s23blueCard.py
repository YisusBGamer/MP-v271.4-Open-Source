# Blue Seal in Floor 2 of Oz Tower
# Undersea Tower of Oz : Undersea 2F
# 992002000
# PortalId: 6
from net.swordie.ms.client.partyquests.towerofoz import OzTowerModule
from net.swordie.ms.connection.packet import ReactorPool
from net.swordie.ms.connection.packet import WvsContext
from net.swordie.ms.constants import OzConstants
from net.swordie.ms.enums import WeatherEffNoticeType

instance = chr.getInstance()
field = chr.getField()
if instance is not None:

    if not instance.hasProperty("b"):

        reactor = field.getLifeByTemplateId(OzConstants.BLUE_REACTOR)

        if reactor.getState() < 7:
            colour = instance.getProperty(OzConstants.F2_CURRENT_CARD_COLOUR)
            number = instance.getProperty(OzConstants.F2_CURRENT_CARD_NUMBER)

            if ("v" in colour or "b" in colour) and number > int(reactor.getState()) + 1:

                instance.addProperty(OzConstants.F2_CURRENT_CARD_COLOUR, "")
                instance.addProperty(OzConstants.F2_CURRENT_CARD_NUMBER, 0)
                OzTowerModule.hideSealBreakerCard(instance)

                reactor.setState(number - 1)  # Finished
                instance.broadcast(ReactorPool.reactorChangeState(reactor, 0, 0))
                instance.addProperty("b", True)
                instance.addProperty(OzConstants.F2_SEALS_CLEARED, int(instance.getProperty(OzConstants.F2_SEALS_CLEARED)) + 1)
                if "v" in colour:
                    text = "You tricked the Blue  seal with a Violet Card!"
                else:
                    text = "That Blue {} card tricked the seal!".format(number)
                instance.broadcast(WvsContext.weatherEffectNotice(WeatherEffNoticeType.Oz_Alicia, text, 15000))

                if int(instance.getProperty(OzConstants.F2_SEALS_CLEARED)) >= 4:
                    instance.broadcast(WvsContext.weatherEffectNotice(WeatherEffNoticeType.Oz_Alicia, "You got them all! Now you can go to the next floor!", 15000))
                    OzTowerModule.clearStage(instance, chr)
            elif "v" not in colour and "b" not in colour:
                chr.write(WvsContext.weatherEffectNotice(WeatherEffNoticeType.Oz_Alicia, "Hey, you don't have a Blue card to use!", 5000))