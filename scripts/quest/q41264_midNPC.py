# Mirror World side quest (Zero; Swordie Plus). Childhood Promises (41264): Russellon receives Bedin's experiments - and remembers
sm.setSpeakerID(2460005)
if sm.hasItem(4033854) and not sm.hasItem(4033855):
    sm.sendNext("Bedin's experiments...? Of course I remember our promise. Take my report back to him.")
    sm.consumeItem(4033854, 1)
    sm.giveItem(4033855)
elif sm.hasItem(4033855):
    sm.sendSayOkay("Give Bedin my report.")
else:
    sm.sendSayOkay("Bedin sent you? Then where are his experiments?")
sm.dispose()
