# Manager Mush (9001044), Piggy Bar lobby: starts a Piggy Bar game (Red team against Blue team, three rounds).
GAME = 910194101

sel = sm.sendNext("Sweet, sweet maple! It's festival time! Want to play the #bPiggy Bar#k minigame?\r\n"
                  "#L0##bStart a game#k#l\r\n#L1#How do I play?#l\r\n#L2#Leave the festival#l")
if sel == 0:
    if chr.getParty() is not None and not sm.isPartyLeader():
        sm.sendSayOkay("Your party leader has to start the game.")
    else:
        sm.warpInstanceIn(GAME, 0, True)
elif sel == 1:
    sm.sendSayOkay("Players are split into the #rRed#k and #bBlue#k teams. Each round, work your side's machines "
                   "(buttons, then levers, then cannons) to fill your team's gauge. The first team to fill it, or "
                   "the fuller one when time runs out, wins the round. Stay on your side: the other team's area "
                   "sends you back!\r\n\r\nAfter three rounds, come see me in the reward room. Winners get more. "
                   "Playing alone? The house plays the other team.")
elif sel == 2:
    sm.warp(chr.getPreviousFieldID() if chr.getPreviousFieldID() not in (0, 910194100) else 100000000)
sm.dispose()
