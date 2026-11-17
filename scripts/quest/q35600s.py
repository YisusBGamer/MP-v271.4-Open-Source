# [Moonbridge] Save the World (35600): back to where the Moonbridge prelude left off (the scene of the next quest of
# 35605-35611; v265 dropped 35602-35604 and 35606, and Maple World Right Now (35607) is now a talk in the Outpost).
# The quest itself is completed with Tenebris Expedition, to War! (35611).
SCENES = ((35605, 993060011), (35607, 993060020), (35608, 993060020), (35609, 993060030), (35610, 993060030),
          (35611, 993060074))
dest = 0
for q, m in SCENES:
    if not sm.hasQuestCompleted(q):
        dest = m
        if q == 35608 and sm.hasQuest(35608):
            dest = 993060021  # Cygnus, on the White Spear
        break
sm.setSpeakerID(3003650)
if dest == 0:
    sm.sendSayOkay("The Tenebris Expedition has already set out!")
elif sm.sendAskYesNo("The Alliance is waiting for you! Shall we go back?"):
    if sm.getChr().getInstance() is not None:
        sm.warp(dest, 0)
    else:
        sm.warpInstanceIn(dest, 0)
sm.dispose()
