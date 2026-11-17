# Fantasy Theme World (Dylan & Delilah, 31363-31392). Official lines where the client has them; otherwise written from
# the quests' Quest.wz summaries / the NPCs' Npc.wz lines. Data: ftwgen/gen_ftw.py.
# Ghostacular Mansion (222110110): every ghost defeated - Ghosts and Ghoulies cleared (31369's record 31388 state=1,
# which Oliver checks). The clear effect and sound are the ones the server's Ghost Sniper game uses.
if sm.hasQuest(31369):
    sm.setQRValue(31388, "state", "1")
sm.showFieldEffect("monsterPark/clear", 0)
sm.playSound("Party1/Clear", 100)
user = sm.getChr()  # not the chr binding
gun = user.getGun()
if gun is not None:
    gun.destroyGunToChar(user)
sm.invokeAfterDelay(2000, "warpInstanceOut", 222110100, 3)
