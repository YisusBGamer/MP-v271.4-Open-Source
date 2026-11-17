# Epic Dungeon exits: High Mountain's save zone (993241900) back to Cernium, the Angler Company's exit (993253200)
# back to Odium, Nightmare Paradise's exit (993271400) back to the Operations Center. Progress stays saved for the
# week; Selazar resumes it.
from net.swordie.ms.world.field.instance.instancehelper.epic import EpicDungeon as ED

dest = {993253200: ED.ANGLER_ENTRANCE, ED.NP_SAVE_ZONE: ED.NP_ENTRANCE}.get(chr.getFieldID(), ED.HM_ENTRANCE)
if chr.getInstance() is not None:
    sm.warpInstanceOut(dest, 0)
else:
    sm.warp(dest, 0)
sm.dispose()
