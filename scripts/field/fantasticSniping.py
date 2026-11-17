# Fantasy Theme World (Dylan & Delilah, 31363-31392). Official lines where the client has them; otherwise written from
# the quests' Quest.wz summaries / the NPCs' Npc.wz lines. Data: ftwgen/gen_ftw.py.
# Ghostacular Mansion (222110110): Ghosts and Ghoulies - the shooting game (the map is the client's FPS mode: mouse
# click shoots, ESC exits; 90 seconds). Official text: "Use your small supply of otherworldly bullets to defeat all the
# flashing ghosts within the time limit." The ghosts are the mansion's own (Ghost, 8145002, the ones out front); the
# countdown pictures are the client's (fantasticThemePark/count). Beating them all: field/clear_222110110.
from net.swordie.ms.client.character import Gun
GHOST = 8145002
SPOTS = ((-300, 142), (-50, 142), (200, 142), (400, 142),
         (-300, -39), (-50, -39), (200, -39), (400, -39),
         (-300, -221), (-50, -221), (200, -221), (400, -221))
COUNT = "Map/Effect3.img/fantasticThemePark/count/%s"

sm.setInstanceTime(90, 222110100)
gun = Gun.getByName("shotgun")
user = sm.getChr()  # not the chr binding (it can belong to an earlier run)
user.setGun(gun)
gun.updateGunToChar(user)
gun.updateAmmoToChar(user)
# The countdown runs on timers: sendDelay only works inside a conversation, and in this map's enter script it would
# end the script at "three", before any ghost comes out.
for i, n in enumerate(("three", "two", "one")):
    sm.invokeAfterDelay(i * 1000, "onLayer", 0, "count", 0, -80, 12, COUNT % n, 4, True, -1, False)
    sm.invokeAfterDelay(i * 1000 + 950, "offLayer", 0, "count", False)
if not sm.hasMobsInField(222110110, GHOST):
    for x, y in SPOTS:
        sm.invokeAfterDelay(3000, "spawnMob", GHOST, x, y, False)
