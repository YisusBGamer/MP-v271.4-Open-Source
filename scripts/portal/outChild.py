# Portal script outChild.
# Aran tutorial, Dead End Forest: once you've found the lost child (Find the Missing Kid 2, 21001) the path leads on
# toward the Ark; before that it goes back into the forest.
if sm.hasQuest(21001) or sm.hasQuestCompleted(21001):
    sm.warp(914000400, 2)
else:
    if not sm.hasHadQuest(21000):
        sm.chat("Go back to Athena Pierce at the camp: she has a request for you.")
    sm.warp(914000220, 2)
sm.dispose()
