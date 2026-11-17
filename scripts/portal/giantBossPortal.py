# Gollux portals
from net.swordie.ms.constants import BossConstants

fields = {
    # Main map id : main portal id : [to field id, to field portal]
    863010100 : {
        3 : [863010300, 2],
        6 : [863010300, 2],
        7 : [863010400, 2],
        8 : [863010200, 1],
        9 : [863010220, 1],
    },
    863010200 : {
        0 : [863010100, 2],
        1 : [863010100, 8],
        2 : [863010210, 1],
    },
    863010210 : {
        1 : [863010200, 2],
        2 : [863010240, 2],
    },
    863010220 : {
        1 : [863010100, 9],
        2 : [863010230, 1],
    },
    863010230 : {
        1 : [863010220, 2],
        2 : [863010240, 1],
    },
    863010240 : {
        1 : [863010230, 2],
        2 : [863010210, 2],
        3 : [863010500, 2]
    },
    863010300 :{
        1 : [863010310, 1],
        2 : [863010100, 6],
    },
    863010310 : {
        1 : [863010300, 1],
        2 : [863010320, 2],
    },
    863010320 : {
        1 : [863010500, 1],
        2 : [863010310, 2],
        3 : [863010330, 1],
    },
    863010330 : {
        1 : [863010320, 3],
        3 : [863010500, 4]
    },
    863010400 : {
        1 : [863010410, 1],
        2 : [863010100, 7],
    },
    863010410 : {
        1 : [863010400, 1],
        2 : [863010420, 1],
    },
    863010420 : {
        1 : [863010410, 2],
        2 : [863010500, 5],
        3 : [863010430, 1],
    },
    863010430 : {
        1 : [863010420, 3],
        3 : [863010500, 6]
    },
    863010500 : {
        1 : [863010320, 1],
        2 : [863010240, 3],
        3 : [863010600, 2],
        4 : [863010330, 3],
        5 : [863010420, 2],
        6 : [863010430, 3],
    },
    863010600 : {
        2 : [863010700, 3],
    },
}

fieldID = sm.getFieldID()


def is_cleared(fid):
    # a map of this Gollux battle whose monsters (or part) are defeated
    f = chr.getInstance().getField(fid) if chr.getInstance() is not None else None
    return f is not None and f.hasProperty(BossConstants.GOLLUX_FINISHED_KEY) and f.getProperty(BossConstants.GOLLUX_FINISHED_KEY)


if fieldID is None or fieldID not in fields:
    sm.chat("This portal (giantBossPortal.py) is not yet coded for this map (" + str(fieldID) + ")")
else:
    innerDict = fields[fieldID]
    if parentID not in innerDict:
        sm.chat("This portal (giantBossPortal, " + str(parentID) + ") is not yet coded for this map (" + str(fieldID) + ")")
    else:
        toField = innerDict[parentID][0]
        toPortal = innerDict[parentID][1]
        if fieldID == 863010600:
            # the Head: out only once it's defeated (to the Heart, for the reward)
            if is_cleared(fieldID):
                sm.warp(toField)
            else:
                sm.chat("Kill the boss first.")
        elif fieldID in (863010100, 863010500) or is_cleared(fieldID):
            sm.warp(toField, toPortal)
        elif toField in (863010100, 863010500) or is_cleared(toField):
            # back the way you came (the road, the Heart hub, or a map already cleared) is always open, so nobody
            # gets shut in a map they can't clear
            sm.warp(toField, toPortal)
        else:
            sm.chat("Defeat all monsters first. (You can always go back the way you came.)")
