# Spiegelette (9076000) | Spiegelmann's Guest House
from net.swordie.ms.world.field.instance.instancehelper.pq import PartyQuestRecords as PR

sm.setSpeakerID(9076000)
sm.sendSayOkay("Welcome to Spiegelmann's Guest House! From here you can reach all kinds of party quests.\r\n\r\n"
               "Clearing a #bCross World Party Quest#k (Moon Bunny's Rice Cake, First Time Together, Forest of Poison "
               "Haze or Remnant of the Goddess) earns #b10 Party Points#k. Spend them at the #bPub#k.\r\n\r\n"
               "You have #b" + str(PR.getPartyPoints(chr)) + "#k Party Points.")
