# Sir Hamselot (3006973), Staging Grounds (993265200): start the raid early, or leave
from net.swordie.ms.client.character.union import ChampionRaid

sm.setSpeakerID(3006973)
choice = sm.sendNext("Are your Champions ready?\r\n"
                     "#b#L0#Start the raid now#l\r\n"
                     "#L1#Leave Draco Isle#l")
if choice == 0:
    ChampionRaid.startNow(chr)
elif choice == 1 and sm.sendAskYesNo("Leave without fighting?"):
    sm.warpInstanceOut(ChampionRaid.EXIT, 0)
sm.dispose()
