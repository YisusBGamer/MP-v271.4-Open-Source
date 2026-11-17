# [Fox Valley] Grow, Tree, Grow! (34754), end - the Frombrella Tree
# Using the fertilizer on the tree hands the quest in and uses it up (the quest's rewards don't take it). Written from
# the quest's own Quest.wz summary ("Grow, tree, grow!").
MARU = 3002023
FERTILIZER = 4036318  # Grow-a-Bunch Fertilizer

if sm.hasQuest(parentID) and sm.hasItem(FERTILIZER):
    sm.setSpeakerID(MARU)
    sm.sendNext('Grow, tree, grow!')
    sm.setPlayerAsSpeaker()
    sm.sendSay('(The tiny tree shoots up like a rocket the moment Maru starts chanting and pouring the fertilizer on it!)')
    if sm.completeQuest(parentID):  # needs the fertilizer in hand, so it's used up after
        sm.consumeItem(FERTILIZER, 1)
elif sm.hasQuest(parentID):
    sm.setSpeakerID(MARU)
    sm.sendSayOkay("What happened to the #b#i4036318# #t4036318##k? We need that to grow the tree!\r\n#r *You need to give up the quest and accept it again.#k")
sm.dispose()
