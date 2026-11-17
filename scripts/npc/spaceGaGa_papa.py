# Grandpa Moon Bunny (9001105): the Lunar World during the Moon Festival. His Moonlight Sonata quests open
# through holidayNpc.py; otherwise he grants the day's wish like Smartie Moon Bunny does in the towns.
from net.swordie.ms.world import HolidayEvents as H
from net.swordie.ms.world import SeasonalEvents

sm.setSpeakerID(9001105)
if H.moonGratitudeDue(chr):
    H.giveMoonGratitude(chr)
if not SeasonalEvents.isActive(H.MOON):
    sm.sendSayOkay("The moon is quiet tonight. Come and see an old rabbit again during the Moon Festival.")
elif sm.hasQuestCompleted(H.MUSIC_BOX):
    sm.sendSayOkay("Can you hear it? Baby Moon Bunny plays the music box every night now. The Moonlight Sonata is "
                   "back where it belongs. Thank you, #h0#.")
else:
    sm.sendNext("Ho ho... a visitor from Maple World. Welcome to our little moon. It's peaceful here, isn't it?")
    sm.sendSayOkay("If you'd like to make a wish under the full moon, #bSmartie Moon Bunny#k in the towns will take "
                   "care of it. And mind the rabbit hole on your way out!")
sm.dispose()
