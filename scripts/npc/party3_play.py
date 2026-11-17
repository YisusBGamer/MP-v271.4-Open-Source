# Chamberlain Eak (2013001) | Remnant of the Goddess
from net.swordie.ms.world.field.instance.instancehelper.pq import GoddessInstanceHelper as GH
inst = chr.getInstance()
helper = inst.getInstanceHelper() if inst is not None else None
if not isinstance(helper, GH):
    helper = None

sm.setSpeakerID(2013001)
if helper is None:
    if sm.sendAskYesNo("Shall I send you back to the waiting room?"):
        sm.warp(GH.WAITING_ROOM, 0)
    sm.dispose()

fid = sm.getFieldID()
if fid == GH.LOUNGE:
    if helper.isStageCleared(1):
        sm.sendSayOkay("The music already played. Go back to the Center Tower!")
    else:
        menu = "This phonograph only plays the music that suits #btoday#k. Which LP should we play?\r\n\r\n#b"
        for i in range(len(GH.LPS)):
            menu += "#L" + str(i) + "##i" + str(GH.LPS[i]) + "# " + GH.LP_NAMES[i] + " Music#l\r\n"
        sm.sendSayOkay(helper.playLp(chr, sm.sendNext(menu)))
elif fid == GH.STORAGE:
    if helper.isLeader(chr):
        sm.sendSayOkay(helper.checkCats(chr))
    else:
        sm.sendSayOkay(helper.getCatOrder() + " Then have your party leader talk to me.")
elif fid == GH.WALKWAY:
    sm.sendSayOkay("Defeat all the pixies on the Walkway to find the 3rd piece.")
elif fid == GH.WAY_UP:
    sm.sendSayOkay("Only one platform on each floor leads up. Find the way to the top for the 4th piece!")
elif fid == GH.GARDEN:
    sm.sendSayOkay("Defeat the Nependeaths, and Papa Pixie will come out of hiding!")
elif fid == GH.CENTER:
    if helper.isLeader(chr):
        sm.sendSayOkay(helper.restoreStatue(chr))
    else:
        sm.sendSayOkay("Bring the 4 Goddess Statue Pieces to your party leader, then have them talk to me.")
else:
    if sm.sendAskYesNo("Do you want to give up and leave the tower?"):
        sm.warpInstanceOut(GH.WAITING_ROOM, 0)
