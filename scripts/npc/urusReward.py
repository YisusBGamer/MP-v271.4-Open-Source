# Jawad (9070102) | Ursus (Entry Map)
from net.swordie.ms.world.field.instance.instancehelper.pq import PartyQuestRecords as PR
sm.setSpeakerID(9070102)
sm.sendSayOkay("I am Jawad, Masarayu's faithful agent. You have defeated Ursus #b" + str(PR.getClears(chr, "ursus")) +
               "#k time(s). Masarayu pays you in Mesos the moment Ursus falls.")
