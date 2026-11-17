# Masarayu (9070100) | Ursus (Entry Map)
from net.swordie.ms.world.field.instance.instancehelper.events import UrsusInstanceHelper as UH

sm.setSpeakerID(9070100)
sel = sm.sendNext("#e<Ursus the Mighty>#n\r\n\r\nOverthrow Ursus and win my gold!\r\n\r\n"
                  "#b#L0#Fight Ursus.#l\r\n#L1#How many more times can I fight today?#l\r\n#L2#Tell me more.#l")
if sel == 0:
    blocker = UH.getEntryBlocker(chr)
    if blocker is not None:
        sm.sendSayOkay(blocker)
    else:
        UH.recordEntries(chr)
        sm.warpInstanceIn(UH.BATTLE_MAP, 0, chr.getParty() is not None)
elif sel == 1:
    sm.sendSayOkay("You can fight Ursus #b" + str(UH.getRemainingEntries(chr)) + "#k more time(s) today.")
elif sel == 2:
    sm.sendSayOkay("#e<Ursus the Mighty>#n\r\n#e-Level#n: " + str(UH.MIN_LEVEL) + " or above\r\n"
                   "#e-Players#n: 1 to " + str(UH.MAX_PARTY) + "\r\n#e-Clears#n: " + str(UH.DAILY_ENTRIES) + " per day\r\n"
                   "#e-Lives#n: " + str(UH.LIVES) + " shared by the group\r\n\r\nUrsus drops no items: you get Mesos by "
                   "your level and how fast you win (rank SSS to E). During #bGolden Time#k (1-5 AM and 6-10 PM server time) "
                   "the Mesos are doubled." + ("\r\n\r\n#rIt's Golden Time right now!#k" if UH.isGoldenTime() else ""))
