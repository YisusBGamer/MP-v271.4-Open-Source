# Baetan (9100037), Ninja Castle: the way into the inner halls, and the way out of them
from net.swordie.ms.world.field.instance.instancehelper.pq import NinjaCastleInstanceHelper as NC
inst = chr.getInstance()
helper = inst.getInstanceHelper() if inst is not None else None
if not isinstance(helper, NC):
    helper = None

sm.setSpeakerID(9100037)
def enter():
    blocker = NC.getEntryBlocker(chr)
    if blocker is not None:
        sm.sendSayOkay(blocker)
        return
    NC.recordEntries(chr)
    sm.warpInstanceIn(NC.CORRIDORS[0], 0, True)

fid = sm.getFieldID()
if fid == NC.CASTLE_CORRIDOR:
    sel = sm.sendNext("The Daimyo shut himself up in the inner halls, and something about him isn't right.\r\n"
                      "#b#L0#Go into the inner halls.#l\r\n#L1#Tell me more.#l\r\n"
                      "#L2#Take me back to #m800040100#.#l")
    if sel == 0:
        enter()
    elif sel == 1:
        sm.sendSayOkay("#e<Ninja Castle: Inner Halls>#n\r\n#e-Level#n: " + str(NC.MIN_LEVEL) + " or above\r\n#e-Players#n: 1 to " + str(NC.MAX_PARTY) + " (the party leader leads the way in)\r\n#e-Time Limit#n: " + str(NC.TIME_LIMIT_SECONDS // 60) + " min\r\n#e-Entries#n: " + str(NC.DAILY_ENTRIES) + " per day\r\n\r\nFive long corridors, each with four chambers: Time, Battle, Traps and Wisdom. Clear one chamber and the corridor's upper door opens. Past them lie the Phantom's Rooms, where only one door in three leads on, and at the top, the Daimyo himself.")
    elif sel == 2:
        sm.warp(800040100, 5)  # in00, the corridor door
elif helper is None:
    if sm.sendAskYesNo("You shouldn't be here. Do you want to leave the castle?"):
        sm.warpInstanceOut(800040000, 0)
else:
    if NC.chamberKind(fid) == 0 and fid in list(NC.CORRIDORS):
        tip = "Clear any one of the four chambers on the floor and the upper door opens. Some walls hide passages."
    elif NC.PHANTOM_FIRST <= fid <= NC.PHANTOM_LAST:
        tip = "Phantom's Room " + str(helper.getMazeStep(chr)) + " of " + str(NC.MAZE_ROOMS) + \
              ". One door leads on; the others hide ninjas."
    elif fid == NC.BOUDOIR or fid == NC.BOUDOIR_B:
        tip = "The Daimyo's toads guard the Tower Room. Clear them and the door on the right opens."
    else:
        tip = "Keep going. The Daimyo is close."
    if sm.sendAskYesNo(tip + "\r\n\r\nOr have you had enough? #bLeave the castle#k? You can't come back into this run."):
        sm.warpInstanceOut(NC.REAR_CORRIDOR, 0)
sm.dispose()
