# [Fox Valley] Climb, Tree, Climb! (34756), end - the Frombrella Tree
# Using the fertilizer on the tree hands the quest in and uses it up (the quest's rewards don't take it). Written from
# the quest's own Quest.wz summary ("Climb, tree, climb!").
MARU = 3002023
FERTILIZER = 4036320  # Grow-a-Buncha-Bunch Fertilizer

if sm.hasQuest(parentID) and sm.hasItem(FERTILIZER):
    sm.setSpeakerID(MARU)
    sm.sendNext('Climb, tree, climb!')
    sm.setPlayerAsSpeaker()
    sm.sendSay('(The tree shoots up quicker than weeds in spring the moment Maru starts chanting and pouring the fertilizer on it!)')
    if sm.completeQuest(parentID):  # needs the fertilizer in hand, so it's used up after
        sm.consumeItem(FERTILIZER, 1)
elif sm.hasQuest(parentID):
    sm.setSpeakerID(MARU)
    sm.sendSayOkay("What happened to the #b#i4036320# #t4036320##k? We need that to grow the tree!\r\n#r *You need to give up the quest and accept it again.#k")
sm.dispose()
