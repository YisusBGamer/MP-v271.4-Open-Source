# Ranking Board (9076004) | Cross World party quest waiting rooms: your clears
from net.swordie.ms.world.field.instance.instancehelper.pq import PartyQuestRecords as PR

sm.setSpeakerID(9076004)
NAMES = [("moon_bunny", "Moon Bunny's Rice Cake"), ("first_time_together", "First Time Together"),
         ("poison_haze", "Forest of Poison Haze"), ("goddess", "Remnant of the Goddess")]
text = "#e<Your Cross World Party Quest Record>#n\r\n\r\n"
for key, name in NAMES:
    text += name + ": #b" + str(PR.getClears(chr, key)) + "#k clear(s)\r\n"
text += "\r\nParty Points: #b" + str(PR.getPartyPoints(chr)) + "#k"
sm.sendSayOkay(text)
