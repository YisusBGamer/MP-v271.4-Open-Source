# [Vanishing Journey] The Starting Point of the Arcane River (34175), Kima. Folding the boat is Folding a Paper Boat
# (34176), done right here.
KIMA = 3003110
FOLDING = 34176

sm.setSpeakerID(KIMA)
sm.sendNext("If that's your world, it must lie at the starting point of the Arcane River.")
sm.sendNext("Nothing goes up the river, but a #bpaper boat#k might. Write down your news and fold it into a boat. The calm water here might carry it upstream.")
if sm.sendAskAccept("Fold a paper boat and bring it to me."):
    sm.startQuest(parentID)
    if not sm.hasQuest(FOLDING) and not sm.hasQuestCompleted(FOLDING):
        sm.startQuestNoCheck(FOLDING)
    if sm.hasQuest(FOLDING):
        sm.setPlayerAsSpeaker()
        sm.sendNext("(You write down what you've learned about the Arcane River and fold the letter into a paper boat.)")
        sm.completeQuest(FOLDING)
sm.dispose()
